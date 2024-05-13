package Model;

import Model.DB.DwarfDB;
import Model.DB.GlyphidDB;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Grunt;
import Model.Weapon.Weapon;

import static Model.CharacterTypes.*;

public class CharacterFactory {

    public static Dwarf createDwarf(String theName) {
        if (!DRILLER.equals(theName) && !ENGINEER.equals(theName) &&
                !SCOUT.equals(theName) && !GUNNER.equals(theName)) {
            throw new Error("Passed dwarf type must be of defined dwarf type(Driller, Engineer, Gunner, Scout)");
        }

        double x = 0; // Default X
        double y = 0; // Default Y
//        int health = 100; // Default health
//        int width = 1; // Default width
//        int height = 1; // Default height
//        double moveSpeed = 2.0; // Default speed

        // Getting values from DwarfDB
        int health = DwarfDB.getDefaultValue(theName, "health"); // Default health
        int width = DwarfDB.getDefaultValue(theName, "width"); // Default width
        int height = DwarfDB.getDefaultValue(theName, "height"); // Default height
        double moveSpeed = DwarfDB.getDefaultValue(theName, "movespeed"); // Default speed
        int damage = DwarfDB.getDefaultValue(theName, "damage");
        Weapon defaultWeapon = new Weapon(new Attack("Basic Attack", damage, x, y, width, height, new Force(new Angle(0), 1.0), new Angle(0)), 0, 10);
        return new Dwarf(theName, x, y, health, width, height, moveSpeed, defaultWeapon);
    }


    public static Glyphid createGlyphid(String theName) {
        double x = 5; // Default X
        double y = 5; // Default Y
//        int health = 50; // Default health
//        int width = 1; // Default width
//        int height = 1; // Default height
//        double moveSpeed = 1.5; // Default speed
//        int fireTimer = 5; // Default cooldown timer

        // Getting values from GlyphidDB
        int health = GlyphidDB.getDefaultValue("health"); // Default health
        int width = GlyphidDB.getDefaultValue("width"); // Default width
        int height = GlyphidDB.getDefaultValue("height"); // Default height
        double moveSpeed = GlyphidDB.getDefaultValue("movespeed"); // Default speed
        int fireTimer = GlyphidDB.getDefaultValue("firetimer"); // Default cooldown timer
        int damage = GlyphidDB.getDefaultValue("damage"); // Default damage
        Weapon defaultWeapon = new Weapon(new Attack("Acid Spit", damage, x, y, width, height, new Force(new Angle(0), 1.0), new Angle(0)), 2, 20);
        return new Grunt(theName, x, y, health, width, height, moveSpeed, defaultWeapon, fireTimer);
    }
}

