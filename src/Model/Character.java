package Model;

import java.util.ArrayList;

public abstract class Character {
    protected String myName;
    protected int myHealth;
    protected int myMaxHealth;
    protected double myX;
    protected double myY;
    protected ArrayList<Force> myForces;
    protected HitBox myHitbox;
    protected double myMoveSpeed;
    protected Weapon myWeapon;

    public Character(String theName, double theX, double theY,
                     int theHealth, double theWidth, double theHeight,
                     double theMoveSpeed, Weapon theWeapon) {
        this.myName = theName;
        this.myX = theX;
        this.myY = theY;
        this.myHealth = theHealth;
        this.myMaxHealth = theHealth;
        this.myMoveSpeed = theMoveSpeed;
        this.myWeapon = theWeapon;
        this.myHitbox = new HitBox(theX, theY, theWidth, theHeight);
        this.myForces = new ArrayList<>();
    }

    public boolean colliding(Character other) {
        return this.myHitbox.colliding(other.myHitbox);
    }

    public boolean colliding(HitBox other) {
        return this.myHitbox.colliding(other);
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
        if (this.colliding(attack.getHitBox())) {
            this.myHealth -= attack.getDamage();
            this.addForce(attack.getKnockBack());
            return true;
        }
        return false;
    }

    private void receiveForces() {
        for (Force force : new ArrayList<>(myForces)) {
            if (!force.update()) {
                myForces.remove(force);
            } else {
                myX += force.getXStrength();
                myY += force.getYStrength();
            }
        }
    }
    public HitBox getHitBox(){
        return this.myHitbox;
    }

    public double getX(){
        return this.myX;
    }

    public double getY(){
        return this.myY;
    }
}
