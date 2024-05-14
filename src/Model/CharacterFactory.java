package Model;

import Model.DB.DwarfDB;
import Model.DB.GlyphidDB;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Grunt;
import Model.Weapon.Weapon;

import static Model.CharacterTypes.*;

public class CharacterFactory {

    /**
     * Creates Dwarf based on specified Dwarf Type.
     *
     * @param theDwarfType is the Dwarf to be created.
     * @return new Dwarf.
     */
    public static Dwarf createDwarf(String theDwarfType) {
        if (!DRILLER.equals(theDwarfType) && !ENGINEER.equals(theDwarfType) &&
                !SCOUT.equals(theDwarfType) && !GUNNER.equals(theDwarfType)) {
            throw new Error("Passed dwarf type must be of defined dwarf type(Driller, Engineer, Gunner, Scout)");
        }

        double x = 0; // Default X
        double y = 0; // Default Y

        // Getting values from DwarfDB
        int health = DwarfDB.getDefaultValue(theDwarfType, "health"); // Default health
        int width = DwarfDB.getDefaultValue(theDwarfType, "width"); // Default width
        int height = DwarfDB.getDefaultValue(theDwarfType, "height"); // Default height
        double moveSpeed = DwarfDB.getDefaultValue(theDwarfType, "movespeed"); // Default speed
        int damage = DwarfDB.getDefaultValue(theDwarfType, "damage");
        Weapon defaultWeapon = new Weapon(new Attack("Basic Attack", damage, x, y, width, height, new Force(new Angle(0), 1.0), new Angle(0)), 0, 10);
        return new Dwarf(theDwarfType, x, y, health, width, height, moveSpeed, defaultWeapon);
    }

    /**
     * Creates Glyphid based on specified Glyphid Type.
     *
     * @param theGlyphidType is the Glyphid to be created.
     * @return new Glyphid.
     */
    public static Glyphid createGlyphid(String theGlyphidType) {
        if (!ACID_SPIITER.equals(theGlyphidType) && !GLYPHID.equals(theGlyphidType) &&
                !GRUNT.equals(theGlyphidType) && !MACTERA.equals(theGlyphidType) &&
                !PRAETORIAN.equals(theGlyphidType) && !ROCK.equals(theGlyphidType) &&
                !SWARMER.equals(theGlyphidType)) {
            throw new Error("Passed glyphid type must be of defined glyphid type: " +
                    "(Acid Spitter, Glyphid, Grunt, Mactera, Praetorian, Rock, Swarmer)");
        }

        double x = 5; // Default X
        double y = 5; // Default Y

        // Getting values from GlyphidDB
        int health = GlyphidDB.getDefaultValue(theGlyphidType, "health"); // Default health
        int width = GlyphidDB.getDefaultValue(theGlyphidType, "width"); // Default width
        int height = GlyphidDB.getDefaultValue(theGlyphidType, "height"); // Default height
        double moveSpeed = GlyphidDB.getDefaultValue(theGlyphidType, "movespeed"); // Default speed
        int fireTimer = GlyphidDB.getDefaultValue(theGlyphidType, "firetimer"); // Default cooldown timer
        int damage = GlyphidDB.getDefaultValue(theGlyphidType, "damage"); // Default damage
        Weapon defaultWeapon = new Weapon(new Attack("Acid Spit", damage, x, y, width, height, new Force(new Angle(0), 1.0), new Angle(0)), 2, 20);
        return new Grunt(theGlyphidType, x, y, health, width, height, moveSpeed, defaultWeapon, fireTimer);
    }
}

