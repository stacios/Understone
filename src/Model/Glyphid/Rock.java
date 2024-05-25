package Model.Glyphid;

import Model.Glyphid.Glyphid;
import Model.Weapon.Weapon;

public class Rock extends Glyphid {
    public Rock(String theName, double theX, double theY, int theHealth,
                int theWidth, int theHeight, double theMoveSpeed,
                Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }


}

