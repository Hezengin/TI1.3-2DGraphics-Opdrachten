import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private Image image1;
    private Image image2;
    private float counter = 1f;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        try {
            image1 = ImageIO.read(getClass().getResource("/landscape1.png"));
            image2 = ImageIO.read(getClass().getResource("/landscape2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        AffineTransform tx = new AffineTransform();

//        graphics.drawImage(image1,tx,null);
        for (float i = 0; i <= 1 ; i++) {

            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, i);
            graphics.setComposite(alphaComposite);

            // Draw the image with the alpha composite
            graphics.drawImage(image1, tx, null);

            // Restore the default composite (no transparency)
            graphics.setComposite(AlphaComposite.SrcOver);
        }

    }


    public void update(double deltaTime) {
        counter -= 0.01f;
        System.out.println(counter);
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
