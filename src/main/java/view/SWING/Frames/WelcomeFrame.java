package view.SWING.Frames;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {

    private JLabel label1;
    private JLabel label2;
    private JTextField textField;
    private JButton button;

    public WelcomeFrame() {
        // recall superclass constructor
        super("My Shelfie");

        // set frame layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        setLayout(layout);


        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;

        // create components


        // C0
        label1 = new JLabel("Welcome to My Shelfie!");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(label1, limits);
        add(label1);

        // C1
        label2 = new JLabel("Please, insert your nickname:");
        limits.gridx = 0;
        limits.gridy = 1;
        layout.setConstraints(label2, limits);
        add(label2);

        // C2
        textField = new JTextField(20);
        limits.gridx = 0;
        limits.gridy = 2;
        layout.setConstraints(textField, limits);
        add(textField);


        // set frame dimensions
        setSize(800, 500);
        // set frame position [screen pixels are counted from high-left corner]
        setLocationRelativeTo(null);
        // set if the window's size can be modified
        setResizable(true);
        // defines what happens at window closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set frame visible
        setVisible(true);
    }

}
