package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.Weapon;

public class AcidSpitter extends Glyphid {

    public AcidSpitter(String theName, double theX, double theY, int theHealth,
                       int theWidth, int theHeight, double theMoveSpeed,
                       Weapon theWeapon, double theAttackRange, int attackPauseDuration) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, attackPauseDuration);
    }

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
        double strafeAngle = Math.random() < 0.5 ? Math.PI / 2 : -Math.PI / 2;
        Angle strafeDirection = new Angle(angleToPlayer.getRadians() + strafeAngle);
        addForce(new Force(strafeDirection, getMoveSpeed(), 0.4));

        return super.update();
    }
}

