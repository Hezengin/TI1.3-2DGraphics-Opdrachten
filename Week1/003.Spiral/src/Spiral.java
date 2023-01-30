import java.awt.*;
import java.awt.geom.*;
import java.util.Map;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    Canvas canvas = new Canvas(1920, 1080);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
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

        double resolution = 0.001;
        double scaler = 10;
        double lastX =  0;
        double lastY = 0;
        float n = 2;//constante dus verandert niet
        //r = lengte tussen de beginpunt en de getekende punt
        for (float r = 0; r < 6* Math.PI; r +=resolution){
            float teta = n * r;//gegeven formule
            double x = n *teta *Math.cos(teta);// gegeven formule om naar coordinaten over te zetten
            double y = n *teta *Math.sin(teta);//""
            graphics.draw(new Line2D.Double(x*scaler,y*scaler,lastX*scaler,lastY*scaler));
            lastX = x;
            lastY = y;
        }
    }


    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
