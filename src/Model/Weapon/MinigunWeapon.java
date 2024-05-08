package Model.Weapon;

import Model.Angle;
import Model.Attack;
import Model.Character;
import Model.Weapon.Weapon;

public class MinigunWeapon extends Weapon {
    private int mySpinup;
    private int myMaxSpinup;
    private int myDecayRate;

    public MinigunWeapon(Attack theAttack, int theCooldown, int theMaxCooldown,
                         int theMaxSpinup, int theDecayRate) {
        super(theAttack, theCooldown, theMaxCooldown);
        this.myMaxSpinup = theMaxSpinup;
        this.myDecayRate = theDecayRate;
        this.mySpinup = 0;  // 0 for start
    }

    @Override
    public boolean attemptAttack(Character origin, Angle angle) {
        if (mySpinup == myMaxSpinup && myCooldown == 0) {
            // attack
            super.attemptAttack(origin, angle);
            // reset cooldown
            myCooldown = myMaxCooldown;
            return true;
        } else if (mySpinup < myMaxSpinup) {
            // increase spinup, but do not attack
            mySpinup += 1;
        }
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (mySpinup > 0) {
            mySpinup = Math.max(0, mySpinup - myDecayRate);  // decrease spinup by the decay rate
        }
    }
}
