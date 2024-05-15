package Model.Weapon;

import Model.Angle;
import Model.Force;
import Model.Weapon.ProjectileAttack;

public class ExplosiveAttack extends ProjectileAttack {
    private double myRadius; // Radius of the explosion effect

    public ExplosiveAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity, double theRadius) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance, theVelocity);
        myRadius = theRadius;
    }


    public double getRadius() {
        return myRadius;
    }


    @Override
    public boolean update() {
        return super.update();
        // handle explosion effect
    }
}
