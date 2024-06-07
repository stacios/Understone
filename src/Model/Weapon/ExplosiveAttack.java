package Model.Weapon;

public class ExplosiveAttack extends ProjectileAttack {
    private int myArea; // Radius of the explosion effect
    private int myTriggeredTimer;

    public ExplosiveAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance, double theVelocity, int theArea) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance, theVelocity);
        setArea(theArea);
        myTriggeredTimer = 0;
    }

    public void setArea(int theArea) {
        if (theArea < 0) {
            throw new IllegalArgumentException("Area cannot be negative");
        }
        myArea = theArea;
    }

    public double getArea() {
        return myArea;
    }


    @Override
    public boolean update() {
        if (myTriggeredTimer != 0) {
            myTriggeredTimer++;
            return myTriggeredTimer == 10;
        }
        else {
            return super.update();
        }
    }

    @Override
    public void collided() {
        if (myTriggeredTimer == 0)
            myTriggeredTimer = 1;
    }

    @Override
    public int[] getHitbox() {
        if (myTriggeredTimer == 0) {
            return super.getHitbox();
        }
        else if (myTriggeredTimer == 2) {
            return new int[]{(int)getX(), (int)getY(), myArea, myArea};
        }
        else {
            return new int[]{0,0,0,0};
        }
    }

    @Override
    public String[] getDrawData() {
        if (myTriggeredTimer == 1) {
            return new String[]{"image:Explosion:" + getX() + ":" + getY() +":" + myArea + ":" + myArea,
            "sound:GrenadeExplosion", "screenShake:30:4"};
        }
        else if (myTriggeredTimer > 1) {
            return new String[]{"image:Explosion:" + getX() + ":" + getY() +":" + myArea + ":" + myArea};
        }
        else {
            return super.getDrawData();
        }
    }
}
