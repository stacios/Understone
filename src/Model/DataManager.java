package Model;

import java.io.*;

public class DataManager {
    private static final String FILE_NAME = "save_game.txt";
    public static void saveGame(Serializable theGameState) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(theGameState);
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public static Object loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return in.readObject();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return null;
        }
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return null;
        }
    }
}
