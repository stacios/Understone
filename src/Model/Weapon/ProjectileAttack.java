package Model.Weapon;

import Model.Angle;
import Model.Force;
/**
 * Represents a basic bullet attack. It travels in a straight line, and has a constant velocity.
 */
public class ProjectileAttack extends Attack {
    private double myVelocity;

    public ProjectileAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance);
        setVelocity(theVelocity);
    }

    public void setVelocity(double velocity) {
        if (velocity < 0) {
            throw new IllegalArgumentException("Velocity cannot be negative");
        }
        this.myVelocity = velocity;
    }

    public double getVelocity() {
        return myVelocity;
    }


    @Override
    public boolean update() {

        // Update the position of the projectile based on its velocity and angle
        double[] temp = getAngle().getComp();
        double deltaX = myVelocity * temp[0];
        double deltaY = myVelocity * temp[1];
        // Update the position by adding the calculated deltas
        setPosition(getX() + deltaX, getY() + deltaY);
        return false;
    }

    @Override
    public String[] getDrawData() {
        return new String[0];
    }
}
