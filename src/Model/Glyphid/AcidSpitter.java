package Model.Glyphid;

import Model.Glyphid.Glyphid;
import Model.Weapon.Weapon;

public class
AcidSpitter extends Glyphid {
    public AcidSpitter(String theName, double theX, double theY, int theHealth,
                       int theWidth, int theHeight, double theMoveSpeed,
                       Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }


    @Override
    public boolean update() {
        // update logic for Acid Spitter

        return super.update();
    }
}

