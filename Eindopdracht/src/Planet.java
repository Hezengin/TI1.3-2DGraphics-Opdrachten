import org.jfree.fx.FXGraphics2D;

import java.awt.*;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Planet {
    static double AU = 149.6e6 * 1000;//austronomical unit in km 28.8
    static double SCALE = 30 / AU;//1 AU = 100 pixels
    private double G = 6.67428e-11;//gravity constant
    private double TIMESTEP = 3600 * 24;//was supposed to be 1 day
    private int radius;
    private double x;
    private double y;
    private double mass;
    private double xVel;
    private double yVel;
    private Color color;
    private String name;
    private List<Point> orbit;
    private final double sunMass = 1.989E30;
    private double newX;
    private double newY;
    private double orbitalPeriodInSeconds;
    private double time = 0;
    private BufferedImage image;
    private int[] xPoints;
    private int[] yPoints;

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

        //formula to calculate the orbital period
        this.orbitalPeriodInSeconds = 2 * Math.PI * Math.sqrt(Math.pow(Math.abs(x), 3) / (G * sunMass));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setColor(this.color);

        if (this.orbit.size() > 2) {
            graphics.drawPolyline(xPoints, yPoints, orbit.size());
        }

        if (this.name.equals("saturn")) {
            graphics.drawImage(this.image, (int) (newX - radius), (int) (newY - radius / 2), (radius * 2), radius, null);
        } else {
            graphics.drawImage(this.image, (int) (newX - radius / 2), (int) (newY - radius / 2), radius, radius, null);
        }
    }

    public void update(List<Planet> planetList) {
        calculateAttraction(planetList);
        calculateOrbit();
    }

    private void calculateOrbit() {
        try {
            //Scaling the x and y position of the planet on the canvas
            newX = this.x * SCALE + Eindopdracht.canvas.getWidth() / 2;
            newY = this.y * SCALE + Eindopdracht.canvas.getHeight() / 2;
            this.orbit.add(new Point((int) newX, (int) newY));//begin point

            //times 1.2 so that we avoid parts where we dont draw the lines
            if (this.time > this.orbitalPeriodInSeconds * 1.2) {
                this.orbit.remove(0);
            }

            List<Point> updatedList = new ArrayList<>();
            for (Point point : this.orbit) {
                int px = point.x;
                int py = point.y;

                updatedList.add(new Point(px, py));
            }
            // 2 arrays so we can use drawPolyLine method
            xPoints = new int[updatedList.size()];
            yPoints = new int[updatedList.size()];

            //setting each element
            for (int i = 0; i < updatedList.size(); i++) {
                Point p = updatedList.get(i);
                xPoints[i] = p.x;
                yPoints[i] = p.y;
            }
        } catch (Exception e) {

        }
    }

    /**
     * calculates the attraction between the current planet object and a list of other planet objects passed as a parameter.
     * formula: F = G * (m1 * m2)/ r^2 to calculate the force between them
     * formula : a = F/M to calculate the acceleration of the current planet
     * @param planetList
     */
    private void calculateAttraction(List<Planet> planetList) {
        this.time += TIMESTEP;

        double totalForceX = 0;
        double totalForceY = 0;

        for (Planet planet : planetList) {
            if (this == planet) continue;

            double distanceX = planet.x - this.x;//the x distance for pythagoras
            double distanceY = planet.y - this.y;//the y distance for pythagoras
            double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));//the pythagoras
            double force = (G * this.mass * planet.mass) / Math.pow(distance, 2);

            double forceX = force * (distanceX / distance);
            double forceY = force * (distanceY / distance);

            //adding the calculated force to totalForce to get the overall force
            totalForceX += forceX;
            totalForceY += forceY;
        }
        //Calculating the acceleration
        double accelX = totalForceX / this.mass;
        double accelY = totalForceY / this.mass;

        //velocity is being updated based on TIMESTEP and the calculated acceleration
        //and positions
        this.xVel += accelX * TIMESTEP;
        this.yVel += accelY * TIMESTEP;

        //gives the updated x-y coordinate of the planet based on the given interval(TIMESTEP).
        this.x += this.xVel * TIMESTEP;
        this.y += this.yVel * TIMESTEP;
    }


    public Point2D getPosition() {
        newX = this.x * SCALE + Eindopdracht.canvas.getWidth() / 2;
        newY = this.y * SCALE + Eindopdracht.canvas.getHeight() / 2;
        return new Point2D.Double(newX, newY);
    }

    public int getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public Point2D getVelocity() {
        return new Point2D.Double(xVel, yVel);
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }
    public void setYVel(double yVel) {
        this.yVel = yVel;
    }
}