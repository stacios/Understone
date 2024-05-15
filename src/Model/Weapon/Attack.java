package Model.Weapon;

import Controller.Drawable;
import Model.Angle;
import Model.Collidable;
import Model.Force;

public abstract class Attack implements Collidable, Cloneable, Drawable {

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
        this.myDamage = theDamage;
        this.myWidth = theWidth;
        this.myHeight = theHeight;
        this.myKnockBackStrength = theKnockBackStrength;
        this.myActive = true;
        this.myInitialDistance = theInitialDistance;
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


    public void setPosition(double theX, double theY) {
        setPosition(theX, theY, null);
    }
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

    public void deactivate() {
        myActive = false;
    }

    @Override
    public Attack clone() {
        try {

            return (Attack) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


