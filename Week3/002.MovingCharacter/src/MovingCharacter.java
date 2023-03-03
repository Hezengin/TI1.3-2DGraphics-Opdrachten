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
    private int currentTileIndex = 0;
    private double positionX = 0.0;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        try {
            image = ImageIO.read(getClass().getResource("/images/sprite.png"));
            tiles = new BufferedImage[24];
            //de subimages van de srite uithalen.
            for (int i = 0, j = 0; i < 400; i += 50, j++) {
                tiles[j] = image.getSubimage(i, 260, 50, 75);
            }
        } catch (Exception e) {
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
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }

    public void draw(FXGraphics2D graphics) {

        graphics.drawImage(tiles[currentTileIndex],tx,null);
    }

    public void update(double deltaTime) {
        AffineTransform tx = new AffineTransform();
        tx.translate(positionX, 0);
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }
}
