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

    private Dwarf myTestingDwarf;
    private Glyphid myTestingGlyphid;

    /**
     * Tests the creation of a Dwarf of type DRILLER.
     */
    @Test
    public void testCreateDwarf() {
        myTestingDwarf = CharacterFactory.createDwarf(DRILLER);
        assertNotNull(myTestingDwarf);
    }

    /**
     * Tests the creation of a Glyphid of type GRUNT.
     */
    @Test
    public void testCreateGlyphid() {
        myTestingGlyphid = CharacterFactory.createGlyphid(GRUNT);
        assertNotNull(myTestingGlyphid);
    }

    /**
     * Tests the values of a created Dwarf. Placeholder for future implementation.
     */
    @Test
    public void testDwarfValues() {
        // TODO: We can just test Dwarf values through the toString, but we should wait for values to be finalized before testing.
        // String expected = "expectedobject";
        // assertEquals(expected, myTestingDwarf.toString());
    }

    /**
     * Tests the values of a created Glyphid. Placeholder for future implementation.
     */
    @Test
    public void testGlyphidValues() {
        // TODO: We can just test Glyphid values through the toString, but we should wait for values to be finalized before testing.
        // String expected = "expectedobject";
        // assertEquals(expected, myTestingGlyphid.toString());
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
