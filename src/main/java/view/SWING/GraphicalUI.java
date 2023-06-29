package view.SWING;


import Network.GameChat.GameMessage;
import model.COLOR;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;
import view.SWING.Frames.MainFrame;
import view.SWING.Panels.*;
import view.UI;
import view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * This class represents a Graphical User Interface (GUI) for a view implementation.
 * It implements the View and UI interfaces.
 */
public class GraphicalUI implements View, UI {
    // ATTRIBUTES
    private final MainFrame mf;

    // Pane that contains the dashboard
    private JLayeredPane dashboardPane;

    // Dashboard panel
    private JPanel dashboardcurrent;

    private DashboardListener dashboardListener;

    // Pane that contains the shelf
    private JLayeredPane shelfPane;

    // Shelf panel
    private JPanel shelfcurrent;

    private ShelfListener shelfListener;

    // First label for the common goals
    private JLabel CGL1;

    // Second label for Common goals
    private JLabel CGL2;
    private JLabel scoringToken1;
    private JLabel scoringToken2;

    // Panel that contains the Common Goals
    private JPanel CGpanel;

    // Panel for the personal goal
    private ImagePanel personalGoalpanel;

    // Pane that contains the personal goal
    private JInternalFrame personalGoalPane;
    private int flagDoneOnceCG;
    private int flagDoneOncePG;
    private int flaginitdone;

    private JLabel labelStatus;
    private JLabel labelPoint;

    private RefreshButtonListener refreshbuttonListen;

    /**
     * Constructs a new instance of the GraphicalUI class.
     * This constructor initializes the GraphicalUI by creating a new MainFrame object
     * and setting the flaginitdone variable to 0.
     */
    public GraphicalUI(){
        mf=new MainFrame();
        flaginitdone=0;
    }

    /**
     * Initializes the game by setting up the user interface and creating necessary components.
     * This method creates and configures internal frames, panels, buttons, and images required for the game.
     * It also sets the positions, sizes, and content of these components within the main frame.
     */
    public void initGame() {
        flaginitdone=1;
        flagDoneOnceCG=0;
        flagDoneOncePG=0;
        mf.setLayout(null);
        mf.revalidate();
        mf.repaint();
        this.dashboardListener= new DashboardListener();
        this.shelfListener= new ShelfListener();

        JInternalFrame gridInternalFrame = new JInternalFrame("DASHBOARD", false, false, true, false);
        gridInternalFrame.setSize(500, 500);
        gridInternalFrame.setLayout(new BorderLayout());

        JLayeredPane dashboardPane = new JLayeredPane();
        gridInternalFrame.setContentPane(dashboardPane);
        //LOAD THE BACKGROUND DASHBOARD IMAGE
        InputStream in = getClass().getResourceAsStream("/graphicalResources/boards/livingroom.png");
        Image backgroundImage;
        try{
            backgroundImage = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImagePanel imagePanel = new ImagePanel(backgroundImage);
        imagePanel.setBounds(0, 0, gridInternalFrame.getWidth(), gridInternalFrame.getHeight());
        dashboardPane.add(imagePanel, Integer.valueOf(0));

        //DASHBOARD BUTTON PANEL
        int buttonPanelSize = 490; //SET THE CORRECT SIZE

        JPanel dashboardButtonPanel = new JPanel(new GridLayout(9, 9));
        dashboardButtonPanel.setOpaque(false);
        dashboardButtonPanel.setBorder(BorderFactory.createEmptyBorder(21, 15, 10, 10));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setEnabled(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true);
                dashboardButtonPanel.add(button);
            }
        }

        dashboardButtonPanel.setBounds(0, 0, buttonPanelSize, buttonPanelSize);
        dashboardPane.add(dashboardButtonPanel, Integer.valueOf(1));
        this.dashboardPane= dashboardPane;
        this.dashboardcurrent=dashboardButtonPanel;
        gridInternalFrame.setResizable(false);
        gridInternalFrame.setVisible(true);

        int internalFrameX = mf.getWidth() - gridInternalFrame.getWidth() - 10;
        int internalFrameY = 10;
        gridInternalFrame.setLocation(internalFrameX, internalFrameY);

        mf.getContentPane().add(gridInternalFrame);


        // SHELF
        JInternalFrame secondInternalFrame = new JInternalFrame("MY SHELFIE", false, false, false, false);
        secondInternalFrame.setSize(400, 400);
        secondInternalFrame.setLayout(new BorderLayout());
        secondInternalFrame.setOpaque(false);

        JLayeredPane secondLayeredPane = new JLayeredPane();

        //SHELF BACKGROUND IMAGE
        InputStream in2 = getClass().getResourceAsStream("/graphicalResources/boards/bookshelf.png");
        Image backgroundImageShelf;
        try{
            backgroundImageShelf = ImageIO.read(in2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImagePanel imagePanelShelf = new ImagePanel(backgroundImageShelf);
        imagePanelShelf.setBounds(0, 0, secondInternalFrame.getWidth(), secondInternalFrame.getHeight());
        secondLayeredPane.add(imagePanelShelf, Integer.valueOf(0));

        //SHELF BUTTON PANEL
        JPanel buttonPanelShelf = new JPanel(new FlowLayout(FlowLayout.CENTER, 17, 8));
        buttonPanelShelf.setOpaque(false);
        buttonPanelShelf.setBorder(BorderFactory.createEmptyBorder(20, 22, 0, 20));

        for(int row=0; row<6; row++) {
            for (int i = 0; i < 5; i++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setEnabled(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true);
                button.setPreferredSize(new Dimension(46, 46));
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


        int secondInternalFrameX =mf.getWidth() - gridInternalFrame.getWidth() - 410;
        int secondInternalFrameY = internalFrameY +100;
        secondInternalFrame.setLocation(secondInternalFrameX, secondInternalFrameY);

        mf.getContentPane().add(secondInternalFrame);
        mf.setVisible(true);
        // COMMONGOALS
        JInternalFrame imageFrame = new JInternalFrame("COMMON GOAL", false, false, false, false);
        imageFrame.setSize(600, 280);
        imageFrame.setLayout(new BorderLayout());
        imageFrame.setResizable(false);

        JPanel imagePanelCG = new JPanel();
        imagePanelCG.setLayout(new BoxLayout(imagePanelCG, BoxLayout.X_AXIS));
        imagePanelCG.setOpaque(false);

        InputStream in3 = getClass().getResourceAsStream("/graphicalResources/common goal cards/back.jpg");
        InputStream in4 = getClass().getResourceAsStream("/graphicalResources/scoring tokens/scoring_back_example.jpg");
        InputStream in5 = getClass().getResourceAsStream("/graphicalResources/common goal cards/back.jpg");
        InputStream in6 = getClass().getResourceAsStream("/graphicalResources/scoring tokens/scoring_back_example.jpg");
        Image image1;
        Image image2;
        Image image3;
        Image image4;
        try{
            image1 = ImageIO.read(in3);
            image2 = ImageIO.read(in4);
            image3 = ImageIO.read(in5);
            image4 = ImageIO.read(in6);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int imageWidth = 150;
        int imageHeight = 255;
        Image scaledImage1 = image1.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        Image scaledImage2 = image2.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        Image scaledImage3 = image3.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        Image scaledImage4 = image4.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);


        JLabel label1 = new JLabel(new ImageIcon(scaledImage1));
        JLabel label2 = new JLabel(new ImageIcon(scaledImage2));
        JLabel label3 = new JLabel(new ImageIcon(scaledImage3));
        JLabel label4 = new JLabel(new ImageIcon(scaledImage4));

        imagePanelCG.add(label1);
        imagePanelCG.add(label2);
        imagePanelCG.add(label3);
        imagePanelCG.add(label4);

        this.CGL1 = label1;
        this.CGL2 = label3;
        this.scoringToken1= label2;
        this.scoringToken2= label4;

        this.CGpanel = imagePanelCG;

        imageFrame.getContentPane().add(imagePanelCG, BorderLayout.SOUTH);


        int imageFrameX = mf.getWidth() - imageFrame.getWidth() - 10;
        int imageFrameY = mf.getHeight() - imageFrame.getHeight() - 10;
        imageFrame.setLocation(imageFrameX, imageFrameY);

        mf.getContentPane().add(imageFrame);
        imageFrame.setVisible(true);


        //  PERSONAL GOAL
        JInternalFrame additionalFrame = new JInternalFrame("PERSONAL GOAL", false, false, false, false);
        additionalFrame.setSize(300, 255);
        additionalFrame.setLayout(null); //Using a null layout
        additionalFrame.setResizable(false);

        InputStream pg = getClass().getResourceAsStream("/graphicalResources/personal goal cards/old/back.jpg");
        Image additionalImage;
        try{
            additionalImage=ImageIO.read(pg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        additionalImage= additionalImage.getScaledInstance(300, 255, Image.SCALE_SMOOTH);
        ImagePanel additionalImagePanel = new ImagePanel(additionalImage);
        this.personalGoalpanel= additionalImagePanel;
        additionalImagePanel.setBounds(0, 0, additionalFrame.getWidth(), additionalFrame.getHeight());
        additionalFrame.setContentPane(additionalImagePanel);
        this.personalGoalPane= additionalFrame;
        int additionalFrameX = imageFrame.getX() - additionalFrame.getWidth();
        int additionalFrameY = imageFrame.getY();
        additionalFrame.setLocation(additionalFrameX, additionalFrameY);

        mf.getContentPane().add(additionalFrame);
        additionalFrame.setVisible(true);


        JInternalFrame textFrame = new JInternalFrame("GAME STATUS", false, false, false, false);
        textFrame.setSize(300, 100);
        textFrame.setLayout(null);
        textFrame.setResizable(false);


        JLabel labelStatus = new JLabel("LOADING");
        JLabel labelPoint = new JLabel("LOADING");
        this.labelStatus= labelStatus;
        this.labelPoint= labelPoint;
        int labelWidth = 200;
        int labelHeight = 20;
        int labelMargin = 10;
        labelStatus.setBounds(labelMargin, labelMargin, labelWidth, labelHeight);
        labelPoint.setBounds(labelMargin, labelMargin + labelHeight + labelMargin, labelWidth, labelHeight);


        textFrame.add(labelStatus);
        textFrame.add(labelPoint);


        int textFrameX = 10;
        int textFrameY = 10;
        textFrame.setLocation(textFrameX, textFrameY);


        mf.getContentPane().add(textFrame);
        textFrame.setVisible(true);

        JInternalFrame RefreshFrame = new JInternalFrame("REFRESH", false, false, false, false);
        RefreshFrame.setSize(100, 100);
        RefreshFrame.setLayout(null);
        RefreshFrame.setResizable(false);


        JButton additionalButton = new JButton();
        additionalButton.setBounds(10, 2, 70, 70);
        this.refreshbuttonListen= new RefreshButtonListener();
        additionalButton.addActionListener(refreshbuttonListen);
        // Add the button to the additional JInternalFrame
        RefreshFrame.add(additionalButton);

        // Position the additional JInternalFrame between "GAME STATUS" and the dashboard
        RefreshFrame.setLocation(310, 10);

        // Add the additional JInternalFrame to the content pane of the JFrame
        mf.getContentPane().add(RefreshFrame);
        RefreshFrame.setVisible(true);
    }



    /**
     * Ends the game and displays the game result in the user interface.
     * This method sets the game result in the status label and opens a new JFrame to display the result.
     *
     * @param esito The result of the game.
     */
    public void endGame(String esito) {
        this.labelStatus.setText("The result of the game is " + esito);
        JFrame frame = new JFrame("End Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("The result of the game is " + esito);
        panel.add(label);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mf.dispose();
                System.exit(0);
            }
        });
        panel.add(closeButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }




    /**
     * Displays the match information in the user interface.
     * This method prints the dashboard, common goals, personal goal, and shelf in the UI.
     * If the game initialization has not been done yet, it performs the initialization.
     *
     * @param copy         The copy of the game dashboard to be displayed.
     * @param commonGoals  The list of common goals to be displayed.
     * @param myShelf      The copy of the player's shelf to be displayed.
     * @param pg           The player's personal goal to be displayed.
     */
    public void showMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg) {
        if(flaginitdone==0){
            initGame();
            flaginitdone=1;
        }
     printDashboard(copy);
     printCommonGoal(commonGoals);
     printShelf(myShelf);
     printPersonalGoal(pg);

    }





    /**
     * Prints the personal goal card image to be displayed in the user interface.
     * This method loads the image and sets it as the content pane of the personal goal panel.
     * If the image has already been displayed once, it won't be reloaded.
     *
     * @param pg The personal goal card to be displayed.
     */
    public void printPersonalGoal(PersonalGoal pg) {
        if (flagDoneOncePG == 0) {
            InputStream in = getClass().getResourceAsStream("/graphicalResources/personal goal cards/old/" + pg.getId() + ".jpg");
            Image additionalImage;
            try{
                additionalImage=ImageIO.read(in);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
            additionalImage = additionalImage.getScaledInstance(300, 255, Image.SCALE_SMOOTH);
            ImagePanel additionalImagePanel = new ImagePanel(additionalImage);
            this.personalGoalPane.remove(this.personalGoalpanel);
            this.personalGoalpanel = additionalImagePanel;
            additionalImagePanel.setBounds(0, 0, this.personalGoalPane.getWidth(), this.personalGoalPane.getHeight());
            this.personalGoalPane.setContentPane(personalGoalpanel);
            this.personalGoalPane.setVisible(true);
            mf.setVisible(true);
            flagDoneOncePG = 1;
        }

    }




    /**
     * Displays a new message in the user interface.
     * This method is typically called when a new message is received.
     */
    @Override
    public void shownewMex() {

    }




    /**
     * Displays the game chat by showing the provided list of game messages.
     *
     * @param listToDisplay the list of game messages to be displayed in the game chat
     */
    @Override
    public void showGameChat(List<GameMessage> listToDisplay) {

    }




    /**
     * Prints the dashboard with the provided tile configuration.
     *
     * @param copy the 2D array representing the tile configuration on the dashboard
     *             where copy[row][column] represents the tile at the specified position
     *             with row and column starting from 1
     */

    public void printDashboard(Tile[][] copy) {
        int variety;
        this.labelStatus.setText("Wait for your turn");
        int buttonPanelSize = 490;

        JPanel DashboardButtonPanel = new JPanel(new GridLayout(9, 9));
        DashboardButtonPanel.setOpaque(false);
        DashboardButtonPanel.setBorder(BorderFactory.createEmptyBorder(21, 15, 10, 10));
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                JButton button = new JButton();
                button.setOpaque(true);
                if(copy[i][j].getColor().equals(COLOR.BLANK)){
                    button.setEnabled(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                }else{
                    variety= copy[i][j].getId()% 3;
                    InputStream in = getClass().getResourceAsStream("/graphicalResources/item tiles/" +copy[i][j].getColor() +variety +".png");

                    ImageIcon imageIcon;
                    try{
                        BufferedImage bufferedImage = ImageIO.read(in);
                        imageIcon=new ImageIcon(bufferedImage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(buttonPanelSize / 9, buttonPanelSize / 9, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    button.setIcon(scaledIcon);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(true);
                    button.addActionListener(dashboardListener);
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





    /**
     * Prints the shelf with the provided tile configuration.
     *
     * @param myShelf the 2D array representing the tile configuration on the shelf
     *                where myShelf[row][column] represents the tile at the specified position
     *                with row and column starting from 0
     */
    public void printShelf(Tile[][] myShelf) {
        int variety;
        JPanel buttonPanelShelf = new JPanel(new FlowLayout(FlowLayout.CENTER, 17, 8));
        buttonPanelShelf.setOpaque(false);
        buttonPanelShelf.setBorder(BorderFactory.createEmptyBorder(22, 22, 0, 20));

        for(int row=0; row<6; row++) {
            for (int i = 0; i < 5; i++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true);
                button.setPreferredSize(new Dimension(46, 46));


                if (!myShelf[row][i].getColor().equals(COLOR.BLANK)) {
                    variety= myShelf[row][i].getId() % 3;
                    InputStream in = getClass().getResourceAsStream("/graphicalResources/item tiles/" + myShelf[row][i].getColor() +variety +".png");
                    ImageIcon imageIcon;
                    try{
                        BufferedImage bufferedImage = ImageIO.read(in);
                        imageIcon=new ImageIcon(bufferedImage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                if(row==0 && myShelf[row][i].getColor().equals(COLOR.BLANK)){
                    button.addActionListener(shelfListener);
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



    /**
     * Initializes the game.
     * This method is called when the game starts to perform any necessary initialization tasks.
     * Implementations of this method can be used to set up game resources, initialize variables, or perform any other initialization logic.
     */
    @Override
    public void init() {

    }



    /**
     * Displays the common goals on the user interface.
     * This method takes a list of common goals and loads the corresponding images from the resources.
     * The images are resized to the desired dimensions and displayed on the user interface using JLabels.
     * The previous common goals and scoring tokens are removed from the panel, and the new images are added.
     *
     * @param commonGoals  The list of common goals to display.
     */
    public void printCommonGoal(List<CommonGoals> commonGoals) {
        InputStream in = getClass().getResourceAsStream("/graphicalResources/common goal cards/" + commonGoals.get(0).getId() + ".jpg");
        InputStream in2 = getClass().getResourceAsStream("/graphicalResources/scoring tokens/scoring_" +commonGoals.get(0).getCurrent_point() +".jpg");
        InputStream in3 = getClass().getResourceAsStream("/graphicalResources/common goal cards/" + commonGoals.get(1).getId() + ".jpg");
        InputStream in4 = getClass().getResourceAsStream("/graphicalResources/scoring tokens/scoring_" +commonGoals.get(1).getCurrent_point() +".jpg");
        Image image1;
            Image image3;
            Image image2;
            Image image4;
            try{
                image1 = ImageIO.read(in);
                image2 = ImageIO.read(in2);
                image3 = ImageIO.read(in3);
                image4 = ImageIO.read(in4);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        int imageWidth = 150;
            int imageHeight = 255;
            Image scaledImage1 = image1.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            Image scaledImage2 = image2.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            Image scaledImage3 = image3.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            Image scaledImage4 = image4.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);


            JLabel label1 = new JLabel(new ImageIcon(scaledImage1));
            JLabel label2 = new JLabel(new ImageIcon(scaledImage2));
            JLabel label3 = new JLabel(new ImageIcon(scaledImage3));
            JLabel label4 = new JLabel(new ImageIcon(scaledImage4));

            this.CGpanel.remove(this.CGL1);
            this.CGpanel.remove(this.CGL2);
            this.CGpanel.remove(this.scoringToken1);
            this.CGpanel.remove(this.scoringToken2);
            this.CGL1=label1;
            this.CGL2=label3;
            this.scoringToken1=label2;
            this.scoringToken2=label4;
            this.CGpanel.add(CGL1);
            this.CGpanel.add(scoringToken1);
            this.CGpanel.add(CGL2);
            this.CGpanel.add(scoringToken2);
            this.CGpanel.setVisible(true);
            mf.setVisible(true);
    }



    /**
     * Displays the current points for the player.
     * This method calculates the sum of the player's points and personal goal points,
     * and updates the label on the user interface to show the total score.
     *
     * @param myPoint     The player's points.
     * @param myPGpoints  The player's personal goal points.
     */
    public void displayPoints(int myPoint, int myPGpoints) {
        int partialSum= myPoint + myPGpoints;
        this.labelPoint.setText("Your points: " +partialSum);
    }

    //Cli methods
    /**
     * Prompts the user to choose a connection method (RMI or SOCKET) using a graphical user interface.
     * This method displays a panel with buttons representing the connection options.
     * The user can click on a button to select the desired connection method.
     * Once the user's choice is collected, it returns the chosen connection method as an integer.
     *
     * @return The chosen connection method:
     *         - 1 for RMI
     *         - 2 for SOCKET
     */
    public int askConnection() {

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

    /**
     * Prompts the user to enter their nickname using a graphical user interface.
     * This method displays a panel with a label asking the user to insert their nickname,
     * along with a text field where the user can enter their nickname.
     * The user can press Enter or click outside the text field to submit their nickname.
     * Once the user's nickname is collected, it returns the entered nickname as a string.
     *
     * @return The entered nickname as a string.
     */
    public String askNickname() {
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




    /**
     * Prompts the user to select between starting a new game or joining an existing game using a graphical user interface.
     * This method displays a panel with buttons representing the options "NEW GAME" and "JOIN GAME".
     * The user can click on a button to make their selection.
     * Once the user's choice is collected, it returns a boolean value indicating whether to start a new game or join an existing game.
     *
     * @return `true` if the user selects "NEW GAME", indicating a new game should be started.
     *         `false` if the user selects "JOIN GAME", indicating they want to join an existing game.
     */
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




    /**
     * Prompts the user to select the number of players using a graphical user interface.
     * This method displays a panel with buttons representing different numbers of players.
     * The user can click on a button to select the desired number of players.
     * Once the user's choice is collected, it returns the selected number of players.
     *
     * @return The selected number of players.
     */
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




    /**
     * Prompts the user to enter the number of tiles they want to draw using a graphical user interface.
     * This method displays an input dialog box and validates the user's input.
     * It ensures that the input is a valid number between 1 and 3, and returns the selected number.
     *
     * @return The number of tiles selected by the user.
     */
    public int askNumberOfTiles() {
        this.labelStatus.setText("Choose the number of tiles to pick:");
        JFrame frame = new JFrame("Main Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        String input = null;
        boolean validInput = false;

        while (!validInput) {
            input = JOptionPane.showInputDialog(frame, "Choose the number of tiles to pick:");

            if (input != null) {
                try {
                    int number = Integer.parseInt(input);
                    if (number >= 1 && number <= 3) {
                        validInput = true;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insert a number between 1 and 3", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Insert a valid number", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break;
            }
        }
        int number=0;
        try {
           number = Integer.parseInt(input);
        }catch(Exception e) {
               return askNumberOfTiles();
            }
            frame.dispose();
            return number;

    }


    /**
     * Prompts the user to select tiles by interacting with a graphical user interface.
     * This method waits for the user's selection by monitoring the status of a dashboard listener.
     * Once the user's choice is collected, it returns a list of selected tile positions.
     *
     * @param numberOfTile The number of tiles required to be picked.
     * @return A list of selected tile positions.
     */
        public List<Integer> askTilesToPick(int numberOfTile) {
            this.labelStatus.setText("Select the tiles");
            dashboardListener.setNumTilesRequired(numberOfTile);
            dashboardListener.setPermission(true);
            boolean choiceCollected = false;
            while (!choiceCollected) {
                if (dashboardListener.isDoneChoice()) {
                    choiceCollected = true;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.labelStatus.setText("Checking parameters");
            return dashboardListener.getButtonPosition();
    }




    /**
     * Prompts the user to select a column by interacting with a graphical user interface.
     * This method waits for the user's selection by monitoring the status of a shelf listener.
     * Once the user's choice is collected, it returns the selected column position.
     *
     * @return The selected column position.
     */
    public int askColumn() {
       this.labelStatus.setText("Choose a column");
       this.shelfListener.setPermission(true);
        boolean choiceCollected = false;
        while (!choiceCollected) {
            if (shelfListener.isDoneChoice()) {
                choiceCollected = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        this.labelStatus.setText("Waiting for your turn");
        return shelfListener.getButtonPosition();
    }

    /**
     * Gets the state of the refresh button click.
     *
     * @return True if the refresh button was clicked, false otherwise.
     */
    public boolean getClicked() {
        if (refreshbuttonListen == null) {
            return false;
        }
        return refreshbuttonListen.getClicked();
    }

    /**
     * Asks the user to input an IP address.
     *
     * @return The IP address provided by the user.
     */
    public String askIP() {
        JPanel nickNamePanel = new JPanel();
        JLabel questionLabel = new JLabel("Insert the IP address: ");
        JTextField textField = new JTextField();
        TextListener mytf = new TextListener(textField);
        textField.addActionListener(mytf);
        String IPaddr = new String();
        boolean nameCollected = false;
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
        IPaddr = mytf.getChoice();
        return IPaddr;
    }

    /**
     * Indicates if any key is pressed.
     *
     * @return Always returns true.
     */
    @Override
    public boolean pressAnyKey() {
        return true;
    }

}
