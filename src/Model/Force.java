package Model;

import java.io.Serializable;
/**
 * Represents a force applied to a character. Each force has an angle, strength, and decay rate.
 * The strength and angle are used to calculate the force applied to the character every tick.
 * The strength is multiplied my (1 - decayRate) every tick.
 * Not having a decay rate means that the force will be applied instantaniously and be deleted after.
 */
public class Force implements Serializable {
    private static final long serialVersionUID = 7L;

    /**
     * The strength of the force.
     */
    private double myStrength;

    /**
     * The angle at which the force is applied.
     */
    private Angle myAngle;

    /**
     * The decay rate of the force's strength.
     */
    private double myDecayRate;

    /**
     * Constructs a Force with the specified angle, strength, and decay rate.
     *
     * @param theAngle the angle at which the force is applied
     * @param theStrength the strength of the force
     * @param theDecayRate the decay rate of the force's strength
     */
    public Force(final Angle theAngle, final double theStrength, final double theDecayRate) {
        setAngle(theAngle);
        setStrength(theStrength);
        setDecayRate(theDecayRate);
    }

    /**
     * Constructs a Force with the specified angle and strength.
     * The decay rate is set to 1, meaning the force is applied instantaneously.
     *
     * @param theAngle the angle at which the force is applied
     * @param theStrength the strength of the force
     */
    public Force(final Angle theAngle, final double theStrength) {
        myAngle = theAngle;
        myStrength = theStrength;
        myDecayRate = 1;
    }

    /**
     * Sets the strength of the force.
     *
     * @param theStrength the strength to set
     * @throws IllegalArgumentException if the strength is negative
     */
    public void setStrength(final double theStrength) {
        if (theStrength < 0) {
            throw new IllegalArgumentException("Strength cannot be negative");
        }
        myStrength = theStrength;
    }

    /**
     * Sets the angle of the force.
     *
     * @param theAngle the angle to set
     * @throws IllegalArgumentException if the angle is null
     */
    public void setAngle(final Angle theAngle) {
        if (theAngle == null) {
            throw new IllegalArgumentException("Angle cannot be null");
        }
        myAngle = theAngle;
    }

    /**
     * Sets the decay rate of the force's strength.
     *
     * @param theDecayRate the decay rate to set
     * @throws IllegalArgumentException if the decay rate is not between 0 and 1
     */
    public void setDecayRate(final double theDecayRate) {
        if (theDecayRate < 0 || theDecayRate > 1) {
            throw new IllegalArgumentException("Decay rate must be between 0 and 1");
        }
        myDecayRate = theDecayRate;
    }

    /**
     * Gets the strength of the force.
     *
     * @return the strength of the force
     */
    public double getStrength() {
        return myStrength;
    }

    /**
     * Gets the horizontal component of the force.
     *
     * @return the horizontal component of the force
     */
    public double getXStrength() {
        return myStrength * Math.cos(myAngle.getRadians());
    }

    /**
     * Gets the vertical component of the force.
     *
     * @return the vertical component of the force
     */
    public double getYStrength() {
        return myStrength * Math.sin(myAngle.getRadians());
    }

    /**
     * Gets the angle of the force.
     *
     * @return the angle of the force
     */
    public Angle getAngle() {
        return myAngle;
    }

    /**
     * Gets the decay rate of the force's strength.
     *
     * @return the decay rate of the force's strength
     */
    public double getDecayRate() {
        return myDecayRate;
    }

    /**
     * Updates the strength of the force based on the decay rate.
     *
     * @return true if the force still has significant strength left, false otherwise
     */
    public boolean update() {
        myStrength *= (1 - myDecayRate);
        return myStrength > 0.001;  // there's still significant strength left
    }
}
