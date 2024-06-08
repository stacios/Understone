package Tests;

import Controller.InputData;
import Model.Angle;
import Model.Character;
import Model.CharacterFactory;
import Model.Dwarf;
import Model.Weapon.Attack;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;
public class WeaponAttackTests {


    @Test
    public void testWeaponCooldown() {
        Dwarf testChar = CharacterFactory.createDwarf("Scout");
        testChar.setInputData(new InputData(new BitSet(13), 0, 0));
        assertTrue(testChar.attemptAttack(0,0));
        assertFalse(testChar.attemptAttack(0,0));
        for (int i = 0; i < 20; i++) {
            testChar.update();
        }
        assertTrue(testChar.attemptAttack(0,0));
        assertFalse(testChar.attemptAttack(0,0));
    }

    @Test
    public void testPendingAttacks() {
        Dwarf testChar = CharacterFactory.createDwarf("Scout");
        testChar.setInputData(new InputData(new BitSet(13), 0, 0));
        assertEquals(0, testChar.getPendingAttacks().length);
        testChar.attemptAttack(0,0);
        assertEquals(1, testChar.getPendingAttacks().length);
        assertEquals(0, testChar.getPendingAttacks().length);
        testChar.attemptAttack(0,0);
        assertEquals(0, testChar.getPendingAttacks().length);
    }

    @Test
    public void testSetPosition() {
        Dwarf testChar = CharacterFactory.createDwarf("Scout");
        testChar.setInputData(new InputData(new BitSet(13), 0, 0));

        testChar.attemptAttack(0,0);
        Attack a = testChar.getPendingAttacks()[0];
        a.setPosition(500, 500);
        assertEquals(a.getX(), 500);
        assertEquals(a.getY(), 500);

    }
}
