package View;

import Controller.DrawData;
import Controller.InputData;
import Model.DataManager;
import Model.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    private JDialog myMenuDialog;
    private boolean isRunning;
    private String myDwarfType;
    private boolean myIsShaking;
    private int myShakeDuration;
    private int myShakeMagnitude;
    private Random myRandom;
    private boolean myIsFading;
    private int myFadeDuration;
    private int myFadeProgress;
    private boolean myFadeIn;

    public Display() {
        myRandom = new Random();

        myDwarfType = askDwarfType();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myWidth = 1920;
        myHeight = 1080;
        myRealWidth = (int) screenSize.getWidth();
        myRealHeight = (int) screenSize.getHeight();
        myScaleMult = myRealHeight / 1080.0;

        myJFrame = new JFrame("Understone");
        myJFrame.setLocation(0,0);
        myJFrame.setUndecorated(true);
        isRunning = true;
        myJFrame.setVisible(true);
        myJFrame.setFocusable(true);
        myJFrame.requestFocus();
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.createBufferStrategy(2);

        myJPanel = new JPanel();
        myJPanel.setPreferredSize(new Dimension(myRealWidth, myRealHeight));
        myJFrame.add(myJPanel);
        myJFrame.setContentPane(myJPanel);
        myJFrame.pack();

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
        System.out.println(myAudioPlayer.mySounds);

        createMenuDialog();
    }

    private String askDwarfType() {
        return (String) JOptionPane.showInputDialog(null, "Choose your Dwarf:", "Understone",
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Driller", "Engineer", "Gunner", "Scout"}, "Driller");
    }
    public String getDwarfSelection() {
        return myDwarfType;
    }

    private void createMenuDialog() {
        myMenuDialog = new JDialog(myJFrame, "Menu", true);
        myMenuDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myMenuDialog.setSize(300, 200);
        myMenuDialog.setLocationRelativeTo(myJFrame);
        myMenuDialog.setLayout(new GridLayout(4, 1));

        JButton newGameButton = new JButton("New Game");
        JButton saveButton = new JButton("Save Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton quitButton = new JButton("Quit");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();

                GameLoop.getInstance().resetGame();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                JOptionPane.showMessageDialog(null, "SAVED!");
                DataManager.saveGame(GameLoop.getInstance());
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                showLoadGameDialog();
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int choice = JOptionPane.showConfirmDialog(null, "Quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    isRunning = false;
                    //dispose();
                }
                myMenuDialog.setVisible(false);
                //myInputManager.resetKeyStates();
                myJFrame.requestFocus();
            }
        });

        myMenuDialog.add(newGameButton);
        myMenuDialog.add(saveButton);
        myMenuDialog.add(loadGameButton);
        myMenuDialog.add(quitButton);
    }

    private void showLoadGameDialog() {
        JDialog loadGameDialog = new JDialog(myJFrame, "Load Game", true);
        loadGameDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadGameDialog.setSize(300, 400);
        loadGameDialog.setLocationRelativeTo(myJFrame);
        loadGameDialog.setLayout(new BorderLayout());

        List<String> savedGames = DataManager.getSavedGames();
        List<String> displayNames = savedGames.stream()
                .map(DataManager::getDisplayName)
                .collect(Collectors.toList());
        JList<String> savedGamesList = new JList<>(displayNames.toArray(new String[0]));
        savedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = savedGamesList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedGame = savedGames.get(selectedIndex);
                    GameLoop loadedGame = DataManager.loadGame(selectedGame);
                    GameLoop.getInstance().setDataLoading(loadedGame);
                    loadGameDialog.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(loadGameDialog, "Please select a game to load.");
                }
            }
        });

        loadGameDialog.add(new JScrollPane(savedGamesList), BorderLayout.CENTER);
        loadGameDialog.add(loadButton, BorderLayout.SOUTH);
        loadGameDialog.setVisible(true);
    }

    public void showMenuDialog() {
        myInputManager.resetKeyStates();
        myMenuDialog.setVisible(true);
    }

    public static Display getInstance() {
        return myInstance;
    }

    public void startFadeAnimation(int theDuration) {
        myIsFading = true;
        myFadeDuration = theDuration;
        myFadeProgress = 0;
        myFadeIn = false;
    }

    /**
     * Method for toggling fade animation between rooms 
     * TODO Possible make a better way to display moving. Possibly a slideshow kind of thing where the Room swipes down?
     * @param theG is the Graphics Object. 
     */
    private void updateFade(Graphics2D theG) {
        if (myIsFading) {
            float alpha = (myFadeIn ? (myFadeDuration - myFadeProgress) : myFadeProgress) / (float) myFadeDuration;
            
            Composite originalComposite = theG.getComposite();
            theG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            theG.setColor(Color.black);
            theG.fillRect(0, 0, myRealWidth, myRealHeight);
            theG.setComposite(originalComposite);
            myFadeProgress++;
            
            if (myFadeProgress >= myFadeDuration) {
                if (myFadeIn) {
                    myIsFading = false;
                } else {
                    myFadeIn = true;
                    myFadeProgress = 0;
                }
            }
        }
    }

    /**
     * Method for shaking screen.
     * @param theDuration is the duration.
     * @param theMagnitude is how much the screen should shake.
     */
    public void shakeScreen(int theDuration, int theMagnitude) {
        myIsShaking = true;
        myShakeDuration = theDuration;
        myShakeMagnitude = theMagnitude;
    }

    public void dispose() {
        isRunning = false;
        myJFrame.dispose();
    }
    /**
     * Renders the draw data to the display. Make sure each draw data string is in the correct format, look to Display documentation.
     */
    public void render(final String[] theDrawList) {
        if (!isRunning) {
            return;
        }
        BufferStrategy bs = myJFrame.getBufferStrategy();
        if (bs == null) {
            myJFrame.createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, myRealWidth, myRealHeight);

        // Apply screen shake if shaking is toggled
        if (myIsShaking) {
            int shakeX = myRandom.nextInt(myShakeMagnitude * 2) - myShakeMagnitude;
            int shakeY = myRandom.nextInt(myShakeMagnitude * 2) - myShakeMagnitude;
            g.translate(shakeX, shakeY);
            myShakeDuration--;
            if (myShakeDuration <= 0) {
                myIsShaking = false;
            }
        }

        for (String data : theDrawList) {
            draw(g, data.split(":"));
        }
        
        // Update fade variables/durations 
        updateFade(g);

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

    public boolean isRunning() {
        return isRunning;
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
