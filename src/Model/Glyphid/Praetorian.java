package Model.Glyphid;

import Model.Weapon.Weapon;

public class Praetorian extends Glyphid {
    private static final double ATTACK_RANGE = 700.0;
    private static final double SPEED_MULTIPLIER = 0.8/2;

    public Praetorian(String theName, double theX, double theY, int theHealth,
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

