package Model;

import java.util.ArrayList;

public class Dwarf extends Character {
    protected ArrayList<Weapon> myWeapons;

    public Dwarf(String theName, double theX, double theY, int theHealth, double theWidth, double theHeight, double theMoveSpeed, Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        myWeapons = new ArrayList<>();
    }

    public void switchWeapon(int index) {
        if (index >= 0 && index < myWeapons.size()) {
            setWeapon(myWeapons.get(index));
        }
    }

    // TBU for game logic!
    public void attack() {
        double targetX = this.myX + 1.0; // Move forward direction
        double targetY = this.myY; // Same horizontal level
        this.attemptAttack(targetX, targetY);
    }

    @Override
    public void update() {
        super.update();
        // specific logic for Dwarf?
    }
}
