package view.SWING.Frames;

import javax.swing.*;


/**
 * The MainFrame class represents the main frame of the MY SHELFIE GAME application.
 * It extends the JFrame class and provides the graphical user interface for the game.
 */
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
