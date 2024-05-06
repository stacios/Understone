package Model;

import Model.Dwarves.Dwarf;
import Model.Glyphids.Glyphid;
import Model.Glyphids.Grunt;

public class CharacterFactory {

    public static Dwarf createDwarf(String theName) {
        double x = 0; // Default X
        double y = 0; // Default Y
        int health = 100; // Default health
        double width = 1.0; // Default width
        double height = 1.0; // Default height
        double moveSpeed = 2.0; // Default speed
        Weapon defaultWeapon = new Weapon(new Attack("Basic Attack", 10, x, y, width, height, new Force(new Angle(0), 1.0), new Angle(0)), 0, 10);
        return new Dwarf(theName, x, y, health, width, height, moveSpeed, defaultWeapon);
    }


    public static Glyphid createGlyphid(String theName) {
        double x = 5; // Default X
        double y = 5; // Default Y
        int health = 50; // Default health
        double width = 1.0; // Default width
        double height = 1.0; // Default height
        double moveSpeed = 1.5; // Default speed
        Weapon defaultWeapon = new Weapon(new Attack("Acid Spit", 5, x, y, width, height, new Force(new Angle(0), 1.0), new Angle(0)), 2, 20);
        int fireTimer = 5; // Default cooldown timer
        return new Grunt(theName, x, y, health, width, height, moveSpeed, defaultWeapon, fireTimer);
    }
}

