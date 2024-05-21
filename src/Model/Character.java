package Model;

import Controller.Drawable;
import Model.Weapon.Attack;
import Model.Weapon.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
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
        this.myForces = new ArrayList<>();
    }

    public void setX(double x) {
        if (x < 0) {
            throw new IllegalArgumentException("X coordinate cannot be negative");
        }
        this.myX = x;
    }

    public void setY(double y) {
        if (y < 0) {
            throw new IllegalArgumentException("Y coordinate cannot be negative");
        }
        this.myY = y;
    }

    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.myHealth = health;
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth < 0) {
            throw new IllegalArgumentException("Max health cannot be negative");
        }
        this.myMaxHealth = maxHealth;
    }

    public void setWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        this.myWidth = width;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        this.myHeight = height;
    }

    public void setMoveSpeed(double moveSpeed) {
        if (moveSpeed < 0) {
            throw new IllegalArgumentException("Move speed cannot be negative");
        }
        this.myMoveSpeed = moveSpeed;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.myName = name;
    }

    public void setWeapon(Weapon weapon) {
        this.myWeapon = weapon;
    }

    public boolean colliding(Collidable other) {
        return Collidable.super.colliding(other);
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

    public void addForce(Force force) {
        this.myForces.add(force);
    }

    public boolean attemptAttack(double targetX, double targetY) {
        if (this.myWeapon != null) {
            Angle attackAngle = new Angle(myX, myY, targetX, targetY);
            return this.myWeapon.attemptAttack(this, attackAngle);
        }
        return false;
    }

    public boolean receiveAttack(Attack attack) {
        if (this.colliding(attack)) {
            this.myHealth -= attack.getDamage();
            this.addForce(attack.getKnockBack());
            return true;
        }
        return false;
    }

    private void receiveForces() {
        for (Force force : new ArrayList<>(myForces)) {
            myX += force.getXStrength();
            myY += force.getYStrength();
            if (!force.update()) {
                myForces.remove(force);
            }
        }
    }

    public double getX() {
        return this.myX;
    }

    public double getY() {
        return this.myY;
    }

    public double getMoveSpeed() {
        return myMoveSpeed;
    }

    @Override
    public String[] getDrawData() {
        return new String[]{"image:" + myName + ":" + myX + ":" + myY + ":" + myWidth + ":" + myHeight};
    }

    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, myWidth, myHeight};
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
