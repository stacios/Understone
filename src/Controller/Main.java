package Controller;

import Model.CharacterFactory;
import Model.DB.DwarfDB;
import Model.DB.SQLiteConnection;
import Model.GameLoop;
import View.Display;

import java.io.*;
import java.sql.SQLException;
/**
 * Main class for the game. Controls the game loop and the display. Contains the main loop that keeps the game running.
 */
public class Main {

    public static void main(String[] args) {

        GameLoop gameLoop = GameLoop.getInstance();
        Display display = Display.getInstance();
        gameLoop.setDwarf(display.getDwarfSelection());



        while (display.isRunning()) {

            int gameState = gameLoop.update(display.getInputData());
            display.render(gameLoop.getDrawData());
            if (gameState == 2) {
                gameLoop.pauseGame();
                display.showLoseScreen();
            }
            if (gameState == 3) {
                gameLoop.pauseGame();
                display.showWinScreen();
            }

            try {
                Thread.sleep((long)((1.0 / 60.0) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        display.dispose();
    }
}
