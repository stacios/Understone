package Controller;

import Model.GameLoop;
import View.Display;

public class Main {


    public static void main(String[] args) {

        GameLoop gameLoop = GameLoop.getInstance();
        Display display = Display.getInstance();

        boolean running = true;

        while (running) {

            running = gameLoop.update(display.getInputData());

            display.render(gameLoop.getDrawData());

            try {
                Thread.sleep((long)((1.0 / 60.0) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        display.dispose();

    }
}