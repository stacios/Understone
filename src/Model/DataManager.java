package Model;

import java.io.*;

public class DataManager {
    private static final String FILE_NAME = "save_game.txt";
    public static void saveGame(GameLoop theGameLoop) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(theGameLoop);
        } catch (IOException ex) {
            System.out.println("IOException is caught while saving " + ex.getMessage());
        }
    }

    public static GameLoop loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (GameLoop) in.readObject();
        } catch (IOException ex) {
            System.out.println("IOException is caught while loading " + ex.getMessage());
            return null;
        }
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return null;
        }
    }
}
