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
    private Angle mySpread;

    public MinigunWeapon(int theMaxCooldown, Attack theAttack, String theSound,
                         int theMaxSpinup, int theDecayRate, Angle theSpread) {
        super(theMaxCooldown, theAttack, theSound);
        setMaxSpinup(theMaxSpinup);
        setDecayRate(theDecayRate);
        this.mySpinup = 0;  // 0 for start
        this.mySpread = theSpread;
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
            double spread = mySpread.getRadians() * ((System.nanoTime() % 1000) / 1000.0) - mySpread.getRadians() / 2;
            return super.attemptAttack(origin, new Angle(angle.getRadians() + spread));
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
