package view.SWING.Frames;

import javax.swing.*;

public class MainFrame extends JFrame {

    /**
     * Constructs a new MainFrame object.
     * Initializes the main frame of the MY SHELFIE GAME application.
     */
    public MainFrame(){
        super("MY SHELFIE GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(930, 800);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(false);

    }
}
