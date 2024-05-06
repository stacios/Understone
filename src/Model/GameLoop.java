package Model;

import Controller.DrawData;
import Controller.InputData;
import Model.DB.SQLTesting;

public class GameLoop {

    private DrawData temp = new DrawData("test", null, 100, 100, 100, 100);
    private double temp2 = 0;

    public boolean update(final InputData theInput) {

        if (theInput.getForward() || theInput.getM1()) {
            if (temp.getWidth() != 100) {
                temp = new DrawData("test", null, 600, 600, 1500, 800);
            }
            else {
                temp = new DrawData("test", new String[]{"boing"}, 600, 600, 1500, 800);
            }

        }
        else {
            temp = new DrawData("test", null, theInput.getMouseX(), theInput.getMouseY(), 100, 100, temp2);
        }

        temp2 = (temp2 + .1) % (2 * Math.PI);

        return !theInput.getEscape();
    }

    public DrawData[] getDrawData() {
        return new DrawData[]{temp};
    }
}
