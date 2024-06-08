package Tests;

import Model.CharacterFactory;
import Model.Dwarf;
import Model.Glyphid.Glyphid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static Model.CharacterTypes.*;

/**
 * Unit tests for the CharacterFactory class.
 */
public class CharacterFactoryTests {

    /**
     * Testing Dwarf.
     */
    private Dwarf myTestingDwarf;

    /**
     * Testing Glyphid.
     */
    private Glyphid myTestingGlyphid;

    /**
     * Tests the creation of a Dwarf.
     */
    @Test
    public void testCreateDwarf() {
        myTestingDwarf = CharacterFactory.createDwarf(DRILLER);
        assertNotNull(myTestingDwarf);
        assertEquals(myTestingDwarf.getName(), "Driller");

        myTestingDwarf = CharacterFactory.createDwarf(GUNNER);
        assertNotNull(myTestingDwarf);
        assertEquals(myTestingDwarf.getName(), "Gunner");

        myTestingDwarf = CharacterFactory.createDwarf(ENGINEER);
        assertNotNull(myTestingDwarf);
        assertEquals(myTestingDwarf.getName(), "Engineer");

        myTestingDwarf = CharacterFactory.createDwarf(SCOUT);
        assertNotNull(myTestingDwarf);
        assertEquals(myTestingDwarf.getName(), "Scout");

    }

    /**
     * Tests the creation of a Glyphid.
     */
    @Test
    public void testCreateGlyphid() {
        myTestingGlyphid = CharacterFactory.createGlyphid(GRUNT);
        assertNotNull(myTestingGlyphid);
        assertEquals(myTestingGlyphid.getName(), "Grunt");

        myTestingGlyphid = CharacterFactory.createGlyphid(ACID_SPITTER);
        assertNotNull(myTestingGlyphid);
        assertEquals(myTestingGlyphid.getName(), "AcidSpitter");

        myTestingGlyphid = CharacterFactory.createGlyphid(PRAETORIAN);
        assertNotNull(myTestingGlyphid);
        assertEquals(myTestingGlyphid.getName(), "Praetorian");
    }

    /**
     * Tests the values of a created Dwarf. Placeholder for future implementation.
     */
    @Test
    public void testDwarfValues() {
        myTestingDwarf = CharacterFactory.createDwarf(DRILLER);
        assertEquals(myTestingDwarf.getHealth(), 100);
        assertEquals(myTestingDwarf.getMaxHealth(), 100);
        assertEquals(myTestingDwarf.getWeaponIndex(), 0);
        assertEquals(myTestingDwarf.getMoveSpeed(), 2.5);
    }

    /**
     * Tests the values of a created Glyphid. Placeholder for future implementation.
     */
    @Test
    public void testGlyphidValues() {
        myTestingGlyphid = CharacterFactory.createGlyphid(GRUNT);
        assertEquals(myTestingGlyphid.getHealth(), 100);
        assertEquals(myTestingGlyphid.getMoveSpeed(), 1.5);
        assertEquals(myTestingGlyphid.getMaxHealth(), 100);
    }

    /**
     * Tests the creation of an invalid Dwarf type and expects an Error.
     */
    @Test
    public void testCreateInvalidDwarf() {
        assertThrows(Error.class, () -> CharacterFactory.createDwarf("InvalidType"));
    }

    /**
     * Tests the creation of an invalid Glyphid type and expects an Error.
     */
    @Test
    public void testCreateInvalidGlyphid() {
        assertThrows(Error.class, () -> CharacterFactory.createGlyphid("InvalidType"));
    }

    /**
     * Tests the creation of an invalid Egg and Rock object and expects an Error.
     */
    @Test
    public void testCreateInvalidEggAndRock() {
        assertThrows(Error.class, () -> CharacterFactory.createObject("InvalidEgg"));
    }
}
