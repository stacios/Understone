package Model.Weapon;

import Model.Angle;
import Model.Character;
import Model.GameLoop;

import java.io.Serializable;

/**
 * Represents a weapon. Each character has a weapon. Has a cooldown between being able to fire.
 * Has an attack template, which is copied and added to the active room on a successful fire.
 */
public class Weapon implements Serializable {
    private static final long serialVersionUID = 4L;
    private final Attack myAttack;
    private int myCooldown;
    private final int myMaxCooldown;


    // Constructor to initialize the Weapon object
    public Weapon(int theMaxCooldown, Attack theAttack) {
        this.myAttack = theAttack;
        this.myCooldown = 0;
        this.myMaxCooldown = theMaxCooldown;
    }
    /**
     * Attempt to fire the weapon. Will only fire if the cooldown is 0.
     */
    public boolean attemptAttack(Character origin, Angle angle) {
        // Check if the weapon is off cooldown
        //System.out.println(myCooldown);
        if (myCooldown == 0) {

            Attack attack = myAttack.clone();
            attack.setPosition(origin.getX(), origin.getY(), angle);

            GameLoop.getInstance().getActiveRoom().addAttack(attack);

            //System.out.println("Attack performed at position: (" + attackX + ", " + attackY + ") with angle " + angle.getDegrees());

            // Reset the cooldown
            myCooldown = myMaxCooldown;

            return true;
        }
        return false;
    }

    //for managing cooldown
    /**
     * Updates the cooldown of the weapon every tick.
     */
    public void update() {
        if (myCooldown > 0) {
            myCooldown--; // Reduce the cooldown by 1 each update call until it reaches 0
        }
    }
}
