import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Affine;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Planet {
    static double AU = 149.6e6 * 1000;//austronomical unit in km 28.8
    private static double SCALE = 30 / AU;//1 AU = 100 pixels
    private double G = 6.67428e-11;//gravity constant
    private double TIMESTEP = 3600 * 24;//1 day
    private int radius;
    private double x;
    private double y;
    private double mass;
    private double xVel;
    private double yVel;
    private Color color;
    private boolean isSun;
    private String name;
    private List<Point> orbit;
    private final double sunMass = 1.989E30;

    private double orbitalPeriodInSeconds;
    private double time = 0;

    private BufferedImage image;
    private int[] xPoints;
    private int[] yPoints;

    public Planet() {

    }

    public Planet(String name, BufferedImage image, double x, double y, int radius, Color color, double mass) {
        this.name = name;
        this.image = image;
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
        this.color = color;

        this.yVel = 0;
        this.xVel = 0;
        this.orbit = new ArrayList<>();

        this.orbitalPeriodInSeconds = 2 * Math.PI * Math.sqrt(Math.pow(Math.abs(x), 3) / (G * sunMass));
    }

    public void draw(FXGraphics2D graphics) {
        double x = this.x * SCALE + Eindopdracht.canvas.getWidth() / 2;
        double y = this.y * SCALE + Eindopdracht.canvas.getHeight() / 2;

        this.orbit.add(new Point((int) x, (int) y));
        if (this.orbit.size() > 2) {

            graphics.setColor(this.color);
            if (this.time > this.orbitalPeriodInSeconds * 1.2) {
                this.orbit.remove(0);
            }
            List<Point> updatedList = new ArrayList<>();
            for (Point point : this.orbit) {
                int px = point.x;
                int py = point.y;

                updatedList.add(new Point(px, py));
            }

            xPoints = new int[updatedList.size()];
            yPoints = new int[updatedList.size()];

            for (int i = 0; i < updatedList.size(); i++) {
                Point p = updatedList.get(i);
                xPoints[i] = p.x;
                yPoints[i] = p.y;
            }
            graphics.drawPolyline(xPoints, yPoints, updatedList.size());
        }

        if (this.name.equals("saturn")) {
            graphics.drawImage(this.image, (int) (x - radius), (int) (y - radius / 2), (radius * 2), radius, null);
        } else {
            graphics.drawImage(this.image, (int) (x - radius / 2), (int) (y - radius / 2), radius, radius, null);
        }
    }

    public void update(ArrayList<Planet> planetList) {
        this.time += TIMESTEP;

        double totalForceX = 0;
        double totalForceY = 0;

        for (Planet planet : planetList) {
            if (this == planet) continue;

            double distanceX = planet.x - this.x;
            double distanceY = planet.y - this.y;
            double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            double force = (G * this.mass * planet.mass) / Math.pow(distance, 2);

            double forceX = force * (distanceX / distance);
            double forceY = force * (distanceY / distance);

            totalForceX += forceX;
            totalForceY += forceY;
        }

        double accelX = totalForceX / this.mass;
        double accelY = totalForceY / this.mass;
        this.xVel += accelX * TIMESTEP;
        this.yVel += accelY * TIMESTEP;
        this.x += this.xVel * TIMESTEP;
        this.y += this.yVel * TIMESTEP;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }
}