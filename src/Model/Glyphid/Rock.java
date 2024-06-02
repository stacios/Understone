package Model.Glyphid;

import Model.Force;
import Model.GameLoop;
import Model.Glyphid.Glyphid;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;

public class Rock extends Glyphid {

    public Rock(String theName, double theX, double theY, int theHealth,
                int theWidth, int theHeight, double theMoveSpeed,
                Weapon theWeapon, double theAttackRange, int theAttackPauseDuration) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange, theAttackPauseDuration);
    }

    // Makes Rock/Egg take no knockback force
    @Override
    public void addForce(Force theForce) {
        theForce.setStrength(0);
    }


    @Override
    public void receiveAttack(Attack theAttack) {
        if (theAttack instanceof MeleeAttack) {
            GameLoop.getInstance().addDrawData("sound:Heal");

            // TODO Have to do in this way because if you call super.recieveForces, then itll play the glyphid death sound on destroyed rock
            int newHealth = super.getHealth() - theAttack.getDamage();
            super.setHealth(newHealth);

            if (newHealth <= 0) {
                GameLoop.getInstance().addDrawData("sound:Heal");
            }
        }
    }
}

