package view.SWING.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextListener implements ActionListener {
    private boolean definedChoice;
    private String choice;
    private JTextField myTF;
    public TextListener(JTextField myTF){
        definedChoice= false;
        this.myTF= myTF;
    }

    public String getChoice() {
        return choice;
    }

    public boolean getdefinedChoice() {
        return definedChoice;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.choice= myTF.getText();
        this.definedChoice=true;

    }
}
