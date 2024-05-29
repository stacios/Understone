package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.Weapon;

public class Swarmer extends Glyphid {
    private double mySpeedMultiplier;

    public Swarmer(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon, double theAttackRange, int theAttackPauseDuration, double theSpeedMultiplier) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration);
        mySpeedMultiplier = theSpeedMultiplier;
    }

    @Override
    public boolean update() {
        Dwarf player = GameLoop.getInstance().getPlayer();
        if (player == null) {
            return super.update();
        }

        double distance = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));

        if (isPausedBeforeAttack()) {
            incrementAttackPauseCounter();
        } else {
            if (distance <= getAttackRange()) {
                if (attemptAttack(player.getX(), player.getY())) {
                    setPausedBeforeAttack(true);
                }
            } else {
                moveToPlayer(player, mySpeedMultiplier);
            }
        }

        return super.update();
    }

    private void moveToPlayer(Dwarf thePlayer, double speedMultiplier) {
        double distance = Math.sqrt(Math.pow(thePlayer.getX() - getX(), 2) + Math.pow(thePlayer.getY() - getY(), 2));
        if (distance > getAttackRange()) {
            Angle angleToPlayer = new Angle(getX(), getY(), thePlayer.getX(), thePlayer.getY());
            addForce(new Force(angleToPlayer, getMoveSpeed() * speedMultiplier, 0.4));
        }
    }
}
