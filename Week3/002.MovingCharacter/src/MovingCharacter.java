import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private BufferedImage[] tiles;

    private BufferedImage[] jump;
    private BufferedImage image;
    private double angle = 0.0;
    private int counter = 0;
    private int location = 20;
    private BufferedImage[] originalTiles;
    private BufferedImage stand;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMousePressed(e -> {
            jump();
        });

        try {
            image = ImageIO.read(getClass().getResource("/images/sprite.png"));
            stand = image.getSubimage(0,512,50,64);
            tiles = new BufferedImage[8];
            jump = new BufferedImage[8];
            //de subimages van de srite uithalen.
            for (int i = 0,j =0 ; i < 8; j+=65,i++) {
                jump[i] = image.getSubimage(j,320,50,61);
            }

            for (int i = 0, j = 0; i < 8; j += 65, i++) {
                tiles[i] = image.getSubimage(j, 260, 50, 61);
            }
            originalTiles = tiles;

        } catch (Exception e) {
            e.printStackTrace();
        }
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000.0);
                last = now;
                draw(g2d);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();


        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }
    boolean state = false;
    public void draw(FXGraphics2D graphics) {

        graphics.setBackground(Color.white);
        graphics.clearRect(0,0,1920,1080);
        AffineTransform tx = new AffineTransform();

        System.out.println(location);

        if (counter > 7){
            counter = 0;
        }
        if (state){
            location-=5;
            tx.translate(location,0);
        }else {
            location += 5;
            tx.translate(location,0);
        }

        if (location > 1500){
            state = true;
            System.out.println("T");
        }
        if (location < 10){
            state = false;
        }

        graphics.drawImage(tiles[counter],tx,null);
        counter++;
    }

    public void update(double deltaTime) {
        angle += 0.0005;

    }

    private void jump() {
        counter = 0;
        tiles = jump;
        new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                if (state){
                    location -= 5;
                }else{
                    location += 5;
                }

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            tiles = originalTiles;
        }).start();
    }


    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }
}
