package Model;
/**
 * Container class representing an angle, used in model
 */
public class Angle {
    private double myRadians;

    public Angle(double theRadians) {
        this.myRadians = theRadians;
    }

    public Angle(double x1, double y1, double x2, double y2) {
        double deltaY = y2 - y1;
        double deltaX = x2 - x1;
        this.myRadians = Math.atan2(deltaY, deltaX); //  calculates the angle in radians between the positive x-axis of a plane and the point given by the coordinates (x, y)
        if (myRadians < 0)
            myRadians = 2 * Math.PI + myRadians;
    }

    public double getRadians() {
        return myRadians;
    }

    public double getDegrees() {
        return Math.toDegrees(myRadians);
    }

    /**
     * Get cos and sin of the angle as a double array
     */
    public double[] getComp() {
        return new double[]{Math.cos(myRadians), Math.sin(myRadians)};
    }
}
