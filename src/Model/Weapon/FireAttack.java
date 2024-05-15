package Model.Weapon;

import Model.Angle;
import Model.Force;
import Model.Weapon.ProjectileAttack;

public class FireAttack extends ProjectileAttack {
    private int myFireTime;

    public FireAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity, int theFireTime) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance, theVelocity);
        myFireTime = theFireTime;
    }


    public int getFireTime() {
        return myFireTime;
    }

    public void setFireTime(int fireTime) {
        this.myFireTime = fireTime;
    }

    @Override
    public boolean update() {
        return super.update();
        // handle fire effect
    }
}
