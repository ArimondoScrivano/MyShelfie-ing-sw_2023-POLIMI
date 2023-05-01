import Network.RMI.Client_RMI;
import Network.messages.MessageType;
import view.Cli;
import view.ColorUI;
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
            //Lobby creation
            client.createLobby(numberOfPlayers, playerName);
            System.out.println("Your lobby reference is " + client.getLobbyReference());
            client.addPlayer(playerName);
        }else {
            //Join a lobby
            client.joinLobby();
            System.out.println("Your lobby reference is " + client.getLobbyReference());
            client.addPlayer(playerName);
        }

        //Waiting for other players
        int flagDisplay=0;
        while(client.notifyMe().getMessageType().equals(MessageType.LOBBYCREATED)){
            if(flagDisplay==0){
                System.out.println(ColorUI.YELLOW_TEXT+"Waiting for the other players to join");
                flagDisplay++;
            }
        }
        flagDisplay=0;

        System.out.println(ColorUI.YELLOW_TEXT+"Starting the game. HAVE FUN");
        while(!client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)){
            view.showMatchInfo(client.getDashboard(), client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
            cli.displayPoints(client.myPoints());
            //Game flow
            if(client.isItMyTurn()){
                flagDisplay=0;
                int numberOfTilesToPick;
                List<Integer> tilesToPick;
                List<Integer> xCoord = new ArrayList<>();
                List<Integer> yCoord = new ArrayList<>();
                int column;
                do{
                    //Asking the number of tiles to pick
                    numberOfTilesToPick=cli.askNumberOfTiles();
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
            }else{
                if(flagDisplay==0){
                    System.out.println(ColorUI.YELLOW_TEXT +"Waiting for your turn");
                    flagDisplay++;
                }
            }
        }
        //Check if i won
        client.checkWinner();
    }
}
