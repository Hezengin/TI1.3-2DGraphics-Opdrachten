import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;



public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,//er mag geen 2 dezelfde elementen in de array zitten
                Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK};//Lengte van fractions en colors moet hetzelfde zijn anders krijg je exception
        float[] fractions = {0,0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f,0.9f};//stop heeft 2 parameters eerste element van colors en eerste element van fractions,
        // als de lengtes van de arrays niet gelijk zijn geeft code fout.Want de ene heb je wel andere niet

        canvas.setOnMouseDragged(event -> {
            double x = event.getX();
            double y = event.getY();
            Point2D.Double focus = new Point2D.Double(x, y);
            RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(canvas.getWidth()/2, canvas.getHeight()/2), 180f,focus, fractions, colors, MultipleGradientPaint.CycleMethod.REPEAT);

            // tekenen van de rechthoek
            Rectangle2D rect = new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight());// grootte van canvas begint bij linksboven
            graphics.setPaint(rgp);
            graphics.fill(rect);
        });
    }


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
