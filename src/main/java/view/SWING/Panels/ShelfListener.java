package view.SWING.Panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShelfListener implements ActionListener {

    private boolean permission;
    private boolean doneChoice;
    private int buttonPosition;

    public ShelfListener() {
        permission = false;
        doneChoice = false;
        buttonPosition = 0;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public int getButtonPosition() {
        permission = false;
        doneChoice = false;
        return buttonPosition;
    }

    public boolean isDoneChoice() {
        return doneChoice;
    }

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


    private static int getButtonColumn(Object source) {
        if (source instanceof Component) {
            Component button = (Component) source;
            Container parent = button.getParent();
            for (int i = 0; i < parent.getComponentCount(); i++) {
                if (parent.getComponent(i) == button) {
                    int col = i % 5;  // 5 rappresenta il numero di colonne nella griglia
                    return col;
                }
            }
        }
        return -1;  // Valore di ritorno di default in caso di errore o se il bottone non è stato trovato
    }
}

