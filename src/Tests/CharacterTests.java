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

public class CharacterTests {

    private Character character;
    private Weapon weapon;

    @Before
    public void setUp() {
        weapon = new Weapon(60, new MeleeAttack(10, 200, 200, 10.0, 70), null);
        character = new Character("TestCharacter", 100, 100, 100, 50, 50, 10, weapon) {

        };
    }

    @Test
    public void testSetX() {
        character.setX(200);
        assertEquals(200, character.getX(), 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setX(-1);
        });
    }

    @Test
    public void testSetY() {
        character.setY(200);
        assertEquals(200, character.getY(), 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setY(-1);
        });
    }

    @Test
    public void testSetHealth() {
        character.setHealth(50);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setHealth(-1);
        });
    }

    @Test
    public void testSetMaxHealth() {
        character.setMaxHealth(150);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setMaxHealth(-1);
        });
    }

    @Test
    public void testSetWidth() {
        character.setWidth(75);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setWidth(0);
        });
    }

    @Test
    public void testSetHeight() {
        character.setHeight(75);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setHeight(0);
        });
    }

    @Test
    public void testSetMoveSpeed() {
        character.setMoveSpeed(20);
        assertEquals(20, character.getMoveSpeed(), 0.01);

        assertThrows(IllegalArgumentException.class, () -> {
            character.setMoveSpeed(-1);
        });
    }

    @Test
    public void testSetName() {
        character.setName("NewName");

        assertThrows(IllegalArgumentException.class, () -> {
            character.setName("");
        });
    }

    @Test
    public void testAttemptAttack() {
        assertTrue(character.attemptAttack(200, 200));
    }


    @Test
    public void testReceiveForces() {
        Force force = new Force(new Angle(0), 10, 0.5);
        character.addForce(force);
        character.update();
        assertEquals(110, character.getX(), 0.01);
        assertEquals(100, character.getY(), 0.01);
    }

    @Test
    public void testGetDrawData() {
        String[] drawData = character.getDrawData();
        assertArrayEquals(new String[]{"image:TestCharacter:100.0:100.0:50:50"}, drawData);
    }

    @Test
    public void testGetHitbox() {
        int[] hitbox = character.getHitbox();
        assertArrayEquals(new int[]{100, 100, 50, 50}, hitbox);
    }

    @Test
    public void testToString() {
        String characterString = character.toString();
        assertEquals("Printing TestCharacter: {X: 100.0, Y: 100.0, Health: 100, MaxHealth: 100, MoveSpeed: 10.0, Width: 50, Height: 50}", characterString);
    }
}
