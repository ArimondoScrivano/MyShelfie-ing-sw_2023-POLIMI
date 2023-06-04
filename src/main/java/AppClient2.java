

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


public class AppClient2 {
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
        if(typechosed==2) {
            view.init();
        }

        System.out.println("Client started");
        int conn=cli.askConnection();
        //THE USER CHOSE SOCKET CONNECTION
        if(conn==2) {
            int defaultPort = 16001;
            ClientControllerV2 clientControllerV2 = new ClientControllerV2(view,cli, "localhost", defaultPort);
            try{
                clientControllerV2.gameFlow();
            }catch(IOException e){
                e.printStackTrace();
            }

        }else{
            //THE USER CHOSE RMI CONNECTION
            String playerName;
            //Asking the nickname
            playerName=cli.askNickname();
            //Creating the client
            Client_RMI client = new Client_RMI(playerName);
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

                    view.showMatchInfo(client.getDashboard(), client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
                    cli.displayPoints(client.myPoints(), client.myPGpoints());
                    flagDisplay = 0;
                    int numberOfTilesToPick;
                    List<Integer> tilesToPick;
                    List<Integer> xCoord = new ArrayList<>();
                    List<Integer> yCoord = new ArrayList<>();
                    int column;
                    do {
                        //Asking the number of tiles to pick
                        numberOfTilesToPick = cli.askNumberOfTiles();
                        do {
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
                        flagDisplay++;
                    }
                }
            }
            //:_________________________:/
            //Check if i won
            if(!client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)){
                view.endGame(client.checkWinner());
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

