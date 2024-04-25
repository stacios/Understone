package Model;

import Controller.DrawData;
import Controller.InputData;

public class GameLoop {

    private DrawData temp = new DrawData("test", null, 100, 100, 100, 100);

    public boolean update(final InputData theInput) {

        if (theInput.getForward() || theInput.getM1()) {
            if (temp.getWidth() != 100) {
                temp = new DrawData("test", null, 200, 200, 800, 800);
            }
            else {
                temp = new DrawData("test", new String[]{"boing"}, 200, 200, 800, 800);
            }

        }
        else {
            temp = new DrawData("test", null, theInput.getMouseX(), theInput.getMouseY(), 100, 100);
        }

        return !theInput.getEscape();
    }

    public DrawData[] getDrawData() {
        return new DrawData[]{temp};
    }
}
