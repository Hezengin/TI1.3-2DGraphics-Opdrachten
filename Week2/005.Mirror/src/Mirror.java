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

        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, 960, 0, -960);
        graphics.drawLine(-1080, 0, 1080, 0);

        graphics.setColor(Color.RED);
        Path2D line = new Path2D.Double();
        line.moveTo(-300, -750);
        line.lineTo(300, 750);
        graphics.draw(line);

        Rectangle2D square = new Rectangle2D.Double(-50, 100, 100, 100);
        graphics.setColor(Color.ORANGE);
        graphics.draw(square);

        AffineTransform mirrorTransform = new AffineTransform((2/(1+(2.5*2.5)))-1, 2*2.5/(1+(2.5*2.5)), 2*2.5/(1+(2.5*2.5)), (2*2.5*2.5/(1+(2.5*2.5)))-1, 0, 0);
        Shape mirroredSquare = mirrorTransform.createTransformedShape(square);

        graphics.setColor(Color.BLUE);
        graphics.draw(mirroredSquare);
    }

    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
