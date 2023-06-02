package view.SWING.Frames;

import javax.swing.*;

public class MainFrame extends JFrame {
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
