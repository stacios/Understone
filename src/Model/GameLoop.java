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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static Model.CharacterTypes.*;
public class GameLoop implements Drawable {

    private static final GameLoop myInstance = new GameLoop();

    private Dwarf myPlayer;
    private Cave myCave;

    private Room myActiveRoom;

    private ArrayList<String> myDrawDataList;
    private DrawData temp = new DrawData("test", null, 100, 100, 100, 100);
    private double temp2 = 0;

    private GameLoop() {
        myDrawDataList = new ArrayList<>();

        //temp
        myActiveRoom = new Room(false, false);
        myPlayer = new Dwarf("Driller", 800, 800, 100, 100, 100, 5, null);
        testCharacterFactoryAndDB();
    }

    public static GameLoop getInstance() {
        return myInstance;
    }

    public boolean update(final InputData theInput) {

        myDrawDataList.clear();


        myPlayer.setInputData(theInput);
        myPlayer.update();
        myDrawDataList.addAll(Arrays.asList(myActiveRoom.getDrawData()));
        myDrawDataList.addAll(Arrays.asList(myPlayer.getDrawData()));


        //System.out.println(myPlayer.getDrawData());



        myDrawDataList.add("text:hello:100:100:40");

        return !theInput.getEscape();
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
        Glyphid testGlyphid = CharacterFactory.createGlyphid("testGlyphid");
        System.out.println(testGlyphid.toString());
    }
}
