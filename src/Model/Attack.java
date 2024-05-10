package Model;

import java.io.Serializable;

public class Attack implements Collidable, Serializable {
    private static final long serialVersionUID = 6L;
    private String myName;
    private int myDamage;
    protected double myX;
    protected double myY;
    private double myWidth;
    private double myHeight;
    private Force myKnockBack;
    protected Angle myAngle;

    public Attack(String theName, int theDamage, double theX, double theY,
                  double theWidth, double theHeight, Force theKnockBack, Angle theAngle) {
        this.myName = theName;
        this.myDamage = theDamage;
        this.myX = theX;
        this.myY = theY;
        this.myWidth = theWidth;
        this.myHeight = theHeight;
        this.myKnockBack = theKnockBack;
        this.myAngle = theAngle;
    }

    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, (int) myWidth, (int) myHeight};
    }

    @Override
    public boolean colliding(Collidable other) {
        return Collidable.super.colliding(other);
    }

    // Update later!!!
    public void update() {
        //movement or effect
    }

   /* // perform the attack
    public void execute(Character origin, Angle attackAngle) {
        this.myX = origin.getX() + Math.cos(attackAngle.getRadians()) * 10; // Example distance
        this.myY = origin.getY() + Math.sin(attackAngle.getRadians()) * 10;
        this.myAngle = attackAngle;

        //check for collision with targets in range and apply damage and effects(?)
        System.out.println("Executing attack: " + myName + " at position (" + myX + ", " + myY + ")");
    }*/

    public Angle getAngle() {
        return this.myAngle;
    }

    public int getDamage(){
        return this.myDamage;
    }

    public Force getKnockBack(){
        return this.myKnockBack;
    }

    public void setAngle(Angle newAngle) {
        this.myAngle = newAngle;
    }

    public double getX() {
        return this.myX;
    }

    public double getY() {
        return this.myY;
    }

    public void setPosition(double x, double y) {
        this.myX = x;
        this.myY = y;
    }
}


