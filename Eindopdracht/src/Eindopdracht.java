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
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

    public class Eindopdracht extends Application {

        private Canvas canvas;
        private FXGraphics2D g2d;
        private BufferedImage sunPic;
        private BufferedImage earthPic;
        private BufferedImage marsPic;
        private BufferedImage mercuryPic;
        private BufferedImage neptunePic;
        private BufferedImage saturnPic;
        private BufferedImage uranusPic;
        private BufferedImage venusPic;
        private BufferedImage moonPic;
        private BufferedImage plutonPic;
        private BufferedImage jupiterPic;
        private BufferedImage backGroundPic;
        Planet sun;
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        canvas.setWidth(1920);
        canvas.setHeight(1080);
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
        try {
            //loading pictures
            backGroundPic = ImageIO.read(getClass().getResource("/background.png"));
            sunPic = ImageIO.read(getClass().getResource("/sun.png"));
            earthPic = ImageIO.read(getClass().getResource("/earth.png"));
            marsPic = ImageIO.read(getClass().getResource("/mars.png"));
            jupiterPic = ImageIO.read(getClass().getResource("/jupiter.png"));
            saturnPic = ImageIO.read(getClass().getResource("/saturn.png"));
            uranusPic = ImageIO.read(getClass().getResource("/uranus.png"));
            venusPic = ImageIO.read(getClass().getResource("/venus.png"));
            neptunePic = ImageIO.read(getClass().getResource("/neptune.png"));
            plutonPic = ImageIO.read(getClass().getResource("/pluton.png"));
            mercuryPic = ImageIO.read(getClass().getResource("/mercury.png"));
            moonPic = ImageIO.read(getClass().getResource("/mercury.png"));
            // images in draw
//            graphics.drawImage(venus,(int) canvas.getWidth() / 2 - 15 - startPos * 2,(int) canvas.getHeight() / 2 - 15,30,30,null);
//            graphics.drawImage(earth,(int) canvas.getWidth() / 2 - 15 - startPos * 3,(int) canvas.getHeight() / 2 - 15,30,30,null);
//            graphics.drawImage(mars,(int) canvas.getWidth() / 2 - 15 - startPos * 4,(int) canvas.getHeight() / 2 - 15,30,30,null);
//            graphics.drawImage(jupiter,(int) canvas.getWidth() / 2 - 15 - startPos * 5,(int) canvas.getHeight() / 2 - 15,30,30,null);
//            graphics.drawImage(saturn,(int) canvas.getWidth() / 2 - 25 - startPos * 6,(int) canvas.getHeight() / 2 - 15,50,30,null);
//            graphics.drawImage(uranus,(int) canvas.getWidth() / 2 - 15 - startPos * 7,(int) canvas.getHeight() / 2 - 15,30,30,null);
//            graphics.drawImage(neptune,(int) canvas.getWidth() / 2 - 15 - startPos * 8,(int) canvas.getHeight() / 2 - 15,30,30,null);
//            graphics.drawImage(pluton,(int) canvas.getWidth() / 2 - 15 - startPos * 9,(int) canvas.getHeight() / 2 - 15,30,30,null);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Planet sun = new Planet(sunPic,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
//        Planet moon = new Planet(sun,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
//        Planet mercury = new Planet(sun,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
//        Planet venus = new Planet(sun,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
//        Planet earth = new Planet(sun,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
//        Planet mars = new Planet(sun,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
//        Planet jupiter = new Planet(sun,(int)canvas.getWidth()/2,(int) canvas.getHeight()/2,0);
    }

    AffineTransform tx = new AffineTransform();
//    int positionXSun = (int) canvas.getWidth() / 2 - 64;
//    int positionYSun = (int) canvas.getHeight() / 2 - 64;

    public void draw(FXGraphics2D graphics) {
        sun = new Planet(sunPic,(int)canvas.getWidth()/2 - 64,(int) canvas.getHeight()/2 - 64,0);
        graphics.drawImage(backGroundPic, 0, 0, (int) canvas.getWidth(), (int) canvas.getHeight(), null);
        graphics.drawImage(sun.getPlanetImage(),sun.getX(), sun.getY() ,null);
        graphics.setTransform(tx);

    }

    double angle = 0;
//    double distanceX = positionXSun - 100;
//    double distanceY = positionYSun - 100;
    double newdistanceX;
    double newdistanceY;

    public void update(double deltaTime) {
//        angle += 0.01;
//        x = distanceX * Math.cos(angle);
//        y = distanceY * Math.sin(angle);
//        newdistanceX =
    }

}
