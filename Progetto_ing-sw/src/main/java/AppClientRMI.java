import Network.RMI.Client_RMI;
import Network.messages.MessageType;
import view.Cli;
import view.TextualUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AppClientRMI {
    public static void main(String[] args) throws NotBoundException, RemoteException {
        Cli cli = new Cli();
        String playerName;
        TextualUI view = new TextualUI();

        //Asking the nickname
        playerName=cli.askNickname();
        //Creating the client
        Client_RMI client = new Client_RMI(playerName);
        if(cli.askNewGame()){
            //New game to create
            int numberOfPlayers=cli.askNumberOfPlayers();
            client.createLobby(numberOfPlayers);
            client.addPlayer(playerName);
            //Waiting for other players

            while(!client.notifyme().getMessageType().equals(MessageType.GAME_ENDING)){
                view.showMatchInfo(client.getDashboard(), numberOfPlayers, client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
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
        }
    }
}
