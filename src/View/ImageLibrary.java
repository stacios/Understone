package View;

import Controller.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Used to contain image data from resource files
 */
public class ImageLibrary {

    /**
     * Map of images
     */
    private final Map<String, BufferedImage> myImages;

    public ImageLibrary() {

        myImages = new HashMap<>();
    }

    /**
     * Returns a BufferedImage of the file {theName}.png
     * @param theName
     * @return
     */
    public BufferedImage get(final String theName) {
        BufferedImage temp = myImages.get(theName);
        if (temp == null) {
            URL temp2 = Main.class.getClassLoader().getResource(theName + ".png");
            if (temp2 != null) {
                try {
                    temp = ImageIO.read(temp2);
                } catch (IOException e) {
                    throw new RuntimeException("Could not read image " + theName + "\n" + e);
                }
                myImages.put(theName, temp);
            }
            else {
                throw new IllegalArgumentException("Could not find image " + theName + ".png");
            }
        }

        return temp;
    }
}
