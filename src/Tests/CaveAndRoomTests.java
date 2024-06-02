package Tests;
import static org.junit.jupiter.api.Assertions.*;

import Model.CharacterFactory;
import Model.Dwarf;
import Model.Spaces.Cave;
import Model.Spaces.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Model.CharacterTypes.*;

/**
 * Unit tests for Cave and Room classes.
 */
public class CaveAndRoomTests {
    private Cave myCave;
    private Room myFirstRoom;
    private Room myLastRoom;
    private Dwarf myDwarf;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        myCave = new Cave();
        // Initialized with an identifier of 0, meaning that it is the first room
        myFirstRoom = new Room(0, 5);
        // Initialized with an identifier of 4, meaning that it is the last room
        myLastRoom = new Room(4, 5);
        myDwarf = CharacterFactory.createDwarf(SCOUT);
    }

    /**
     * Tests if the cave is generated correctly.
     */
    @Test
    public void testGenerateCave() {
        assertNotNull(myCave.getCurrentRoom());
        //assertTrue(myCave.hasNextRoom() || !myCave.hasNextRoom());
    }

    /**
     * Tests moving to the next room in the cave.
     */
    @Test
    public void testMoveToNextRoom() {
        Room initialRoom = myCave.getCurrentRoom();
        myCave.moveToNextRoom();
        assertNotEquals(initialRoom, myCave.getCurrentRoom());
    }

    /**
     * Tests moving to the previous room in the cave.
     */
    @Test
    public void testMoveToPreviousRoom() {
        myCave.moveToNextRoom();
        Room secondRoom = myCave.getCurrentRoom();
        myCave.moveToPreviousRoom();
        assertNotEquals(secondRoom, myCave.getCurrentRoom());
    }

    /**
     * Tests if the cave has a next room.
     */
    @Test
    public void testHasNextRoom() {
        myCave.moveToNextRoom();
        //assertTrue(myCave.hasNextRoom() || !myCave.hasNextRoom());
    }

    /**
     * Tests spawning enemies in the first room.
     */
    @Test
    public void testSpawnEnemies() {
        myFirstRoom.spawnEnemies();
        assertNotEquals(0, myFirstRoom.getGlyphids());
    }

    /**
     * Tests spawning rock and egg in the last room.
     */
    @Test
    public void testSpawnRockAndEggInLastRoom() {
        myFirstRoom.spawnEnemies();
        myLastRoom.spawnEnemies();

        assertFalse(myFirstRoom.hasEgg());

    }

    /**
     * Tests if the first room can be exited.
     */
    @Test
    public void testCanExit() {
        myFirstRoom.spawnEnemies();
        assertFalse(myFirstRoom.canExit());
    }

    /**
     * Tests if the dwarf is in an exitable area.
     */
    @Test
    public void testIsDwarfInExitableArea() {
        //Dwarf is in exitable area
        myDwarf.setX(900);
        myDwarf.setY(110);
        assertTrue(myFirstRoom.isDwarfInArea(myDwarf));

        // Dwarf is not in exitable area
        myDwarf.setX(100);
        myDwarf.setY(110);
        assertFalse(myFirstRoom.isDwarfInArea(myDwarf));
    }
}
