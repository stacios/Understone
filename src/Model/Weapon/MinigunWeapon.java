package Model.Weapon;

import Model.Angle;
import Model.Character;
/**
 * Modified weapon for the minigun. Has an extra cooldown timer before being able to fire.
 */
public class MinigunWeapon extends Weapon {
    private int mySpinup;
    private int myMaxSpinup;
    private int myDecayRate;

    public MinigunWeapon(Attack theAttack, int theMaxCooldown,
                         int theMaxSpinup, int theDecayRate) {
        super(theMaxCooldown, theAttack);
        setMaxSpinup(theMaxSpinup);
        setDecayRate(theDecayRate);
        this.mySpinup = 0;  // 0 for start
    }

    public void setMaxSpinup(int maxSpinup) {
        if (maxSpinup < 0) {
            throw new IllegalArgumentException("Max spinup cannot be negative");
        }
        this.myMaxSpinup = maxSpinup;
    }

    public void setDecayRate(int decayRate) {
        if (decayRate < 0) {
            throw new IllegalArgumentException("Decay rate cannot be negative");
        }
        this.myDecayRate = decayRate;
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
