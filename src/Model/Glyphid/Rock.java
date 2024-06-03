package Model.Glyphid;

import Model.Character;
import Model.Force;
import Model.GameLoop;
import Model.Glyphid.Glyphid;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;

public class Rock extends Character {

    public Rock(String theName, double theX, double theY, int theHealth,
                int theWidth, int theHeight, double theMoveSpeed,
                Weapon theWeapon, double theAttackRange, int theAttackPauseDuration) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }

    // Makes Rock/Egg take no knockback force
    @Override
    public void addForce(Force theForce) {
        theForce.setStrength(0);
    }


    @Override
    public void receiveAttack(Attack theAttack) {
        if (theAttack instanceof MeleeAttack) {
            if (getName().equals("Heal"))
                GameLoop.getInstance().addDrawData("sound:Heal");

            super.receiveAttack(theAttack);
            /*
            if (newHealth <= 0) {
                GameLoop.getInstance().addDrawData("sound:Heal");
            }

             */
        }
    }
}

