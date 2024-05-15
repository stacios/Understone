package Model.Glyphid;

import Model.Character;
import Model.Weapon.Weapon;

public abstract class Glyphid extends Character {
    protected int myFireTimer;

    public Glyphid(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon, int fireTimer) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        this.myFireTimer = fireTimer;
    }

    public abstract void attack();


    protected void updateFireTimer() {
        if (myFireTimer > 0) {
            myFireTimer--;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " firetimer: " + myFireTimer;
    }
}

