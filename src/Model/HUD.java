package Model;

import View.ImageLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD extends JPanel {
    private JLabel myWeaponIcon;
    private JProgressBar myHealthBar;
    private BufferedImage myWeaponImg;

    public HUD() {

        //setTitle("Game HUD");
        //setSize(300, 100);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 100)); // Size of the HUD panel
        setLayout(new BorderLayout());

        // Initialize HUD elements
        ImageLibrary myImageLibrary = new ImageLibrary();
        //BufferedImage myWeaponImg = myImageLibrary.get(GameLoop.getInstance().getMyPlayer().getMyWeapon().getMyName());
        BufferedImage myWeaponImgOriginal = myImageLibrary.get("Pistol");
        myWeaponImg = scaleImage(myWeaponImgOriginal, 100, 100);
        ImageIcon icon = new ImageIcon(myWeaponImg);
        myWeaponIcon = new JLabel(icon);
        myHealthBar = new JProgressBar(0, 100);
        myHealthBar.setValue(GameLoop.getInstance().getMyPlayer().getMyHealth());
        myHealthBar.setStringPainted(true);

        add(myWeaponIcon, BorderLayout.WEST);
        add(myHealthBar, BorderLayout.CENTER);

        //setVisible(true);
    }

   /* public void draw(Graphics g, int screenWidth, int screenHight) {
        int hudHeight = 70; // Assuming the total height of the HUD elements
        int margin = 10; // Margin from the edges
        int screenHeight = 600;
        int health = GameLoop.getInstance().getMyPlayer().getMyHealth();

        g.drawImage(myWeaponImg, margin, screenHeight - hudHeight + 10, null);

        g.drawImage(myWeaponImg, 10, 10, null);
        g.setColor(Color.RED);
        g.fillRect(margin, screenHeight - hudHeight + 40, health * 2, 20);
        //g.fillRect(10, 50, health * 2, 20);
        g.setColor(Color.BLACK);
        g.drawRect(margin, screenHeight - hudHeight + 40, 200, 20);
        //g.drawRect(10, 50, 200, 20);
    }*/

    private BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return scaledImage;
    }
}
