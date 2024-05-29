package Model.Glyphid;

import Model.Weapon.Weapon;

public class Rock extends Glyphid {

    public Rock(String theName, double theX, double theY, int theHealth,
                int theWidth, int theHeight, double theMoveSpeed,
                Weapon theWeapon, double theAttackRange, int theAttackPauseDuration) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration);
    }

    @Override
    public boolean update() {
        // Rocks do not move or attack
        return super.update();
    }
}




