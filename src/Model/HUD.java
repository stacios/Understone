package Model;

import Controller.Drawable;
import View.Display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Heads-Up Display (HUD) for the game, displaying information about the player's Dwarf character.
 */
public class HUD implements Drawable, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The Dwarf player associated with the HUD.
     */
    private final Dwarf myPlayer;

    /**
     * A map of Dwarf types to their respective weapon lists.
     */
    private static final Map<String, List<String>> myDwarfWeapons;

    /**
     * The height of the text displayed on the HUD.
     */
    private static final int TEXT_HEIGHT = 30;

    static {
        myDwarfWeapons = new HashMap<>();
        myDwarfWeapons.put("Driller", List.of("Flamethrower", "Pistol", "Pickaxe"));
        myDwarfWeapons.put("Scout", List.of("M1000", "SawedOff", "Pickaxe"));
        myDwarfWeapons.put("Engineer", List.of("Shotgun", "GrenadeLauncher", "Pickaxe"));
        myDwarfWeapons.put("Gunner", List.of("Minigun", "Revolver", "Pickaxe"));
        myDwarfWeapons.put("karl", List.of("GrenadeLauncher", "GrenadeLauncher", "Pickaxe"));
    }

    /**
     * Constructs an HUD for the specified Dwarf player.
     *
     * @param thePlayer the Dwarf player
     */
    public HUD(final Dwarf thePlayer) {
        myPlayer = thePlayer;
    }

    /**
     * Gets the draw data for the HUD, including the Dwarf type, health, and current weapon.
     *
     * @return an array of strings representing the draw data
     */
    @Override
    public String[] getDrawData() {
        List<String> drawData = new ArrayList<>();

        // Display Dwarf type
        int dwarfTextX = 20;
        int dwarfTextY = Display.getInstance().getHeight() - 250;
        String dwarfType = myPlayer.getName();
        drawData.add(getTextDrawData(dwarfType, dwarfTextX, dwarfTextY));

        // Display health
        int healthTextY = dwarfTextY + 45;
        int health = myPlayer.getHealth();
        drawData.add(getTextDrawData("Health " + health, dwarfTextX, healthTextY));

        // Display weapon
        int weaponTextY = healthTextY + 45;
        String myWeapon = getCurrentWeapon(dwarfType, myPlayer.getWeaponIndex());
        drawData.add(getTextDrawData("Weapon " + myWeapon, dwarfTextX, weaponTextY));

        // Display weapon image below the weapon text
        int weaponImageX = dwarfTextX;
        int weaponImageY = weaponTextY + 20;
        drawData.add(getImageDrawData(myWeapon, weaponImageX, weaponImageY, .5));

        return drawData.toArray(new String[0]);
    }

    /**
     * Creates a string representing the draw data for a text element.
     *
     * @param theText the text to display
     * @param theX the x-coordinate for the text
     * @param theY the y-coordinate for the text
     * @return a string representing the draw data for the text
     */
    private String getTextDrawData(final String theText, final int theX, final int theY) {
        return "text:" + theText + ":" + theX + ":" + theY + ":30:30";
    }

    /**
     * Creates a string representing the draw data for an image element.
     *
     * @param theImageName the name of the image
     * @param theX the x-coordinate for the image
     * @param theY the y-coordinate for the image
     * @param theScale the scale of the image
     * @return a string representing the draw data for the image
     */
    private String getImageDrawData(final String theImageName, final int theX, final int theY, final double theScale) {
        return "unboundImage:" + theImageName + ":" + theX + ":" + theY + ":" + theScale;
    }

    /**
     * Gets the current weapon for the specified Dwarf type and weapon index.
     *
     * @param theDwarfType the type of the Dwarf
     * @param theWeaponIndex the index of the weapon
     * @return the name of the current weapon, or "Unknown" if the index is out of bounds
     */
    private String getCurrentWeapon(final String theDwarfType, final int theWeaponIndex) {
        List<String> weapons = myDwarfWeapons.get(theDwarfType);
        return weapons != null && theWeaponIndex < weapons.size() ? weapons.get(theWeaponIndex) : "Unknown";
    }
}







