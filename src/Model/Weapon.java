package Model;

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
            myAttack.execute(origin, angle);
            myCooldown = myMaxCooldown;
            return true; // Attack was successful
        }
        return false; // Attack was not possible, cooldown
    }

    //for managing cooldown
    public void update() {
        if (myCooldown > 0) {
            myCooldown--; // Reduce the cooldown by 1 each update call until it reaches 0
        }
    }
}
