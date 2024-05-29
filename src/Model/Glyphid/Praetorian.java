package Model.Glyphid;

import Model.Force;
import Model.Angle;
import Model.Weapon.Weapon;

public class Praetorian extends Glyphid {
    private double myForceResistance;

    public Praetorian(String theName, double theX, double theY, int theHealth,
                      int theWidth, int theHeight, double theMoveSpeed,
                      Weapon theWeapon, double theAttackRange, int theAttackPauseDuration, double theForceResistance) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration);
        myForceResistance = theForceResistance;
    }

    @Override
    public void addForce(Force theForce) {
        double validDecayRate = Math.max(0, Math.min(1, theForce.getStrength() * myForceResistance));
        Force reducedForce = new Force(theForce.getAngle(), theForce.getStrength() * myForceResistance, validDecayRate);
        super.addForce(reducedForce);
    }
}



