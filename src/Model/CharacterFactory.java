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
                !SCOUT.equals(theDwarfType) && !GUNNER.equals(theDwarfType) &&
                !"karl".equals(theDwarfType)) {
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
        weapons[2] = new Weapon(30, new MeleeAttack(10, 275, 275, 40, 100), "Swing");

        switch (theDwarfType) {

            case "Driller":
                // flamethrower
                weapons[0] = new MinigunWeapon(6,
                        new ProjectileAttack(7, 100, 100, 30, 100, 7, "Fire"), "FlamethrowerShot",
                0, 0, new Angle(Math.toRadians(10)));
                // pistol
                weapons[1] = new Weapon(15,
                        new ProjectileAttack(30, 30, 30, 1, 70, 30), "SubataShot");
                break;

            case "Scout":
                // m1000
                weapons[0] = new Weapon(20,
                        new ProjectileAttack(35, 30, 30, 30, 70, 30), "M1000Shot");
                // sawedoff
                weapons[1] = new ShotgunWeapon(90,
                        new ProjectileAttack(8, 30, 30, 15, 70, 30), "DoubleBarrelShot",
                        16, new Angle(Math.toRadians(45)));
                break;

            case "Engineer":
                // shotgun
                weapons[0] = new ShotgunWeapon(40,
                        new ProjectileAttack(8, 30, 30, 10, 70, 30), "WarthogShot",
                        8, new Angle(Math.toRadians(20)));
                // grenade launcher
                weapons[1] = new Weapon(250,
                        new ExplosiveAttack(100, 30, 30, 100, 70, 20, 400), "GrenadeLauncherShot");
                break;

            case "Gunner":
                // minigun
                weapons[0] = new MinigunWeapon(6,
                        new ProjectileAttack(9, 30, 30, 12, 70, 30), "MinigunShot",
                        40, 2, new Angle(Math.toRadians(20)));
                // revolver
                weapons[1] = new Weapon(60,
                        new ProjectileAttack(50, 30, 30, 70, 70, 30), "RevolverShot");
                break;

            case "karl":
                // destroyer of worlds
                weapons[0] = new ShotgunWeapon(6,
                        new ExplosiveAttack(999, 100, 100, 10, 70, 40, 400), "CharSelkarl",
                        10, new Angle(Math.toRadians(25)));
                weapons[1] = weapons[0];
                break;
        }
        //Weapon defaultWeapon = new ShotgunWeapon(60, new ProjectileAttack(10, 20, 20, 10.0, 70, 20), 10, new Angle(Math.toRadians(45)));
        //return new Dwarf(theDwarfType, x, y, health, width, height, moveSpeed, defaultWeapon);
        if (theDwarfType.equals("karl")) {
            return new Dwarf(theDwarfType, 400, 400, 999999, 100, 100, 3, 20, 10, 20, weapons);
        }
        return new Dwarf(theDwarfType, 400, 400, 100, 100, 100, 2.5, 20, 10, 20, weapons);
    }

    /**
     * Creates Glyphid based on specified Glyphid Type.
     *
     * @param theGlyphidType is the Glyphid to be created.
     * @return new Glyphid.
     */
    public static Glyphid createGlyphid(final String theGlyphidType) {
        if (!ACID_SPITTER.equals(theGlyphidType) && !GLYPHID.equals(theGlyphidType) &&
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


        //return new Grunt(theGlyphidType, x, y, health, width, height, moveSpeed, defaultWeapon, fireTimer);
        switch (theGlyphidType) {
            case PRAETORIAN:
                return new Praetorian(theGlyphidType, 800, 800, 400, 200, 200, 5,
                        new Weapon(40, new MeleeAttack(20, 350, 350, 30, 120), "Swing"),
                        200, 40, 0.25, "PraetorianRoar1");
            case ACID_SPITTER:
                return new AcidSpitter(theGlyphidType, 600, 600, 150, 100, 100, 0.8,
                        new Weapon(60, new ProjectileAttack(10, 80, 80, 10, 100, 6, "AcidSpit"), "AcidSpit"),
                        400, 60, null);
            case GRUNT:
                return new Grunt(theGlyphidType, 800, 800, 100, 100, 100, 1.5,
                        new Weapon(40, new MeleeAttack(10, 250, 250, 10, 70), "Swing"),
                        150, 40, "GruntRoar1");
            default:

        }

        return null;
    }

    /**
     * Creates rock objects.
     * @param theRockType is the rock type.
     * @return created rock object.
     */
    public static Rock createObject(final String theRockType) {
        if (!theRockType.equals(HEAL) && !theRockType.equals(EGG) && !theRockType.equals(ROCK)) {
            throw new Error("Must be of type Egg or Rock");
        }

        Weapon defaultWeapon = new Weapon(60, new MeleeAttack(10, 100, 100, 10.0, 100), null);

        return switch (theRockType) {
            case ROCK -> new Rock(theRockType, 600, 500, 100, 200, 200, 0, defaultWeapon);
            case HEAL -> new Rock(theRockType, 600, 500, 40, 75, 75, 0, defaultWeapon);
            case EGG -> new Rock(theRockType, 500, 500, Integer.MAX_VALUE, 100, 100, 0, defaultWeapon);
            default -> null;
        };

    }
}

