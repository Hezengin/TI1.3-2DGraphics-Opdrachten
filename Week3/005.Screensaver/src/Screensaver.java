import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    ArrayList<Point> points;
    ArrayList<Line2D.Double> lines;

    @Override
    public void start(Stage stage) throws Exception {
        points = new ArrayList<>();
        lines = new ArrayList<>();
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        points.add(new Point(10, 10, 1, -1));
        points.add(new Point(400, 20, -1, 1));
        points.add(new Point(830, 90, -1, -1));
        points.add(new Point(789, 598, 1, 1));

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        // Draw points and then add the point that is being drawn to the lines arraylist
        graphics.setColor(Color.BLUE);
        for (int i = 0; i < points.size() - 1; i++) {
            graphics.drawLine((int) points.get(i).getX() - 10, (int) points.get(i).getY() - 10, (int) points.get(i + 1).getX() - 10 , (int) points.get(i + 1).getY() - 10);
            lines.add(new Line2D.Double((int) points.get(i).getX() - 10, (int) points.get(i).getY() - 10, (int) points.get(i + 1).getX() - 10 , (int) points.get(i + 1).getY() - 10));
        }
        // drawing and adding the line between the last and first point in the points arraylist
        lines.add(new Line2D.Double((int)points.get(0).getX()-10,(int)points.get(0).getY() -10, (int)points.get(3).getX()-10 , (int)points.get(3).getY() -10));
        graphics.drawLine((int)points.get(0).getX()-10,(int)points.get(0).getY() -10, (int)points.get(3).getX()-10 , (int)points.get(3).getY() -10);

        // drawing the lines in lines arraylist and removing the element if the size is more than 1000
        Iterator<Line2D.Double> iterator = lines.iterator();
        while (iterator.hasNext()) {
            Line2D.Double line = iterator.next();
            if (lines.size() > 1000) {
                iterator.remove();
            } else {
                graphics.draw(line);
            }
        }
    }

    public void init() {

    }

    public void update(double deltaTime) {
        for (Point point : points) {
            point.x += point.xDirection * deltaTime * 100;
            point.y += point.yDirection * deltaTime * 100;

            // Check if point hits left or right edge of the screen
            if (point.x < 0 || point.x > canvas.getWidth()) {
                point.xDirection *= -1;
            }

            // Check if point hits top or bottom edge of the screen
            if (point.y < 0 || point.y > canvas.getHeight()) {
                point.yDirection *= -1;
            }
        }
    }

    public static void main(String[] args) {
        launch(Screensaver.class);
    }

}
