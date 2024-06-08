package Model;

import java.io.Serializable;

/**
 * Container class representing an angle, used in model
 */
public class Angle implements Serializable {
    private static final long serialVersionUID = 5L;

    /**
     * The angle in radians.
     */
    private double myRadians;

    /**
     * Constructs an Angle object with the specified radians.
     *
     * @param theRadians the angle in radians.
     */
    public Angle(final double theRadians) {
        myRadians = theRadians;
    }

    /**
     * Constructs an Angle object using the coordinates of two points.
     *
     * @param theX1 the x-coordinate of the first point.
     * @param theY1 the y-coordinate of the first point.
     * @param theX2 the x-coordinate of the second point.
     * @param theY2 the y-coordinate of the second point.
     */
    public Angle(final double theX1, final double theY1, final double theX2, final double theY2) {
        double deltaY = theY2 - theY1;
        double deltaX = theX2 - theX1;
        double radians = Math.atan2(deltaY, deltaX); //  calculates the angle in radians between the positive x-axis of a plane and the point given by the coordinates (x, y)
        setRadians(radians);
    }

    /**
     * Sets the angle in radians. Converts negative radians to a positive equivalent.
     *
     * @param theRadians the angle in radians.
     */
    public void setRadians(final double theRadians) {
        myRadians = theRadians < 0 ? 2 * Math.PI + theRadians : theRadians;
    }

    /**
     * Gets the angle in radians.
     *
     * @return the angle in radians.
     */
    public double getRadians() {
        return myRadians;
    }

    /**
     * Gets the angle in degrees.
     *
     * @return the angle in degrees.
     */
    public double getDegrees() {
        return Math.toDegrees(myRadians);
    }

    /**
     * Gets the cosine and sine of the angle as an array.
     *
     * @return an array where the first element is the cosine of the angle and the second element is the sine of the angle.
     */
    public double[] getComp() {
        return new double[]{Math.cos(myRadians), Math.sin(myRadians)};
    }
}
