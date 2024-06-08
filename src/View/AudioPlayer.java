package View;

import Controller.Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Used to play sound clips from the project's resource files
 */
public class AudioPlayer {

    /**
     * Map of sound URLs
     */
    public final Map<String, URL> mySounds;

    public AudioPlayer() {
        mySounds = new HashMap<>();
    }

    /**
     * Plays the sound file {theSound}.wav
     * @param theSound
     */
    public void playSound(final String theSound) {
        URL url = mySounds.get(theSound);
        if (url == null) {
            url = Main.class.getClassLoader().getResource(theSound + ".wav");
            if (url == null) {
                throw new IllegalArgumentException("Sound file not found: " + theSound);
            }
            mySounds.put(theSound, url);
        }

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(url);
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
