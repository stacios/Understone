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
    private static final long serialVersionUID = 1L;
    private String myName;
    private int myHealth;
    private int myMaxHealth;
    private double myX;
    private double myY;
    private int myWidth;
    private int myHeight;
    private ArrayList<Force> myForces;
    private double myMoveSpeed;
    private Weapon myWeapon;

    public Character(String theName, double theX, double theY,
                     int theHealth, int theWidth, int theHeight,
                     double theMoveSpeed, Weapon theWeapon) {
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

    public void setX(double theX) {
        if (theX < 0) {
            throw new IllegalArgumentException("X coordinate cannot be negative");
        }
        myX = theX;
    }

    public void setY(double theY) {
        if (theY < 0) {
            throw new IllegalArgumentException("Y coordinate cannot be negative");
        }
        myY = theY;
    }

    public void setHealth(int theHealth) {
        if (theHealth < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        myHealth = theHealth;
    }

    public void setMaxHealth(int theMaxHealth) {
        if (theMaxHealth < 0) {
            throw new IllegalArgumentException("Max health cannot be negative");
        }
        myMaxHealth = theMaxHealth;
    }

    public void setWidth(int theWidth) {
        if (theWidth <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        myWidth = theWidth;
    }

    public void setHeight(int theHeight) {
        if (theHeight <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        myHeight = theHeight;
    }

    public void setMoveSpeed(double theMoveSpeed) {
        if (theMoveSpeed < 0) {
            throw new IllegalArgumentException("Move speed cannot be negative");
        }
        myMoveSpeed = theMoveSpeed;
    }

    public void setName(String theName) {
        if (theName == null || theName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        myName = theName;
    }

    public void setWeapon(Weapon theWeapon) {
        myWeapon = theWeapon;
    }

    public boolean colliding(Collidable theOther) {
        return Collidable.super.colliding(theOther);
    }

    /**
     * Update the character every game tick.
     * Includes character AI and recieving forces.
     * Returns true if the character should be deleted.
     */
    public boolean update() {
        this.receiveForces();
        myWeapon.update();
        //Additional update logic TBA
        return false;
    }

    public void addForce(Force theForce) {
        myForces.add(theForce);
    }

    public boolean attemptAttack(double theTargetX, double theTargetY) {
        if (myWeapon != null) {
            Angle attackAngle = new Angle(myX, myY, theTargetX, theTargetY);
            return myWeapon.attemptAttack(this, attackAngle);
        }
        return false;
    }

    public boolean receiveAttack(Attack theAttack) {
        if (this.colliding(theAttack)) {
            myHealth -= theAttack.getDamage();
            this.addForce(theAttack.getKnockBack());
            return true;
        }
        return false;
    }

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

    public double getX() {
        return myX;
    }

    public double getY() {
        return myY;
    }

    public double getMoveSpeed() {
        return myMoveSpeed;
    }

    @Override
    public String[] getDrawData() {
        List<String> temp = new LinkedList<>();
        temp.add("image:" + myName + ":" + myX + ":" + myY + ":" + myWidth + ":" + myHeight);
        temp.addAll(List.of(myWeapon.getDrawData()));
        return temp.toArray(new String[0]);
    }

    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, myWidth, myHeight};
    }

    public Attack[] getPendingAttacks() {
        return myWeapon.getPendingAttacks();
    }

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
}
