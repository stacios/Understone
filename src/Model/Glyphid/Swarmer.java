package Model.Glyphid;

import Model.Weapon.Weapon;

public class Swarmer extends Glyphid {
    private static final double ATTACK_RANGE = 150.0;
    private static final double SPEED_MULTIPLIER = 1.5/2; // Faster speed than Grunts

    public Swarmer(String theName, double theX, double theY, int theHealth,
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
}

