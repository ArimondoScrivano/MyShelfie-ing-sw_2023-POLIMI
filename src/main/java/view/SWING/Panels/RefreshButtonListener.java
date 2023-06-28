package view.SWING.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The RefreshButtonListener class implements the ActionListener interface and represents a listener for the refresh button.
 */
public class RefreshButtonListener implements ActionListener {

    private boolean clicked;

    public RefreshButtonListener(){
        this.clicked= false;
    }

    public boolean getClicked(){
        if(clicked){
            clicked= false;
            return true;
        }else{
            return false;
        }
    }


    /**
     * Handles the button click event.
     *
     * @param e the ActionEvent representing the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       clicked= true;
    }
}
