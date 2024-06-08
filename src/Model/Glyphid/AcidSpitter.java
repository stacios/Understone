package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.Weapon;

/**
 * Represents an AcidSpitter enemy in the game.
 * AcidSpitters are ranged enemies that periodically shoot acid ball projectiles at the player.
 */
public class AcidSpitter extends Glyphid {

    /**
     * Constructs a new AcidSpitter with the specified parameters.
     *
     * @param theName the name of the AcidSpitter
     * @param theX the initial x-coordinate of the AcidSpitter
     * @param theY the initial y-coordinate of the AcidSpitter
     * @param theHealth the health points of the AcidSpitter
     * @param theWidth the width of the AcidSpitter
     * @param theHeight the height of the AcidSpitter
     * @param theMoveSpeed the movement speed of the AcidSpitter
     * @param theWeapon the weapon the AcidSpitter uses
     * @param theAttackRange the range within which the AcidSpitter can attack
     * @param theAttackPauseDuration the duration the AcidSpitter pauses before attacking
     * @param theRoar the roar sound of the AcidSpitter
     */
    public AcidSpitter(final String theName, final double theX, final double theY, final int theHealth,
                       final int theWidth, final int theHeight, final double theMoveSpeed,
                       final Weapon theWeapon, final double theAttackRange, final int theAttackPauseDuration, final String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration, theRoar);
    }

    /**
     * Updates the state of the AcidSpitter. The AcidSpitter will move towards the player if they are out of attack range,
     * or away from the player if they are within attack range. Additionally, it will strafe left or right.
     *
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean update() {
        Dwarf player = GameLoop.getInstance().getPlayer();
        if (player == null) {
            return super.update();
        }

        double distance = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));
        Angle angleToPlayer = new Angle(getX(), getY(), player.getX(), player.getY());

        if (distance < getAttackRange()) {
            // Move away from the player
            if (angleToPlayer != null) {
                Angle moveAwayAngle = new Angle(angleToPlayer.getRadians() + Math.PI);
                addForce(new Force(moveAwayAngle, getMoveSpeed(), 0.4));
            }
        } else {
            // Move towards the player
            if (angleToPlayer != null) {
                addForce(new Force(angleToPlayer, getMoveSpeed(), 0.4));
            }
        }

        // Strafe logic
        final double strafeAngle = Math.random() < 0.5 ? Math.PI / 2 : -Math.PI / 2;
        final Angle strafeDirection = new Angle(angleToPlayer.getRadians() + strafeAngle);
        addForce(new Force(strafeDirection, getMoveSpeed(), 0.4));

        return super.update();
    }
}

