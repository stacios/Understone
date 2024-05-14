package Model.Weapon;

import Model.Angle;
import Model.Attack;
import Model.Character;

import java.io.Serializable;

public class Weapon implements Serializable {
    private static final long serialVersionUID = 4L;
    private Attack myAttack;
    protected int myCooldown;
    protected int myMaxCooldown;

    public Weapon(Attack theAttack, int theCooldown, int theMaxCooldown) {
        this.myAttack = theAttack;
        this.myCooldown = theCooldown;
        this.myMaxCooldown = theMaxCooldown;
    }

    public boolean attemptAttack(Character origin, Angle angle) {
        // Check if the weapon is off cooldown
        if (myCooldown == 0) {

            double attackRange = 10; //default attack range
            double attackX = origin.getX() + Math.cos(angle.getRadians()) * attackRange;
            double attackY = origin.getY() + Math.sin(angle.getRadians()) * attackRange;

            myAttack.setPosition(attackX, attackY);

            myAttack.setAngle(angle);

            System.out.println("Attack performed at position: (" + attackX + ", " + attackY + ") with angle " + angle.getDegrees());

            // Reset the cooldown
            myCooldown = myMaxCooldown;

            return true;
        }
        return false;
    }

    //for managing cooldown
    public void update() {
        if (myCooldown > 0) {
            myCooldown--; // Reduce the cooldown by 1 each update call until it reaches 0
        }
    }
    @Override
    public String toString() {
        return "Weapon{" +
                "myAttack=" + myAttack +
                ", myCooldown=" + myCooldown +
                ", myMaxCooldown=" + myMaxCooldown +
                '}';
    }
}
