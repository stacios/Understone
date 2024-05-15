package Controller;

import java.util.BitSet;

/**
 * Represents the input data for the game, key presses, mouse presses, and mouse position.
 */
public class InputData {

    private int myMouseX;
    private int myMouseY;
    private BitSet myKeys;

    public InputData(final BitSet theKeys, final int theMouseX, final int theMouseY) {

        myMouseX = theMouseX;
        myMouseY = theMouseY;
        myKeys = (BitSet) theKeys.clone();

    }

    public boolean getM1() {
        return myKeys.get(0);
    }

    public boolean getM2() {
        return myKeys.get(1);
    }

    public boolean getUp() {
        return myKeys.get(2);
    }
    public boolean getLeft() {
        return myKeys.get(3);
    }
    public boolean getDown() {
        return myKeys.get(4);
    }
    public boolean getRight() {
        return myKeys.get(5);
    }
    public boolean getInteract() {
        return myKeys.get(6);
    }
    public boolean getWeapon1() {
        return myKeys.get(7);
    }
    public boolean getWeapon2() {
        return myKeys.get(8);
    }
    public boolean getWeapon3() {
        return myKeys.get(9);
    }
    public boolean getWeapon4() {
        return myKeys.get(10);
    }
    public boolean getDash() {
        return myKeys.get(11);
    }
    public boolean getEscape() {
        return myKeys.get(12);
    }

    public int getMouseX() {
        return myMouseX;
    }

    public int getMouseY() {
        return myMouseY;
    }
}
