package model;

public class FireAttack extends ProjectileAttack {
    private int myFireTime;

    public FireAttack(String theName, int theDamage, double theX, double theY,
                      double theWidth, double theHeight, Force theKnockBack,
                      Angle theAngle, double theVelocity, int theFireTime) {
        super(theName, theDamage, theX, theY, theWidth, theHeight, theKnockBack, theAngle, theVelocity);
        this.myFireTime = theFireTime;
    }

    public int getFireTime() {
        return myFireTime;
    }

    public void setFireTime(int fireTime) {
        this.myFireTime = fireTime;
    }

    @Override
    public void update() {
        super.update();
        // Logic to handle fire effect can be implemented here
    }
}
