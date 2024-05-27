package Model.Glyphid;

import Model.*;
import Model.Character;
import Model.Weapon.Weapon;

public abstract class Glyphid extends Character {
    private double attackRange;

    public Glyphid(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon, double theAttackRange) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        this.attackRange = theAttackRange;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public void moveToPlayer(Dwarf player) {
        double distance = Math.sqrt(Math.pow(player.getX() - this.getX(), 2) + Math.pow(player.getY() - this.getY(), 2));
        if (distance > attackRange) {
            Angle angleToPlayer = new Angle(this.getX(), this.getY(), player.getX(), player.getY());
            addForce(new Force(angleToPlayer, getMoveSpeed(), 0.4));
        }
    }

    @Override
    public boolean update() {
        Dwarf player = GameLoop.getInstance().getPlayer();
        moveToPlayer(player);
        return super.update();
    }
}
