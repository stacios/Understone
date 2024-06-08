package Model;

import Controller.Drawable;
import Controller.InputData;
import Model.DB.DwarfDB;
import Model.DB.GlyphidDB;
import Model.DB.SQLiteConnection;
import Model.Spaces.Cave;
import Model.Spaces.Room;
import View.Display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main game loop. Controls the player, cave, and active room.
 */
public class GameLoop implements Drawable, Serializable {
    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 8L;

    /**
     * The singleton instance of the GameLoop.
     */
    private static final GameLoop myInstance = new GameLoop();

    /**
     * The player character.
     */
    private Dwarf myPlayer;

    /**
     * The cave the player is navigating.
     */
    private Cave myCave;

    /**
     * The currently active room in the cave.
     */
    private Room myActiveRoom;

    /**
     * The HUD (heads-up display) for the player.
     */
    private HUD myHUD;

    /**
     * The list of draw data for rendering.
     */
    private transient ArrayList<String> myDrawDataList;

    /**
     * Flag indicating if the dwarf is interacting.
     */
    private transient boolean myDwarfInteracting;

    /**
     * Flag indicating if the game is paused.
     */
    private boolean myPaused;

    /**
     * Private constructor for the GameLoop singleton.
     */
    private GameLoop() {
        myDrawDataList = new ArrayList<>();
        myDwarfInteracting = false;
        myPaused = false;

        startDB();
        myCave = new Cave();
        myActiveRoom = myCave.getCurrentRoom();
    }

    /**
     * Starts the database connections and initializes the databases.
     */
    public void startDB() {
        SQLiteConnection.getDataSource();
        DwarfDB.initializeDB();
        GlyphidDB.initializeDB();
    }

    /**
     * Sets the player's dwarf character based on the specified type.
     *
     * @param theDwarfType the type of dwarf to create
     */
    public void setDwarf(String theDwarfType) {
        myPlayer = CharacterFactory.createDwarf(theDwarfType);
        myHUD = new HUD(myPlayer);
        myActiveRoom.positionDwarf(myPlayer);
    }

    /**
     * Gets the singleton instance of the GameLoop.
     *
     * @return the singleton instance
     */
    public static GameLoop getInstance() {
        return myInstance;
    }

    /**
     * Sets the game data from a saved game.
     *
     * @param theSavedGame the saved game to load
     */
    public void setDataLoading(GameLoop theSavedGame) {
        myPlayer = theSavedGame.myPlayer;
        myCave = theSavedGame.myCave;
        myActiveRoom = myCave.getCurrentRoom();
        myHUD = new HUD(myPlayer);
    }

    /**
     * Run every tick to update the game.
     * Returns an int indicating the game state:
     * 0 - nothing
     * 1 - close game
     * 2 - player is dead
     * 3 - player has won
     *
     * @param theInput the input data for the current tick
     * @return the game state code
     */
    public int update(final InputData theInput) {
        if (!Display.getInstance().isRunning()) {
            return 1;
        }
        if (myPlayer.getHealth() <= 0) {
            return 2;
        }
        if (myPlayer.hasEgg() && myActiveRoom.getIdentifier() == 0) {
            return 3;
        }
        if (myPaused) {
            return 0;
        }
        myDrawDataList.clear();
        myPlayer.setInputData(theInput);

        handleDwarfInteraction(theInput);

        myActiveRoom.update();
        myDrawDataList.addAll(Arrays.asList(myActiveRoom.getDrawData()));
        myDrawDataList.addAll(Arrays.asList(myHUD.getDrawData()));

        return 0;
    }

    /**
     * Checks if the dwarf is currently interacting.
     *
     * @return true if the dwarf is interacting, false otherwise
     */
    public boolean isDwarfInteracting() {
        return myDwarfInteracting;
    }

    /**
     * Handles the dwarf's interaction based on the input data.
     *
     * @param theInput the input data for the current tick
     */
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

    /**
     * Moves the player to the next room in the cave.
     */
    public void moveToNextRoom() {
        Room currentRoom = myCave.getCurrentRoom();

        // If player has egg
        if (myPlayer.hasEgg() && currentRoom.canExit()) {

            // If cave has previous rooms
            if (myCave.hasPreviousRoom()) {
                if (currentRoom.isDwarfInArea(myPlayer)) {
                    currentRoom.clearRoom();
                    myCave.moveToPreviousRoom();
                    myActiveRoom.positionDwarf(myPlayer);
                    myDrawDataList.add("sound:Transition");
                    GameLoop.getInstance().addDrawData("fade:20");
                    myActiveRoom = myCave.getCurrentRoom();
                } else {
                    System.out.println("Cannot move to the previous room.");
                }
            } else {
                System.out.println("You are in the first room. Cannot move to previous room.");
            }
        } else if (currentRoom.canExit()) {
            if (myCave.hasNextRoom()) {
                if (currentRoom.isDwarfInArea(myPlayer)) {
                    currentRoom.clearRoom();
                    myCave.moveToNextRoom();
                    myActiveRoom.positionDwarf(myPlayer);
                    myDrawDataList.add("sound:Transition");
                    GameLoop.getInstance().addDrawData("fade:20");
                    myActiveRoom = myCave.getCurrentRoom();
                } else {
                    System.out.println("Cannot move to the next room.");
                }
            } else {
                System.out.println("You are in the last room. Cannot move to next room.");
            }
        }
    }

    /**
     * Adds draw data to the list for rendering.
     *
     * @param drawData the draw data to add
     */
    public void addDrawData(final String drawData) {
        myDrawDataList.add(drawData);
    }

    /**
     * Gets the player character.
     *
     * @return the player character
     */
    public Dwarf getPlayer() {
        return myPlayer;
    }

    /**
     * Gets the draw data for rendering.
     *
     * @return an array of draw data strings
     */
    public String[] getDrawData() {
        return myDrawDataList.toArray(new String[0]);
    }

    /**
     * Resets the game with a new player character of the specified type.
     *
     * @param theDwarfType the type of dwarf to create
     */
    public void resetGame(final String theDwarfType) {
        myDrawDataList = new ArrayList<>();
        myCave = new Cave();
        myActiveRoom = myCave.getCurrentRoom();
        myPlayer = CharacterFactory.createDwarf(theDwarfType);
        myHUD = new HUD(myPlayer);
    }

    /**
     * Toggles the pause state of the game.
     */
    public void pauseGame() {
        myPaused = !myPaused;
    }

    /**
     * String representation of GameLoop.
     * @return String of GameLoop.
     */
    @Override
    public String toString() {
        return "GameLoop{" +
                "myPlayer=" + myPlayer +
                ", myCave=" + myCave +
                ", myActiveRoom=" + myActiveRoom +
                '}';
    }
}
