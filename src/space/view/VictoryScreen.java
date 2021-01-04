package space.view;

import space.Translations;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VictoryScreen extends JDialog implements ActionListener {
    private static BufferedImage image = null;

    public VictoryScreen(JFrame parent){
        super(parent);
        try{
            if(image == null)
                image = ImageIO.read(new File("Data/Static/victory.png"));
        }
        catch(IOException e){
            System.out.println("Error while reading from file: " + "Data/Static/victory.png" + ": " + e);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(800, 600);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(new ImageIcon(image.getScaledInstance(getWidth(), getHeight()-100, WIDTH))), gbc);
        gbc.gridy = 1;
        add(new JLabel(Translations.getTranslation("victory_text")), gbc);
        JButton closeButton = new JButton(Translations.getTranslation("victory_restart"));
        closeButton.addActionListener(this);
        gbc.gridy = 2;
        add(closeButton, gbc);
    }

    public void actionPerformed(ActionEvent e){
        dispose();
    }
}
