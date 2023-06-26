package view.SWING.Panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShelfListener implements ActionListener {

    private boolean permission;
    private boolean doneChoice;
    private int buttonPosition;



    /**
     * Constructs a new ShelfListener.
     * Initializes the permission, doneChoice, and buttonPosition variables.
     * The permission variable indicates whether the listener has permission to perform actions.
     * The doneChoice variable indicates whether a choice has been made.
     * The buttonPosition variable stores the position of a button.
     */
    public ShelfListener() {
        permission = false;
        doneChoice = false;
        buttonPosition = 0;
    }




    /**
     * Sets the permission flag of the ShelfListener.
     *
     * @param permission the new value for the permission flag
     */
    public void setPermission(boolean permission) {
        this.permission = permission;
    }




    /**
     * Retrieves the button position selected by the ShelfListener.
     *
     * @return the button position selected
     */
    public int getButtonPosition() {
        permission = false;
        doneChoice = false;
        return buttonPosition;
    }



    /**
     * Checks if the choice selection is done by the ShelfListener.
     *
     * @return true if the choice selection is done, false otherwise
     */
    public boolean isDoneChoice() {
        return doneChoice;
    }




    /**
     * Performs an action in response to an event.
     *
     * @param e the ActionEvent representing the event that occurred
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (permission) {
            Object source = e.getSource();
            int position = getButtonColumn(source);
            if (position != -1) {
                doneChoice = true;
                buttonPosition = position;
            }
        }

    }




    /**
     * Retrieves the column position of a button in a grid layout based on its source object.
     *
     * @param source the source object representing the button
     * @return the column position of the button, or -1 if the button is not found or an error occurs
     */
    private static int getButtonColumn(Object source) {
        if (source instanceof Component) {
            Component button = (Component) source;
            Container parent = button.getParent();
            for (int i = 0; i < parent.getComponentCount(); i++) {
                if (parent.getComponent(i) == button) {
                    return i % 5;
                }
            }
        }
        return -1;  //Default return value
    }
}

