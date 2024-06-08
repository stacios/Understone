package Model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages the saving and loading of game data.
 */
public class DataManager {

    /**
     * Prefix for save game files.
     */
    private static final String FILE_PREFIX = "save_game_";

    /**
     * Extension for save game files.
     */
    private static final String FILE_EXTENSION = ".txt";

    /**
     * Directory for save game files.
     */
    private static final String SAVE_DIR = "save_games";

    /**
     * Static block to create the save_games directory if it does not exist.
     */
    static {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Saves the entire GameLoop using passed Gameloop Parameter. Prints to save game file
     * and includes system date as text identifier.
     *
     * @param theGameLoop is the complete GameLoop object passed to ensure correct save.
     */
    public static void saveGame(final GameLoop theGameLoop) {
        // Generate a unique file name using the current timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = SAVE_DIR + File.separator + FILE_PREFIX + timeStamp + FILE_EXTENSION;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(theGameLoop);
            System.out.println("Saved game: " + theGameLoop);
        } catch (IOException ex) {
            System.out.println("IOException is caught while saving: " + ex.getMessage());
        }
    }

    /**
     * Loads game from specified filename. Processes through reading object and casting
     * to a GameLoop Type. Returns complete Gameloop Object to be played.
     *
     * @param theFileName is the specified file String to be read.
     * @return new GameLoop to be played.
     */
    public static GameLoop loadGame(final String theFileName) {
        final String filePath = SAVE_DIR + File.separator + theFileName;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            GameLoop gl = (GameLoop) in.readObject();
            System.out.println("Loaded game: " + gl);
            return gl;
        } catch (IOException ex) {
            System.out.println("IOException is caught while loading: " + ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return null;
        }
    }

    /**
     * Gets list of saved games by checking files and returns as a list.
     *
     * @return saved games.
     */
    public static List<String> getSavedGames() {
        List<String> savedGames = new ArrayList<>();
        File dir = new File(SAVE_DIR);

        for (File file : dir.listFiles()) {
            if (file.getName().startsWith(FILE_PREFIX) && file.getName().endsWith(FILE_EXTENSION)) {
                savedGames.add(file.getName());
            }
        }

        return savedGames;
    }

    /**
     * Gets display name of save game.
     *
     * @param theFileName is the specified file name.
     * @return timestamp of the time the file was saved.
     */
    public static String getDisplayName(final String theFileName) {
        String timeStamp = theFileName.substring(FILE_PREFIX.length(), theFileName.length() - FILE_EXTENSION.length());
        return "Saved game " + timeStamp;
    }
}
