package model;

public class ExplosiveAttack extends ProjectileAttack{
    private double myRadius; // Radius of the explosion effect

    public ExplosiveAttack(String theName, int theDamage, double theX, double theY,
                           double theWidth, double theHeight, Force theKnockBack,
                           Angle theAngle, double theVelocity, double theRadius) {
        super(theName, theDamage, theX, theY, theWidth, theHeight, theKnockBack, theAngle, theVelocity);
        this.myRadius = theRadius;
    }

    public double getRadius() {
        return myRadius;
    }

    public void setRadius(double radius) {
        this.myRadius = radius;
    }

    @Override
    public void update() {
        super.update();
        // Logic to handle explosion effect can be implemented here
    }
}
