package Model.Spaces;

import Model.Attack;
import Model.Dwarves.Dwarf;
import Model.Glyphids.Glyphid;
import Model.Rock;

import java.util.ArrayList;
import java.util.List;

public class Room {
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
}
