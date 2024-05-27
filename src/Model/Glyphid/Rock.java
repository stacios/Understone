package Model.Glyphid;

import Model.Weapon.Weapon;

public class Rock extends Glyphid {

    public Rock(String theName, double theX, double theY, int theHealth,
                int theWidth, int theHeight, double theMoveSpeed,
                Weapon theWeapon, double theAttackRange) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange);
    }

    @Override
    public boolean update() {
        // Rocks do not move
        return super.update();
    }
}



