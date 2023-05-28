package view.SWING.Frames;

import javax.swing.*;
import java.awt.*;

public class NewGameFrame extends JFrame {
    private JLabel label;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public NewGameFrame() {
        super("My Shelfie");

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        setLayout(layout);

        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;

        // C0
        label = new JLabel("Choose the number of players:");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(label, limits);
        add(label);
        // C1
        button1 = new JButton("2 PLAYERS");
        limits.gridx = 0;
        limits.gridy = 1;
        layout.setConstraints(button1, limits);
        add(button1);

        // C2
        button2 = new JButton("3 PLAYERS");
        limits.gridx = 0;
        limits.gridy = 2;
        layout.setConstraints(button2, limits);
        add(button2);

        // C3
        button3 = new JButton("4  PLAYERS");
        limits.gridx = 0;
        limits.gridy = 3;
        layout.setConstraints(button3, limits);
        add(button3);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
