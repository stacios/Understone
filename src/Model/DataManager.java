package Model;

import java.io.*;

public class DataManager {
    public static void saveGame(Serializable theGameState, String theFilename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(theFilename))) {
            out.writeObject(theGameState);
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public static Object loadGame(String theFilename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(theFilename))) {
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
