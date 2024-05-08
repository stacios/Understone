package Model;

public class Weapon {
    private Attack myAttack;
    protected int myCooldown;
    protected int myMaxCooldown;
    double myAttackRange = 10; //default attack range, to be assigned later

    // Constructor to initialize the Weapon object
    public Weapon(Attack theAttack, int theCooldown, int theMaxCooldown) {
        this.myAttack = theAttack;
        this.myCooldown = theCooldown;
        this.myMaxCooldown = theMaxCooldown;
    }

    public boolean attemptAttack(Character origin, Angle angle) {
        // Check if the weapon is off cooldown
        if (myCooldown == 0) {

            double attackX = origin.getX() + Math.cos(angle.getRadians()) * myAttackRange;
            double attackY = origin.getY() + Math.sin(angle.getRadians()) * myAttackRange;

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
}
