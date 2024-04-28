package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AudioPlayer {

    private final HashMap<String, File> mySounds;
    public AudioPlayer() {

        mySounds = new HashMap<>();

        for (File file : Objects.requireNonNull(new File("sounds").listFiles())) {
            if (file.getName().endsWith(".wav")) {
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

        } catch (UnsupportedAudioFileException | LineUnavailableException
                 | IOException | NullPointerException e) {
            System.err.println("Unable to play audio");
        }
    }
}
