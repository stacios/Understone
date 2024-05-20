package Model.Spaces;

import Controller.Drawable;
import Model.GameLoop;
import Model.Weapon.Attack;
import Model.Dwarf;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Rock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Room implements Drawable, Serializable {
    private static final long serialVersionUID = 3L;
    private List<Glyphid> myGlyphids;
    private List<Attack> myAttacks;
    private boolean myHasDropPod;
    private Rock myRock;

    public Room(boolean theHasDropPod, boolean theHasRock) {
        myGlyphids = new ArrayList<>();
        myAttacks = new ArrayList<>();
        myHasDropPod = theHasDropPod;
        if (theHasRock) {
            myRock = new Rock("Rock", 1,1,1,1,1,0,null,0);
        }

    }
    public boolean hasRock() {
        return myRock != null;
    }

    public void spawnEnemies() {
        System.out.println("Enemies spawned in the room.");
    }

    public boolean canExit() {
        // Return true only if (?)
        return false;
    }


    @Override
    public String[] getDrawData() {

        List<String> result = new ArrayList<>();
        result.add("image:Room:" + 1920/2 + ":" + 1080/2 + ":1920:1080");
        for (Glyphid e : myGlyphids) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }
        result.addAll(Arrays.asList(GameLoop.getInstance().getPlayer().getDrawData()));
        for (Attack e : myAttacks) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }


        return result.toArray(new String[0]);
    }

    public void update() {
        GameLoop.getInstance().getPlayer().update();
        for (int i = myGlyphids.size() - 1; i >= 0; i--) {
            if (myGlyphids.get(i).update()) {
                myGlyphids.remove(i);
            }
        }
        for (int i = myAttacks.size() - 1; i >= 0; i--) {
            if (myAttacks.get(i).update()) {
                myAttacks.remove(i);
            }
        }
    }

    public void addAttack(Attack theAttack) {
        myAttacks.add(theAttack);
    }

    @Override public String toString() { return "Room{" + "Glyphids: " + myGlyphids + ", Attacks: " + myAttacks + ", Has Drop Pod: " + myHasDropPod + ", Rock: " + myRock + "}"; }
}
