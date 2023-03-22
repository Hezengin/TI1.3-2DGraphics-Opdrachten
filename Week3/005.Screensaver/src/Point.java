public class Point {
    public double x;
    public double y;
    public double xDirection;
    public double yDirection;

    public Point(double x, double y, double xDirection, double yDirection) {
        this.x = x;
        this.y = y;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setxDirection(double xDirection) {
        this.xDirection = xDirection;
    }
    public void setyDirection(double yDirection) {
        this.yDirection = yDirection;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getxDirection() {
        return xDirection;
    }
    public double getyDirection() {
        return yDirection;
    }
}
