package model;

public class Weapon {
    private Attack myAttack;
    protected int myCooldown;
    protected int myMaxCooldown;

    // Constructor to initialize the Weapon object
    public Weapon(Attack theAttack, int theCooldown, int theMaxCooldown) {
        this.myAttack = theAttack;
        this.myCooldown = theCooldown;
        this.myMaxCooldown = theMaxCooldown;
    }

    public boolean attemptAttack(Character origin, Angle angle) {
        if (myCooldown == 0) {
            myAttack.getHitBox().setPosition(origin.getX(), origin.getY());
            myAttack.setAngle(angle);
            myAttack.update();
            // Reset cooldown
            myCooldown = myMaxCooldown;
            return true; // Attack was successful
        }
        return false; // Attack was not possible, cooldown
    }

    // Update method for managing cooldown
    public void update() {
        if (myCooldown > 0) {
            myCooldown--; // Reduce the cooldown by 1 each update call until it reaches 0
        }
    }
}
