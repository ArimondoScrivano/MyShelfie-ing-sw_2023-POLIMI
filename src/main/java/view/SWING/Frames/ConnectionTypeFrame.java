package view.SWING.Frames;

import javax.swing.*;
import java.awt.*;

public class ConnectionTypeFrame extends JFrame {

    private JPanel mainPanel;
    private JLabel label;
    private JButton button1;
    private JButton button2;


    public ConnectionTypeFrame() {
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
        //mainPanel = new JPanel();
        //mainPanel.setLayout(layout);

        // C0
        label = new JLabel("CHOOSE CONNECTION'S TYPE");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(label, limits);
        add(label);

        // C1
        button1 = new JButton("RMI");
        limits.gridx = 0;
        limits.gridy = 1;
        limits.gridwidth = 1;
        limits.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(button1, limits);
        add(button1);

        // C2
        button2 = new JButton("SOCKET");
        limits.gridx = 0;
        limits.gridy = 2;
        limits.gridwidth = 1;
        limits.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(button2, limits);
        add(button2);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
