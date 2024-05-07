package Model;

public class Grunt extends Glyphid {
    public Grunt(String theName, double theX, double theY, int theHealth,
                 double theWidth, double theHeight, double theMoveSpeed,
                 Weapon theWeapon, int fireTimer) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, fireTimer);
    }

    @Override
    public void attack() {
        // attack logic
        System.out.println("Grunt attacks.");
    }

    @Override
    public void update() {
        // update logic for Grunt
        updateFireTimer();

    }
}

