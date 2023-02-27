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
        ArrayList<String> array = new ArrayList<>();
        Collections.addAll(array,"R","e","g","e","n","b","o","o","g");

        Font font = new Font(Font.MONOSPACED,Font.BOLD,40);
        for (int i = 0; i < array.size(); i++) {
            System.out.println(i);
            String s = array.get(i);
            Shape shape = font.createGlyphVector(graphics.getFontRenderContext(), s).getOutline();
            graphics.setColor(Color.getHSBColor((float) i/array.size(), 1, 1));
            graphics.rotate(Math.toRadians(-90));
            graphics.fill(shape);
            graphics.rotate(Math.toRadians(90));
            graphics.rotate(Math.toRadians(20));
            graphics.translate(0, -50);
        }

    }

    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
