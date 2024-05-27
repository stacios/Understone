package Model.Glyphid;

import Model.*;
import Model.Character;
import Model.Weapon.Weapon;

public abstract class Glyphid extends Character {

    public Glyphid(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }

    public abstract double getAttackRange();
    public abstract double getSpeedMultiplier();

    public void moveToPlayer(Dwarf player) {
        double distance = Math.sqrt(Math.pow(player.getX() - this.getX(), 2) + Math.pow(player.getY() - this.getY(), 2));
        if (distance > getAttackRange()) {
            Angle angleToPlayer = new Angle(this.getX(), this.getY(), player.getX(), player.getY());
            addForce(new Force(angleToPlayer, getMoveSpeed() * getSpeedMultiplier(), 0.4));
        }
    }

    @Override
    public boolean update() {
        // Get the player from the game loop instance
        Dwarf player = GameLoop.getInstance().getPlayer();
        moveToPlayer(player);
        return super.update();
    }
}



