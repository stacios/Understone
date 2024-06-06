package View;

import Controller.Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class AudioPlayer {

    public final HashMap<String, URL> mySounds;

    public AudioPlayer() {
        mySounds = new HashMap<>();
    }

    public void playSound(String theSound) {
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
