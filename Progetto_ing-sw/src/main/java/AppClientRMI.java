import Network.RMI.Client_RMI;
import Network.messages.MessageType;
import view.Cli;
import view.TextualUI;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AppClientRMI {
    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {
        Cli cli = new Cli();
        String playerName;
        TextualUI view = new TextualUI();

        view.init();
        //Asking the nickname
        playerName=cli.askNickname();
        //Creating the client
        Client_RMI client = new Client_RMI(playerName);
        if(cli.askNewGame()) {
            //New game to create
            int numberOfPlayers = cli.askNumberOfPlayers();
            client.createLobby(numberOfPlayers, playerName);
            System.out.println("LobbyReference: " + client.getLobbyReference());
            client.addPlayer(playerName);
        }else {
            client.joinLobby();
            System.out.println("LobbyReference: " + client.getLobbyReference());
            client.addPlayer(playerName);
        }
            //Waiting for other players
            System.out.println("Waiting for the other players to join");
            while(client.notifyMe().getMessageType().equals(MessageType.LOBBYCREATED)){

            }
            System.out.println("Starting the game. HAVE FUN");
            while(!client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)){
                view.showMatchInfo(client.getDashboard(),  client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
                //Game flow
                if(client.isItMyTurn()){
                    int numberOfTilesToPick=cli.askNumberOfTiles();
                    List<Integer> tilesToPick;
                    List<Integer> xCoord = new ArrayList<>();
                    List<Integer> yCoord = new ArrayList<>();
                    int column;
                    do{
                        do{
                            tilesToPick=cli.askTilesToPick(numberOfTilesToPick);
                            for(int i=0; i<tilesToPick.size(); i++){
                                if(i%2==0){
                                    xCoord.add(tilesToPick.get(i));
                                }else{
                                    yCoord.add(tilesToPick.get(i));
                                }
                            }
                        }while(client.pickableTiles(xCoord, yCoord));

                        //Choosing the column to insert the tiles
                        column = cli.askColumn();
                    }while(!client.columnAvailable(client.getSelectedTiles(numberOfTilesToPick, xCoord, yCoord), column));

                    //Inserting the tiles
                    client.insertTiles(client.getSelectedTiles(numberOfTilesToPick, xCoord, yCoord), column);
                    //Displaying the points
                    cli.displayPoints(client.myPoints());
                }
            }
            //Check if i won
            client.checkWinner();
        }else{
            //TODO: no new game created and method that return the number of players in game for the client
            //No new game created
            //Research for a game
            //client.joinLobby();
            while(!client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)){
                view.showMatchInfo(client.getDashboard(), 2, client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
                //Game flow
                if(client.isItMyTurn()){
                    int numberOfTilesToPick=cli.askNumberOfTiles();
                    List<Integer> tilesToPick;
                    List<Integer> xCoord = new ArrayList<>();
                    List<Integer> yCoord = new ArrayList<>();
                    int column;
                    do{
                        do{
                            tilesToPick=cli.askTilesToPick(numberOfTilesToPick);
                            for(int i=0; i<tilesToPick.size(); i++){
                                if(i%2==0){
                                    xCoord.add(tilesToPick.get(i));
                                }else{
                                    yCoord.add(tilesToPick.get(i));
                                }
                            }
                        }while(client.pickableTiles(xCoord, yCoord));

                        //Choosing the column to insert the tiles
                        column = cli.askColumn();
                    }while(!client.columnAvailable(client.getSelectedTiles(numberOfTilesToPick, xCoord, yCoord), column));

                    //Inserting the tiles
                    client.insertTiles(client.getSelectedTiles(numberOfTilesToPick, xCoord, yCoord), column);
                    //Displaying the points
                    cli.displayPoints(client.myPoints());
                }
            }

        }
    }
}
