import Network.GameChat.ChatAndMiscellaneusThread;
import Network.RMI.Client_RMI;
import Network.messages.MessageType;
import controller.ClientControllerV2;
import view.*;
import view.SWING.GraphicalUI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for starting the MyShelfie client application.
 */
public class AppMyShelfie {

    /**
     * The main method that starts the MyShelfie client application.
     *
     * @param args command-line arguments
     * @throws NotBoundException     if the remote object is not bound in the registry
     * @throws RemoteException      if a remote communication error occurs
     * @throws MalformedURLException if the URL of the remote object is malformed
     */
    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {
        Cli cliinit = new Cli();
        int typechosed= cliinit.askGUI();

        View view;
        UI cli;
        if(typechosed==1){
            //GUI PARAMETERS
            GraphicalUI GUI = new GraphicalUI();
            view = GUI;
            cli= GUI;
        }else{
            //TUI PARAMETERS
            view = new TextualUI();
            cli= new Cli();
        }
        String ipaddress= cli.askIP();
        if(typechosed==2) {
            view.init();
        }

        System.out.println("Client started");
        int conn=cli.askConnection();
        //THE USER CHOSE SOCKET CONNECTION
        if(conn==2) {
            int defaultPort = 16001;
            ClientControllerV2 clientControllerV2 = new ClientControllerV2(view,cli, ipaddress, defaultPort, typechosed);
            try{
                clientControllerV2.gameFlow();
            }catch(IOException e){
                System.exit(-1);
            }

        }else{
            //THE USER CHOSE RMI CONNECTION
            String playerName;
            //Asking the nickname
            playerName=cli.askNickname();
            //Creating the client
            Client_RMI client = new Client_RMI(playerName,ipaddress);

            //ASYNCRONOUS REQUEST THREAD CREATION
            ChatAndMiscellaneusThread chatAndMiscellaneusThread= new ChatAndMiscellaneusThread(client,view,typechosed,cli);

            if(cli.askNewGame()) {
                //New game to create
                int numberOfPlayers = cli.askNumberOfPlayers();
                //Lobby creation
                client.createLobby(numberOfPlayers, playerName);
                System.out.println("Your lobby reference is " + client.getLobbyReference());
            }else {
                System.out.println("Joining a lobby already created...");
                //Join a lobby
                client.joinLobby();
                System.out.println("Your lobby reference is " + client.getLobbyReference());
                client.addPlayer(playerName);
            }

            Thread controlDisconnection = new Thread(client.controlDisconnection(), "Control disconnection");
            controlDisconnection.start();
            //check if the name is already taken
            while(client.nameAlreadyTaken(playerName)){
                System.out.println("name already taken, select another: ");
                playerName=cli.askNickname();
                client.changeName(playerName);
            }


            //Waiting for other players
            int flagDisplay=0;
            System.out.println(client.notifyMe().getMessageType());
            while(client.notifyMe().getMessageType().equals(MessageType.LOBBYCREATED)){

                if(flagDisplay==0){
                    System.out.println(ColorUI.YELLOW_TEXT+"Waiting for the other players to join"+ColorUI.RESET);
                    flagDisplay++;
                }
            }
            flagDisplay=0;

            while(!client.notifyMe().getMessageType().equals(MessageType.GAME_STARTING)){

            }


            System.out.println(ColorUI.YELLOW_TEXT+"Starting the game. HAVE FUN"+ColorUI.RESET);
            if(typechosed==1){
                view.initGame();
                view.showMatchInfo(client.getDashboard(), client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());

            }
            while(!client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)) {

                //Game flow
                //:_________________________:/
                if (client.isItMyTurn()) {
                    System.out.println(ColorUI.BLUE_TEXT + playerName + " is your turn!" + ColorUI.RESET);
                    chatAndMiscellaneusThread.setBufferEnd();

                    view.showMatchInfo(client.getDashboard(), client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
                    cli.displayPoints(client.myPoints(), client.myPGpoints());
                    flagDisplay = 0;
                    int numberOfTilesToPick;
                    List<Integer> tilesToPick;
                    List<Integer> xCoord = new ArrayList<>();
                    List<Integer> yCoord = new ArrayList<>();
                    int column;
                    do {

                        do {
                            //Asking the number of tiles to pick
                            numberOfTilesToPick = cli.askNumberOfTiles();
                            xCoord.clear();
                            yCoord.clear();
                            tilesToPick = cli.askTilesToPick(numberOfTilesToPick);
                            for (int i = 0; i < tilesToPick.size(); i++) {
                                if (i % 2 == 0) {
                                    xCoord.add(tilesToPick.get(i));
                                } else {
                                    yCoord.add(tilesToPick.get(i));
                                }
                            }
                        } while (!client.pickableTiles(xCoord, yCoord));

                        //Choosing the column to insert the tiles
                        column = cli.askColumn();
                    } while (!client.columnAvailable(numberOfTilesToPick, column));
                    //Inserting the tiles
                    client.insertTiles(xCoord, yCoord, column);
                    //removing the tiles from the dashboard
                    client.FinalPick(numberOfTilesToPick, xCoord, yCoord);
                    //Printing the shelf updated
                    view.printShelf(client.getMyShelfie());
                    //Displaying the points
                    cli.displayPoints(client.myPoints(), client.myPGpoints());
                } else {
                    if (flagDisplay == 0) {
                        System.out.println(ColorUI.YELLOW_TEXT + "Waiting for your turn" + ColorUI.RESET);
                        chatAndMiscellaneusThread= new ChatAndMiscellaneusThread(client, view, typechosed,cli);
                        chatAndMiscellaneusThread.start();
                        flagDisplay++;
                    }

                }

            }
            //:_________________________:/

            //Check if I won
            if(client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)){
                view.endGame(client.checkWinner());
                chatAndMiscellaneusThread.setBufferEnd();
                Thread waitEnd = new Thread(()->{
                    try{
                        //Wait for a minute then disconnect the client
                        Thread.sleep(60000);
                        System.out.println("Timer ended, disconnection...");
                        System.exit(0);
                    }catch(InterruptedException e){
                        //For debugging purpose
                        //e.printStackTrace();
                    }
                });
                waitEnd.start();
                if(typechosed==2){
                    if(cli.pressAnyKey()) {
                        System.exit(0);
                    }
                }
            }else{
                if(typechosed==2){
                    System.out.println("GAME ENDING FROM DISCONNECTION");
                    System.exit(-1);
                }else{
                    view.endGame("GAME ENDING FROM DISCONNECTION");
                    System.exit(-1);
                }

            }

        }



    }
}

