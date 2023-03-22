import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;

public class RopeConstraint implements Constraint{
    private final Particle p1;
    private final Particle p2;
    private final double restLength;

    public RopeConstraint(Particle p1, Particle p2, double restLength) {
        this.p1 = p1;
        this.p2 = p2;
        this.restLength = restLength;
    }

    @Override
    public void satisfy() {//controleer of dat de lengte niet overschrijdt, als het overschrijdt doe de berekening hiervoor in deze methode
        Point2D p1Current = p1.getPosition();
        Point2D p2Current = p2.getPosition();

        double distance = p1.getPosition().distance(p2.getPosition());

        if(distance > restLength){//als de gewenste lengte is overschreden voeg ze dichter naar elkaar toe
            double distanceDifference = (distance - restLength) / 2;// het verschil nemen en delen door 2 zodat je weet hoeveel elke punt geschoven moet worden
            p1.setPosition(new Point2D.Double(p1Current.getX() - distanceDifference,p1Current.getY() - distanceDifference));//het schuiven van punten naar elkaar
            p2.setPosition(new Point2D.Double(p2Current.getX() + distanceDifference,p2Current.getY() + distanceDifference));
        }
    }

    @Override
    public void draw(FXGraphics2D g2d) {//

    }
}
