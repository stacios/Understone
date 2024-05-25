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
    private double myStrength;
    private Angle myAngle;
    private double myDecayRate;

    public Force(Angle theAngle, double theStrength, double theDecayRate) {
        setAngle(theAngle);
        setStrength(theStrength);
        setDecayRate(theDecayRate);
    }

    public void setStrength(final double theStrength) {
        if (theStrength < 0) {
            throw new IllegalArgumentException("Strength cannot be negative");
        }
        myStrength = theStrength;
    }

    public void setAngle(final Angle theAngle) {
        if (theAngle == null) {
            throw new IllegalArgumentException("Angle cannot be null");
        }
        myAngle = theAngle;
    }

    public void setDecayRate(final double theDecayRate) {
        if (theDecayRate < 0 || theDecayRate > 1) {
            throw new IllegalArgumentException("Decay rate must be between 0 and 1");
        }
        myDecayRate = theDecayRate;
    }

    public Force(final Angle theAngle, final double theStrength) {
        myAngle = theAngle;
        myStrength = theStrength;
        myDecayRate = 1;
    }

    public double getStrength() {
        return myStrength;
    }

    // horizontal component of the force
    public double getXStrength() {
        return myStrength * Math.cos(myAngle.getRadians());
    }

    // vertical component of the force
    public double getYStrength() {
        return myStrength * Math.sin(myAngle.getRadians());
    }

    public Angle getAngle() {
        return myAngle;
    }

    // update the strength of the force based on the decay rate
    public boolean update() {
        myStrength *= (1 - myDecayRate);
        return myStrength > 0.001;  // there's still significant strength left
    }
}
