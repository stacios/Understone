package Model.Glyphids;

import Model.Weapon;

public class Swarmer extends Grunt{
    public Swarmer(String theName, double theX, double theY, int theHealth, double theWidth, double theHeight, double theMoveSpeed, Weapon theWeapon, int fireTimer) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, fireTimer);
    }
}
