package Model;

public class Force {
    private double myStrength;
    private Angle myAngle;
    private double myDecayRate;

    public Force(Angle theAngle, double theStrength, double theDecayRate) {
        this.myAngle = theAngle;
        this.myStrength = theStrength;
        this.myDecayRate = theDecayRate;
    }

    public Force(Angle theAngle, double theStrength) {
        this.myAngle = theAngle;
        this.myStrength = theStrength;
        this.myDecayRate = 1;
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
