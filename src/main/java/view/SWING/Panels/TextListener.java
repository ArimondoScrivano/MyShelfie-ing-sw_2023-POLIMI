package view.SWING.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The TextListener class implements the ActionListener interface and represents a listener for text input fields.
 */
public class TextListener implements ActionListener {
    private boolean definedChoice;
    private String choice;
    private final JTextField myTF;


    /**
     * Constructs a new instance of the TextListener class.
     * This constructor initializes the TextListener by setting the definedChoice flag to false
     * and assigning the provided JTextField to the myTF instance variable.
     *
     * @param myTF the JTextField associated with this TextListener
     */
    public TextListener(JTextField myTF){
        definedChoice= false;
        this.myTF= myTF;
    }



    /**
     * Returns the user's choice as a string.
     *
     * @return the user's choice as a string
     */
    public String getChoice() {
        return choice;
    }



    /**
     * Checks if a choice has been defined or not.
     *
     * @return true if a choice has been defined, false otherwise
     */
    public boolean getdefinedChoice() {
        return definedChoice;

    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the ActionEvent associated with the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.choice= myTF.getText();
        this.definedChoice=true;

    }
}
