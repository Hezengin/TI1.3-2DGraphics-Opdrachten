import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private BufferedImage[] tiles;
    private BufferedImage image;
    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        try {
            image = ImageIO.read(getClass().getResource("/images/sprite.png"));
            tiles = new BufferedImage[24];
            //knip de afbeelding op in 24 stukjes van 32x32 pixels.
            for(int i = 0; i < 24; i++)
                tiles[i] = image.getSubimage(0,0,50,70);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        AffineTransform tx = new AffineTransform();

        graphics.drawImage(tiles[1],tx,null);
//        graphics.drawImage(tiles[2],tx,null);
//        graphics.drawImage(tiles[3],tx,null);
//        graphics.drawImage(tiles[4],tx,null);

    }


    public void update(double deltaTime)
    {
    }

    public static void main(String[] args)
    {
        launch(MovingCharacter.class);
    }

}
