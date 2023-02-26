import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;
    private ArrayList<String> array = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, 1);

        Collections.addAll(array,"R","e","g","e","n","b","o","o","g");
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, new Color(128, 0, 128)};

        Font font = new Font(Font.MONOSPACED,Font.BOLD,40);
        for (String s : array) {
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(),s).getOutline();
            graphics.draw(shape);
            shape.
            update();
        }


    }

    public void update(FXGraphics2D graphics){
        int a = 50;
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(20));
        transform.translate(,a);
        graphics.setTransform(transform);
    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
