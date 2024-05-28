package Tests;

import Model.DB.DwarfDB;
import org.junit.jupiter.api.*;

import static Model.CharacterTypes.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DwarfDB class.
 */
public class DwarfDBTests {

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        DwarfDB.initializeDB();
    }

    /**
     * Tests the initialization of dwarf tables.
     */
    @Test
    public void testInitializeDwarfTables() {
        assertDoesNotThrow(() -> DwarfDB.initializeDB());
    }

    /**
     * Tests inserting default values for the Driller.
     */
    @Test
    public void testInsertDrillerDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertDrillerDefaults());
    }

    /**
     * Tests inserting default values for the Engineer.
     */
    @Test
    public void testInsertEngineerDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertEngineerDefaults());
    }

    /**
     * Tests inserting default values for the Gunner.
     */
    @Test
    public void testInsertGunnerDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertGunnerDefaults());
    }

    /**
     * Tests inserting default values for the Scout.
     */
    @Test
    public void testInsertScoutDefaults() {
        assertDoesNotThrow(() -> DwarfDB.insertScoutDefaults());
    }

    /**
     * Tests retrieving the default value for a specific attribute of a dwarf type.
     */
    @Test
    public void testGetDefaultValue() {
        int value = DwarfDB.getDefaultValue(DRILLER, "health");
        assertEquals(110, value);

        // Todo: Mock and Test all Dwarf values
        // (alternatively this doesn't really need to be tested since there's no way another value can be inserted)
    }

    /**
     * Tests retrieving the default value for a non-existent setting.
     */
    @Test
    public void testGetDefaultValueNonExistentSetting() {
        int value = DwarfDB.getDefaultValue(DRILLER, "non_existent");
        assertEquals(0, value);
    }
}
