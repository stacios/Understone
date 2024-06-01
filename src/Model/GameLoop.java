package Model;

import Controller.DrawData;
import Controller.Drawable;
import Controller.InputData;
import Model.DB.DwarfDB;
import Model.DB.GlyphidDB;
import Model.DB.SQLiteConnection;
import Model.Glyphid.Glyphid;
import Model.Spaces.Cave;
import Model.Spaces.Room;
import Model.Weapon.Attack;
import View.Display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static Model.CharacterTypes.*;
/**
 * The main game loop. Controls the player, cave, and active room.
 */
public class GameLoop implements Drawable, Serializable {
    private static final long serialVersionUID = 8L;
    private static final GameLoop myInstance = new GameLoop();
    private Dwarf myPlayer;
    private Cave myCave;
    private Room myActiveRoom;
    private HUD myHUD;
    private transient ArrayList<String> myDrawDataList;
    private transient boolean myDwarfInteracting;
    private boolean myPaused;

    private GameLoop() {
        myDrawDataList = new ArrayList<>();
        myDwarfInteracting = false;
        myPaused = false;

        startDB();
        myCave = new Cave();
        myActiveRoom = myCave.getCurrentRoom();
        //myPlayer = CharacterFactory.createDwarf("Engineer");
    }

    public void startDB() {
        SQLiteConnection.getDataSource();
        DwarfDB.initializeDB();
        GlyphidDB.initializeDB();
    }

    public void setDwarf(String theDwarfType) {
        myPlayer = CharacterFactory.createDwarf(theDwarfType);
        //myPlayer = CharacterFactory.createDwarf("Scout");
        myHUD = new HUD(myPlayer);
    }

    public static GameLoop getInstance() {
        return myInstance;
    }

    public void setDataLoading(GameLoop theSavedGame) {
        myPlayer = theSavedGame.myPlayer;
        myCave = theSavedGame.myCave;
        myActiveRoom = myCave.getCurrentRoom();
        myHUD = new HUD(myPlayer);
    }

    public boolean update(final InputData theInput) {
        if (myPaused) {
            return !theInput.getEscape();
        }
        myDrawDataList.clear();
        myPlayer.setInputData(theInput);

        handleDwarfInteraction(theInput);

        myActiveRoom.update();
        myDrawDataList.addAll(Arrays.asList(myActiveRoom.getDrawData()));
        myDrawDataList.add("text:Room ID " + myActiveRoom.getIdentifier() + ": 0" + ":100:100:40");

        myDrawDataList.addAll(Arrays.asList(myHUD.getDrawData()));

        return !theInput.getEscape();
    }

    // Todo method for external classes to detect if Dwarf etc is interacting with Door/Egg
    public boolean isDwarfInteracting() {
        return myDwarfInteracting;
    }

    private void handleDwarfInteraction(final InputData theInput) {
        if (theInput.getInteract()) {
            if (!isDwarfInteracting()) {
                moveToNextRoom();
                myDwarfInteracting = true;
            }
        } else {
            myDwarfInteracting = false;
        }
    }

    public void moveToNextRoom() {
        Room currentRoom = myCave.getCurrentRoom();

        if (myCave.hasNextRoom()) {
            // Todo add additional condition that all enemies must be dead
            if (currentRoom.isDwarfInArea(myPlayer)) {
                myCave.moveToNextRoom();
                // Initiate fade animation in Display
                Display.getInstance().startFadeAnimation(35);
                myActiveRoom = myCave.getCurrentRoom();
                //System.out.println("Moved to room " + myActiveRoom.getIdentifier());
            } else {
                if (!currentRoom.isDwarfInArea(myPlayer)) {
                    //System.out.println("Dwarf is not in the top area.");
                }
                if (!currentRoom.canExit()) {
                    //System.out.println("Not all enemies are dead.");
                }
            }
        } else {
            System.out.println("You are in the last room. Cannot move to next room.");
        }
    }

    public void addDrawData(String drawData) {
        myDrawDataList.add(drawData);
    }

    public void setActiveRoom(Room room) {
        myActiveRoom = room;
    }

    public Dwarf getPlayer() {
        return myPlayer;
    }

    public String[] getDrawData() {
        return myDrawDataList.toArray(new String[0]);
    }

    public Room getActiveRoom() {
        return myActiveRoom;
    }

    public void resetGame(String dwarfType) {
        myDrawDataList = new ArrayList<>();
        myCave = new Cave();
        myActiveRoom = myCave.getCurrentRoom();
        myPlayer = CharacterFactory.createDwarf(dwarfType);
        myHUD = new HUD(myPlayer);
    }

    // Method to pause and resume the game
    public void pauseGame() {
        myPaused = !myPaused;
    }

    // Method to check if the game is paused
    public boolean isPaused() {
        return myPaused;
    }

    @Override
    public String toString() {
        return "GameLoop{" +
                "myPlayer=" + myPlayer +
                ", myCave=" + myCave +
                ", myActiveRoom=" + myActiveRoom +
                '}';
    }
}
