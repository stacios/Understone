package Controller;

import java.io.Serializable;
import java.util.BitSet;

/**
 * Represents the input data for the game, key presses, mouse presses, and mouse position.
 */
public class InputData {

    /**
     * The x-coordinate of the mouse pointer.
     */
    private int myMouseX;

    /**
     * The y-coordinate of the mouse pointer.
     */
    private int myMouseY;

    /**
     * BitSet representing the state of the keys.
     */
    private BitSet myKeys;

    /**
     * Constructs an InputData object with the specified key states and mouse position.
     *
     * @param theKeys the BitSet representing the state of the keys
     * @param theMouseX the x-coordinate of the mouse pointer
     * @param theMouseY the y-coordinate of the mouse pointer
     * @throws IllegalArgumentException if theKeys is null
     */
    public InputData(final BitSet theKeys, final int theMouseX, final int theMouseY) {
        if (theKeys == null) {
            throw new IllegalArgumentException("Keys BitSet cannot be null");
        }
        myMouseX = theMouseX;
        myMouseY = theMouseY;
        myKeys = (BitSet) theKeys.clone();

    }

    /**
     * Gets the state of the primary mouse button.
     *
     * @return true if the primary mouse button is pressed, false otherwise
     */
    public boolean getM1() {
        return myKeys.get(0);
    }

    /**
     * Gets the state of the secondary mouse button.
     *
     * @return true if the secondary mouse button is pressed, false otherwise
     */
    public boolean getM2() {
        return myKeys.get(1);
    }

    /**
     * Gets the state of the up movement key.
     *
     * @return true if the up movement key is pressed, false otherwise
     */
    public boolean getUp() {
        return myKeys.get(2);
    }

    /**
     * Gets the state of the left movement key.
     *
     * @return true if the left movement key is pressed, false otherwise
     */
    public boolean getLeft() {
        return myKeys.get(3);
    }

    /**
     * Gets the state of the down movement key.
     *
     * @return true if the down movement key is pressed, false otherwise
     */
    public boolean getDown() {
        return myKeys.get(4);
    }

    /**
     * Gets the state of the right movement key.
     *
     * @return true if the right movement key is pressed, false otherwise
     */
    public boolean getRight() {
        return myKeys.get(5);
    }

    /**
     * Gets the state of the interact key.
     *
     * @return true if the interact key is pressed, false otherwise
     */
    public boolean getInteract() {
        return myKeys.get(6);
    }

    /**
     * Gets the state of the first weapon key.
     *
     * @return true if the first weapon key is pressed, false otherwise
     */
    public boolean getWeapon1() {
        return myKeys.get(7);
    }

    /**
     * Gets the state of the second weapon key.
     *
     * @return true if the second weapon key is pressed, false otherwise
     */
    public boolean getWeapon2() {
        return myKeys.get(8);
    }

    /**
     * Gets the state of the third weapon key.
     *
     * @return true if the third weapon key is pressed, false otherwise
     */
    public boolean getWeapon3() {
        return myKeys.get(9);
    }

    /**
     * Gets the state of the fourth weapon key.
     *
     * @return true if the fourth weapon key is pressed, false otherwise
     */
    public boolean getWeapon4() {
        return myKeys.get(10);
    }

    /**
     * Gets the state of the dash key.
     *
     * @return true if the dash key is pressed, false otherwise
     */
    public boolean getDash() {
        return myKeys.get(11);
    }

    /**
     * Gets the state of the escape key.
     *
     * @return true if the escape key is pressed, false otherwise
     */
    public boolean getEscape() {
        return myKeys.get(12);
    }

    /**
     * Gets the x-coordinate of the mouse pointer.
     *
     * @return the x-coordinate of the mouse pointer
     */
    public int getMouseX() {
        return myMouseX;
    }

    /**
     * Gets the y-coordinate of the mouse pointer.
     *
     * @return the y-coordinate of the mouse pointer
     */
    public int getMouseY() {
        return myMouseY;
    }
}
