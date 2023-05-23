package controller;

import Network.SOCKET.SocketClientV2;
import Network.messages.Message;
import Network.messages.SocketMessages;
import view.Cli;
import view.ColorUI;
import view.TextualUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientControllerV2 {
    private TextualUI view;
    private SocketClientV2 client;
    private String name;
    //Index of the Lobby
    private int idLobby;
    public int getIdLobby() {
        return idLobby;
    }

    public void setIdLobby(int idLobby) {
        this.idLobby = idLobby;
    }
    public String getName() {
        return name;
    }


    public ClientControllerV2(TextualUI view, String address, int port){
        this.view=view;
        try{
            this.client=new SocketClientV2(address, port, this);
            client.pingMessage(true);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void onUpdateName(String name){
        client.sendMessage(new Message(name, SocketMessages.NAME_UPDATE));
    }

    public void onNewGameChoice(int np){
        client.sendMessage(new Message(name, SocketMessages.NEW_GAME, np));
    }

    public void onJoiningLobby(){
        client.sendMessage(new Message(name, SocketMessages.JOIN_LOBBY));
    }

    public void onMessageReceived(Message message){
        switch (message.getMsg()){
            case NAME_FAILED -> {
                System.out.println("Name failed! Retry");
                Cli cli = new Cli();
                this.name=cli.askNickname();
                onUpdateName(name);
            }
            case GAME_STARTING -> {
                System.out.println("Game starting");
                client.sendMessage(new Message(name, SocketMessages.IS_IT_MY_TURN, idLobby));
            }

            case WAITING_FOR_OTHER_PLAYERS -> {
                System.out.println("Waiting for other players");
            }
            case CHECK_YOUR_TURN -> {
                client.sendMessage(new Message(name, SocketMessages.IS_IT_MY_TURN, idLobby));
            }
            case MY_TURN->{
                System.out.println(ColorUI.BLUE_TEXT+this.name+" is your turn!"+ColorUI.RESET);
                view.showMatchInfo(message.getDashboard(), message.getCommonGoals(), message.getShelf(), message.getPg());
                //Points display at the end of the turn
                Cli cli = new Cli();
                cli.displayPoints(message.getPoints(), message.getPgPoints());
                //siamo arrivati qui
                        //devo displayare i miei punti, li potremmo allegare direttamente al messaggio di prima
                List<List<Integer>> myPossiblePick= new ArrayList<>();
                myPossiblePick= chooseOnlyTile();
                int possibleCol= chooseOnlyColumn();
                client.sendMessage(new Message(name, SocketMessages.ARE_PARAMETERS_OK, idLobby, myPossiblePick, possibleCol));

            }
            case PARAMETERS_KO -> {
                List<List<Integer>> myPossiblePick = new ArrayList<>();
                myPossiblePick = chooseOnlyTile();
                int possibleCol = chooseOnlyColumn();
                client.sendMessage(new Message(name, SocketMessages.ARE_PARAMETERS_OK, idLobby, myPossiblePick, possibleCol));
            }
            case PARAMETERS_OK -> {
                view.printDashboard(message.getDashboard());
                view.printShelf(message.getShelf());

                //Points display at the end of the turn
                Cli cli = new Cli();
                cli.displayPoints(message.getPoints(), message.getPgPoints());
                client.sendMessage(new Message(name, SocketMessages.MY_TURN_ENDED, idLobby));
            }
            case GAME_ENDING -> {
                //Check if i won
                client.sendMessage(new Message(name, SocketMessages.HAVE_I_WON, idLobby));

            }
            case WINNER -> {
                System.out.println("Congratulation, you WON the match");
            }
            case LOSER -> {
                System.out.println("Sorry, You Lost");
            }
        }
    }

    public int chooseOnlyColumn(){
        Cli Cli= new Cli();
        return Cli.askColumn();
    }

    //the return List contains xCoord, yCoord.
    public List<List<Integer>> chooseOnlyTile(){
        Cli Cli= new Cli();
        int numberOfTilesToPick;
        List<Integer> tilesToPick;
        List<Integer> xCoord = new ArrayList<>();
        List<Integer> yCoord = new ArrayList<>();
        //Asking the number of tiles to pick
        numberOfTilesToPick=Cli.askNumberOfTiles();
        tilesToPick=Cli.askTilesToPick(numberOfTilesToPick);
        for(int i=0; i<tilesToPick.size(); i++){
            if(i%2==0){
                xCoord.add(tilesToPick.get(i));
            }else{
                yCoord.add(tilesToPick.get(i));
            }
        }
        List<List<Integer>> listToReturn = new ArrayList<>();
        listToReturn.add(xCoord);
        listToReturn.add(yCoord);
        return listToReturn;
    }



    public void gameFlow() throws IOException {
        Thread clientInputThread = new Thread(()->{
            Cli cli = new Cli();
            if(!Thread.currentThread().isInterrupted()){
                this.name=cli.askNickname();
                //onUpdateName(this.name);
                if(cli.askNewGame()){
                    //New game to create
                    int numberOfPlayers = cli.askNumberOfPlayers();
                    onNewGameChoice(numberOfPlayers);
                }else{
                    System.out.println("Joining a lobby already created...");
                    //Join a lobby
                    onJoiningLobby();
                }
                //Thread parallelo che legge messaggi di continuo dal server
                //Gestione flusso di gioco da gestire in altro metodo
                client.readMessage();
            }
        });
        clientInputThread.start();
    }
}