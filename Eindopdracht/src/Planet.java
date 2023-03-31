import com.sun.javafx.geom.Line2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Affine;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Planet {
    static double AU = 149.6e6 * 1000;//austronomical unit in km 28.8
    private double G = 6.67428e-11;//gravity constant
    private double SCALE = 200 / AU;//1 AU = 100 pixels
    private double TIMESTEP = 3600 * 5;//1 day
    private int radius;
    private double x;
    private double y;
    private double mass;
    private double distanceToSun;
    private double xVel;
    private double yVel;
    private Color color;
    boolean isSun;
    private List<Point> orbit;
    final double sunMass = 1.989E30;
    double orbitalPeriodInSeconds;
    double time = 0;

    public Planet(double x, double y, int radius, Color color, double mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
        this.color = color;

        this.distanceToSun = 0;//update for each planet
        this.isSun = false;
        this.yVel = 0;
        this.xVel = 0;
        this.orbit = new LinkedList<>();

        this.orbitalPeriodInSeconds = 2 * Math.PI * Math.sqrt(Math.pow(Math.abs(x), 3) / (G * sunMass));
    }

    public void setSun(boolean sun) {
//        System.out.println("planet status before:" + isSun);
        isSun = sun;
//        System.out.println("planet status after:" + isSun);
    }

    public void draw(FXGraphics2D graphics) {
        double x = this.x * SCALE + 1920 / 2;
        double y = this.y * SCALE + 1080 / 2;

        if (this.time > this.orbitalPeriodInSeconds) {
            this.orbit.remove(0);
        }
        System.out.println(orbit.size());
        graphics.setColor(this.color);
        this.orbit.add(new Point((int) x, (int) y));

        if (this.orbit.size() > 2) {

            List<Point> updatedList = new ArrayList<>();
            for (Point point : this.orbit) {
                int px = point.x;
                int py = point.y;

                updatedList.add(new Point(px, py));
            }

            int[] xPoints = new int[updatedList.size()];
            int[] yPoints = new int[updatedList.size()];

            for (int i = 0; i < updatedList.size(); i++) {
                Point p = updatedList.get(i);
                xPoints[i] = p.x;
                yPoints[i] = p.y;
            }
            graphics.drawPolyline(xPoints, yPoints, updatedList.size());
        }


        graphics.fillOval((int) (x - radius / 2), (int) (y - radius / 2), radius, radius);
    }

    public void update(ArrayList<Planet> planetList) {
        this.time += TIMESTEP;

        double totalForceX = 0;
        double totalForceY = 0;
        for (Planet planet1 : planetList) {
            if (this == planet1)
                continue;

            double distanceX = planet1.x - this.x;
            double distanceY = planet1.y - this.y;
            double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            double force = (G * this.mass * planet1.mass) / Math.pow(distance, 2);

            // Calculate the x and y components of the force
            double forceX = force * (distanceX / distance);
            double forceY = force * (distanceY / distance);

            // Update the total force
            totalForceX += forceX;
            totalForceY += forceY;
        }

        // Calculate the x and y components of the acceleration
        double accelX = totalForceX / this.mass;
        double accelY = totalForceY / this.mass;

        // Update the velocity and position of the planet
        this.xVel += accelX * TIMESTEP;
        this.yVel += accelY * TIMESTEP;
        this.x += this.xVel * TIMESTEP;
        this.y += this.yVel * TIMESTEP;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }
}