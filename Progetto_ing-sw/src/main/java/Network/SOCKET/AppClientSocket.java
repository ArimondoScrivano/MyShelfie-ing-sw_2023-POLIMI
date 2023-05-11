package Network.SOCKET;

import Network.messages.MessageType;
import view.Cli;
import view.ColorUI;
import view.TextualUI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AppClientSocket {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Cli cli = new Cli();
        String playerName;
        TextualUI view = new TextualUI();

        view.init();
        //Asking the nickname
        playerName=cli.askNickname();
        //Creating the client
        Socket soc=new Socket("localhost", 16001); //socket di comunicazione con il server
        PrintWriter out=new PrintWriter(soc.getOutputStream(), true);
        BufferedReader in= new BufferedReader(new InputStreamReader(soc.getInputStream())); //lettore dello stream da server
        OutputStream objectOutput=soc.getOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(objectOutput);
        InputStream objectInput= soc.getInputStream();
        ObjectInputStream ois=new ObjectInputStream(objectInput);
        ConcreteSocketClient client= new ConcreteSocketClient(playerName, in, out, ois, oos);
        System.out.println("Connection established");

        //Game flow
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
        //check if the name is already taken
        //TODO
        /*while(client.nameAlreadyTaken(playerName)){
            System.out.println("Il nome inserito è già occupato");
            playerName=cli.askNickname();
            client.changeName(playerName);
        }*/


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

        System.out.println(ColorUI.YELLOW_TEXT+"Starting the game. HAVE FUN"+ColorUI.RESET);

        while(!client.notifyMe().getMessageType().equals(MessageType.GAME_ENDING)){

            //Game flow
            if(client.isItMyTurn()){
                System.out.println(ColorUI.BLUE_TEXT+playerName+" is your turn!"+ColorUI.RESET);;
                view.showMatchInfo(client.getDashboard(), client.getCommonGoals(), client.getMyShelfie(), client.getMyPersonalGoal());
                cli.displayPoints(client.myPoints(), client.myPGpoints());
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
                        xCoord.clear();
                        yCoord.clear();
                        tilesToPick=cli.askTilesToPick(numberOfTilesToPick);
                        for(int i=0; i<tilesToPick.size(); i++){
                            if(i%2==0){
                                xCoord.add(tilesToPick.get(i));
                            }else{
                                yCoord.add(tilesToPick.get(i));
                            }
                        }
                    }while(!client.pickableTiles(xCoord, yCoord));

                    //Choosing the column to insert the tiles
                    column = cli.askColumn();
                }while(!client.columnAvailable(numberOfTilesToPick, column));
                //Inserting the tiles
                client.insertTiles(xCoord,yCoord,column);
                //removing the tiles from the dashboard
                client.FinalPick(numberOfTilesToPick,xCoord,yCoord);
                //Printing the shelf updated
                view.printShelf(client.getMyShelfie());
                //Displaying the points
                cli.displayPoints(client.myPoints(), client.myPGpoints());
            }else{
                if(flagDisplay==0){
                    System.out.println(ColorUI.YELLOW_TEXT +"Waiting for your turn"+ColorUI.RESET);
                    flagDisplay++;
                }
            }
        }
        //Check if i won
        System.out.println(client.checkWinner());
    }
    }

