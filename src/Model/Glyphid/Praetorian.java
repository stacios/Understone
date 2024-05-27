package Model.Glyphid;

import Model.Force;
import Model.Glyphid.Grunt;
import Model.Weapon.Weapon;

public class Praetorian extends Grunt {
    public Praetorian(String theName, double theX, double theY, int theHealth, int theWidth, int theHeight, double theMoveSpeed, Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }


    @Override
    public void addForce(Force theForce) {
        super.addForce(new Force(theForce.getAngle(), theForce.getStrength() * .25));
    }
}
