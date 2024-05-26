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
    private transient ArrayList<String> myDrawDataList;
    private boolean spacePressed;

    private GameLoop() {
        myDrawDataList = new ArrayList<>();
        spacePressed = false;

        startDB();
        myCave = new Cave();
        myActiveRoom = myCave.getCurrentRoom();
        //myPlayer = CharacterFactory.createDwarf("Engineer");
    }

    public void setDwarf(String theDwarfType) {
        myPlayer = CharacterFactory.createDwarf(theDwarfType);
    }

    public static GameLoop getInstance() {
        return myInstance;
    }

    public void setDataLoading(GameLoop theSavedGame) {
        myPlayer = theSavedGame.myPlayer;
        myCave = theSavedGame.myCave;
        myActiveRoom = myCave.getCurrentRoom();
    }

    public boolean update(final InputData theInput) {
        myDrawDataList.clear();
        myPlayer.setInputData(theInput);

        handleRoomTransition(theInput);

        myActiveRoom.update();
        myDrawDataList.addAll(Arrays.asList(myActiveRoom.getDrawData()));
        myDrawDataList.add("text:Room ID " + myActiveRoom.getIdentifier() + ": 0" + ":100:100:40");

        return !theInput.getEscape();
    }

    private void handleRoomTransition(final InputData theInput) {
        if (theInput.getInteract()) { // Space key is used for interaction
            if (!spacePressed) {
                moveToNextRoom();
                spacePressed = true;
            }
        } else {
            spacePressed = false;
        }
    }

    public void moveToNextRoom() {
        Room currentRoom = myCave.getCurrentRoom();

        if (myCave.hasNextRoom()) {
            // Todo add additional condition that all enemies must be dead
            if (currentRoom.isDwarfInArea(myPlayer)) {
                myCave.moveToNextRoom();
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


    public void setActiveRoom(Room room) {
        myActiveRoom = room;
    }

    public Dwarf getPlayer() {
        return myPlayer;
    }

    public String[] getDrawData() {
        return myDrawDataList.toArray(new String[0]);
    }

    public void startDB() {
        SQLiteConnection.getDataSource();
        DwarfDB.initializeDB();
        GlyphidDB.initializeDB();
    }

    public Room getActiveRoom() {
        return myActiveRoom;
    }

    public void resetGame() {
        myDrawDataList = new ArrayList<>();
        myCave = new Cave();
        myActiveRoom = myCave.getCurrentRoom();
        myPlayer = CharacterFactory.createDwarf("Driller");
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
