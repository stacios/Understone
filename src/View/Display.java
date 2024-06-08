package View;

import Controller.InputData;
import Model.DataManager;
import Model.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
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

    /**
     * The main JFrame for displaying the game.
     */
    private final JFrame myJFrame;

    /**
     * The main JPanel for holding the game content.
     */
    private final JPanel myJPanel;

    /**
     * Library for managing game images.
     */
    private final ImageLibrary myImageLibrary;

    /**
     * Manager for handling user input.
     */
    private final InputManager myInputManager;

    /**
     * Player for handling game audio.
     */
    private final AudioPlayer myAudioPlayer;

    /**
     * Dialog for displaying the game menu.
     */
    private JDialog myMenuDialog;

    /**
     * Flag indicating whether the game is currently running.
     */
    private boolean isRunning;

    /**
     * The type of dwarf selected by the user.
     */
    private String myDwarfType;

    /**
     * Flag indicating whether the screen is currently shaking.
     */
    private boolean myIsShaking;

    /**
     * Duration of the screen shake effect.
     */
    private int myShakeDuration;

    /**
     * Magnitude of the screen shake effect.
     */
    private int myShakeMagnitude;

    /**
     * Random number generator for screen shake effect.
     */
    private Random myRandom;

    /**
     * Flag indicating whether the screen is currently fading.
     */
    private boolean myIsFading;

    /**
     * Duration of the fade effect.
     */
    private int myFadeDuration;

    /**
     * Progress of the fade effect.
     */
    private int myFadeProgress;

    /**
     * Flag indicating whether the fade effect is fading in.
     */
    private boolean myFadeIn;

    /**
     * Constructor for the Display class. Initializes the display components and settings.
     */
    public Display() {
        myRandom = new Random();
        myImageLibrary = new ImageLibrary();
        myAudioPlayer = new AudioPlayer();
        myDwarfType = askDwarfType();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myWidth = 1920;
        myHeight = 1080;
        myRealWidth = (int) screenSize.getWidth();
        myRealHeight = (int) screenSize.getHeight();
        myScaleMult = myRealWidth / 1920.0;

        myJFrame = new JFrame("Understone");
        myJFrame.setLocation(0, 0);
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

        myInputManager = new InputManager();


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

    /**
     * Prompts the user to select a dwarf type.
     * @return The selected dwarf type.
     */
    private String askDwarfType() {
        JPanel panel = new JPanel(new FlowLayout());
        JLabel imageLabel = new JLabel();
        panel.add(imageLabel);
        JComboBox<String> dwarfComboBox = new JComboBox<>(new String[]{"Driller", "Engineer", "Gunner", "Scout", "karl"});
        // Default Selected Driller
        dwarfComboBox.setSelectedIndex(0);
        updateDwarfSelectionEffect(imageLabel, (String) dwarfComboBox.getSelectedItem());
        dwarfComboBox.addActionListener(e -> updateDwarfSelectionEffect(imageLabel, (String) dwarfComboBox.getSelectedItem()));
        panel.add(dwarfComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Choose your Dwarf", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            myDwarfType = (String) dwarfComboBox.getSelectedItem();
            return (String) dwarfComboBox.getSelectedItem();
        } else {
            System.exit(0);
            return null;
        }
    }

    /**
     * Updates the visual and audio effects based on the selected dwarf type.
     * @param theImageLabel The JLabel to update with the dwarf image.
     * @param theDwarfType The selected dwarf type.
     */
    private void updateDwarfSelectionEffect(final JLabel theImageLabel, final String theDwarfType) {
        // Change Dwarf Selection Image
        Image scaledImage = myImageLibrary.get(theDwarfType).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        theImageLabel.setIcon(new ImageIcon(scaledImage));

        // Play Dwarf Selection Sound
        try {
            // comment this out if you need because gets annoying for dev
            myAudioPlayer.playSound("CharSel" + theDwarfType);
        } catch (Exception theEx) {
            System.out.println("Incorrect character sound file specified.");
        }
    }

    /**
     * Gets the selected dwarf type.
     * @return The selected dwarf type.
     */
    public String getDwarfSelection() {
        return myDwarfType;
    }

    /**
     * Creates the menu dialog for the game.
     */
    private void createMenuDialog() {
        myMenuDialog = new JDialog(myJFrame, "Menu", true);
        myMenuDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myMenuDialog.setSize(300, 400);
        myMenuDialog.setLocationRelativeTo(myJFrame);
        myMenuDialog.setLayout(new GridLayout(7, 1));
        myMenuDialog.setResizable(false);
        myMenuDialog.setUndecorated(true);

        JButton resumeButton = new JButton("Resume");
        JButton newGameButton = new JButton("New Game");
        JButton saveButton = new JButton("Save Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton instructionsButton = new JButton("Instructions");
        JButton creditsButton = new JButton("Credits");
        JButton quitButton = new JButton("Quit");


        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
                GameLoop.getInstance().pauseGame();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();

                // Reset the game with the current dwarf type
                GameLoop.getInstance().resetGame(myDwarfType);
                GameLoop.getInstance().pauseGame();
            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMenuDialog.setVisible(false);
               // JOptionPane.showMessageDialog(myMenuDialog, "SAVED!");
                DataManager.saveGame(GameLoop.getInstance());
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
                GameLoop.getInstance().pauseGame();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoadGameDialog();
                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
                //GameLoop.getInstance().pauseGame();
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBackground(Color.WHITE);

                JLabel messageLabel = new JLabel("<html>Welcome to Hoxxes IV!<br>"
                        + "Fight your way down the cave to reach the alien egg and bring it back!<br><br>"
                        + "<b>Controls:</b><br>"
                        + "<div></div>"
                        + "Use <b>W A S D</b> to move around.<br>"
                        + "Press <b>Shift</b> to dash in the direction you are moving.<br>"
                        + "<b>Click</b> to shoot your weapon towards the cursor.<br>"
                        + "Use <b>1, 2, and 3</b> to switch weapons.<br>"
                        + "<div></div>"
                        + "<b>Gameplay:</b>"
                        + "<div></div>"
                        + "Once all of the bugs in a room are dead, approach a tunnel and press <b>Space</b> to move through it.<br>"
                        + "In the final room, break the egg out of the rock and press <b>Space</b> to pick it up.<br>"
                        + "Then go back the way you came to escape. Good luck miner!</html>");
                messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                messageLabel.setForeground(Color.BLACK);

                panel.add(messageLabel, BorderLayout.CENTER);

                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JOptionPane.showMessageDialog(myMenuDialog, panel, "Instructions", JOptionPane.PLAIN_MESSAGE);

                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
            }
        });

        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(Color.WHITE);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                gbc.anchor = GridBagConstraints.CENTER;

                JLabel messageLabel =
                        new JLabel("<html><div style='text-align: center;'>Developed By:<br>Owen Crema<br>Staci Laban<br>Ares Zhang<br>Spring 2024</div></html>");
                messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                messageLabel.setForeground(Color.BLACK);
                messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                panel.add(messageLabel, gbc);

                JOptionPane.showMessageDialog(myMenuDialog, panel, "Credits", JOptionPane.PLAIN_MESSAGE);

                myInputManager.resetKeyStates();
                myJFrame.requestFocus();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int choice = JOptionPane.showConfirmDialog(myMenuDialog, "Quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    isRunning = false;
                    myJFrame.dispose();
                    System.exit(0);
                }
                GameLoop.getInstance().pauseGame();
                myMenuDialog.setVisible(false);
                myJFrame.requestFocus();
            }
        });

        myMenuDialog.add(resumeButton);
        myMenuDialog.add(newGameButton);
        myMenuDialog.add(saveButton);
        myMenuDialog.add(loadGameButton);
        myMenuDialog.add(instructionsButton);
        myMenuDialog.add(creditsButton);
        myMenuDialog.add(quitButton);
    }

    /**
     * Shows the load game dialog, allowing the user to select a saved game to load.
     */
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

    /**
     * Shows the game menu dialog.
     */
    public void showMenuDialog() {
        myInputManager.resetKeyStates();
        GameLoop.getInstance().pauseGame();
        myMenuDialog.setVisible(true);
    }

    /**
     * Gets the singleton instance of the Display class.
     * @return The singleton instance of the Display class.
     */
    public static Display getInstance() {
        return myInstance;
    }

    /**
     * Starts the fade animation for room transitions.
     * @param theDuration The duration of the fade animation.
     */
    public void startFadeAnimation(int theDuration) {
        myIsFading = true;
        myFadeDuration = theDuration;
        myFadeProgress = 0;
        myFadeIn = false;
    }

    /**
     * Updates the fade effect for the display.
     * @param theG The Graphics2D object used for drawing.
     */
    private void updateFade(final Graphics2D theG) {
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
     * Shakes the screen for a specified duration and magnitude.
     * @param theDuration The duration of the screen shake effect.
     * @param theMagnitude The magnitude of the screen shake effect.
     */
    public void shakeScreen(final int theDuration, final int theMagnitude) {
        myIsShaking = true;
        myShakeDuration = theDuration;
        myShakeMagnitude = theMagnitude;
    }

    /**
     * Disposes of the display and stops the game.
     */
    public void dispose() {
        isRunning = false;
        myJFrame.dispose();
    }

    /**
     * Renders the draw data to the display. Ensure each draw data string is in the correct format, as documented.
     * @param theDrawList The list of draw data strings.
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

        g.setColor(Color.black);
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

    /**
     * Draws the specified data onto the graphics context.
     * @param theGraphics The Graphics2D object used for drawing.
     * @param theData The draw data to be processed.
     */
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
        BufferedImage image;

        switch (theData[0]) {

            case "image":
                x = (int) (myScaleMult * Double.parseDouble(theData[2]));
                y = (int) (myScaleMult * Double.parseDouble(theData[3]));
                width = (int) (myScaleMult * Integer.parseInt(theData[4]));
                height = (int) (myScaleMult * Integer.parseInt(theData[5]));

                theGraphics.drawImage(myImageLibrary.get(theData[1]),
                        x - width / 2, y - height / 2,
                        width, height, null);
                break;

            case "unboundImage":
                x = (int) (myScaleMult * Double.parseDouble(theData[2]));
                y = (int) (myScaleMult * Double.parseDouble(theData[3]));
                image = myImageLibrary.get(theData[1]);
                width = (int) (myScaleMult * Double.parseDouble(theData[4]) * image.getWidth());
                height = (int) (myScaleMult * Double.parseDouble(theData[4]) * image.getHeight());

                theGraphics.drawImage(image,
                        x, y, width, height,  null);
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
                        x - width / 2, y - height / 2,
                        width, height, null);
                theGraphics.setTransform(backup);
                break;

            case "rectangle":
                x = (int) (myScaleMult * Double.parseDouble(theData[1]));
                y = (int) (myScaleMult * Double.parseDouble(theData[2]));
                width = (int) (myScaleMult * Integer.parseInt(theData[3]));
                height = (int) (myScaleMult * Integer.parseInt(theData[4]));

                theGraphics.setColor(Color.RED);
                theGraphics.fillRect(x - width / 2, y - height / 2, width, height);
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

            case "screenShake":
                myIsShaking = true;
                myShakeDuration = Integer.parseInt(theData[1]);
                myShakeMagnitude = Integer.parseInt(theData[2]);
                break;

            case "fade":
                myIsFading = true;
                myFadeDuration = Integer.parseInt(theData[1]);
                myFadeProgress = 0;
                //myFadeIn = false;
                myFadeIn = true;
                break;

            default:
                throw new IllegalArgumentException(theData[0] + " not valid input");
        }

    }

    /**
     * Checks if the display is currently running.
     * @return true if the display is running, false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Gets the input data from the input manager.
     * @return The input data from the input manager.
     */
    public InputData getInputData() {
        return myInputManager.getInputData();
    }

    /**
     * Gets the in-game width of the display.
     * @return The in-game width of the display.
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Gets the in-game height of the display.
     * @return The in-game height of the display.
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Shows the lose screen and resets the game.
     */
    public void showLoseScreen() {
        myAudioPlayer.playSound("Lose");
        JOptionPane.showMessageDialog(myMenuDialog, "You Died!");
        myMenuDialog.setVisible(false);
        myInputManager.resetKeyStates();
        myJFrame.requestFocus();

        // Reset the game with the current dwarf type
        GameLoop.getInstance().resetGame(myDwarfType);
        GameLoop.getInstance().pauseGame();
    }

    /**
     * Shows the win screen and resets the game.
     */
    public void showWinScreen() {
        myAudioPlayer.playSound("Win");
        myAudioPlayer.playSound("RockAndStone" + myDwarfType);
        JOptionPane.showMessageDialog(myMenuDialog, "You Collected the Egg! Restarting Game.");
        myMenuDialog.setVisible(false);
        myInputManager.resetKeyStates();
        myJFrame.requestFocus();

        // Reset the game with the current dwarf type
        GameLoop.getInstance().resetGame(myDwarfType);
        GameLoop.getInstance().pauseGame();
    }
}
