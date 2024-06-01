package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AudioPlayer {

    public final HashMap<String, File> mySounds;

    public AudioPlayer() {
        mySounds = new HashMap<>();
        loadSounds(new File("sounds"));
    }

    private void loadSounds(File folder) {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                loadSounds(file); //Basically recursively goes through the folder and subfolders until all .wav files are added
            } else if (file.getName().endsWith(".wav")) {
                mySounds.put(file.getName().substring(0, file.getName().length() - 4), file);
            }
        }
    }

    public void playSound(String theSound) {
        File file = mySounds.get(theSound);
        if (file == null) {
            throw new IllegalArgumentException("Sound file not found: " + theSound);
        }

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            //System.out.println("playing sound " +theSound);
        } catch (UnsupportedAudioFileException | LineUnavailableException
                 | IOException | NullPointerException e) {
            System.err.println("Unable to play audio: \n" + e);
        }
    }
}
