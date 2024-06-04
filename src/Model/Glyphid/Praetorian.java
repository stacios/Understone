package Model.Glyphid;

import Model.Force;
import Model.Glyphid.Grunt;
import Model.Weapon.Weapon;

public class Praetorian extends Glyphid {
    private double myForceResistance;

    public Praetorian(String theName, double theX, double theY, int theHealth,
                      int theWidth, int theHeight, double theMoveSpeed,
                      Weapon theWeapon, double theAttackRange, int theAttackPauseDuration, double theForceResistance, String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration, theRoar);
        myForceResistance = theForceResistance;
    }


    @Override
    public void addForce(Force theForce) {
        super.addForce(new Force(theForce.getAngle(), theForce.getStrength() * myForceResistance, theForce.getDecayRate()));
    }


}
