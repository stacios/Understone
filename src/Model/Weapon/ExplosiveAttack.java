package Model.Weapon;

import Model.Angle;
import Model.Force;
import Model.Weapon.ProjectileAttack;

public class ExplosiveAttack extends ProjectileAttack {
    private double myRadius; // Radius of the explosion effect

    public ExplosiveAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity, double theRadius) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance, theVelocity);
        setRadius(theRadius);
    }

    public void setRadius(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }
        this.myRadius = radius;
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
