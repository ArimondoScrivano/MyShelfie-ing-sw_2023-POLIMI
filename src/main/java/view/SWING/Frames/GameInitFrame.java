package view.SWING.Frames;

import javax.swing.*;
import java.awt.*;

public class GameInitFrame extends JFrame {

    private JButton button1;
    private JButton button2;

    public GameInitFrame() {
        super("My Shelfie");

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        setLayout(layout);

        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;


        // C0
        button1 = new JButton("NEW GAME");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(button1, limits);
        add(button1);

        // C1
        button2 = new JButton("JOIN GAME");
        limits.gridx = 0;
        limits.gridy = 2;
        layout.setConstraints(button2, limits);
        add(button2);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
