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
    private HUD myHud;
    private JMenuBar myMenuBar;
    private JMenu myGameMenu;
    private JMenuItem myNewGameMenuItem;
    private JMenuItem mySaveMenuItem;
    private JMenuItem myResumeMenuItem;
    private JMenuItem myQuitMenuItem;
    private JMenu myHelpMenu;
    private JMenuItem myAboutMenuItem;
    private JMenuItem myRulesMenuItem;
    private JMenuItem myShortcutsMenuItem;

    public Display() {
        //myWidth = 1920;
        //myHeight = 1080;
        myWidth = 920;
        myHeight = 600;
        myJFrame = new JFrame("Understone");
        myJFrame.setLocation(0,0);
        myJFrame.setUndecorated(true);
        myJFrame.setVisible(true);
        myJFrame.setFocusable(true);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myJPanel = new JPanel();
        myJPanel.setPreferredSize(new Dimension(myWidth, myHeight));
        myJPanel.setLayout(null);
        myHud = new HUD();
        myHud.setBounds(0, myHeight - 100, 300, 100);
        myJPanel.add(myHud);


        myJFrame.add(myJPanel);
        myJFrame.pack();
        myJFrame.setLocationRelativeTo(null);
        myJFrame.setVisible(true);

        myImageLibrary = new ImageLibrary();
        myInputManager = new InputManager();
        myAudioPlayer = new AudioPlayer();

        myJFrame.addKeyListener(myInputManager);
        myJPanel.addMouseListener(myInputManager);
        myJPanel.addMouseMotionListener(myInputManager);

        setMenu();
        addListeners();
    }

    private void setMenu() {
        // Create menu components and set mnemonics and accelerators.
        myMenuBar = new JMenuBar();
        myGameMenu = new JMenu("Game");
        myNewGameMenuItem = new JMenuItem("New Game");
        mySaveMenuItem = new JMenuItem("Save");
        myResumeMenuItem = new JMenuItem("Resume");
        myQuitMenuItem = new JMenuItem("Exit");
        myHelpMenu = new JMenu("Help");
        myAboutMenuItem = new JMenuItem("About");
        myRulesMenuItem = new JMenuItem("Rules");
        myShortcutsMenuItem = new JMenuItem("Shortcuts");


        setMenuMnemonicsAndAccelerators();

        // Add menu items to the menu.
        myGameMenu.add(myNewGameMenuItem);
        myGameMenu.add(mySaveMenuItem);
        myGameMenu.add(myResumeMenuItem);
        myGameMenu.addSeparator();
        myGameMenu.add(myQuitMenuItem);
        myHelpMenu.add(myAboutMenuItem);
        myHelpMenu.add(myRulesMenuItem);
        myHelpMenu.add(myShortcutsMenuItem);

        // Add menus to the menu bar.
        myMenuBar.add(myGameMenu);
        myMenuBar.add(myHelpMenu);
    }

    private void setMenuMnemonicsAndAccelerators() {
        //mnemonics
        myGameMenu.setMnemonic(KeyEvent.VK_G);
        myHelpMenu.setMnemonic(KeyEvent.VK_H);
        //accelerators for menu items
        myNewGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
        mySaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        myResumeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                ActionEvent.CTRL_MASK));
        myQuitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                ActionEvent.CTRL_MASK));
        myAboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                ActionEvent.CTRL_MASK));
        myRulesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                ActionEvent.CTRL_MASK));
        myShortcutsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
                ActionEvent.CTRL_MASK));
    }

    private void addListeners() {
        myNewGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(null,
                        "NEW GAME!");

            }
        });
        mySaveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(null,
                        "SAVED!");
                DataManager.saveGame(GameLoop.getInstance());
            }
        });
        myResumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(null,
                        "CONTINUE!");
                DataManager.loadGame();
            }
        });
        myQuitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                final int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?",
                        "Exit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        myAboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                JOptionPane.showMessageDialog(myMenuBar,
                        "Author: Ares, Owen, Staci \nVersion: 1.0\nJava Version: "
                                + System.getProperty("java.version")
                                + "\nEnjoy the game!", "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        myRulesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                final String gameRules = "Rules:\n"
                        + "JUST WIN!\n";

                JOptionPane.showMessageDialog(null, gameRules,
                        "Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        myShortcutsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theE) {
                final String shortcutList = "List of Shortcuts:\n"
                        + "Game Menu: Alt + G\n"
                        + "Help Menu: Alt + H\n"
                        + "New Game: Ctrl + N\n"
                        + "Save Game: Ctrl + S\n"
                        + "Resume Session: Ctrl + R\n"
                        + "Quit: Ctrl + E\n"
                        + "About: Ctrl + A\n"
                        + "Rules: Ctrl + L\n"
                        + "Shortcuts: Ctrl + T\n";
                JOptionPane.showMessageDialog(null, shortcutList,
                        "Shortcuts", JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
