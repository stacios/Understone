package Tests;

import Model.Angle;
import Model.Character;
import Model.Force;
import Model.Weapon.Attack;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Character class.
 */
public class CharacterTests {

    private Character myCharacter;
    private Weapon myWeapon;

    /**
     * Sets up the test environment before each test.
     */
    @Before
    public void setUp() {
        myWeapon = new Weapon(60, new MeleeAttack(10, 200, 200, 10.0, 70), null);
        myCharacter = new Character("TestCharacter", 100, 100, 100, 50, 50, 10, myWeapon) {
        };
    }

    /**
     * Tests the setX method.
     */
    @Test
    public void testSetX() {
        myCharacter.setX(200);
        assertEquals(200, myCharacter.getX(), 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setX(-1);
        });
    }

    /**
     * Tests the setY method.
     */
    @Test
    public void testSetY() {
        myCharacter.setY(200);
        assertEquals(200, myCharacter.getY(), 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setY(-1);
        });
    }

    /**
     * Tests the setHealth method.
     */
    @Test
    public void testSetHealth() {
        myCharacter.setHealth(50);
        assertEquals(50, myCharacter.getHealth());

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setHealth(-1);
        });
    }

    /**
     * Tests the setMaxHealth method.
     */
    @Test
    public void testSetMaxHealth() {
        myCharacter.setMaxHealth(150);
        assertEquals(150, myCharacter.getMaxHealth());

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setMaxHealth(-1);
        });
    }

    /**
     * Tests the setWidth method.
     */
    @Test
    public void testSetWidth() {
        myCharacter.setWidth(75);

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setWidth(0);
        });
    }

    /**
     * Tests the setHeight method.
     */
    @Test
    public void testSetHeight() {
        myCharacter.setHeight(75);

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setHeight(0);
        });
    }

    /**
     * Tests the setMoveSpeed method.
     */
    @Test
    public void testSetMoveSpeed() {
        myCharacter.setMoveSpeed(20);
        assertEquals(20, myCharacter.getMoveSpeed(), 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setMoveSpeed(-1);
        });
    }

    /**
     * Tests the setName method.
     */
    @Test
    public void testSetName() {
        myCharacter.setName("NewName");
        assertEquals("NewName", myCharacter.getName());

        assertThrows(IllegalArgumentException.class, () -> {
            myCharacter.setName("");
        });
    }

    /**
     * Tests the attemptAttack method.
     */
    @Test
    public void testAttemptAttack() {
        assertTrue(myCharacter.attemptAttack(200, 200));
    }

    /**
     * Tests the receiveForces method.
     */
    @Test
    public void testReceiveForces() {
        //Force force = new Force(new Angle(0), 10, 0.5);
        //myCharacter.addForce(force);
        //myCharacter.update();
        //assertEquals(110, myCharacter.getX(), 0.01);
        //assertEquals(100, myCharacter.getY(), 0.01);
    }

    /**
     * Tests the getDrawData method.
     */
    @Test
    public void testGetDrawData() {
        String[] drawData = myCharacter.getDrawData();
        assertArrayEquals(new String[]{"image:TestCharacter:100.0:100.0:50:50"}, drawData);
    }

    /**
     * Tests the getHitbox method.
     */
    @Test
    public void testGetHitbox() {
        int[] hitbox = myCharacter.getHitbox();
        assertArrayEquals(new int[]{100, 100, 50, 50}, hitbox);
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        String myCharacterString = myCharacter.toString();
        assertEquals("Printing TestCharacter: {X: 100.0, Y: 100.0, Health: 100, MaxHealth: 100, MoveSpeed: 10.0, Width: 50, Height: 50}", myCharacterString);
    }
}
