package Model.Weapon;

import Controller.Drawable;
import Model.Angle;
import Model.Collidable;
import Model.Force;

import java.io.Serializable;

/**
 * Representation of an attack. Attacks are bullets flying through the air, or the swings of melee weapons.
 * A template of an attack is contained within each weapon to be copied and added to the active room.
 * Has damage, position, width, height, knockback strength, and initial distance (static distance initially added to the set position).
 */
public abstract class Attack implements Collidable, Cloneable, Drawable, Serializable {
    private static final long serialVersionUID = 6L;
    private final int myDamage;
    private double myX;
    private double myY;

    private final int myWidth;
    private final int myHeight;
    private final double myKnockBackStrength;
    private Angle myAngle;
    private final double myInitialDistance;
    private boolean myActive;

    public Attack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance) {
        if (theDamage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        if (theWidth <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        if (theHeight <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        if (theKnockBackStrength < 0) {
            throw new IllegalArgumentException("KnockBack strength cannot be negative");
        }

        this.myDamage = theDamage;
        this.myWidth = theWidth;
        this.myHeight = theHeight;
        this.myKnockBackStrength = theKnockBackStrength;
        this.myInitialDistance = theInitialDistance;
        this.myActive = true;
    }

    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, myWidth, myHeight};
    }

    @Override
    public boolean colliding(Collidable other) {
        return myActive && colliding(other);
    }

    // Update later!!!
    public abstract boolean update();


    public Angle getAngle() {
        return this.myAngle;
    }

    public int getDamage(){
        return this.myDamage;
    }

    public Force getKnockBack(){
        return new Force(myAngle, myKnockBackStrength);
    }


    public double getX() {
        return this.myX;
    }

    public double getY() {
        return this.myY;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the position and angle of the attack. Inacts the jump of intiialDistance.
     */
    public void setPosition(double theX, double theY) {
        setPosition(theX, theY, null);
    }
    /**
     * Sets the position and angle of the attack. Inacts the jump of intiialDistance.
     */
    public void setPosition(double theX, double theY, Angle theAngle) {

        if (myAngle == null && theAngle != null) {
            this.myAngle = theAngle;
            double[] temp = theAngle.getComp();
            this.myX = theX + temp[0] * myInitialDistance;
            this.myY = theY + temp[1] * myInitialDistance;
        }
        else {
            this.myX = theX;
            this.myY = theY;
        }

    }
    /**
     * Makes it so that the attack can no longer collide with characters.
     */
    public void deactivate() {
        myActive = false;
    }
    /**
     * Creates a clone of the attack to be added to the active room.
     * Remember to set the position of the clone, as this should not be set for the attack template being cloned.
     */
    @Override
    public Attack clone() {
        try {

            return (Attack) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


