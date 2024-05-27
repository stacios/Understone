package Model.Glyphid;

import Model.Weapon.Weapon;

public class Rock extends Glyphid {
    private static final double ATTACK_RANGE = 0.0;
    private static final double SPEED_MULTIPLIER = 0.0;

    public Rock(String theName, double theX, double theY, int theHealth,
                int theWidth, int theHeight, double theMoveSpeed,
                Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }

    @Override
    public double getAttackRange() {
        return ATTACK_RANGE;
    }

    @Override
    public double getSpeedMultiplier() {
        return SPEED_MULTIPLIER;
    }

    @Override
    public boolean update() {
        // Rocks do not move
        return super.update();
    }
}


