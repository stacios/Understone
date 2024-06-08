package Model;

import Controller.Drawable;
import Model.Weapon.Attack;
import Model.Weapon.Weapon;
import Model.Spaces.*;
import View.Display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a character in the game. Parent class for Dwarf and Glyphids.
 */
public abstract class Character implements Drawable, Collidable, Serializable {
    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the character.
     */
    private String myName;

    /**
     * The current health of the character.
     */
    private int myHealth;

    /**
     * The maximum health of the character.
     */
    private int myMaxHealth;

    /**
     * The x-coordinate of the character.
     */
    private double myX;

    /**
     * The y-coordinate of the character.
     */
    private double myY;

    /**
     * The width of the character.
     */
    private int myWidth;

    /**
     * The height of the character.
     */
    private int myHeight;

    /**
     * The list of forces acting on the character.
     */
    private ArrayList<Force> myForces;

    /**
     * The movement speed of the character.
     */
    private double myMoveSpeed;

    /**
     * The weapon of the character.
     */
    private Weapon myWeapon;

    /**
     * Constructs a Character with the specified parameters.
     *
     * @param theName      the name of the character
     * @param theX         the x-coordinate of the character
     * @param theY         the y-coordinate of the character
     * @param theHealth    the health of the character
     * @param theWidth     the width of the character
     * @param theHeight    the height of the character
     * @param theMoveSpeed the movement speed of the character
     * @param theWeapon    the weapon of the character
     */
    public Character(final String theName, final double theX, final double theY,
                     final int theHealth,final int theWidth,final int theHeight,
                     final double theMoveSpeed, Weapon theWeapon) {
        setName(theName);
        setX(theX);
        setY(theY);
        setHealth(theHealth);
        setMaxHealth(theHealth);
        setWidth(theWidth);
        setHeight(theHeight);
        setMoveSpeed(theMoveSpeed);
        setWeapon(theWeapon);
        myForces = new ArrayList<>();
    }

    /**
     * Sets the x-coordinate of the character.
     *
     * @param theX the x-coordinate to set
     * @throws IllegalArgumentException if the x-coordinate is negative
     */
    public void setX(final double theX) {
        if (theX < 0) {
            throw new IllegalArgumentException("X coordinate cannot be negative");
        }
        myX = theX;
    }

    /**
     * Sets the y-coordinate of the character.
     *
     * @param theY the y-coordinate to set
     * @throws IllegalArgumentException if the y-coordinate is negative
     */
    public void setY(final double theY) {
        if (theY < 0) {
            throw new IllegalArgumentException("Y coordinate cannot be negative");
        }
        myY = theY;
    }

    /**
     * Adds health to the character, up to the maximum health.
     *
     * @param theHealth the amount of health to add
     */
    public void addHealth(final int theHealth) {
        if (myHealth + theHealth < myMaxHealth) {
            myHealth += theHealth;
        } else {
            myHealth = myMaxHealth;
        }
    }

    /**
     * Sets the health of the character.
     *
     * @param theHealth the health to set
     * @throws IllegalArgumentException if the health is negative
     */
    public void setHealth(final int theHealth) {
        if (theHealth < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }

        myHealth = theHealth;
    }

    /**
     * Sets the maximum health of the character.
     *
     * @param theMaxHealth the maximum health to set
     * @throws IllegalArgumentException if the maximum health is negative
     */
    public void setMaxHealth(final int theMaxHealth) {
        if (theMaxHealth < 0) {
            throw new IllegalArgumentException("Max health cannot be negative");
        }
        myMaxHealth = theMaxHealth;
    }

    /**
     * Sets the width of the character.
     *
     * @param theWidth the width to set
     * @throws IllegalArgumentException if the width is not positive
     */
    public void setWidth(final int theWidth) {
        if (theWidth <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        myWidth = theWidth;
    }

    /**
     * Sets the height of the character.
     *
     * @param theHeight the height to set
     * @throws IllegalArgumentException if the height is not positive
     */
    public void setHeight(final int theHeight) {
        if (theHeight <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        myHeight = theHeight;
    }

    /**
     * Sets the movement speed of the character.
     *
     * @param theMoveSpeed the movement speed to set
     * @throws IllegalArgumentException if the movement speed is negative
     */
    public void setMoveSpeed(final double theMoveSpeed) {
        if (theMoveSpeed < 0) {
            throw new IllegalArgumentException("Move speed cannot be negative");
        }
        myMoveSpeed = theMoveSpeed;
    }

    /**
     * Sets the name of the character.
     *
     * @param theName the name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(final String theName) {
        if (theName == null || theName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        myName = theName;
    }

    /**
     * Sets the weapon of the character.
     *
     * @param theWeapon the weapon to set
     */
    public void setWeapon(final Weapon theWeapon) {
        myWeapon = theWeapon;
    }

    /**
     * Updates the character every game tick. Includes character AI and receiving forces.
     * Returns true if the character should be deleted.
     *
     * @return true if the character should be deleted, false otherwise
     */
    public boolean update() {
        receiveForces();
        myWeapon.update();
        return myHealth <= 0;
    }

    /**
     * Adds a force to the character.
     *
     * @param theForce the force to add
     */
    public void addForce(Force theForce) {
        myForces.add(theForce);
    }

    /**
     * Attempts an attack at the specified target coordinates.
     *
     * @param theTargetX the x-coordinate of the target
     * @param theTargetY the y-coordinate of the target
     * @return true if the attack was successful, false otherwise
     */
    public boolean attemptAttack(double theTargetX, double theTargetY) {
        if (myWeapon != null) {
            Angle attackAngle = new Angle(myX, myY, theTargetX, theTargetY);
            return myWeapon.attemptAttack(this, attackAngle);
        }
        return false;
    }

    /**
     * Receives an attack and applies damage and knockback to the character.
     *
     * @param theAttack the attack to receive
     */
    public void receiveAttack(Attack theAttack) {
        myHealth -= theAttack.getDamage();
        addForce(theAttack.getKnockBack());
    }

    /**
     * Receives and processes all forces acting on the character.
     */
    private void receiveForces() {
        double newX;
        double newY;
        for (Force force : new ArrayList<>(myForces)) {
            newX = myX + force.getXStrength();
            newY = myY + force.getYStrength();
            if (newX + myWidth/2 < Display.getInstance().getWidth() - Room.WALL_THICKNESS
                    && newX - myWidth/2 > Room.WALL_THICKNESS) {
                myX = newX;
            }
            if (newY + myHeight/2 < Display.getInstance().getHeight() - Room.WALL_THICKNESS
                    && newY - myHeight/2 > Room.WALL_THICKNESS) {
                myY = newY;
            }
            if (!force.update()) {
                myForces.remove(force);
            }
        }
    }

    /**
     * Gets the x-coordinate of the character.
     *
     * @return the x-coordinate of the character
     */
    public double getX() {
        return myX;
    }

    /**
     * Gets the y-coordinate of the character.
     *
     * @return the y-coordinate of the character
     */
    public double getY() {
        return myY;
    }

    /**
     * Gets the movement speed of the character.
     *
     * @return the movement speed of the character
     */
    public double getMoveSpeed() {
        return myMoveSpeed;
    }

    /**
     * Gets the draw data for the character.
     *
     * @return an array of strings representing the draw data
     */
    @Override
    public String[] getDrawData() {
        final List<String> temp = new LinkedList<>();
        temp.add("image:" + myName + ":" + myX + ":" + myY + ":" + myWidth + ":" + myHeight);
        temp.addAll(List.of(myWeapon.getDrawData()));
        return temp.toArray(new String[0]);
    }

    /**
     * Gets the hitbox of the character.
     *
     * @return an array of integers representing the hitbox
     */
    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, myWidth, myHeight};
    }

    /**
     * Gets the pending attacks of the character.
     *
     * @return an array of pending attacks
     */
    public Attack[] getPendingAttacks() {
        return myWeapon.getPendingAttacks();
    }

    /**
     * Returns a string representation of the character.
     *
     * @return a string representation of the character
     */
    @Override
    public String toString() {
        return "Printing " + myName + ": {" +
                "X: " + myX +
                ", Y: " + myY +
                ", Health: " + myHealth +
                ", MaxHealth: " + myMaxHealth +
                ", MoveSpeed: " + myMoveSpeed +
                ", Width: " + myWidth +
                ", Height: " + myHeight +
                '}';
    }

    /**
     * Gets the health of the character.
     *
     * @return the health of the character
     */
    public int getHealth() {
        return myHealth;
    }

    /**
     * Gets the maximum health of the character.
     *
     * @return the maximum health of the character
     */
    public int getMaxHealth() {
        return myMaxHealth;
    }

    /**
     * Gets the weapon of the character.
     *
     * @return the weapon of the character
     */
    public Weapon getWeapon() {
        return myWeapon;
    }

    /**
     * Gets the name of the character.
     *
     * @return the name of the character
     */
    public String getName() {
        return myName;
    }
}
