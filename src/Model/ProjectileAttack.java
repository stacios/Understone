package Model;

public class ProjectileAttack extends Attack{
    private double myVelocity;

    public ProjectileAttack(String theName, int theDamage, double theX, double theY,
                            double theWidth, double theHeight, Force theKnockBack,
                            Angle theAngle, double theVelocity) {
        super(theName, theDamage, theX, theY, theWidth, theHeight, theKnockBack, theAngle);
        this.myVelocity = theVelocity;
    }

    public double getVelocity() {
        return myVelocity;
    }

    public void setVelocity(double velocity) {
        this.myVelocity = velocity;
    }

    @Override
    public void update() {
        super.update();

        // Update the position of the projectile based on its velocity and angle
        double deltaX = myVelocity * Math.cos(getAngle().getRadians());
        double deltaY = myVelocity * Math.sin(getAngle().getRadians());
        // Update the position by adding the calculated deltas
        setPosition(getX() + deltaX, getY() + deltaY);
    }
}
