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

    @Override
    public abstract void update();

    protected void updateFireTimer() {
        if (myFireTimer > 0) {
            myFireTimer--;
        }
    }
}

