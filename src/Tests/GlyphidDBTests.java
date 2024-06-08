package Tests;

import Model.DB.GlyphidDB;
import org.junit.jupiter.api.*;

import static Model.CharacterTypes.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GlyphidDB class.
 */
public class GlyphidDBTests {

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        GlyphidDB.initializeDB();
    }

    /**
     * Tests the initialization of Glyphid tables.
     */
    @Test
    public void testInitializeGlyphidTables() {
        assertDoesNotThrow(() -> GlyphidDB.initializeDB());
    }

    /**
     * Tests inserting default values for the Acid Spitter.
     */
    @Test
    public void testInsertAcidSpitterDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertAcidSpitterDefaults());
    }

    /**
     * Tests inserting default values for Glyphid.
     */
    @Test
    public void testInsertGlyphidDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertGlyphidDefaults());
    }

    /**
     * Tests inserting default values for the Grunt.
     */
    @Test
    public void testInsertGruntDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertGruntDefaults());
    }

    /**
     * Tests inserting default values for the Mactera.
     */
    @Test
    public void testInsertMacteraDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertMacteraDefaults());
    }

    /**
     * Tests inserting default values for the Praetorian.
     */
    @Test
    public void testInsertPraetorianDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertPraetorianDefaults());
    }

    /**
     * Tests inserting default values for the Rock.
     */
    @Test
    public void testInsertRockDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertRockDefaults());
    }

    /**
     * Tests inserting default values for the Swarmer.
     */
    @Test
    public void testInsertSwarmerDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertSwarmerDefaults());
    }

    /**
     * Tests retrieving the default value for a specific attribute of a Glyphid type.
     */
    @Test
    public void testGetDefaultValue() {
        int value = GlyphidDB.getDefaultValue(GLYPHID, "health");
        assertEquals(100, value);
    }

    /**
     * Tests retrieving the default value for a non-existent setting.
     */
    @Test
    public void testGetDefaultValueNonExistentSetting() {
        int value = GlyphidDB.getDefaultValue(GLYPHID, "non_existent");
        assertEquals(0, value);
    }
}
