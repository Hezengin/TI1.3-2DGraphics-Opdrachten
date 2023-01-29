import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Line2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.draw(new Line2D.Double(250, 0, 400, 200));
        graphics.draw(new Line2D.Double(250,0,100,200));
        graphics.draw(new Line2D.Double(400,200,100,200));
        graphics.draw(new Line2D.Double(100,200,100,400));
        graphics.draw(new Line2D.Double(400,200,400,400));
        graphics.draw(new Line2D.Double(100,400,400,400));

        graphics.draw(new Line2D.Double(150,250,150,400));
        graphics.draw(new Line2D.Double(200,250,200,400));
        graphics.draw(new Line2D.Double(150,250,200,250));

        graphics.draw(new Line2D.Double(250,250,250,350));
        graphics.draw(new Line2D.Double(350,250,350,350));

        graphics.draw(new Line2D.Double(250,250,350,250));
        graphics.draw(new Line2D.Double(250,350,350,350));





    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
