import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    Canvas canvas;
    private ArrayList<Renderable> blocks = new ArrayList<>();
    private Renderable selectedblock;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());


        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseReleased(this::mouseReleased);
        canvas.setOnMouseDragged(e -> mouseDragged(e,g2d));

        draw(g2d);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());



        for (Renderable block : blocks) {
            block.draw(graphics);
        }

    }

    public void init() {
        for (int i = 0; i <= 800; i += 100) {
            Color color = Color.getHSBColor((float) i/800,1,1);
            Renderable renderable = new Renderable(i, 0, 30, color);
            blocks.add(renderable);
        }
        System.out.println(blocks.size());
    }

    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        for (Renderable block : blocks) {
            if (block.getShape().contains(e.getX(),e.getY())) {
                selectedblock = block;
            }
        }
        System.out.println(selectedblock);
    }

    private void mouseReleased(MouseEvent e) {
        selectedblock = null;
    }

    private void mouseDragged(MouseEvent e,FXGraphics2D g2d) {
        if (selectedblock != null) {
            selectedblock.setX(e.getX());
            selectedblock.setY(e.getY());
            System.out.println(e);
        }
        draw(g2d);
    }



}
