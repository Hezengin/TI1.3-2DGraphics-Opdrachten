import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Graph extends Application {
    Canvas canvas = new Canvas(1920, 1080);

    @Override
    public void start(Stage primaryStage) throws Exception {

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        graphics.setColor(Color.black);
        graphics.drawLine(0, 960, 0, -960);// assenstelsel y
        graphics.setColor(Color.BLUE.brighter());
        graphics.drawLine(-1080, 0, 1080, 0);// assenstelsel x
        graphics.setColor(Color.green);

        double resolution = 0.1;// zorgt voor een smoother lijn
        double lastY = -10 * -10 * -10;// y = x^3
        double lastX = -10;// x = x

        float res = 100;// zorgt ervoor dat het ingezoomd wordt bij x en y.
        for (float x = -10; x < 10; x += resolution) {
            float y = x * x * x;// y = x^3
            graphics.draw(new Line2D.Double(x * res, y * res, lastX * res, lastY * res));
            lastX = x;// x is de vorige x
            lastY = y;// y is de vorige y
        }
    }


    public static void main(String[] args) {
        launch(Graph.class);
    }

}
