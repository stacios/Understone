package Model.Spaces;

import Controller.DrawData;
import Controller.Drawable;
import Model.Attack;
import Model.Dwarf;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Rock;

import java.util.ArrayList;
import java.util.List;

public class Room implements Drawable {
    private List<Glyphid> myGlyphids;
    private List<Attack> myAttacks;
    private boolean myHasDropPod;
    private Rock myRock;
    private Dwarf myPlayer;

    public Room(boolean theHasDropPod, boolean theHasRock) {
        myGlyphids = new ArrayList<>();
        myAttacks = new ArrayList<>();
        myHasDropPod = theHasDropPod;
        if (theHasRock) {
            myRock = new Rock("Rock", 1,1,1,1,1,0,null,0);
        }
        myPlayer = null;
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

    public void addPlayer(Dwarf thePlayer){
        myPlayer = thePlayer;
    }

    @Override
    public DrawData getDrawData() {
        return new DrawData("Room", null, 1920/2, 1080/2, 1920, 1080);
    }
}
