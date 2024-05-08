package Model.Glyphid;

import Model.Weapon.Weapon;

public class Swarmer extends Grunt {
    public Swarmer(String theName, double theX, double theY, int theHealth, int theWidth, int theHeight, double theMoveSpeed, Weapon theWeapon, int fireTimer) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, fireTimer);
    }
}
