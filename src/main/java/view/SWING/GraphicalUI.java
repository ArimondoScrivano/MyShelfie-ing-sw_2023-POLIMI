package view.SWING;

import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;
import view.SWING.Frames.MainFrame;
import view.SWING.Panels.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.List;
import java.util.Observable;
import view.SWING.Frames.MainFrame;

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
        // display dashboard
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
        JLabel questionLabel= new JLabel("Choose your connection method: 1 for RMI, 2 for SOCKET");
        JButton b1= new JButton("1");
        int choice[]={0};
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=1;
            }
        });
        JButton b2= new JButton("2");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=2;
            }
        });
        connectionPanel.add(questionLabel);
        connectionPanel.add(b1);
        connectionPanel.add(b2);
        mf.add(connectionPanel);


        return choice[0];
    }

    public String askNickname() {
        //usare una text Area
        JPanel nickNamePanel= new JPanel();
        JLabel questionLabel= new JLabel("Insert your nickname: ");
        JTextArea textArea = new JTextArea();
        String playerName= new String();
        boolean nameCollected= false;
        while(!nameCollected){
            playerName= textArea.getText();
            if(playerName!=null) {
                nameCollected= true;
            }
        }
        nickNamePanel.add(questionLabel);
        nickNamePanel.add(textArea);
        mf.add(nickNamePanel);
        return playerName;
    }

    public boolean askNewGame() {
        JPanel newGamePanel= new JPanel();
        JLabel questionLable= new JLabel("Do you want to START/ JOIN a game? ");
        final boolean[] choice = {false};
        JButton b1= new JButton("START");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0] =true;
            }
        });
        JButton b2=new JButton("JOIN");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=false;
            }
        });
        newGamePanel.add(questionLable);
        newGamePanel.add(b1);
        newGamePanel.add(b2);
        return choice[0];
    }

    public int askNumberOfPlayers() {
        JPanel npPanel= new JPanel();
        JLabel questionLabel= new JLabel("Please choose the number of players: ");
        int choice[]= {0};
        JButton b1= new JButton("2");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=2;
            }
        });
        JButton b2= new JButton("3");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=3;
            }
        });
        JButton b3= new JButton("4");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=4;
            }
        });
        npPanel.add(questionLabel);
        npPanel.add(b1);
        npPanel.add(b2);
        npPanel.add(b3);
        return choice[0];
    }

    public List<String> askNewChatMessage(List<String> playersName, String myplayername) {
        return null;
    }

    public int askNumberOfTiles() {
        JPanel ntPanel= new JPanel();
        JLabel questionLabel= new JLabel("How many tiles do you want to pick?");
        int choice[]={0};
        JButton b1= new JButton("1");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=1;
            }
        });
        JButton b2= new JButton("2");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=2;
            }
        });
        JButton b3= new JButton("3");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=3;
            }
        });
        ntPanel.add(questionLabel);
        ntPanel.add(b1);
        ntPanel.add(b2);
        ntPanel.add(b3);
        return choice[0];
    }

    public List<Integer> askTilesToPick(int numberOfTile) {
        return null;
    }

    public int askColumn() {
        JPanel columnPanel= new JPanel();
        JLabel questionLabel= new JLabel("In which column do you want to insert the tiles?");
        JTextField textField= new JTextField();
        int choice[]= {0};
        JButton b1= new JButton("1");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=1;
            }
        });
        JButton b2= new JButton("2");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=2;
            }
        });
        JButton b3= new JButton("3");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=3;
            }
        });
        JButton b4= new JButton("4");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=4;
            }
        });
        JButton b5= new JButton("5");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice[0]=5;
            }
        });
        columnPanel.add(questionLabel);
        columnPanel.add(b1);
        columnPanel.add(b2);
        columnPanel.add(b3);
        columnPanel.add(b4);
        columnPanel.add(b5);
        return choice[0];

    }

}
