package Model;

import Model.DB.DwarfDB;
import Model.DB.GlyphidDB;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Grunt;
import Model.Weapon.*;
import Model.Glyphid.*;

import static Model.CharacterTypes.*;

/**
 * Contains methods for creating dwarves and glyphids correctly.
 */
public class CharacterFactory {

    /**
     * Creates Dwarf based on specified Dwarf Type.
     *
     * @param theDwarfType is the Dwarf to be created.
     * @return new Dwarf.
     */
    public static Dwarf createDwarf(final String theDwarfType) {
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
        //int damage = DwarfDB.getDefaultValue(theDwarfType, "damage");

        Weapon[] weapons = new Weapon[3];
        // pickaxe
        weapons[2] = new Weapon(30, new MeleeAttack(10, 200, 200, 10, 100), null);

        switch (theDwarfType) {

            case "Driller":
                // flamethrower
                weapons[0] = new Weapon(6,
                        new FireAttack(10, 100, 100, 0, 100, 15, 20), null);
                // pistol
                weapons[1] = new Weapon(30,
                        new ProjectileAttack(10, 30, 30, 10, 70, 30), "SubataShot");
                break;

            case "Scout":
                // m1000
                weapons[0] = new Weapon(25,
                        new ProjectileAttack(20, 30, 30, 0, 70, 30), "M1000Shot");
                // sawedoff
                weapons[1] = new ShotgunWeapon(60,
                        new ProjectileAttack(8, 30, 30, 10, 70, 30), "DoubleBarrelShot",
                        10, new Angle(Math.toRadians(40)));
                break;

            case "Engineer":
                // shotgun
                weapons[0] = new ShotgunWeapon(40,
                        new ProjectileAttack(8, 30, 30, 10, 70, 30), "WarthogShot",
                        8, new Angle(Math.toRadians(20)));
                // grenade launcher
                weapons[1] = new Weapon(200,
                        new ExplosiveAttack(100, 30, 30, 10, 70, 20, 200), null);
                break;

            case "Gunner":
                // minigun
                weapons[0] = new MinigunWeapon(6,
                        new ProjectileAttack(5, 30, 30, 5, 70, 30), "MinigunShot",
                        40, 2, new Angle(Math.toRadians(20)));
                // revolver
                weapons[1] = new Weapon(60,
                        new ProjectileAttack(40, 30, 30, 30, 70, 30), "RevolverShot");
                break;
        }
        //Weapon defaultWeapon = new ShotgunWeapon(60, new ProjectileAttack(10, 20, 20, 10.0, 70, 20), 10, new Angle(Math.toRadians(45)));
        //return new Dwarf(theDwarfType, x, y, health, width, height, moveSpeed, defaultWeapon);
        return new Dwarf(theDwarfType, 400, 400, 100, 100, 100, 5, weapons);
    }

    /**
     * Creates Glyphid based on specified Glyphid Type.
     *
     * @param theGlyphidType is the Glyphid to be created.
     * @return new Glyphid.
     */
    public static Glyphid createGlyphid(final String theGlyphidType) {
        if (!ACID_SPIITER.equals(theGlyphidType) && !GLYPHID.equals(theGlyphidType) &&
                !GRUNT.equals(theGlyphidType) && !MACTERA.equals(theGlyphidType) &&
                !PRAETORIAN.equals(theGlyphidType) && !SWARMER.equals(theGlyphidType)) {
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
        Weapon defaultWeapon = new Weapon(60, new MeleeAttack(10, 100, 100, 10.0, 100), null);

        //return new Grunt(theGlyphidType, x, y, health, width, height, moveSpeed, defaultWeapon, fireTimer);
        switch (theGlyphidType) {
            case PRAETORIAN:
                return new Praetorian(theGlyphidType, 800, 800, 200, 150, 150, 2, defaultWeapon, fireTimer);
            case ACID_SPIITER:
                return new AcidSpitter(theGlyphidType, 600, 600, 80, 100, 100, 7, defaultWeapon, fireTimer);
            case GRUNT:
                return new Grunt(theGlyphidType, 800, 800, 100, 100, 100, 5, defaultWeapon, fireTimer);
            default:
                
        }

        return null;
    }

    public static Rock createRock(final String theRockType) {
        if (!theRockType.equals(ROCK) && !theRockType.equals(EGG)) {
            throw new Error("Must be of type Egg or Rock");
        }

        Weapon defaultWeapon = new Weapon(60, new MeleeAttack(10, 100, 100, 10.0, 100), null);

        switch (theRockType) {
            case ROCK:
                return new Rock(theRockType, 600, 500, 100, 200, 200, 0, defaultWeapon, 0);
            case EGG:
                return new Rock(theRockType, 500, 500, 100, 100, 100, 0, defaultWeapon, 0);
            default:
        }

        return null;
    }
}

