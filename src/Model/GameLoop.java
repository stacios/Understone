package Model;

import Controller.DrawData;
import Controller.InputData;
import Model.Spaces.Cave;
import Model.Spaces.Room;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLoop implements Serializable {
    private static final long serialVersionUID = 8L;
    private static final GameLoop myInstance = new GameLoop();

    private Dwarf myPlayer;
    private Cave myCave;

    private Room myActiveRoom;

    private ArrayList<DrawData> myDrawDataList;
    private DrawData temp = new DrawData("test", null, 100, 100, 100, 100);
    private double temp2 = 0;

    private GameLoop() {
        myDrawDataList = new ArrayList<>();

        //temp
        myActiveRoom = new Room(false, false);
        myPlayer = new Dwarf("Driller", 800, 800, 100, 100, 100, 5, null);
    }

    public static GameLoop getInstance() {
        return myInstance;
    }

    public boolean update(final InputData theInput) {

        myDrawDataList.clear();

        /*
        if (theInput.getUp() || theInput.getM1()) {
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

         */


        myPlayer.setInputData(theInput);
        myPlayer.update();
        myDrawDataList.add(myActiveRoom.getDrawData());
        myDrawDataList.add(myPlayer.getDrawData());

        //System.out.println(myPlayer.getDrawData());


        myDrawDataList.add(new DrawData("Driller", null, 100, 100, 100, 100));

        return !theInput.getEscape();
    }

    public DrawData[] getDrawData() {
        return myDrawDataList.toArray(new DrawData[0]);
    }
}
