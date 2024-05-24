package View;

import Controller.InputData;

import java.awt.event.*;
import java.util.BitSet;

public class InputManager implements MouseListener, KeyListener, MouseMotionListener {

    private final BitSet myKeysPressed;
    private int myMouseX;
    private int myMouseY;
    private EscapeKeyListener myEscapeKeyListener;

    public InputManager() {
        myKeysPressed = new BitSet(13);
    }
    public InputData getInputData() {
        return new InputData(myKeysPressed, myMouseX, myMouseY);
    }
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

    public void setEscapeKeyListener(EscapeKeyListener listener) {
        this.myEscapeKeyListener = listener;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        setKeyCode(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setKeyCode(e.getKeyCode(), false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setKeyCode(e.getButton(), true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setKeyCode(e.getButton(), false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nothing
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        myMouseX = e.getX();
        myMouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        myMouseX = e.getX();
        myMouseY = e.getY();
    }

    public interface EscapeKeyListener {
        void onEscapePressed();
    }

    public void resetKeyStates() {
        myKeysPressed.clear();
    }

}
