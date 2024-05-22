package Tests;

import Model.DB.GlyphidDB;
import org.junit.jupiter.api.*;

import static Model.CharacterTypes.*;
import static org.junit.jupiter.api.Assertions.*;

public class GlyphidDBTest {

    @BeforeEach
    public void setUp() {
        // Initialize the database before each test
        GlyphidDB.initializeDB();
    }

    @Test
    public void testInitializeGlyphidTables() {
        assertDoesNotThrow(() -> GlyphidDB.initializeDB());
    }

    @Test
    public void testInsertAcidSpitterDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertAcidSpitterDefaults());
    }

    @Test
    public void testInsertGlyphidDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertGlyphidDefaults());
    }

    @Test
    public void testInsertGruntDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertGruntDefaults());
    }

    @Test
    public void testInsertMacteraDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertMacteraDefaults());
    }

    @Test
    public void testInsertPraetorianDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertPraetorianDefaults());
    }

    @Test
    public void testInsertRockDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertRockDefaults());
    }

    @Test
    public void testInsertSwarmerDefaults() {
        assertDoesNotThrow(() -> GlyphidDB.insertSwarmerDefaults());
    }

    @Test
    public void testGetDefaultValue() {
        int value = GlyphidDB.getDefaultValue(GLYPHID, "health");
        assertEquals(100, value);
        // Todo: Mock and Test all Glyphid values
        // (alternatively this doesn't really need to be tested since there's no way another value can be inserted)
    }

    @Test
    public void testGetDefaultValueNonExistentSetting() {
        int value = GlyphidDB.getDefaultValue(GLYPHID, "non_existent");
        assertEquals(0, value);
    }
}
