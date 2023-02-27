import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Renderable {

    private Color color;
    private double x;
    private double y;
    private double size;

    public Renderable(int x, int y, double size, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    public Shape getShape() {
        Rectangle2D.Double rect = new Rectangle2D.Double(x,y,size,size);
        return rect;
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

    public double getSize() {
        return size;
    }

    public void draw(FXGraphics2D g2d){
        g2d.setColor(this.color);
        g2d.fill(getShape());
    }
}
