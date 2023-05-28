package view.SWING.Frames;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class InGameFrame extends JFrame {

    private JPanel gameStatusPanel;
    private JPanel dashBoardPanel;
    private JPanel shelfPanel;
    private JPanel personalGoalPanel;
    private JPanel commonGoalPanel1;
    private JPanel commonGoalPanel2;
    private JPanel currentPointsPanel;
    private JPanel personalGoalPointsPanel;
    private JPanel chatPanel;
    private JPanel chatTextPanel;


    //TODO ripensare il layout in base a come vogliamo l'interazione utente
    // se teniamo questo finire di calibrare shelf panel / PG panel

    public InGameFrame() {

        super("My Shelfie");

        // set frame layout
        GridBagLayout frameLayout = new GridBagLayout();
        GridBagConstraints frameLimits = new GridBagConstraints();
        setLayout(frameLayout);

        frameLimits.insets.top = 3;
        frameLimits.insets.bottom = 3;
        frameLimits.insets.left = 3;
        frameLimits.insets.right = 3;

        // create panels

        // GAME_STATUS PANEL
        gameStatusPanel = new BorderedPanel();
        frameLimits.gridx = 0;
        frameLimits.gridy = 0;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 6;
        frameLimits.gridheight = 1;
        JLabel gsLabel = new JLabel("GAME STATUS");
        gameStatusPanel.add(gsLabel);
        frameLayout.setConstraints(gameStatusPanel, frameLimits);
        add(gameStatusPanel);

        // DASHBOARD PANEL
        dashBoardPanel = new BorderedPanel();
        frameLimits.gridx = 0;
        frameLimits.gridy = 1;
        frameLimits.weightx = 2;
        frameLimits.weighty = 10;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 6;
        frameLimits.gridheight = 6;
        frameLayout.setConstraints(dashBoardPanel, frameLimits);
        JLabel dashboardLabel = new JLabel("DASHBOARD");
        dashBoardPanel.add(dashboardLabel);
        add(dashBoardPanel);

        // SHELF PANEL
        shelfPanel = new BorderedPanel();
        frameLimits.gridx = 6;
        frameLimits.gridy = 0;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 4;
        frameLimits.gridheight = 5;
        frameLayout.setConstraints(shelfPanel, frameLimits);
        JLabel shelfLabel = new JLabel("SHELF");
        shelfPanel.add(shelfLabel);
        add(shelfPanel);


        // PERSONAL_GOAL PANEL
        personalGoalPanel = new BorderedPanel();
        frameLimits.gridx = 6;
        frameLimits.gridy = 5;
        frameLimits.weightx = 1;
        frameLimits.weighty = 0;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 4;
        frameLimits.gridheight = 5;
        frameLayout.setConstraints(personalGoalPanel, frameLimits);
        JLabel pgLabel = new JLabel("PG");
        personalGoalPanel.add(pgLabel);
        add(personalGoalPanel);

        // COMMON_GOAL1 PANEL
        commonGoalPanel1 = new BorderedPanel();
        frameLimits.gridx = 0;
        frameLimits.gridy = 7;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 3;
        frameLimits.gridheight = 5;
        frameLayout.setConstraints(commonGoalPanel1, frameLimits);
        JLabel cg1Label = new JLabel("CG1");
        commonGoalPanel1.add(cg1Label);
        add(commonGoalPanel1);

        // COMMON_GOAL2 PANEL
        commonGoalPanel2 = new BorderedPanel();
        frameLimits.gridx = 3;
        frameLimits.gridy = 7;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 3;
        frameLimits.gridheight = 5;
        frameLayout.setConstraints(commonGoalPanel2, frameLimits);
        JLabel cg2Label = new JLabel("CG2");
        commonGoalPanel2.add(cg2Label);
        add(commonGoalPanel2);

        // PERSONAL_GOAL_POINTS PANEL
        personalGoalPointsPanel = new BorderedPanel();
        frameLimits.gridx = 6;
        frameLimits.gridy = 10;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 4;
        frameLimits.gridheight = 1;
        frameLayout.setConstraints(personalGoalPointsPanel, frameLimits);
        JLabel pgPLabel = new JLabel("PGP");
        personalGoalPointsPanel.add(pgPLabel);
        add(personalGoalPointsPanel);

        // CURRENT_POINTS PANEL
        currentPointsPanel = new BorderedPanel();
        frameLimits.gridx = 6;
        frameLimits.gridy = 11;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 4;
        frameLimits.gridheight = 1;
        frameLayout.setConstraints(currentPointsPanel, frameLimits);
        JLabel cPLabel = new JLabel("CP");
        currentPointsPanel.add(cPLabel);
        add(currentPointsPanel);

        // CHAT PANEL
        chatPanel = new BorderedPanel();
        frameLimits.gridx = 10;
        frameLimits.gridy = 0;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 2;
        frameLimits.gridheight = 9;
        frameLayout.setConstraints(chatPanel, frameLimits);
        JLabel chatLabel = new JLabel("CHAT");
        chatPanel.add(chatLabel);
        add(chatPanel);

        //CHAT TEXT AREA PANEL
        chatTextPanel = new BorderedPanel();
        frameLimits.gridx = 10;
        frameLimits.gridy = 9;
        frameLimits.weightx = 1;
        frameLimits.weighty = 1;
        frameLimits.fill = GridBagConstraints.BOTH;
        //frameLimits.anchor
        frameLimits.gridwidth = 2;
        frameLimits.gridheight = 3;
        frameLayout.setConstraints(chatTextPanel, frameLimits);
        JLabel chatTALabel = new JLabel("MESSAGE");
        chatTextPanel.add(chatTALabel);
        add(chatTextPanel);


        // frame specs
        setSize(970, 540);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    private class BorderedPanel extends JPanel {

        private Border border;

        public BorderedPanel() {
            this.border = BorderFactory.createLineBorder(Color.BLACK, 3, false);
            setBorder(border);
        }

    }

}
