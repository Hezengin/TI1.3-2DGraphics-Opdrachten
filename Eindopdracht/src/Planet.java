import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Planet {
    private BufferedImage planetImage;
    private Point2D.Double location;
    private double distanceToSun;


    public Planet(BufferedImage planetImage, int x,int y, double distanceToSun) {
        this.planetImage = planetImage;
        this.location = new Point2D.Double(x,y);
        this.distanceToSun = distanceToSun;
    }

    public BufferedImage getPlanetImage() {
        return planetImage;
    }
    public int getX(){
     return (int)location.getX();
    }
    public int getY(){
       return (int)this.location.getY();
    }
    public double getDistanceToSun() {
        return distanceToSun;
    }

    public void setLocation(Point2D.Double location) {
        this.location = location;
    }
    public void setDistanceToSun(double distanceToSun) {
        this.distanceToSun = distanceToSun;
    }
}
