package Model;

import Controller.DrawData;
import Controller.InputData;
import Model.DB.DwarfDB;
import Model.DB.GlyphidDB;
import Model.DB.SQLiteConnection;
import Model.Glyphid.Glyphid;
import Model.Spaces.Cave;
import Model.Spaces.Room;

import java.util.ArrayList;

public class GameLoop {

    private static final GameLoop myInstance = new GameLoop();

    private Dwarf myPlayer;
    private Cave myCave;

    private Room myActiveRoom;

    private ArrayList<DrawData> myDrawDataList;
    private DrawData temp = new DrawData("test", null, 100, 100, 100, 100);
    private double temp2 = 0;

    private GameLoop() {
        myDrawDataList = new ArrayList<>();

        //temp
        myActiveRoom = new Room(false, false);
        myPlayer = new Dwarf("Driller", 800, 800, 100, 100, 100, 5, null);
        //testCharacterFactoryAndDB();
    }

    public static GameLoop getInstance() {
        return myInstance;
    }

    public boolean update(final InputData theInput) {

        myDrawDataList.clear();

        /*
        if (theInput.getUp() || theInput.getM1()) {
            if (temp.getWidth() != 100) {
                temp = new DrawData("test", null, 600, 600, 1500, 800);
            }
            else {
                temp = new DrawData("test", new String[]{"boing"}, 600, 600, 1500, 800);
            }

        }
        else {
            temp = new DrawData("test", null, theInput.getMouseX(), theInput.getMouseY(), 100, 100, temp2);
        }

        temp2 = (temp2 + .1) % (2 * Math.PI);

         */


        myPlayer.setInputData(theInput);
        myPlayer.update();
        myDrawDataList.add(myActiveRoom.getDrawData());
        myDrawDataList.add(myPlayer.getDrawData());

        //System.out.println(myPlayer.getDrawData());


        myDrawDataList.add(new DrawData("Driller", null, 100, 100, 100, 100));

        return !theInput.getEscape();
    }

    public DrawData[] getDrawData() {
        return myDrawDataList.toArray(new DrawData[0]);
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
        Dwarf testDwarf = CharacterFactory.createDwarf("testDwarf");
        System.out.println(testDwarf.toString());

        // Creates test Dwarf object
        Glyphid testGlyphid = CharacterFactory.createGlyphid("testGlyphid");
        System.out.println(testGlyphid.toString());
    }
}
