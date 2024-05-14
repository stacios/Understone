package Model;

import Controller.DrawData;
import Controller.Drawable;
import Controller.InputData;
import Model.Spaces.Cave;
import Model.Spaces.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameLoop implements Drawable {

    private static final GameLoop myInstance = new GameLoop();

    private Dwarf myPlayer;
    private Cave myCave;

    private Room myActiveRoom;

    private ArrayList<String> myDrawDataList;
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


        myPlayer.setInputData(theInput);
        myPlayer.update();
        myDrawDataList.addAll(Arrays.asList(myActiveRoom.getDrawData()));
        myDrawDataList.addAll(Arrays.asList(myPlayer.getDrawData()));


        //System.out.println(myPlayer.getDrawData());



        myDrawDataList.add("text:hello:100:100:40");

        return !theInput.getEscape();
    }

    public String[] getDrawData() {
        return myDrawDataList.toArray(new String[0]);
    }

}
