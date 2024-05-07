package Model;

import Controller.DrawData;
import Controller.Drawable;

import java.util.ArrayList;

public abstract class Character implements Drawable, Collidable {
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
        this.myName = theName;
        this.myX = theX;
        this.myY = theY;
        this.myHealth = theHealth;
        this.myMaxHealth = theHealth;
        this.myMoveSpeed = theMoveSpeed;
        this.myWeapon = theWeapon;
        this.myWidth = theWidth;
        this.myHeight = theHeight;
        this.myForces = new ArrayList<>();
    }

    public boolean colliding(Collidable other) {
        return Collidable.super.colliding(other);
    }

    public void update() {
        this.receiveForces();
        //Additional update logic TBA
    }

    public void addForce(Force force) {
        this.myForces.add(force);
    }

    public void setWeapon(Weapon weapon) {
        this.myWeapon = weapon;
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

    public double getX(){
        return this.myX;
    }

    public double getY(){
        return this.myY;
    }

    public double getMoveSpeed() {
        return myMoveSpeed;
    }

    @Override
    public DrawData getDrawData() {
        return new DrawData(myName, null, myX, myY, myWidth, myHeight);
    }

    @Override
    public int[] getHitbox() {
        return new int[]{(int) myX, (int) myY, myWidth, myHeight};
    }
}
