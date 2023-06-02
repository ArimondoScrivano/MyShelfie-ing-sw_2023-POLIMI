package view.SWING;


import model.COLOR;
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

    //pane in cui è contenuta la dashboard
    private JLayeredPane dashboardPane;

    //pane della dashboard
    private JPanel dashboardcurrent;

    //pane in cui è contenuta la shelf;
    private JLayeredPane shelfPane;

    //pane della shelf
    private JPanel shelfcurrent;

    public GraphicalUI(){
        mf=new MainFrame();
    }

    public void initGame() {
        mf.setLayout(null);
        mf.revalidate();
        mf.repaint();

        JInternalFrame gridInternalFrame = new JInternalFrame("DASHBOARD", true, true, true, true);
        gridInternalFrame.setSize(500, 500);
        gridInternalFrame.setLayout(new BorderLayout());

        JLayeredPane dashboardPane = new JLayeredPane();
        gridInternalFrame.setContentPane(dashboardPane);

// Carica l'immagine di sfondo
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/boards/livingroom.png"); // Inserisci il percorso corretto dell'immagine
        ImagePanel imagePanel = new ImagePanel(backgroundImage);
        imagePanel.setBounds(0, 0, gridInternalFrame.getWidth(), gridInternalFrame.getHeight());
        dashboardPane.add(imagePanel, Integer.valueOf(0));

// Pannello per i bottoni della DASHBOARD
        int buttonPanelSize = 490; // Imposta la dimensione desiderata per il pannello dei bottoni

        JPanel dashboardButtonPanel = new JPanel(new GridLayout(9, 9));
        dashboardButtonPanel.setOpaque(false);
        dashboardButtonPanel.setBorder(BorderFactory.createEmptyBorder(21, 15, 10, 10)); // Imposta il margine del pannello dei bottoni
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true); // Imposta il bordo dei bottoni visibile
                dashboardButtonPanel.add(button);
            }
        }

        dashboardButtonPanel.setBounds(0, 0, buttonPanelSize, buttonPanelSize);
        dashboardPane.add(dashboardButtonPanel, Integer.valueOf(1));
        this.dashboardPane= dashboardPane;
        this.dashboardcurrent=dashboardButtonPanel;
        gridInternalFrame.setResizable(false);
        gridInternalFrame.setVisible(true);

// Blocca il JInternalFrame in alto a destra del JFrame
        int internalFrameX = mf.getWidth() - gridInternalFrame.getWidth() - 10; // Imposta la posizione X desiderata
        int internalFrameY = 10; // Imposta la posizione Y desiderata
        gridInternalFrame.setLocation(internalFrameX, internalFrameY);

        mf.getContentPane().add(gridInternalFrame);


// SHELF
        JInternalFrame secondInternalFrame = new JInternalFrame("MY SHELFIE", true, true, true, true);
        secondInternalFrame.setSize(400, 400);
        secondInternalFrame.setLayout(new BorderLayout());
        secondInternalFrame.setOpaque(false);

        JLayeredPane secondLayeredPane = new JLayeredPane();

// Carica l'immagine di sfondo
        Image backgroundImageShelf = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/boards/bookshelf.png"); // Inserisci il percorso corretto dell'immagine
        ImagePanel imagePanelShelf = new ImagePanel(backgroundImageShelf);
        imagePanelShelf.setBounds(0, 0, secondInternalFrame.getWidth(), secondInternalFrame.getHeight());
        secondLayeredPane.add(imagePanelShelf, Integer.valueOf(0));

// Pannello per i bottoni della SHELF
        JPanel buttonPanelShelf = new JPanel(new FlowLayout(FlowLayout.CENTER, 17, 8));
        buttonPanelShelf.setOpaque(false);
        buttonPanelShelf.setBorder(BorderFactory.createEmptyBorder(20, 22, 0, 20));// Imposta il margine del pannello dei bottoni

        for(int row=0; row<6; row++) {
            for (int i = 0; i < 5; i++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true);
                button.setPreferredSize(new Dimension(46, 46)); // Imposta la dimensione personalizzata per i bottoni
                buttonPanelShelf.add(button);
                if(row>0){
                    button.setEnabled(false);
                }
            }
        }

        buttonPanelShelf.setBounds(0, 0, secondInternalFrame.getWidth(), secondInternalFrame.getHeight());
        secondLayeredPane.add(buttonPanelShelf, Integer.valueOf(1));
        this.shelfPane= secondLayeredPane;
        this.shelfcurrent=buttonPanelShelf;

        secondInternalFrame.setContentPane(secondLayeredPane);
        secondInternalFrame.setResizable(false);
        secondInternalFrame.setVisible(true);

// Posiziona il secondo internal frame a sinistra della dashboard
        int secondInternalFrameX =mf.getWidth() - gridInternalFrame.getWidth() - 410;
        int secondInternalFrameY = internalFrameY +100;
        secondInternalFrame.setLocation(secondInternalFrameX, secondInternalFrameY);

        mf.getContentPane().add(secondInternalFrame);
        mf.setVisible(true);

// COMMONGOALS
        JInternalFrame imageFrame = new JInternalFrame("COMMON GOAL", true, true, true, true);
        imageFrame.setSize(600, 280);
        imageFrame.setLayout(new BorderLayout());
        imageFrame.setResizable(false);

        JPanel imagePanelCG = new JPanel();
        imagePanelCG.setLayout(new BoxLayout(imagePanelCG, BoxLayout.X_AXIS)); // Utilizza un BoxLayout con allineamento orizzontale
        imagePanelCG.setOpaque(false);

// Carica le immagini
        Image image1 = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/common goal cards/5.jpg");
        Image image2 = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/common goal cards/5.jpg");

// Ridimensiona le immagini alle dimensioni desiderate
        int imageWidth = 300; // Larghezza desiderata dell'immagine
        int imageHeight = 255; // Altezza desiderata dell'immagine
        Image scaledImage1 = image1.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        Image scaledImage2 = image2.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

// Crea i componenti per le immagini
        JLabel label1 = new JLabel(new ImageIcon(scaledImage1));
        JLabel label2 = new JLabel(new ImageIcon(scaledImage2));

// Aggiungi spaziatura tra le immagini
        imagePanelCG.add(Box.createHorizontalGlue()); // Spazio a sinistra
        imagePanelCG.add(label1);
        imagePanelCG.add(Box.createRigidArea(new Dimension(10, 0))); // Spazio tra le immagini
        imagePanelCG.add(label2);
        imagePanelCG.add(Box.createHorizontalGlue()); // Spazio a destra

// Aggiungi il pannello delle immagini al content pane del JInternalFrame
        imageFrame.getContentPane().add(imagePanelCG, BorderLayout.SOUTH);

// Posiziona il JInternalFrame in basso a destra
        int imageFrameX = mf.getWidth() - imageFrame.getWidth() - 10;
        int imageFrameY = mf.getHeight() - imageFrame.getHeight() - 10;
        imageFrame.setLocation(imageFrameX, imageFrameY);

        mf.getContentPane().add(imageFrame);
        imageFrame.setVisible(true);

        // Nuovo JInternalFrame gestione PERSONAL GOAL
        JInternalFrame additionalFrame = new JInternalFrame("PERSONAL GOAL", true, true, true, true);
        additionalFrame.setSize(300, 255);
        additionalFrame.setLayout(null); // Utilizza un layout di tipo null
        additionalFrame.setResizable(false);

// Carica l'immagine di sfondo per il nuovo JInternalFrame
        Image additionalImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/graphicalResources/personal goal cards/old/7.jpg"); // Inserisci il percorso corretto dell'immagine
        additionalImage= additionalImage.getScaledInstance(300, 255, Image.SCALE_SMOOTH);
        ImagePanel additionalImagePanel = new ImagePanel(additionalImage);
        additionalImagePanel.setBounds(0, 0, additionalFrame.getWidth(), additionalFrame.getHeight());
        additionalFrame.setContentPane(additionalImagePanel); // Imposta direttamente il pannello dell'immagine come content pane

// Posizione del nuovo JInternalFrame
        int additionalFrameX = imageFrame.getX() - additionalFrame.getWidth();
        int additionalFrameY = imageFrame.getY();
        additionalFrame.setLocation(additionalFrameX, additionalFrameY);

        mf.getContentPane().add(additionalFrame);
        additionalFrame.setVisible(true);

        // Crea il JInternalFrame per le etichette di testo
        JInternalFrame textFrame = new JInternalFrame("GAME STATUS", true, true, true, true);
        textFrame.setSize(600, 100);
        textFrame.setLayout(null);
        textFrame.setResizable(false);

// Crea le JLabel per visualizzare del testo
        JLabel labelStatus = new JLabel("Status:");
        JLabel labelPoint = new JLabel("Points:");

// Imposta la posizione e le dimensioni delle JLabel
        int labelWidth = 200;
        int labelHeight = 20;
        int labelMargin = 10;
        labelStatus.setBounds(labelMargin, labelMargin, labelWidth, labelHeight);
        labelPoint.setBounds(labelMargin, labelMargin + labelHeight + labelMargin, labelWidth, labelHeight);

// Aggiungi le JLabel al JInternalFrame
        textFrame.add(labelStatus);
        textFrame.add(labelPoint);

// Posiziona il JInternalFrame in alto a sinistra
        int textFrameX = 10;
        int textFrameY = 10;
        textFrame.setLocation(textFrameX, textFrameY);

// Aggiungi il JInternalFrame al content pane del JFrame
        mf.getContentPane().add(textFrame);
        textFrame.setVisible(true);
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


    public void printDashboard(Tile[][] copy) {
        int buttonPanelSize = 490; // Imposta la dimensione desiderata per il pannello dei bottoni

        JPanel DashboardButtonPanel = new JPanel(new GridLayout(9, 9));
        DashboardButtonPanel.setOpaque(false);
        DashboardButtonPanel.setBorder(BorderFactory.createEmptyBorder(21, 15, 10, 10)); // Imposta il margine del pannello dei bottoni

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                JButton button = new JButton();
                button.setOpaque(true);
                if(copy[i][j].getColor().equals(COLOR.BLANK)){
                    button.setEnabled(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false); // Imposta il bordo dei bottoni non visibile
                }else{
                    ImageIcon imageIcon = new ImageIcon("src/main/resources/graphicalResources/item tiles/" + copy[i][j].getColor() +".png"); // Inserisci il percorso corretto dell'immagine
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(buttonPanelSize / 9, buttonPanelSize / 9, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    button.setIcon(scaledIcon);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(true); // Imposta il bordo dei bottoni visibile
                }
                DashboardButtonPanel.add(button);
            }
        }

        this.dashboardPane.remove(this.dashboardcurrent);
        this.dashboardcurrent = DashboardButtonPanel;
        this.dashboardcurrent.setBounds(0, 0, buttonPanelSize, buttonPanelSize);
        this.dashboardPane.add(this.dashboardcurrent, Integer.valueOf(1));
        this.dashboardPane.setVisible(true);
        mf.setVisible(true);



    }


    public void printShelf(Tile[][] myShelf) {
        // Pannello per i bottoni della SHELF
        JPanel buttonPanelShelf = new JPanel(new FlowLayout(FlowLayout.CENTER, 17, 8));
        buttonPanelShelf.setOpaque(false);
        buttonPanelShelf.setBorder(BorderFactory.createEmptyBorder(22, 22, 0, 20));// Imposta il margine del pannello dei bottoni

        for(int row=0; row<6; row++) {
            for (int i = 0; i < 5; i++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true);
                button.setPreferredSize(new Dimension(46, 46)); // Imposta la dimensione personalizzata per i bottoni


                if (!myShelf[row][i].getColor().equals(COLOR.BLANK)) {
                    ImageIcon imageIcon = new ImageIcon("src/main/resources/graphicalResources/item tiles/" + myShelf[row][i].getColor() + ".png"); // Inserisci il percorso corretto dell'immagine
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(400 / 5, 400 / 6, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    button.setIcon(scaledIcon);
                    button.setBorderPainted(false);
                }

                if (row > 0 && myShelf[row][i].getColor().equals(COLOR.BLANK) ) {
                    button.setEnabled(false);
                    button.setBorderPainted(false);
                }
                buttonPanelShelf.add(button);
            }
        }
                this.shelfPane.remove(this.shelfcurrent);
                this.shelfcurrent = buttonPanelShelf;
                this.shelfcurrent.setBounds(0, 0, 400, 400);
                this.shelfPane.add(this.shelfcurrent, Integer.valueOf(1));
                this.shelfPane.setVisible(true);
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
