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
    private static final Map<String, List<String>> dwarfWeapons;
    private static final int TEXT_HEIGHT = 20;

    static {
        dwarfWeapons = new HashMap<>();
        dwarfWeapons.put("Driller", List.of("Flamethrower", "Pistol"));
        dwarfWeapons.put("Scout", List.of("M1000", "Pistol"));
        dwarfWeapons.put("Engineer", List.of("Shotgun", "GrenadeLauncher"));
        dwarfWeapons.put("Gunner", List.of("Minigun", "Revolver"));
    }

    public HUD(Dwarf thePlayer) {
        myPlayer = thePlayer;
    }

    @Override
    public String[] getDrawData() {
        List<String> drawData = new ArrayList<>();

        // Display Dwarf type
        int dwarfTextX = 20;
        int dwarfTextY = Display.getInstance().getHeight() - 200; // Moved up by 30 pixels
        String dwarfType = myPlayer.getName();
        drawData.add(getTextDrawData("Dwarf " + dwarfType, dwarfTextX, dwarfTextY));

        // Display health
        int healthTextY = dwarfTextY + 30;
        int health = myPlayer.getHealth();
        drawData.add(getTextDrawData("Health " + health, dwarfTextX, healthTextY));

        // Display weapon
        int weaponTextY = healthTextY + 30;
        String myWeapon = getCurrentWeapon(dwarfType, myPlayer.getWeaponIndex());
        drawData.add(getTextDrawData("Weapon " + myWeapon, dwarfTextX, weaponTextY));

        // Display weapon image below the weapon text
        int weaponImageX = dwarfTextX + 100;
        int weaponImageY = weaponTextY + 30; // Positioned below the weapon text
        drawData.add(getImageDrawData(myWeapon, weaponImageX, weaponImageY, 70, 70));

        return drawData.toArray(new String[0]);
    }

    private String getTextDrawData(String text, int x, int y) {
        return "text:" + text + ":" + x + ":" + y + ":20:20";
    }

    private String getImageDrawData(String imageName, int x, int y, int width, int height) {
        return "image:" + imageName + ":" + x + ":" + y + ":" + width + ":" + height;
    }

    private String getCurrentWeapon(String dwarfType, int weaponIndex) {
        List<String> weapons = dwarfWeapons.get(dwarfType);
        return weapons != null && weaponIndex < weapons.size() ? weapons.get(weaponIndex) : "Unknown";
    }
}






