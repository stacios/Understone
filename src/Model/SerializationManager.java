package Model;

import java.io.*;

public class SerializationManager {
    String filename = "game.ser";
    public static void saveGame(GameLoop game, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(game);
            out.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public static GameLoop loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameLoop) in.readObject();
            //in.close();
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
