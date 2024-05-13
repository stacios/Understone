package Model.DB;

import Model.CharacterFactory;
import Model.Dwarf;
import Model.Glyphid.Glyphid;

import static Model.CharacterTypes.*;

/**
 * Runnable main DB class for making database changes.
 */
public class DBMain {
    public static void main(String[] theArgs) {

        // Initializes Database
        //SQLiteConnection.getDataSource();

        // Initializes tables and data insertion for Dwarf and Glyphid.
        DwarfDB.initializeDB();
        //GlyphidDB.initializeDB();

        // Creates test Dwarf object
        Dwarf testDriller = CharacterFactory.createDwarf(DRILLER);
        System.out.println(testDriller.toString());

        // Creates test Dwarf object
        Glyphid testGlyphid = CharacterFactory.createGlyphid("testGlyphid");
        System.out.println(testGlyphid.toString());
    }
}
