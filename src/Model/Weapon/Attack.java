package Model.Weapon;

import Controller.Drawable;
import Model.Angle;
import Model.Collidable;
import Model.Force;
import View.Display;

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
    private boolean myCollided;

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

        myDamage = theDamage;
        myWidth = theWidth;
        myHeight = theHeight;
        myKnockBackStrength = theKnockBackStrength;
        myInitialDistance = theInitialDistance;
        myCollided = false;
    }

    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, myWidth, myHeight};
    }

    @Override
    public boolean colliding(Collidable theOther) {
        return colliding(theOther);
    }

    public boolean update() {
        return myCollided ||
                myX > Display.getInstance().getWidth() || myX < 0 ||
                myY > Display.getInstance().getHeight() || myY < 0;
    }


    public Angle getAngle() {
        return myAngle;
    }

    public int getDamage(){
        return myDamage;
    }

    public Force getKnockBack(){
        return new Force(myAngle, myKnockBackStrength, .4);
    }


    public double getX() {
        return myX;
    }

    public double getY() {
        return myY;
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
            myAngle = theAngle;
            double[] temp = theAngle.getComp();
            myX = theX + temp[0] * myInitialDistance;
            myY = theY + temp[1] * myInitialDistance;
        }
        else {
            myX = theX;
            myY = theY;
        }

    }

    public void setAngle(Angle theAngle) {
        myAngle = theAngle;
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

    public void collided() {
        myCollided = true;
    }
}


