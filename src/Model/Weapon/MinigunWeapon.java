package Model.Weapon;

import Model.Angle;
import Model.Character;

public class MinigunWeapon extends Weapon {
    private int mySpinup;
    private int myMaxSpinup;
    private int myDecayRate;

    public MinigunWeapon(Attack theAttack, int theMaxCooldown,
                         int theMaxSpinup, int theDecayRate) {
        super(theMaxCooldown, theAttack);
        this.myMaxSpinup = theMaxSpinup;
        this.myDecayRate = theDecayRate;
        this.mySpinup = 0;  // 0 for start
    }

    @Override
    public boolean attemptAttack(Character origin, Angle angle) {
        mySpinup += myDecayRate;
        if (mySpinup >= myMaxSpinup) {
            return super.attemptAttack(origin, angle);
        } else {
            // increase spinup, but do not attack
            mySpinup += 1;
            return false;
        }
    }

    @Override
    public void update() {
        super.update();
        if (mySpinup > 0) {
            mySpinup = Math.max(0, mySpinup - myDecayRate);  // decrease spinup by the decay rate
        }
    }
}
