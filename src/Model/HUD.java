package Model;

import Controller.Drawable;
import View.Display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HUD implements Drawable, Serializable {
    private static final long serialVersionUID = 1L;
    private final Dwarf myPlayer;
    private static final Map<String, List<String>> myDwarfWeapons;
    private static final int TEXT_HEIGHT = 30;

    static {
        myDwarfWeapons = new HashMap<>();
        myDwarfWeapons.put("Driller", List.of("Flamethrower", "Pistol", "Pickaxe"));
        myDwarfWeapons.put("Scout", List.of("M1000", "SawedOff", "Pickaxe"));
        myDwarfWeapons.put("Engineer", List.of("Shotgun", "GrenadeLauncher", "Pickaxe"));
        myDwarfWeapons.put("Gunner", List.of("Minigun", "Revolver", "Pickaxe"));
        myDwarfWeapons.put("karl", List.of("GrenadeLauncher", "GrenadeLauncher", "Pickaxe"));
    }

    public HUD(Dwarf thePlayer) {
        myPlayer = thePlayer;
    }

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
        drawData.add(getTextDrawData("Health- " + health, dwarfTextX, healthTextY));

        // Display weapon
        int weaponTextY = healthTextY + 45;
        String myWeapon = getCurrentWeapon(dwarfType, myPlayer.getWeaponIndex());
        drawData.add(getTextDrawData("Weapon- " + myWeapon, dwarfTextX, weaponTextY));

        // Display weapon image below the weapon text
        int weaponImageX = dwarfTextX;
        int weaponImageY = weaponTextY + 20;
        drawData.add(getImageDrawData(myWeapon, weaponImageX, weaponImageY, .5));

        return drawData.toArray(new String[0]);
    }

    private String getTextDrawData(String theText, int theX, int theY) {
        return "text:" + theText + ":" + theX + ":" + theY + ":30:30";
    }

    private String getImageDrawData(String theImageName, int theX, int theY, double theScale) {
        return "unboundImage:" + theImageName + ":" + theX + ":" + theY + ":" + theScale;
    }

    private String getCurrentWeapon(String theDwarfType, int theWeaponIndex) {
        List<String> weapons = myDwarfWeapons.get(theDwarfType);
        return weapons != null && theWeaponIndex < weapons.size() ? weapons.get(theWeaponIndex) : "Unknown";
    }
}







