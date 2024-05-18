package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ImageLibrary {

    private final HashMap<String, BufferedImage> myImages;

    public ImageLibrary() {

        myImages = new HashMap<>();

        for (File file : Objects.requireNonNull(new File("images").listFiles())) {
            if (file.getName().endsWith(".png")) {
                try {
                    myImages.put(file.getName().substring(0, file.getName().length() - 4), ImageIO.read(file));
                } catch (IOException e) {
                    throw new RuntimeException("Unable to load image " + file.getName());
                }
            }
        }
        //System.out.println(myImages);

    }

    public BufferedImage get(String theName) {
        BufferedImage temp = myImages.get(theName);
        if (temp == null) {
            throw new IllegalArgumentException("Cannot find image " + theName);
        }
        else {
            return temp;
        }
    }
}
