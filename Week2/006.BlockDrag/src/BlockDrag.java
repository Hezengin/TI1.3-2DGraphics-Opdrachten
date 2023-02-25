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
        init(); // Call the init method to initialize the blocks

        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseReleased(this::mouseReleased);
        canvas.setOnMouseDragged(this::mouseDragged);

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        for (Renderable block : blocks) {
            block.draw(graphics);
        }
    }

    public void init() {
        for (int i = 0; i <= 800; i += 100) {
            Color color = Color.CYAN;
            Shape rectangle = new Rectangle2D.Double(i, 0, 50, 50);
            Renderable renderable = new Renderable(i, 0, rectangle, color);
            blocks.add(renderable);
        }

    }

    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        for (Renderable block : blocks) {
            if (e.getX() > block.getX() && e.getX() < block.getX() + 50 && e.getY() > block.getY() && e.getY() < block.getY() + 50) {
                selectedblock = block;
            }
        }
    }

    private void mouseReleased(MouseEvent e) {
        selectedblock = null;
    }

    private void mouseDragged(MouseEvent e) {
        if (selectedblock != null) {
            selectedblock.setX(e.getX());
            selectedblock.setY(e.getY());
        }
    }

}
