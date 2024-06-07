package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;
import Model.Character;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Glyphid extends Character {
    private final String myRoar;
    private int myRoarCooldown;
    private double myAttackRange;
    private int myAttackPauseDuration;
    private int myAttackPauseCounter = 0;
    private boolean isPausedBeforeAttack = false;

    public Glyphid(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon, double theAttackRange, int theAttackPauseDuration, String theRoar) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        myAttackRange = theAttackRange;
        myAttackPauseDuration = theAttackPauseDuration;
        myRoar = theRoar;
        myRoarCooldown = -1;
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
        boolean flag = super.update();
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

        myRoarCooldown--;
        if (myRoarCooldown < 0) {
            myRoarCooldown = (int)(Math.random() * 400) + 200;
        }

        return flag;
    }


    @Override
    public String[] getDrawData() {
        if (myRoar != null && myRoarCooldown == 0) {
            ArrayList<String> temp = new ArrayList<>(List.of(super.getDrawData()));
            temp.add("sound:" + myRoar);
            return temp.toArray(new String[0]);
        } else {
            return super.getDrawData();
        }
    }
}

