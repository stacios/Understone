package View;

import Controller.DrawData;
import Controller.InputData;
import Model.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;

/**
 * Used to display the game. Uses formatted strings as draw data. Should be formatted as follows:
 * "image:filename:x:y:width:height"
 * "rotatedimage:filename:x:y:width:height:angle"
 * "rectangle:x:y:width:height"
 * "text:yourText:x:y:fontSize"
 * "sound:soundname"
 */
public class Display {

    private static final Display myInstance = new Display();
    /**
     * The multiplier that converts from game coordinates to real coordinates.
     */
    private final double myScaleMult;

    /**
     * The in-game width of the display (1920).
     */
    private final int myWidth;
    /**
     * The in-game height of the display (1080).
     */
    private final int myHeight;
    /**
     * The actual width of the display on the user's screen
     */
    private final int myRealWidth;
    /**
     * The actual height of the display on the user's screen
     */
    private final int myRealHeight;
    private final JFrame myJFrame;
    private final JPanel myJPanel;
    private final ImageLibrary myImageLibrary;
    private final InputManager myInputManager;
    private final AudioPlayer myAudioPlayer;

    public Display() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myWidth = 1920;
        myHeight = 1080;
        myRealWidth = (int) screenSize.getWidth();
        myRealHeight = (int) screenSize.getHeight();
        myScaleMult = myRealHeight / 1080.0;

        myJFrame = new JFrame("Understone");
        myJFrame.setLocation(0,0);
        myJFrame.setUndecorated(true);
        myJFrame.setVisible(true);
        myJFrame.setFocusable(true);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myJPanel = new JPanel();
        myJPanel.setPreferredSize(new Dimension(myRealWidth, myRealHeight));
        myJFrame.add(myJPanel);
        myJFrame.setContentPane(myJPanel);
        myJFrame.pack();

        myImageLibrary = new ImageLibrary();
        myInputManager = new InputManager();
        myAudioPlayer = new AudioPlayer();

        myJFrame.addKeyListener(myInputManager);
        myJPanel.addMouseListener(myInputManager);
        myJPanel.addMouseMotionListener(myInputManager);
        System.out.println(myAudioPlayer.mySounds);

    }

    public static Display getInstance() {
        return myInstance;
    }

    public void dispose() {
        myJFrame.dispose();
    }
    /**
     * Renders the draw data to the display. Make sure each draw data string is in the correct format, look to Display documentation.
     */
    public void render(final String[] theDrawList) {
        BufferStrategy bs = myJFrame.getBufferStrategy();
        if (bs == null){
            myJFrame.createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, myRealWidth, myRealHeight);


        for (String data : theDrawList) {
            draw(g, data.split(":"));
        }

        g.dispose();
        bs.show();
    }

    private void draw(final Graphics2D theGraphics, final String[] theData) {

        for (int i = 0; i < theData.length; i++) {
            theData[0] = theData[0].strip();
        }
        int x;
        int y;
        int width;
        int height;
        double angle;
        int size;

        switch (theData[0]) {

            case "image":
                x = (int) (myScaleMult * Double.parseDouble(theData[2]));
                y = (int) (myScaleMult * Double.parseDouble(theData[3]));
                width = (int) (myScaleMult * Integer.parseInt(theData[4]));
                height = (int) (myScaleMult * Integer.parseInt(theData[5]));

                theGraphics.drawImage(myImageLibrary.get(theData[1]),
                        x - width/2, y - height/2,
                        width, height, null);
                break;

            case "rotatedImage":
                x = (int) (myScaleMult * Double.parseDouble(theData[2]));
                y = (int) (myScaleMult * Double.parseDouble(theData[3]));
                width = (int) (myScaleMult * Integer.parseInt(theData[4]));
                height = (int) (myScaleMult * Integer.parseInt(theData[5]));
                angle = Double.parseDouble(theData[6]);

                AffineTransform backup = theGraphics.getTransform();
                theGraphics.rotate(angle, x, y);
                theGraphics.drawImage(myImageLibrary.get(theData[1]),
                        x - width/2, y - height/2,
                        width, height, null);
                theGraphics.setTransform(backup);
                break;

            case "rectangle":
                x = (int) (myScaleMult * Double.parseDouble(theData[1]));
                y = (int) (myScaleMult * Double.parseDouble(theData[2]));
                width = (int) (myScaleMult * Integer.parseInt(theData[3]));
                height = (int) (myScaleMult * Integer.parseInt(theData[4]));

                theGraphics.setColor(Color.RED);
                theGraphics.fillRect(x - width/2, y - height/2, width, height);
                break;

            case "sound":
                myAudioPlayer.playSound(theData[1]);
                break;

            case "text":
                x = (int) (myScaleMult * Double.parseDouble(theData[2]));
                y = (int) (myScaleMult * Double.parseDouble(theData[3]));
                size = (int) (myScaleMult * Integer.parseInt(theData[4]));

                theGraphics.setColor(Color.RED);
                theGraphics.setFont(new Font("SansSerif", Font.BOLD, size));
                theGraphics.drawString(theData[1], x, y);
                break;

            default:
                throw new IllegalArgumentException(theData[0] + " not valid input");
        }

    }

    public InputData getInputData() {
        return myInputManager.getInputData();
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }
}
