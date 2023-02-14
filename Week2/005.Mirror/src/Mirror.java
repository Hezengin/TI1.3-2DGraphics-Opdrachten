import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);
        graphics.setColor(Color.black);
        graphics.drawLine(0, 960, 0, -960);// assenstelsel y
        graphics.setColor(Color.BLUE.brighter());
        graphics.drawLine(-1080, 0, 1080, 0);// assenstelsel x
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawLine(-100,-250,100,250);
        graphics.setColor(Color.RED);


        graphics.setColor(Color.ORANGE);
        Rectangle rec = new Rectangle(50,50,100,100);
        graphics.draw(rec);


        AffineTransform tx = new AffineTransform();


    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
