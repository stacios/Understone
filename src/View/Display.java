package View;

import Controller.DrawData;
import Controller.InputData;
import Model.DataManager;
import Model.GameLoop;
import Model.HUD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private JDialog myMenuDialog;

    public Display() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myWidth = (int) screenSize.getWidth();
        myHeight = (int) screenSize.getHeight();
        System.out.println(myWidth + " " + myHeight);

        myJFrame = new JFrame("Understone");
        myJFrame.setLocation(0,0);
        myJFrame.setUndecorated(true);
        myJFrame.setVisible(true);
        myJFrame.setFocusable(true);
        myJFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        myJPanel = new JPanel();
        myJPanel.setPreferredSize(new Dimension(myWidth, myHeight));
        myJPanel.setLayout(null);

        myJFrame.add(myJPanel);
        myJFrame.pack();
        myJFrame.setLocationRelativeTo(null);
        myJFrame.setVisible(true);

        myImageLibrary = new ImageLibrary();
        myInputManager = new InputManager();
        myAudioPlayer = new AudioPlayer();

        myInputManager.setEscapeKeyListener(new InputManager.EscapeKeyListener() {
            @Override
            public void onEscapePressed() {
                showMenuDialog();
            }
        });

        myJFrame.addKeyListener(myInputManager);
        myJPanel.addMouseListener(myInputManager);
        myJPanel.addMouseMotionListener(myInputManager);

        createMenuDialog();
    }
    private void createMenuDialog() {
        myMenuDialog = new JDialog(myJFrame, "Menu", true);
        myMenuDialog.setSize(300, 200);
        myMenuDialog.setLocationRelativeTo(myJFrame);
        myMenuDialog.setLayout(new GridLayout(4, 1));

        JButton newGameButton = new JButton("New Game");
        JButton saveButton = new JButton("Save");
        JButton resumeButton = new JButton("Resume");
        JButton quitButton = new JButton("Quit");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                JOptionPane.showMessageDialog(null, "NEW GAME!");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                JOptionPane.showMessageDialog(null, "SAVED!");
                DataManager.saveGame(GameLoop.getInstance());
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                JOptionPane.showMessageDialog(null, "RESUME!");
                System.out.println("Loaded Game: " + DataManager.loadGame());
                GameLoop.getInstance().setDataLoading(DataManager.loadGame());
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int choice = JOptionPane.showConfirmDialog(null, "QUIT!", "QUIT", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                }
                myMenuDialog.setVisible(false);
            }
        });

        myMenuDialog.add(newGameButton);
        myMenuDialog.add(saveButton);
        myMenuDialog.add(resumeButton);
        myMenuDialog.add(quitButton);
    }
    public void showMenuDialog() {
        myMenuDialog.setVisible(true);
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
