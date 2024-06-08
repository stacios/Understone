package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;
import Model.Character;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Glyphid, an abstract class for different types of Glyphid enemies in the game.
 * Glyphids have specific behaviors like moving towards the player and attacking.
 */
public abstract class Glyphid extends Character {

    /** The roar sound of the Glyphid. */
    private final String myRoar;

    /** The cooldown period for the Glyphid's roar sound. */
    private int myRoarCooldown;

    /** The attack range of the Glyphid. */
    private double myAttackRange;

    /** The duration for which the Glyphid pauses before attacking. */
    private int myAttackPauseDuration;

    /** The counter for the attack pause duration. */
    private int myAttackPauseCounter = 0;

    /** Indicates if the Glyphid is paused before attacking. */
    private boolean isPausedBeforeAttack = false;

    /**
     * Constructs a new Glyphid with the specified parameters.
     *
     * @param theName the name of the Glyphid
     * @param theX the initial x-coordinate of the Glyphid
     * @param theY the initial y-coordinate of the Glyphid
     * @param theHealth the health points of the Glyphid
     * @param theWidth the width of the Glyphid
     * @param theHeight the height of the Glyphid
     * @param theMoveSpeed the movement speed of the Glyphid
     * @param theWeapon the weapon the Glyphid uses
     * @param theAttackRange the range within which the Glyphid can attack
     * @param theAttackPauseDuration the duration the Glyphid pauses before attacking
     * @param theRoar the roar sound of the Glyphid
     */
    public Glyphid(final String theName, final double theX, final double theY, final int theHealth,
                   final int theWidth, final int theHeight, final double theMoveSpeed,
                   final Weapon theWeapon, final double theAttackRange, final int theAttackPauseDuration, final String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        myAttackRange = theAttackRange;
        myAttackPauseDuration = theAttackPauseDuration;
        myRoar = theRoar;
        myRoarCooldown = -1;
    }

    /**
     * Gets the attack range of the Glyphid.
     *
     * @return the attack range of the Glyphid
     */
    public double getAttackRange() {
        return myAttackRange;
    }

    /**
     * Sets whether the Glyphid is paused before attacking.
     *
     * @param thePaused true if the Glyphid is paused before attacking, false otherwise
     */
    public void setPausedBeforeAttack(final boolean thePaused) {
        isPausedBeforeAttack = thePaused;
    }

    /**
     * Increments the attack pause counter and resets it if it reaches the attack pause duration.
     */
    public void incrementAttackPauseCounter() {
        myAttackPauseCounter++;
        if (myAttackPauseCounter >= myAttackPauseDuration) {
            isPausedBeforeAttack = false;
            myAttackPauseCounter = 0;
        }
    }

    /**
     * Checks if the Glyphid is paused before attacking.
     *
     * @return true if the Glyphid is paused before attacking, false otherwise
     */
    public boolean isPausedBeforeAttack() {
        return isPausedBeforeAttack;
    }

    /**
     * Moves the Glyphid towards the player if they are out of attack range.
     *
     * @param thePlayer the player character to move towards
     */
    public void moveToPlayer(final Dwarf thePlayer) {
        double distance = Math.sqrt(Math.pow(thePlayer.getX() - getX(), 2) + Math.pow(thePlayer.getY() - getY(), 2));
        if (distance > myAttackRange) {
            Angle angleToPlayer = new Angle(getX(), getY(), thePlayer.getX(), thePlayer.getY());
            addForce(new Force(angleToPlayer, getMoveSpeed(), 0.4));
        }
    }

    /**
     * Receives an attack and processes it. Plays appropriate sounds based on the type of attack.
     *
     * @param theAttack the attack received by the Glyphid
     */
    @Override
    public void receiveAttack(final Attack theAttack) {
        int oldHealth = super.getHealth();
        super.receiveAttack(theAttack);

        if (theAttack instanceof MeleeAttack) {
            GameLoop.getInstance().addDrawData("sound:PickaxeImpact");
        }

        // oldHealth used so shotguns do not cause multiple death sounds
        if (super.getHealth() <= 0 && oldHealth > 0) {
            GameLoop.getInstance().addDrawData("sound:GlyphidDeath");
        }
    }

    /**
     * Updates the state of the Glyphid. Handles movement towards the player, pausing before attacking, and playing sounds.
     *
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean update() {
        boolean flag = super.update();
        Dwarf player = GameLoop.getInstance().getPlayer();
        if (player == null) {
            return super.update();
        }

        double distance = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));

        if (isPausedBeforeAttack) {
            incrementAttackPauseCounter();
        } else {
            if (distance <= myAttackRange) {
                if (attemptAttack(player.getX(), player.getY())) {
                    isPausedBeforeAttack = true;
                }
            } else {
                moveToPlayer(player);
            }
        }

        myRoarCooldown--;
        if (myRoarCooldown < 0) {
            myRoarCooldown = (int)(Math.random() * 400) + 200;
        }

        return flag;
    }

    /**
     * Gets the draw data for the Glyphid, including the roar sound if applicable.
     *
     * @return an array of strings representing the draw data
     */
    @Override
    public String[] getDrawData() {
        if (myRoar != null && myRoarCooldown == 0) {
            ArrayList<String> temp = new ArrayList<>(List.of(super.getDrawData()));
            temp.add("sound:" + myRoar);
            return temp.toArray(new String[0]);
        } else {
            return super.getDrawData();
        }
    }
}

