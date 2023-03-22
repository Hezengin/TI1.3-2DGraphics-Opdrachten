
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import sun.java2d.windows.GDIRenderer;

public class VerletEngine extends Application {

    private ResizableCanvas canvas;
    private ResizableCanvas canvas2;
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Constraint> constraints = new ArrayList<>();
    private PositionConstraint mouseConstraint = new PositionConstraint(null);
    private ArrayList<DistanceConstraint> distanceConstraints = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        BorderPane pane = new BorderPane();

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        canvas2 = new ResizableCanvas(g -> draw(g), pane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        //opgave 3 kleuren
        for (DistanceConstraint constraint : distanceConstraints) {
            double power = constraint.calcDistance();
            double distance = constraint.getDistance();
            double difference = power - distance;
            double force = Math.abs(difference);

            Color color;

            if (force == 0) {
                color = Color.GREEN;
            } else if (force < 10) {
                color = Color.YELLOW;
            } else if (force < 20) {
                color = Color.ORANGE;
            } else {
                color = Color.RED;
            }
            constraint.setColor(color);
        }

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        // Mouse Events
        canvas.setOnMouseClicked(e -> mouseClicked(e));
        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));


        Button doekButton = new Button("Make cloth");
        Button switchScene = new Button("Add a Cloth");
        HBox box = new HBox();
        box.getChildren().add(doekButton);

        pane.setTop(box);
        mainPane.setCenter(canvas2);

        Scene scene = new Scene(pane);

        switchScene.setOnMouseClicked(event -> {
            stage.setScene(scene);
        });

        doekButton.setOnMouseClicked(event -> {
            Canvas canvas = new Canvas();
            Point2D point = new Point2D.Double(100, 100);
            canvas.addPoint(point);
            Constraint constraint = new DistanceConstraint(point, new Point2D.Double(0, 0), 50);
            canvas.addConstraint(constraint);
            canvas.draw(g2d);
        });


        mainPane.setTop(switchScene);

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Verlet Engine");
        stage.show();
        draw(g2d);
    }

    public void init() {
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle(new Point2D.Double(100 + 50 * i, 100)));
        }

        for (int i = 0; i < 10; i++) {
            constraints.add(new DistanceConstraint(particles.get(i), particles.get(i + 1)));
            distanceConstraints.add(new DistanceConstraint(particles.get(i), particles.get(i + 1)));
        }

        constraints.add(new PositionConstraint(particles.get(10)));
        constraints.add(mouseConstraint);
        System.out.println(constraints.size());
        System.out.println(distanceConstraints.size());
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Constraint c : constraints) {
            c.draw(graphics);
        }

        for (Particle p : particles) {
            p.draw(graphics);
        }
    }

    private void update(double deltaTime) {
        for (Particle p : particles) {
            p.update((int) canvas.getWidth(), (int) canvas.getHeight());
        }

        for (Constraint c : constraints) {
            c.satisfy();
        }
    }

    private void mouseClicked(MouseEvent e) {
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        Particle newParticle = new Particle(mousePosition);
        particles.add(newParticle);
        constraints.add(new DistanceConstraint(newParticle, nearest));

        if (e.isControlDown() && e.getButton() == MouseButton.SECONDARY) {// CTRL + right
            System.out.println(e.getButton());

            ArrayList<Particle> sort = new ArrayList<>();
            sort.addAll(particles);

            Collections.sort(sort, (o1, o2) -> (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition)));
            DistanceConstraint constraint1 = new DistanceConstraint(newParticle, sort.get(1), 100);//eerste dichtsbijzijnde
            DistanceConstraint constraint2 = new DistanceConstraint(newParticle, sort.get(2), 100);//tweede dichtsbijzijnde
            distanceConstraints.add(constraint1);
            distanceConstraints.add(constraint2);

            constraints.add(constraint1);
            constraints.add(constraint2);
        } else if (e.isShiftDown() && e.getButton() == MouseButton.PRIMARY) {// SHIFT  + LEFT
            ArrayList<Particle> sorted = new ArrayList<>();
            sorted.addAll(particles);
            Collections.sort(sorted, (o1, o2) -> (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition)));

            DistanceConstraint constraint1 = new DistanceConstraint(newParticle, sorted.get(1));
            DistanceConstraint constraint2 = new DistanceConstraint(newParticle, sorted.get(2));
            DistanceConstraint constraint3 = new DistanceConstraint(newParticle, sorted.get(3));
            distanceConstraints.add(constraint1);
            distanceConstraints.add(constraint2);
            distanceConstraints.add(constraint3);

            constraints.add(constraint1);
            constraints.add(constraint2);
            constraints.add(constraint3);
        } else if (e.getButton() == MouseButton.SECONDARY) {
            ArrayList<Particle> sorted = new ArrayList<>();
            sorted.addAll(particles);

            //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
            Collections.sort(sorted, new Comparator<Particle>() {
                @Override
                public int compare(Particle o1, Particle o2) {
                    return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                }
            });

            constraints.add(new DistanceConstraint(newParticle, sorted.get(2)));
            distanceConstraints.add(new DistanceConstraint(newParticle, sorted.get(2)));
            System.out.println(constraints.size());
        } else if (e.getButton() == MouseButton.MIDDLE) {
            // Reset
            particles.clear();
            constraints.clear();
            distanceConstraints.clear();
            init();
        }
    }

    private Particle getNearest(Point2D point) {
        Particle nearest = particles.get(0);
        for (Particle p : particles) {
            if (p.getPosition().distance(point) < nearest.getPosition().distance(point)) {
                nearest = p;
            }
        }
        return nearest;
    }

    private void mousePressed(MouseEvent e) {
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        if (nearest.getPosition().distance(mousePosition) < 10) {
            mouseConstraint.setParticle(nearest);
        }
    }

    private void mouseReleased(MouseEvent e) {
        mouseConstraint.setParticle(null);
    }

    private void mouseDragged(MouseEvent e) {
        mouseConstraint.setFixedPosition(new Point2D.Double(e.getX(), e.getY()));
    }


    public static void main(String[] args) {
        launch(VerletEngine.class);
    }

}
