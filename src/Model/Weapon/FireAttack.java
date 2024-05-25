package Model.Weapon;

import Model.Angle;
import Model.Force;
import Model.Weapon.ProjectileAttack;

public class FireAttack extends ProjectileAttack {
    private int myFireTime;

    public FireAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity, int theFireTime) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance, theVelocity);
        setFireTime(theFireTime);
    }

    public void setFireTime(int fireTime) {
        if (fireTime < 0) {
            throw new IllegalArgumentException("Fire time cannot be negative");
        }
        this.myFireTime = fireTime;
    }

    public int getFireTime() {
        return myFireTime;
    }

    @Override
    public boolean update() {
        return super.update();
        // handle fire effect
    }
}
