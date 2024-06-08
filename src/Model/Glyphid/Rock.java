package Model.Glyphid;

import Model.Character;
import Model.Force;
import Model.GameLoop;
import Model.Glyphid.Glyphid;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;

/**
 * Represents a Rock, an immovable object or character in the game.
 * Rocks do not take knockback force and can respond to melee attacks.
 */
public class Rock extends Character {

    /**
     * Constructs a new Rock with the specified parameters.
     *
     * @param theName the name of the Rock
     * @param theX the initial x-coordinate of the Rock
     * @param theY the initial y-coordinate of the Rock
     * @param theHealth the health points of the Rock
     * @param theWidth the width of the Rock
     * @param theHeight the height of the Rock
     * @param theMoveSpeed the movement speed of the Rock
     * @param theWeapon the weapon the Rock uses
     */
    public Rock(final String theName, final double theX, final double theY, final int theHealth,
                final int theWidth, final int theHeight, final double theMoveSpeed,
                final Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }

    /**
     * Overrides the addForce method to prevent the Rock from taking any knockback force.
     *
     * @param theForce the force to be applied to the Rock
     */
    @Override
    public void addForce(final Force theForce) {
        theForce.setStrength(0);
    }


    /**
     * Receives an attack and processes it. If the attack is a melee attack and the Rock's name is "Heal",
     * it plays a healing sound.
     *
     * @param theAttack the attack received by the Rock
     */
    @Override
    public void receiveAttack(final Attack theAttack) {
        if (theAttack instanceof MeleeAttack) {
            if (getName().equals("Heal"))
                GameLoop.getInstance().addDrawData("sound:Heal");

            super.receiveAttack(theAttack);
        }
    }
}

