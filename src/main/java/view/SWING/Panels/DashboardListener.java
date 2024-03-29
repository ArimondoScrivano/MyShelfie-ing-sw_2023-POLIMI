package view.SWING.Panels;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The DashboardListener class implements the ActionListener interface to handle dashboard-related events.
 * It keeps track of permissions, the number of tiles required, and whether the choice is done.
 * It also stores a list of button positions.
 */
public class DashboardListener implements ActionListener {


    private boolean permission;
    private int numTilesRequired;
    private boolean doneChoice;
    private final List<Integer> buttonPosition;

    /**
     * Constructs a new DashboardListener object.
     * Initializes the listener with default values.
     */
    public DashboardListener() {
        permission = false;
        numTilesRequired = 0;
        doneChoice = false;
        buttonPosition = new ArrayList<>();
    }

    /**
     * Sets the permission flag indicating whether the listener has permission.
     *
     * @param permission the permission flag to set
     */
    public void setPermission(boolean permission) {
        this.permission = permission;
    }


    /**
     * Sets the number of tiles required by the listener.
     *
     * @param numTilesRequired the number of tiles required
     */
    public void setNumTilesRequired(int numTilesRequired) {
        this.numTilesRequired = numTilesRequired;
    }


    /**
     * Retrieves the button positions selected by the listener.
     * Clears the internal button positions and resets the listener state.
     *
     * @return the list of button positions selected by the listener
     */
    public List<Integer> getButtonPosition() {
        List<Integer> listToReturn = new ArrayList<>(buttonPosition);
        buttonPosition.clear();
        this.doneChoice = false;
        this.permission=false;
        return listToReturn;
    }


    /**
     * Checks if the choice selection is done.
     *
     * @return true if the choice selection is done, false otherwise
     */
    public boolean isDoneChoice() {
        return doneChoice;
    }



    /**
     * Performs an action in response to the user's interaction with the component.
     *
     * @param e the ActionEvent representing the user's action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (permission && numTilesRequired > 0) {
            Object source = e.getSource();
            List<Integer> position = getButtonPosition(source);
            if (position != null) {
                buttonPosition.addAll(position);
                numTilesRequired--;
                if (numTilesRequired == 0) {
                    doneChoice = true;
                }
            }
        }
    }





    /**
     * Retrieves the position of a button in a grid layout based on the specified source component.
     *
     * @param source the source component representing the button
     * @return a List containing the column and row position of the button, or null if the button is not found
     */
    private static List<Integer> getButtonPosition(Object source) {
        if (source instanceof Component) {
            Component button = (Component) source;
            Container parent = button.getParent();
            for (int i = 0; i < parent.getComponentCount(); i++) {
                if (parent.getComponent(i) == button) {
                    int row = i / 9 + 1;
                    int col = i % 9 + 1;
                    List<Integer> list = new ArrayList<>();
                    list.add(col);
                    list.add(row);
                    return list;
                }
            }
        }
        return null;
    }
}
