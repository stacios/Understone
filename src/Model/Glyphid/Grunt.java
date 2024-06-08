package Model.Glyphid;

import Model.Weapon.Weapon;

/**
 * Represents a Grunt, a basic enemy type in the game.
 * Grunts move towards the player and attack.
 */
public class Grunt extends Glyphid {

    /**
     * Constructs a new Grunt with the specified parameters.
     *
     * @param theName the name of the Grunt
     * @param theX the initial x-coordinate of the Grunt
     * @param theY the initial y-coordinate of the Grunt
     * @param theHealth the health points of the Grunt
     * @param theWidth the width of the Grunt
     * @param theHeight the height of the Grunt
     * @param theMoveSpeed the movement speed of the Grunt
     * @param theWeapon the weapon the Grunt uses
     * @param theAttackRange the range within which the Grunt can attack
     * @param theAttackPauseDuration the duration the Grunt pauses before attacking
     * @param theRoar the roar sound of the Grunt
     */
    public Grunt(final String theName, final double theX, final double theY, final int theHealth,
                 final int theWidth, final int theHeight, final double theMoveSpeed,
                 final Weapon theWeapon, final double theAttackRange, final int theAttackPauseDuration, final String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration, theRoar);
    }
}



