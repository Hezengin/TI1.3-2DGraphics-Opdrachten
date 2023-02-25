import org.jfree.fx.FXGraphics2D;

import java.awt.*;

public class Renderable {
    private Shape shape;
    private Color color;
    private double x;
    private double y;

    public Renderable(int x, int y, Shape shape, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void draw(FXGraphics2D g2d){
        g2d.setColor(this.color);
        g2d.fill(this.shape);
    }
}
