package Model.Glyphid;

import Model.Force;
import Model.Glyphid.Grunt;
import Model.Weapon.Weapon;

/**
 * Represents a Praetorian, a tank-like enemy type in the game.
 * Praetorians have high health, high knockback resistance, and slower movement.
 */
public class Praetorian extends Glyphid {

    /** The resistance to force applied to the Praetorian. */
    private double myForceResistance;

    /**
     * Constructs a new Praetorian with the specified parameters.
     *
     * @param theName the name of the Praetorian
     * @param theX the initial x-coordinate of the Praetorian
     * @param theY the initial y-coordinate of the Praetorian
     * @param theHealth the health points of the Praetorian
     * @param theWidth the width of the Praetorian
     * @param theHeight the height of the Praetorian
     * @param theMoveSpeed the movement speed of the Praetorian
     * @param theWeapon the weapon the Praetorian uses
     * @param theAttackRange the range within which the Praetorian can attack
     * @param theAttackPauseDuration the duration the Praetorian pauses before attacking
     * @param theForceResistance the resistance to force applied to the Praetorian
     * @param theRoar the roar sound of the Praetorian
     */
    public Praetorian(final String theName, final double theX, final double theY, final int theHealth,
                      final int theWidth, final int theHeight, final double theMoveSpeed,
                      Weapon theWeapon, double theAttackRange, int theAttackPauseDuration, double theForceResistance, String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration, theRoar);
        myForceResistance = theForceResistance;
    }

    /**
     * Adds a force to the Praetorian, taking into account its force resistance.
     *
     * @param theForce the force to be applied to the Praetorian
     */
    @Override
    public void addForce(final Force theForce) {
        super.addForce(new Force(theForce.getAngle(), theForce.getStrength() * myForceResistance, theForce.getDecayRate()));
    }
}
