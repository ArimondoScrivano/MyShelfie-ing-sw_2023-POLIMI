package view.SWING.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ButtonListener class implements the ActionListener interface to handle button click events.
 * It keeps track of a choice associated with the button and provides methods to retrieve the choice value.
 */
public class ButtonListener implements ActionListener {
    private boolean definedChoice;
    private final int choice;

    /**
     * Constructs a new ButtonListener object with the specified choice.
     *
     * @param choice the choice associated with the button
     */
    public ButtonListener(int choice){
        definedChoice= false;
        this.choice=choice;
    }

    /**
     * Returns the choice associated with the button.
     *
     * @return the choice value
     */
    public int getChoice() {
        return choice;
    }

    /**
     * Checks if the choice has been defined.
     *
     * @return true if the choice has been defined, false otherwise
     */
    public boolean getdefinedChoice() {
        return definedChoice;
    }


    /**
     * Handles the button click event.
     *
     * @param e the ActionEvent representing the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.definedChoice=true;

    }
}
