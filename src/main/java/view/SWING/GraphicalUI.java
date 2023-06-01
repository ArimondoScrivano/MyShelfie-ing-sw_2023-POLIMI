package view.SWING;


import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;
import view.SWING.Frames.MainFrame;
import view.SWING.Panels.ButtonListener;
import view.SWING.Panels.ImagePanel;
import view.SWING.Panels.TextListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;

public class GraphicalUI extends Observable {

    // ATTRIBUTI
    private MainFrame mf;


    //CONSTRUCTOR

    public GraphicalUI(){
        mf=new MainFrame();
    }

    public void initGame(){
      /*  JInternalFrame gridInternalFrame = new JInternalFrame("Grid Internal Frame", true, true, true, true);
        gridInternalFrame.setSize(500, 500);
        gridInternalFrame.setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        gridInternalFrame.setContentPane(layeredPane);

// Carica l'immagine di sfondo
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/boards/livingroom.png"); // Inserisci il percorso corretto dell'immagine
        ImagePanel imagePanel = new ImagePanel(backgroundImage);
        imagePanel.setBounds(0, 0, gridInternalFrame.getWidth(), gridInternalFrame.getHeight());
        layeredPane.add(imagePanel, Integer.valueOf(0));

// Pannello per i bottoni
        JPanel buttonPanel = new JPanel(new GridLayout(10, 10));
        buttonPanel.setOpaque(false);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true); // Imposta il bordo dei bottoni visibile
                buttonPanel.add(button);
            }
        }
        buttonPanel.setBounds(0, 0, gridInternalFrame.getWidth(), gridInternalFrame.getHeight());
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        gridInternalFrame.setResizable(false);
        gridInternalFrame.setVisible(true);

        mf.getContentPane().add(gridInternalFrame);
        mf.setVisible(true);*/
        JInternalFrame gridInternalFrame = new JInternalFrame("Grid Internal Frame", true, true, true, true);
        gridInternalFrame.setSize(500, 500);
        gridInternalFrame.setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        gridInternalFrame.setContentPane(layeredPane);

// Carica l'immagine di sfondo
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/boards/livingroom.png"); // Inserisci il percorso corretto dell'immagine
        ImagePanel imagePanel = new ImagePanel(backgroundImage);
        imagePanel.setBounds(0, 0, gridInternalFrame.getWidth(), gridInternalFrame.getHeight());
        layeredPane.add(imagePanel, Integer.valueOf(0));

// Pannello per i bottoni
        int buttonPanelSize = 490; // Imposta la dimensione desiderata per il pannello dei bottoni

        JPanel buttonPanel = new JPanel(new GridLayout(9, 9));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(21, 15, 10, 10)); // Imposta il margine del pannello dei bottoni
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true); // Imposta il bordo dei bottoni visibile
                buttonPanel.add(button);
            }
        }
        buttonPanel.setBounds(0, 0, buttonPanelSize, buttonPanelSize);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        gridInternalFrame.setResizable(false);
        gridInternalFrame.setVisible(true);
        mf.getContentPane().add(gridInternalFrame);
        mf.setVisible(true);
    }

    public void  showmMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg) {
        // display della dashboard

        // display common goals

        // display della shelf

        // display personal goal

    }



    public void printPersonalGoal(PersonalGoal pg){
        // DISPLAY PERSONAL GOAL

       // Image myPersonalGoal;
    }

    public void shownewMex() {
        // display chat
    }

    public void printDashboard(Tile[][] copy) {
   JPanel mainPanel= new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel DashboardGridPanel = new JPanel(new GridLayout(10, 10));

        // Aggiungi i bottoni alla griglia
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton("(" + i + ", " + j + ")");
                button.setEnabled(false);
                DashboardGridPanel.add(button);
            }
        }

        DashboardGridPanel.setBounds(10, 40, 450, 450);
    /*    this.dashboard.remove(this.dashboardcurrent);
        this.dashboard.add(DashboardGridPanel);
        this.dashboardcurrent= DashboardGridPanel;*/
        //mf.add(mainPanel,BorderLayout.CENTER);
        //mf.setVisible(true);

    }


    public void printShelf(Tile[][] myShelf) {
        JPanel mainPanel= new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel Shelfpanel = new JPanel(new GridLayout(6, 5));
        JLabel currentShelf= new JLabel("CURRENT SHELF");


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                JButton button = new JButton("(" + i + ", " + j + ")");
                Shelfpanel.add(button);
            }
        }
        Dimension preferredSize = new Dimension(300, 200);
        Shelfpanel.setPreferredSize(preferredSize);
        mainPanel.setPreferredSize(preferredSize);
        mainPanel.add(Shelfpanel);
        mf.add(mainPanel,BorderLayout.EAST);
        mf.setVisible(true);

    }

    public void printCommonGoal(List<CommonGoals> commonGoals) {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.setPreferredSize(new Dimension(200, 200));

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.BLUE);
        panel2.setPreferredSize(new Dimension(200, 200));

        mainPanel.add(panel1, BorderLayout.NORTH);
        mainPanel.add(panel2, BorderLayout.CENTER);
        mf.add(mainPanel, BorderLayout.WEST);

        mf.setVisible(true);
    }

    public void displayPoints(int myPoint, int myPGpoints) {

    }

    //METODI DELLA CLI
    public int askConnection() {

        //Usare una textArea
        JPanel connectionPanel= new JPanel();
        JLabel questionLabel= new JLabel("Choose your connection method:");
        JButton b1= new JButton("RMI");
        ButtonListener RMIButton= new ButtonListener(1);
        ButtonListener SOCKETButton= new ButtonListener(2);
        b1.addActionListener(RMIButton);
        JButton b2= new JButton("SOCKET");
        b2.addActionListener(SOCKETButton);
        connectionPanel.add(questionLabel);
        connectionPanel.add(b1);
        connectionPanel.add(b2);
        mf.add(connectionPanel);
        mf.setVisible(true);
        boolean choiceCollected=false;
        while(!choiceCollected){
            if(SOCKETButton.getdefinedChoice() || RMIButton.getdefinedChoice()){
                choiceCollected=true;
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        if(SOCKETButton.getdefinedChoice()){
            mf.remove(connectionPanel);
            return SOCKETButton.getChoice();
        }else{
            mf.remove(connectionPanel);
            return RMIButton.getChoice();
        }
    }

    public String askNickname() {
        //usare una text Area
        JPanel nickNamePanel= new JPanel();
        JLabel questionLabel= new JLabel("Insert your nickname: ");
        JTextField textField = new JTextField();
        TextListener mytf= new TextListener(textField);
        textField.addActionListener(mytf);
        String playerName= new String();
        boolean nameCollected= false;
        nickNamePanel.add(questionLabel);
        Dimension preferredSize = new Dimension(150, 25);
        textField.setPreferredSize(preferredSize);
        nickNamePanel.add(textField);
        mf.add(nickNamePanel);
        mf.setVisible(true);

        while (!nameCollected) {
            if (mytf.getdefinedChoice()) {
                nameCollected = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        mf.remove(nickNamePanel);
        playerName= mytf.getChoice();
        return playerName;
    }

    public boolean askNewGame() {
        JPanel ngPanel = new JPanel();
        JLabel questionLabel = new JLabel("Select One:");
        JButton b1 = new JButton("NEW GAME");
        ButtonListener NGButton = new ButtonListener(1);
        ButtonListener JGButton = new ButtonListener(2);
        b1.addActionListener(NGButton);
        JButton b2 = new JButton("JOIN GAME");
        b2.addActionListener(JGButton);
        ngPanel.add(questionLabel);
        ngPanel.add(b1);
        ngPanel.add(b2);
        mf.add(ngPanel);
        mf.setVisible(true);
        boolean choiceCollected = false;
        while (!choiceCollected) {
            if (JGButton.getdefinedChoice() || NGButton.getdefinedChoice()) {
                choiceCollected = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (JGButton.getdefinedChoice()) {
            mf.remove(ngPanel);
            return false;
        } else {
            mf.remove(ngPanel);
            return true;
        }
    }

    public int askNumberOfPlayers() {
        JPanel npPanel = new JPanel();
        JLabel questionLabel = new JLabel("Select Number of Players:");
        JButton b1 = new JButton("2");
        JButton b2 = new JButton("3");
        JButton b3 = new JButton("4");
        ButtonListener TWPButton = new ButtonListener(2);
        ButtonListener THPButton = new ButtonListener(3);
        ButtonListener FOPButton = new ButtonListener(4);
        b1.addActionListener(TWPButton);
        b2.addActionListener(THPButton);
        b3.addActionListener(FOPButton);
        npPanel.add(questionLabel);
        npPanel.add(b1);
        npPanel.add(b2);
        npPanel.add(b3);
        mf.add(npPanel);
        mf.setVisible(true);
        boolean choiceCollected = false;
        while (!choiceCollected) {
            if (TWPButton.getdefinedChoice() || THPButton.getdefinedChoice()|| FOPButton.getdefinedChoice()) {
                choiceCollected = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (TWPButton.getdefinedChoice()) {
            mf.remove(npPanel);
            return TWPButton.getChoice();
        } else if(THPButton.getdefinedChoice()) {
            mf.remove(npPanel);
            return THPButton.getChoice();
        }else{
            mf.remove(npPanel);
            return FOPButton.getChoice();
        }

    }

    public List<String> askNewChatMessage(List<String> playersName, String myplayername) {
        return null;
    }

    public int askNumberOfTiles() {
        JPanel ntPanel = new JPanel();
        JLabel questionLabel = new JLabel("Select Number of Tiles:");
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        ButtonListener ONTButton = new ButtonListener(1);
        ButtonListener TWTButton = new ButtonListener(2);
        ButtonListener THTButton = new ButtonListener(3);
        b1.addActionListener(ONTButton);
        b2.addActionListener(TWTButton);
        b3.addActionListener(THTButton);
        ntPanel.add(questionLabel);
        ntPanel.add(b1);
        ntPanel.add(b2);
        ntPanel.add(b3);
        mf.add(ntPanel);
        mf.setVisible(true);
        boolean choiceCollected = false;
        while (!choiceCollected) {
            if (ONTButton.getdefinedChoice() || TWTButton.getdefinedChoice() || THTButton.getdefinedChoice()) {
                choiceCollected = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (ONTButton.getdefinedChoice()) {
            mf.remove(ntPanel);
            return ONTButton.getChoice();
        } else if (TWTButton.getdefinedChoice()) {
            mf.remove(ntPanel);
            return TWTButton.getChoice();
        } else {
            mf.remove(ntPanel);
            return THTButton.getChoice();
        }
    }

        public List<Integer> askTilesToPick(int numberOfTile) {
        return null;
    }

    public int askColumn() {
        JPanel columnPanel = new JPanel();
        JLabel questionLabel = new JLabel("Choose the column: ");
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("5");
        ButtonListener ONButton = new ButtonListener(1);
        ButtonListener TWButton = new ButtonListener(2);
        ButtonListener THButton = new ButtonListener(3);
        ButtonListener FOButton = new ButtonListener(4);
        ButtonListener FIButton = new ButtonListener(5);
        b1.addActionListener(ONButton);
        b2.addActionListener(TWButton);
        b3.addActionListener(THButton);
        b4.addActionListener(FOButton);
        b5.addActionListener(FIButton);
        columnPanel.add(questionLabel);
        columnPanel.add(b1);
        columnPanel.add(b2);
        columnPanel.add(b3);
        columnPanel.add(b4);
        columnPanel.add(b5);
        mf.add(columnPanel);
        mf.setVisible(true);
        boolean choiceCollected = false;
        while (!choiceCollected) {
            if (ONButton.getdefinedChoice() || TWButton.getdefinedChoice() || THButton.getdefinedChoice()|| FOButton.getdefinedChoice()|| FIButton.getdefinedChoice()) {
                choiceCollected = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        mf.remove(columnPanel);
        if (ONButton.getdefinedChoice()) {
            return ONButton.getChoice();
        } else if (TWButton.getdefinedChoice()) {
            return TWButton.getChoice();
        } else if(THButton.getdefinedChoice()) {
            return THButton.getChoice();
        }else if(FOButton.getdefinedChoice()) {
            return FOButton.getChoice();
        }else{
            return FIButton.getChoice();
        }
    }

}
