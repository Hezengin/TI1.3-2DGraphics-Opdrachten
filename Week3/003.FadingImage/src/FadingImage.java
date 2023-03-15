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
    private BufferedImage image1;
    private BufferedImage image2;
    private float alpha = 0f;

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

        stage.setResizable(false);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        AffineTransform tx = new AffineTransform();

        graphics.setPaint(new TexturePaint(image1, new Rectangle2D.Double(0, 0,image1.getWidth(), canvas.getHeight())));
        graphics.fill(new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight()));
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));

        graphics.setPaint(new TexturePaint(image2, new Rectangle2D.Double(0, 0, image2.getWidth() , canvas.getHeight())));
        graphics.fill(new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight()));
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    boolean flip = false;
    public void update(double deltatime){
        System.out.println(alpha);
        if (flip){
            alpha -= 0.005f;
        }else{
            alpha += 0.005f;
        }
        if (alpha >= 1.0f){
            flip = true;
            alpha = 1;
        }else if (alpha <= 0){
            flip = false;
            alpha = 0;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
