package Tests;

import Model.CharacterFactory;
import Model.Dwarf;
import Model.Glyphid.Glyphid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static Model.CharacterTypes.*;

public class CharacterFactoryTests {

    private Dwarf myTestingDwarf;
    private Glyphid myTestingGlyphid;

    @Test
    public void testCreateDwarf() {
        myTestingDwarf = CharacterFactory.createDwarf(DRILLER);
        assertNotNull(myTestingDwarf);
    }

    @Test
    public void testCreateGlyphid() {
        myTestingGlyphid = CharacterFactory.createGlyphid(GRUNT);
        assertNotNull(myTestingGlyphid);
    }

    @Test
    public void testDwarfValues() {
        //Todo We can just test Dwarf values through the toString, but we should wait for values to be finalized before testing.
        //String expected = "expectedobject";

        //assertEquals(expected, myTestingDwarf.toString());
    }

    @Test
    public void testGlyphidValues() {
        //Todo We can just test Glyphid values through the toString, but we should wait for values to be finalized before testing.
        //String expected = "expectedobject";

        //assertEquals(expected, myTestingGlyphid.toString());
    }

    @Test
    public void testCreateInvalidDwarf() {
        assertThrows(Error.class, () -> CharacterFactory.createDwarf("InvalidType"));
    }

    @Test
    public void testCreateInvalidGlyphid() {
        assertThrows(Error.class, () -> CharacterFactory.createGlyphid("InvalidType"));
    }
}