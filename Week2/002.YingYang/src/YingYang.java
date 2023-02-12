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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D g2d)
    {
        g2d.setTransform(new AffineTransform());
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g2d.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        g2d.scale(1, -1);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, 960, 0, -960);// assenstelsel y
        g2d.setColor(Color.BLUE.brighter());
        g2d.drawLine(-1080, 0, 1080, 0);// assenstelsel x

        Area b = new Area(new Ellipse2D.Double(0,100,100,100));
        Area c = new Area(new Ellipse2D.Double(-50,0,200,200));
        Area d = new Area(new Ellipse2D.Double(40,140,25,25));
        Area a = new Area(new Ellipse2D.Double(40,40,25,25));

        g2d.setColor(Color.black);
        g2d.fill(c);
        g2d.setColor(Color.white);
        g2d.fill(b);
        g2d.setColor(Color.black);
        g2d.fill(d);
        g2d.setColor(Color.white);
        g2d .fill(a);
    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
