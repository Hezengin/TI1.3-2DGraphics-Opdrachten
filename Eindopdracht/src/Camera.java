import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Camera {
    private Canvas canvas;
    // ZOOM VALUE
    private double scale = 0.7;
    // X-Y VALUES FOR THE CAMERA
    double xOffset = 0;
    double yOffset = 0;

    public Camera(Canvas canvas) {
        this.canvas = canvas;

        canvas.setScaleX(this.scale);
        canvas.setScaleY(this.scale);
    }


    public void scrollEvent(ScrollEvent event) {
        if (event.getDeltaY() > 0 && this.scale < 4) {
            this.scale += 0.05;
            canvas.setScaleX(this.scale);
            canvas.setScaleY(this.scale);
        } else if (this.scale > 0.4) {
            this.scale -= 0.05;
            canvas.setScaleX(this.scale);
            canvas.setScaleY(this.scale);
        }
    }

    public void mousePressed(MouseEvent e) {
        xOffset = e.getSceneX() - canvas.getTranslateX();
        yOffset = e.getSceneY() - canvas.getTranslateY();
    }

    public void mouseDragged(MouseEvent e) {
        canvas.setTranslateX(e.getSceneX() - xOffset);
        canvas.setTranslateY(e.getSceneY() - yOffset);
    }
}