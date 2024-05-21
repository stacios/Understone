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

    private GameLoop() {
        myDrawDataList = new ArrayList<>();

        myActiveRoom = new Room(false, false);
        myPlayer = CharacterFactory.createDwarf("Driller");
        testCharacterFactoryAndDB();
    }

    public static GameLoop getInstance() {
        return myInstance;
    }

    public void setDataLoading(GameLoop theSavedGame){
        myPlayer = theSavedGame.myPlayer;
        myCave = theSavedGame.myCave;
        myActiveRoom = theSavedGame.myActiveRoom;
    }

    public boolean update(final InputData theInput) {

        myDrawDataList.clear();


        myPlayer.setInputData(theInput);

        myActiveRoom.update();
        myDrawDataList.addAll(Arrays.asList(myActiveRoom.getDrawData()));

        myDrawDataList.add("text:hello:100:100:40");

        return !theInput.getEscape();
    }

    public Dwarf getPlayer() {
        return myPlayer;
    }

    public String[] getDrawData() {
        return myDrawDataList.toArray(new String[0]);
    }

    /**
     * Temporary method for testing and printing values from database.
     */
    public void testCharacterFactoryAndDB() {
        // Initializes Database
        SQLiteConnection.getDataSource();

        // Initializes tables and data insertion for Dwarf and Glyphid.
        DwarfDB.initializeDB();
        GlyphidDB.initializeDB();

        // Creates test Dwarf object
        Dwarf testDwarf = CharacterFactory.createDwarf(DRILLER);
        System.out.println(testDwarf.toString());

        // Creates test Dwarf object
        //Glyphid testGlyphid = CharacterFactory.createGlyphid("testGlyphid");
        //System.out.println(testGlyphid.toString());
    }

    public Room getActiveRoom() {
        return myActiveRoom;
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
