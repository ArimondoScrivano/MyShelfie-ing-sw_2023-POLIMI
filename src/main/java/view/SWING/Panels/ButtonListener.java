package view.SWING.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private boolean definedChoice;
    private int choice;
    public ButtonListener(int choice){
        definedChoice= false;
        this.choice=choice;
    }

    public int getChoice() {
        return choice;
    }

    public boolean getdefinedChoice() {
        return definedChoice;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.definedChoice=true;

    }
}
