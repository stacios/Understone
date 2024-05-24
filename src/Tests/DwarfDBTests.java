package Tests;

import Model.DB.DwarfDB;
import org.junit.jupiter.api.*;

import static Model.CharacterTypes.*;
import static org.junit.jupiter.api.Assertions.*;

public class DwarfDBTests {

    @BeforeEach
    public void setUp() {
        // Initialize the database before each test
        DwarfDB.initializeDB();
    }

    @Test
    public void testInitializeDwarfTables() {
        assertDoesNotThrow(() -> DwarfDB.initializeDB());
    }

    @Test
    public void testInsertDrillerDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertDrillerDefaults());
    }

    @Test
    public void testInsertEngineerDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertEngineerDefaults());
    }

    @Test
    public void testInsertGunnerDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertGunnerDefaults());
    }

    @Test
    public void testInsertScoutDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertScoutDefaults());
    }

    @Test
    public void testGetDefaultValue() {
        int value = DwarfDB.getDefaultValue(DRILLER, "health");
        assertEquals(110, value);

        // Todo: Mock and Test all Dwarf values
        // (alternatively this doesn't really need to be tested since there's no way another value can be inserted)
    }

    @Test
    public void testGetDefaultValueNonExistentSetting() {
        int value = DwarfDB.getDefaultValue(DRILLER, "non_existent");
        assertEquals(0, value);
    }
}