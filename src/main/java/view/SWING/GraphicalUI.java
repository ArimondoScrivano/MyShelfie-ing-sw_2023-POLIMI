package view.SWING;

import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;
import view.SWING.Frames.MainFrame;
import view.SWING.Panels.ButtonListener;
import view.SWING.Panels.ImagePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.List;
import java.util.Observable;
import view.SWING.Frames.MainFrame;
import view.SWING.Panels.TextListener;

public class GraphicalUI extends Observable {

    // ATTRIBUTI
    MainFrame mf;

    //CONSTRUCTOR

    public GraphicalUI(){
        mf=new MainFrame();
    }

    // MAIN
    /*
    public static void main(String[] args) {
        createAndShowGUI();
    }
*/

    // METODI GUI
    private static void createAndShowGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // show del primo frame
                showWelcomeFrame();
            }
        });
    }

    //TODO definire paradigma multithreading

    // defijnsco tutti i frame, aggiungendo i componenti e definendo
    // gli actionPerformed in modo da impostarne la successione

    //TODO abbellire il tutto cambiando font e dimensioni
    public static void showWelcomeFrame() {

        JFrame welcomeFrame = new JFrame("My Shelfie");

        GridBagLayout frameLayout = new GridBagLayout();
        GridBagConstraints frameLimits = new GridBagConstraints();
        welcomeFrame.setLayout(frameLayout);

        frameLimits.insets.top = 10;
        frameLimits.insets.bottom = 10;
        frameLimits.insets.left = 10;
        frameLimits.insets.right = 10;

        // setting background ImagePanel
        ImagePanel backgroundPanel = new ImagePanel();
        welcomeFrame.setContentPane(backgroundPanel);
        // ImagePanel layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        welcomeFrame.setLayout(layout);
        // Image panel inner margins
        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;

        //TODO immagine del titolo sgranata perchè è stata ridimensionata -> migliorabile?
        //TITLE     //TODO accedere a immagine tramite repo resources
        ImageIcon titleImage = new ImageIcon("C:\\Users\\pietr\\Desktop\\MyShelfie\\Piccole\\Title.png");
        JLabel title = new JLabel();
        title.setIcon(titleImage);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(title, limits);
        welcomeFrame.add(title);



        // C0
        JLabel label1 = new JLabel("Welcome to My Shelfie!");
        limits.gridx = 0;
        limits.gridy = 1;
        layout.setConstraints(label1, limits);
        welcomeFrame.add(label1);

        // C1
        JLabel label2 = new JLabel("Please, insert your nickname:");
        limits.gridx = 0;
        limits.gridy = 2;
        layout.setConstraints(label2, limits);
        welcomeFrame.add(label2);

        // C2
        JTextField textField = new JTextField(20);
        limits.gridx = 0;
        limits.gridy = 3;
        layout.setConstraints(textField, limits);
        welcomeFrame.add(textField);


        // C3
        JButton button = new JButton("OK");
        limits.gridx = 0;
        limits.gridy = 4;
        layout.setConstraints(button, limits);
        welcomeFrame.add(button);

        // components listeners
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // text in textField saved player's nickname
                // show next frame
                showConnectionTypeFrame();
                // dispose current frame
                welcomeFrame.dispose();
            }
        });


        // set frame dimensions
        welcomeFrame.setSize(800, 500);
        // set frame position [screen pixels are counted from high-left corner]
        welcomeFrame.setLocationRelativeTo(null);
        // set if the window's size can be modified
        welcomeFrame.setResizable(true);
        // defines what happens at window closing
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set frame visible
        welcomeFrame.setVisible(true);
    }

    public static void showConnectionTypeFrame() {

        JFrame connectionTypeFrame = new JFrame("My Shelfie");

        GridBagLayout frameLayout = new GridBagLayout();
        GridBagConstraints frameLimits = new GridBagConstraints();
        connectionTypeFrame.setLayout(frameLayout);

        frameLimits.insets.top = 10;
        frameLimits.insets.bottom = 10;
        frameLimits.insets.left = 10;
        frameLimits.insets.right = 10;

        // setting background ImagePanel
        ImagePanel backgroundPanel = new ImagePanel();
        connectionTypeFrame.setContentPane(backgroundPanel);
        // ImagePanel layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        connectionTypeFrame.setLayout(layout);
        // Image panel inner margins
        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;

        // C0
        JLabel label = new JLabel("CHOOSE CONNECTION'S TYPE");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(label, limits);
        connectionTypeFrame.add(label);

        // C1
        JButton button1 = new JButton("RMI");
        limits.gridx = 0;
        limits.gridy = 1;
        limits.gridwidth = 1;
        limits.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(button1, limits);
        connectionTypeFrame.add(button1);

        // C2
        JButton button2 = new JButton("SOCKET");
        limits.gridx = 0;
        limits.gridy = 2;
        limits.gridwidth = 1;
        limits.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(button2, limits);
        connectionTypeFrame.add(button2);

        // components listeners
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) connecting to RMI server
                // IF connection OK
                // 2) show next frame
                showGameInitFrame();
                // 3) dispose current frame
                connectionTypeFrame.dispose();
                // ELSE
                // advert player -> JDialog?
                // reload frame ?
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) connecting to SOCKET server
                // IF connection OK
                // 2) show next frame
                showGameInitFrame();
                // 3) dispose current frame
                connectionTypeFrame.dispose();
                // ELSE
                // advert player -> JDialog?
                // reload frame ?
            }
        });

        // frame specs
        connectionTypeFrame.setSize(800, 500);
        connectionTypeFrame.setLocationRelativeTo(null);
        connectionTypeFrame.setResizable(true);
        connectionTypeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connectionTypeFrame.setVisible(true);
    }

    public static void showGameInitFrame() {

        JFrame gameInitFrame = new JFrame("My Shelfie");

        GridBagLayout frameLayout = new GridBagLayout();
        GridBagConstraints frameLimits = new GridBagConstraints();
        gameInitFrame.setLayout(frameLayout);

        frameLimits.insets.top = 10;
        frameLimits.insets.bottom = 10;
        frameLimits.insets.left = 10;
        frameLimits.insets.right = 10;

        // setting background ImagePanel
        ImagePanel backgroundPanel = new ImagePanel();
        gameInitFrame.setContentPane(backgroundPanel);
        // ImagePanel layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        gameInitFrame.setLayout(layout);
        // Image panel inner margins
        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;


        // C0
        JButton button1 = new JButton("NEW GAME");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(button1, limits);
        gameInitFrame.add(button1);

        // C1
        JButton button2 = new JButton("JOIN GAME");
        limits.gridx = 0;
        limits.gridy = 2;
        layout.setConstraints(button2, limits);
        gameInitFrame.add(button2);

        // components listeners
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) create NEW GAME
                // 2) show next frame
                showNewGameFrame();
                // 3) dispose current frame
                gameInitFrame.dispose();
            }
        });

        // frame specs
        gameInitFrame.setSize(800, 500);
        gameInitFrame.setLocationRelativeTo(null);
        gameInitFrame.setResizable(true);
        gameInitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameInitFrame.setVisible(true);
    }

    public static void showNewGameFrame() {

        JFrame newGameFrame = new JFrame();

        GridBagLayout frameLayout = new GridBagLayout();
        GridBagConstraints frameLimits = new GridBagConstraints();
        newGameFrame.setLayout(frameLayout);

        frameLimits.insets.top = 10;
        frameLimits.insets.bottom = 10;
        frameLimits.insets.left = 10;
        frameLimits.insets.right = 10;

        // setting background ImagePanel
        ImagePanel backgroundPanel = new ImagePanel();
        newGameFrame.setContentPane(backgroundPanel);
        // ImagePanel layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints limits = new GridBagConstraints();
        newGameFrame.setLayout(layout);
        // Image panel inner margins
        limits.insets.top = 10;
        limits.insets.bottom = 10;
        limits.insets.left = 10;
        limits.insets.right = 10;

        // C0
        JLabel label = new JLabel("Choose the number of players:");
        limits.gridx = 0;
        limits.gridy = 0;
        layout.setConstraints(label, limits);
        newGameFrame.add(label);

        // C1
        JButton button1 = new JButton("2 PLAYERS");
        limits.gridx = 0;
        limits.gridy = 1;
        layout.setConstraints(button1, limits);
        newGameFrame.add(button1);

        // C2
        JButton button2 = new JButton("3 PLAYERS");
        limits.gridx = 0;
        limits.gridy = 2;
        layout.setConstraints(button2, limits);
        newGameFrame.add(button2);

        // C3
        JButton button3 = new JButton("4  PLAYERS");
        limits.gridx = 0;
        limits.gridy = 3;
        layout.setConstraints(button3, limits);
        newGameFrame.add(button3);

        // add components listeners
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) create 2 PLAYERS new game
                // 2) show next frame
                // 3) dispose current frame
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) create 3 PLAYERS new game
                // 2) show next frame
                // 3) dispose current frame
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) create 4 PLAYERS new game
                // 2) show next frame
                // 3) dispose current frame
            }
        });

        // frame specs
        newGameFrame.setSize(800, 500);
        newGameFrame.setLocationRelativeTo(null);
        newGameFrame.setResizable(true);
        newGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGameFrame.setVisible(true);
    }

    //TODO mettere a posto frame di gioco (al momento costruito in cartella frame)
    public static void showInGameFrame() {

        /* This frame manage  the whole game flow till the endgame
         * Structure: Frame
         * Layout: GridBadLayout
         * Panels:
         *      1) Dashboard
         *      2) Player's Shelf
         *      3) Player's Personal Goal
         *      4) CommonGoal1
         *      5) CommonGoal2
         *      6) Player's Current Points
         *      7) Player's PersonalGoal Points
         *
         * Dobbiamo decidere se serviranno altri componenti per l'interazione utente
         * ad es. pick , selezione colonna, etc.
         *  */
        JFrame inGameFrame = new JFrame("My Shelfie");

        GridBagLayout frameLayout = new GridBagLayout();
        GridBagConstraints frameLimits = new GridBagConstraints();
        inGameFrame.setLayout(frameLayout);



        inGameFrame.setSize(800, 500);
        inGameFrame.setLocationRelativeTo(null);
        inGameFrame.setResizable(true);
        inGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inGameFrame.setVisible(true);
    }

    //
    public void  showmMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg) {
        // display della dashboard

        // display common goals

        // display della shelf

        // display personal goal

    }

    public void printPersonalGoal(PersonalGoal pg){
        // DISPLAY PERSONAL GOAL

        Image myPersonalGoal;
    }

    public void shownewMex() {
        // display chat
    }

    public void printDashboard(Tile[][] copy) {
        JPanel mainPanel= new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel Dashboardpanel = new JPanel(new GridLayout(10, 10));
        JLabel currentDashboard= new JLabel("CURRENT DASHBOARD");
        mainPanel.add(currentDashboard, BorderLayout.NORTH);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton("(" + i + ", " + j + ")");
                Dashboardpanel.add(button);
            }
        }
        Dimension preferredSize = new Dimension(300, 300);
        Dashboardpanel.setPreferredSize(preferredSize);
        mainPanel.add(Dashboardpanel, BorderLayout.CENTER);
        mf.add(mainPanel, BorderLayout.NORTH);
        mf.setVisible(true);


    }


    public void printShelf(Tile[][] myShelf) {
        // display shelf
    }

    public void printCommonGoal(List<CommonGoals> commonGoals) {
        // display common goal
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
