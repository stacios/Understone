package View;

import Controller.DrawData;
import Controller.InputData;
import Model.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;

public class Display {

    private static final Display myInstance = new Display();
    private int temp;
    private final int myWidth;
    private final int myHeight;
    private final JFrame myJFrame;
    private final JPanel myJPanel;
    private final ImageLibrary myImageLibrary;
    private final InputManager myInputManager;
    private final AudioPlayer myAudioPlayer;

    public Display() {
        myWidth = 1920;
        myHeight = 1080;
        myJFrame = new JFrame("Understone");
        myJFrame.setLocation(0,0);
        myJFrame.setUndecorated(true);
        myJFrame.setVisible(true);
        myJFrame.setFocusable(true);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myJPanel = new JPanel();
        myJPanel.setPreferredSize(new Dimension(myWidth, myHeight));
        myJFrame.add(myJPanel);
        myJFrame.setContentPane(myJPanel);
        myJFrame.pack();

        myImageLibrary = new ImageLibrary();
        myInputManager = new InputManager();
        myAudioPlayer = new AudioPlayer();

        myJFrame.addKeyListener(myInputManager);
        myJPanel.addMouseListener(myInputManager);
        myJPanel.addMouseMotionListener(myInputManager);

    }

    public static Display getInstance() {
        return myInstance;
    }

    public void dispose() {
        myJFrame.dispose();
    }

    public void render(final DrawData[] theDrawList) {
        BufferStrategy bs = myJFrame.getBufferStrategy();
        if (bs == null){
            myJFrame.createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, myWidth, myHeight);

        for (DrawData data : theDrawList) {
            draw(g, data);
        }

        g.dispose();
        bs.show();
    }

    private void draw(final Graphics2D theGraphics, final DrawData theData) {

        if (theData.getAngle() == 0) {
            theGraphics.drawImage(myImageLibrary.get(theData.getImage()),
                    theData.getX() - theData.getWidth()/2, theData.getY() - theData.getHeight()/2,
                    theData.getWidth(), theData.getHeight(), null);
        }
        else {
            AffineTransform backup = theGraphics.getTransform();
            theGraphics.rotate(theData.getAngle(), theData.getX(), theData.getY());
            theGraphics.drawImage(myImageLibrary.get(theData.getImage()),
                    theData.getX() - theData.getWidth()/2, theData.getY() - theData.getHeight()/2,
                    theData.getWidth(), theData.getHeight(), null);
            theGraphics.setTransform(backup);
        }

        if (theData.getSounds() != null) {
            for (String sound : theData.getSounds()) {
                myAudioPlayer.playSound(sound);
            }
        }
    }

    public InputData getInputData() {
        return myInputManager.getInputData();
    }
}
