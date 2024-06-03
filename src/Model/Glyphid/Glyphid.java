package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;
import Model.Character;

public abstract class Glyphid extends Character {
    private double myAttackRange;
    private int myAttackPauseDuration;
    private int myAttackPauseCounter = 0;
    private boolean isPausedBeforeAttack = false;

    public Glyphid(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon, double theAttackRange, int theAttackPauseDuration) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        myAttackRange = theAttackRange;
        myAttackPauseDuration = theAttackPauseDuration;
    }

    public double getAttackRange() {
        return myAttackRange;
    }

    public void setPausedBeforeAttack(boolean thePaused) {
        isPausedBeforeAttack = thePaused;
    }

    public void incrementAttackPauseCounter() {
        myAttackPauseCounter++;
        if (myAttackPauseCounter >= myAttackPauseDuration) {
            isPausedBeforeAttack = false;
            myAttackPauseCounter = 0;
        }
    }

    public boolean isPausedBeforeAttack() {
        return isPausedBeforeAttack;
    }

    public void moveToPlayer(Dwarf thePlayer) {
        double distance = Math.sqrt(Math.pow(thePlayer.getX() - getX(), 2) + Math.pow(thePlayer.getY() - getY(), 2));
        if (distance > myAttackRange) {
            Angle angleToPlayer = new Angle(getX(), getY(), thePlayer.getX(), thePlayer.getY());
            addForce(new Force(angleToPlayer, getMoveSpeed(), 0.4));
        }
    }

    @Override
    public void receiveAttack(Attack theAttack) {
        int oldHealth = super.getHealth();
        super.receiveAttack(theAttack);

        if (theAttack instanceof MeleeAttack) {
            GameLoop.getInstance().addDrawData("sound:PickaxeImpact");
        }

        // oldHealth used so shotguns do not cause multiple death sounds
        if (super.getHealth() <= 0 && oldHealth > 0) {
            GameLoop.getInstance().addDrawData("sound:GlyphidDeath");
        }
    }

    @Override
    public boolean update() {
        Dwarf player = GameLoop.getInstance().getPlayer();
        if (player == null) {
            return super.update();
        }

        double distance = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));

        if (isPausedBeforeAttack) {
            incrementAttackPauseCounter();
        } else {
            if (distance <= myAttackRange) {
                if (attemptAttack(player.getX(), player.getY())) {
                    isPausedBeforeAttack = true;
                }
            } else {
                moveToPlayer(player);
            }
        }

        return super.update();
    }
}

