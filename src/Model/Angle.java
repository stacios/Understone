package Model;

import java.io.Serializable;

/**
 * Container class representing an angle, used in model
 */
public class Angle implements Serializable {
    private static final long serialVersionUID = 5L;
    private double myRadians;

    public Angle(double theRadians) {
        myRadians = theRadians;
    }

    public Angle(double theX1, double theY1, double theX2, double theY2) {
        double deltaY = theY2 - theY1;
        double deltaX = theX2 - theX1;
        double radians = Math.atan2(deltaY, deltaX); //  calculates the angle in radians between the positive x-axis of a plane and the point given by the coordinates (x, y)
        setRadians(radians);
    }

    public void setRadians(double theRadians) {
        myRadians = theRadians < 0 ? 2 * Math.PI + theRadians : theRadians;
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
