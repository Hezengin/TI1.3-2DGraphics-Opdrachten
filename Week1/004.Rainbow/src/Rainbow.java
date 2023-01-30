import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    private Canvas canvas = new Canvas(1280, 768);

    @Override
    public void start(Stage primaryStage) throws Exception {

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

//        graphics.setColor(Color.black);
//        graphics.drawLine(0, 960, 0, -960);// assenstelsel y
//        graphics.setColor(Color.BLUE.brighter());
//        graphics.drawLine(-1080, 0, 1080, 0);// assenstelsel x
//        graphics.setColor(Color.green);

        double resolution = 0.01;
        int scaler = 100;


        double radiusBinnen = 2;// binnen moet kleiner zijn dan buiten
        double radiusBuiten = 3;

        for (float j = 0; j < 100; j += resolution) {

            double hoek = Math.PI * (j /100);// hoek moet tussen 0 en 1 zitten
            System.out.println(hoek);// debug
            double x1 = radiusBinnen *  Math.cos(hoek);
            double y1 = radiusBinnen *  Math.sin(hoek);// gegeven vergelijkingen
            double x2 = radiusBuiten *  Math.cos(hoek);
            double y2 = radiusBuiten *  Math.sin(hoek);

            graphics.setColor(Color.getHSBColor(j / 100f, 1, 1));
            graphics.drawLine((int) (x1 * scaler), (int) (y1 * scaler), (int) (x2 * scaler), (int) (y2 * scaler));

        }
    }


    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
