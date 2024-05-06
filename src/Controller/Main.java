package Controller;

import Model.DB.DwarfDB;
import Model.DB.SQLiteConnection;
import Model.GameLoop;
import View.Display;

import java.sql.SQLException;

public class Main {

    private static GameLoop myGameLoop;

    private static Display myDisplay;


    public static void main(String[] args) {

        myGameLoop = new GameLoop();
        myDisplay = new Display();

        boolean running = true;
        SQLiteConnection.getDataSource();
        DwarfDB.intitializeDwarfDB();

        while (running) {

            running = myGameLoop.update(myDisplay.getInputData());

            myDisplay.render(myGameLoop.getDrawData());

            try {
                Thread.sleep((long)((1.0 / 60.0) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        myDisplay.dispose();

    }
}