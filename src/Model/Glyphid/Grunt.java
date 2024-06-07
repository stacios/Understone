package Model.Glyphid;

import Model.Weapon.Weapon;

public class Grunt extends Glyphid {

    public Grunt(String theName, double theX, double theY, int theHealth,
                 int theWidth, int theHeight, double theMoveSpeed,
                 Weapon theWeapon, double theAttackRange, int theAttackPauseDuration, String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration, theRoar);
    }
}



