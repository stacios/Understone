package Model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    private static final String FILE_PREFIX = "save_game_";
    private static final String FILE_EXTENSION = ".txt";

    public static void saveGame(GameLoop theGameLoop) {
        // Generate a unique file name using the current timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = FILE_PREFIX + timeStamp + FILE_EXTENSION;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(theGameLoop);
        } catch (IOException ex) {
            System.out.println("IOException is caught while saving " + ex.getMessage());
        }
    }

    public static GameLoop loadGame(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameLoop) in.readObject();
        } catch (IOException ex) {
            System.out.println("IOException is caught while loading " + ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return null;
        }
    }
    public static List<String> listSavedGames() {
        List<String> savedGames = new ArrayList<>();
        File dir = new File(".");

        for (File file : dir.listFiles()) {
            if (file.getName().startsWith(FILE_PREFIX) && file.getName().endsWith(FILE_EXTENSION)) {
                savedGames.add(file.getName());
            }
        }

        return savedGames;
    }
    public static String getDisplayName(String fileName) {
        String timeStamp = fileName.substring(FILE_PREFIX.length(), fileName.length() - FILE_EXTENSION.length());
        return "Saved game " + timeStamp;
    }
}


