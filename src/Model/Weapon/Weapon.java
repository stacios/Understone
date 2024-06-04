package Model.Weapon;

import Controller.Drawable;
import Model.Angle;
import Model.Character;
import Model.GameLoop;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

/**
 * Represents a weapon. Each character has a weapon. Has a cooldown between being able to fire.
 * Has an attack template, which is copied and added to the active room on a successful fire.
 */
public class Weapon implements Serializable, Drawable {
    private static final long serialVersionUID = 4L;
    private final Attack myAttack;
    private int myCooldown;
    private final int myMaxCooldown;
    private final String mySound;

    private final List<Attack> myPendingAttacks;


    // Constructor to initialize the Weapon object
    public Weapon(int theMaxCooldown, Attack theAttack, String theSound) {
        if (theMaxCooldown < 0) {
            throw new IllegalArgumentException("Max cooldown cannot be negative");
        }
        if (theAttack == null) {
            throw new IllegalArgumentException("Attack cannot be null");
        }
        myAttack = theAttack;
        myCooldown = 0;
        myMaxCooldown = theMaxCooldown;
        myPendingAttacks = new LinkedList<>();
        mySound = theSound;
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

            //GameLoop.getInstance().getActiveRoom().addAttack(attack);
            myPendingAttacks.add(attack);

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

    public Attack[] getPendingAttacks() {
        Attack[] temp = myPendingAttacks.toArray(new Attack[0]);
        myPendingAttacks.clear();
        return temp;
    }

    @Override
    public String[] getDrawData() {

        if (mySound != null && myCooldown == myMaxCooldown) {
            return new String[]{"sound:" + mySound};
        }
        else {
            return new String[0];
        }
    }
}
