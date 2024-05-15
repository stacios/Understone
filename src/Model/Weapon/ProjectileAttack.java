package Model.Weapon;

import Model.Angle;
import Model.Force;

public class ProjectileAttack extends Attack {
    private double myVelocity;

    public ProjectileAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance);
        myVelocity = theVelocity;
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
