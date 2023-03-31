import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SplittableRandom;

public class Eindopdracht extends Application {

    static Canvas canvas;
    private FXGraphics2D g2d;
    private ArrayList<Planet> planets = new ArrayList<>();

    //Images
    private BufferedImage background;
    private BufferedImage sun;
    private BufferedImage earth;
    private BufferedImage mercury;
    private BufferedImage mars;
    private BufferedImage venus;
        @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

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

        primaryStage.setTitle("Planet Simulation");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }

    public void init() {
        try{
            System.out.println("---------------------");

            background = ImageIO.read(getClass().getResource("/images/background.png"));
            sun = ImageIO.read(getClass().getResource("/images/sun.png"));
            earth = ImageIO.read(getClass().getResource("/images/earth.png"));
            mercury = ImageIO.read(getClass().getResource("/images/mercury.png"));
            mars = ImageIO.read(getClass().getResource("/images/mars.png"));
            venus = ImageIO.read(getClass().getResource("/images/venus.png"));


            System.out.println("Images Loaded");

            Planet sun = new Planet(0,0,30,Color.YELLOW,1.98892 * Math.pow(10,30));

            Planet earth = new Planet(-1 * Planet.AU,0,16,Color.BLUE,5.9742 * Math.pow(10,24));
            earth.setYVel(29.783 * 1000);

            Planet mars = new Planet(1.524 * Planet.AU,0,12,Color.RED,6.39 * Math.pow(10,23));
            mars.setYVel(-24.077 * 1000);

            Planet mercury = new Planet(0.387 * Planet.AU,0,8,Color.DARK_GRAY,3.3 * Math.pow(10,23));
            mercury.setYVel(-47.4 * 1000);

            Planet venus = new Planet(0.723 * Planet.AU,0,14,Color.WHITE,4.8685 * Math.pow(10,24));
            venus.setYVel(-35.02 * 1000);

            sun.setSun(true);

            Collections.addAll(planets,sun,earth,mars,mercury,venus);
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    private void draw(FXGraphics2D graphics) {

        graphics.setPaint(new TexturePaint(background,new Rectangle2D.Double(0, 0,canvas.getWidth(), canvas.getHeight())));
        graphics.fillRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Planet planet : planets) {
            planet.draw(graphics);
        }
    }

    public void update(double deltaTime) {
        for (Planet planet : planets) {
            planet.update(planets);
        }
    }
}
