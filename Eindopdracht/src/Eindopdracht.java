import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

public class Eindopdracht extends Application {

    static Canvas canvas;
    private FXGraphics2D g2d;
    private ArrayList<Planet> planets = new ArrayList<>();

    //Images
    private BufferedImage background;
    private BufferedImage sunImage;
    private BufferedImage earthImage;
    private BufferedImage moonimage;
    private BufferedImage mercuryImage;
    private BufferedImage marsImage;
    private BufferedImage venusImage;
    private BufferedImage jupiterImage;
    private BufferedImage saturnImage;
    private BufferedImage uranusImage;
    private BufferedImage neptuneImage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new Canvas();
        canvas.setWidth(1920);
        canvas.setHeight(1080);
        mainPane.setCenter(canvas);
        g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        Camera camera = new Camera(canvas);
        canvas.setOnScroll(camera::scrollEvent);
        canvas.setOnMouseDragged(camera::mouseDragged);
        canvas.setOnMousePressed(camera::mousePressed);

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
        try {
            System.out.println("---------------------");

            background = ImageIO.read(getClass().getResource("/images/background.png"));
            sunImage = ImageIO.read(getClass().getResource("/images/sun.png"));
            earthImage = ImageIO.read(getClass().getResource("/images/earth.png"));
            moonimage = ImageIO.read(getClass().getResource("/images/moon.png"));
            mercuryImage = ImageIO.read(getClass().getResource("/images/mercury.png"));
            marsImage = ImageIO.read(getClass().getResource("/images/mars.png"));
            venusImage = ImageIO.read(getClass().getResource("/images/venus.png"));
            jupiterImage = ImageIO.read(getClass().getResource("/images/jupiter.png"));
            saturnImage = ImageIO.read(getClass().getResource("/images/saturn.png"));
            uranusImage = ImageIO.read(getClass().getResource("/images/uranus.png"));
            neptuneImage = ImageIO.read(getClass().getResource("/images/neptune.png"));

            System.out.println("Images Loaded");

            Planet sun = new Planet("sun", sunImage, 0, 0, 20, Color.YELLOW, 1.98892 * Math.pow(10, 30));
            sun.setYVel(10);

//            funny mode
//            Planet sun2 = new Planet("sun", sunImage, 30.1 * Planet.AU, 15 * Planet.AU, 20, Color.YELLOW, 1.98892 * Math.pow(10, 30));
//            sun.setYVel(10);

            Planet mercury = new Planet("mercury", mercuryImage, 0.387 * Planet.AU, 0, 2, Color.DARK_GRAY, 3.3 * Math.pow(10, 23));
            mercury.setYVel(-47.4 * 1000);

            Planet venus = new Planet("venus", venusImage, 0.723 * Planet.AU, 0, 6, Color.WHITE, 4.8685 * Math.pow(10, 24));
            venus.setYVel(-35.02 * 1000);

            Planet earth = new Planet("earth", earthImage, -1 * Planet.AU, 0, 6, Color.BLUE, 5.9742 * Math.pow(10, 24));
            earth.setYVel(29.783 * 1000);

            Planet mars = new Planet("mars", marsImage, 1.524 * Planet.AU, 0, 3, Color.RED, 6.39 * Math.pow(10, 23));
            mars.setYVel(-24.077 * 1000);

            Planet jupiter = new Planet("jupiter", jupiterImage, 5.2 * Planet.AU, 0, 11, Color.GREEN, 1.8982 * Math.pow(10, 27));
            jupiter.setYVel(-13 * 1000);

            Planet saturn = new Planet("saturn", saturnImage, 9.6 * Planet.AU, 0, 10, Color.ORANGE, 5.6834 * Math.pow(10, 26));
            saturn.setYVel(-9.65 * 1000);

            Planet uranus = new Planet("uranus", uranusImage, 19.2 * Planet.AU, 0, 9, Color.CYAN, 8.6810 * Math.pow(10, 25));
            uranus.setYVel(-6.85 * 1000);

            Planet neptune = new Planet("neptune", neptuneImage, 30.1 * Planet.AU, 0, 9, Color.BLUE, 1.0243 * Math.pow(10, 26));
            neptune.setYVel(-5.46 * 1000);

            Collections.addAll(planets, sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune);

            for (Planet planet : planets) {
                planet.update(planets);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void draw(FXGraphics2D graphics) {
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setPaint(new TexturePaint(background, new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight())));
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
