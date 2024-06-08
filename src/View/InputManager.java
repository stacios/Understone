package View;

import Controller.InputData;

import java.awt.event.*;
import java.util.BitSet;

/**
 * Detects inputs from the mouse and keyboard
 */
public class InputManager implements MouseListener, KeyListener, MouseMotionListener {

    /**
     * Bitset with boolean representing each control
     */
    private final BitSet myKeysPressed;

    /**
     * The x-coordinate of the mouse pointer.
     */
    private int myMouseX;

    /**
     * The y-coordinate of the mouse pointer.
     */
    private int myMouseY;

    /**
     * Listener for the escape key.
     */
    private EscapeKeyListener myEscapeKeyListener;

    /**
     * Constructs an InputManager and initializes the key state BitSet.
     */
    public InputManager() {
        myKeysPressed = new BitSet(13);
    }

    /**
     * Returns InputData based on current detected inputs.
     *
     * @return the InputData representing the current state of inputs
     */
    public InputData getInputData() {
        return new InputData(myKeysPressed, myMouseX, myMouseY);
    }

    /**
     * Sets the state of a key based on its key code.
     *
     * @param theCode  the key code
     * @param theValue the state of the key (true if pressed, false if released)
     */
    private void setKeyCode(final int theCode, final boolean theValue) {
        switch (theCode) {
            case 1:
                myKeysPressed.set(0, theValue);
                break;
            case 2:
                myKeysPressed.set(1, theValue);
                break;
            case KeyEvent.VK_W:
                myKeysPressed.set(2, theValue);
                break;
            case KeyEvent.VK_A:
                myKeysPressed.set(3, theValue);
                break;
            case KeyEvent.VK_S:
                myKeysPressed.set(4, theValue);
                break;
            case KeyEvent.VK_D:
                myKeysPressed.set(5, theValue);
                break;
            case KeyEvent.VK_SPACE:
                myKeysPressed.set(6, theValue);
                break;
            case KeyEvent.VK_1:
                myKeysPressed.set(7, theValue);
                break;
            case KeyEvent.VK_2:
                myKeysPressed.set(8, theValue);
                break;
            case KeyEvent.VK_3:
                myKeysPressed.set(9, theValue);
                break;
            case KeyEvent.VK_4:
                myKeysPressed.set(10, theValue);
                break;
            case KeyEvent.VK_SHIFT:
                myKeysPressed.set(11, theValue);
                break;
            case KeyEvent.VK_ESCAPE:
                myKeysPressed.set(12, theValue);
                if (theValue && myEscapeKeyListener != null) {
                    myEscapeKeyListener.onEscapePressed();
                }
                break;
        }
    }

    /**
     * Sets the escape key listener.
     *
     * @param listener the listener to set
     */
    public void setEscapeKeyListener(EscapeKeyListener listener) {
        this.myEscapeKeyListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        setKeyCode(e.getKeyCode(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        setKeyCode(e.getKeyCode(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        setKeyCode(e.getButton(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        setKeyCode(e.getButton(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        //nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        myMouseX = e.getX();
        myMouseY = e.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        myMouseX = e.getX();
        myMouseY = e.getY();
    }

    /**
     * Interface for handling escape key press events.
     */
    public interface EscapeKeyListener {
        void onEscapePressed();
    }

    /**
     * Resets the state of all keys to not pressed.
     */
    public void resetKeyStates() {
        myKeysPressed.clear();
    }
}
