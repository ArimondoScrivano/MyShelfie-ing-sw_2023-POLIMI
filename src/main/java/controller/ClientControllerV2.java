package controller;

import Network.GameChat.ChatAndMiscellaneusThreadSocket;
import Network.SOCKET.SocketClientV2;
import Network.messages.Message;
import Network.messages.SocketMessages;
import view.ColorUI;
import view.UI;
import view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientControllerV2 {
    private View view;
    private UI cli;
    private SocketClientV2 client;
    private String name;
    //Index of the Lobby
    private int idLobby;

    //index that indicates if the User picked TUI(2) or GUI(1)
    private int typechosed;
    private int FlagStartThread;

    private boolean doneOnceInit;

    private ChatAndMiscellaneusThreadSocket chatAndMiscellaneusThreadSocket;
    /**
     * Returns the ID of the lobby.
     *
     * @return the lobby ID as an integer.
     */
    public int getIdLobby() {
        return idLobby;
    }

    /**
     * Sets the ID of the lobby.
     *
     * @param idLobby the lobby ID to be set.
     */
    public void setIdLobby(int idLobby) {
        this.idLobby = idLobby;
    }

    /**
     * Returns the name.
     *
     * @return the name as a String.
     */
    public String getName() {
        return name;
    }


    /**
     * Constructs a new ClientControllerV2 object.
     *
     * @param view    the View object associated with the client.
     * @param cli     the UI object for the client.
     * @param address the server address to connect to.
     * @param port    the server port to connect to.
     */
    public ClientControllerV2(View view, UI cli, String address, int port,int typechosed){
        this.view=view;
        this.cli= cli;
        this.typechosed=typechosed;
        this.FlagStartThread=0;
        doneOnceInit=false;
        try{
            this.client=new SocketClientV2(address, port, this);
            client.pingMessage(true);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Notifies the Client with a message with the name associated in it.
     *
     * @param name the new name to be set.
     */
    public void onUpdateName(String name){
        client.sendMessage(new Message(name, SocketMessages.NAME_UPDATE));
    }

    /**
     * Sends a new game request message to the server.
     *
     * @param np the number of players for the new game.
     */
    public void onNewGameChoice(int np){
        client.sendMessage(new Message(name, SocketMessages.NEW_GAME, np));
    }

    /**
     * Sends a joining lobby message to the server.
     */
    public void onJoiningLobby(){
        client.sendMessage(new Message(name, SocketMessages.JOIN_LOBBY));
    }


    /**
     * Handles the received message from the server.<br>
     * POSSIBLE MESSAGE TYPES:<br>
     * - NAME_FAILED -> The Server notifies a non-correct input<br>
     * - GAME_STARTING -> The Server notifies the starting game<br>
     * - WAITING_FOR_OTHER_PLAYERS -> The Server notifies that it is not the Client's turn<br>
     * - CHECK_YOUR_TURN -> The Server notifies that the Client can ask for their turn<br>
     * - MY_TURN -> The Server notifies the player's turn<br>
     * - PARAMETERS_KO -> Parameters are not correct<br>
     * - PARAMETERS_OK -> Parameters are correct<br>
     * - GAME_ENDING -> The Server notifies the ending of the game<br>
     * - WINNER -> The Server notifies a losing client<br>
     * - LOSER -> The Server notifies the winning client<br>
     * - DISCONNECT -> The Server notifies a disconnection<br>
     *
     * @param message the message received from the server.
     */
    public void onMessageReceived(Message message){
        switch (message.getMsg()){
            case NAME_FAILED -> {
                System.out.println("Name failed! Retry");
                this.name=this.cli.askNickname();
                onUpdateName(name);
            }
            case GAME_STARTING -> {
                System.out.println("Game starting");
                if(typechosed==1 && !doneOnceInit){
                    doneOnceInit=true;
                    view.initGame();
                }
                client.sendMessage(new Message(name, SocketMessages.IS_IT_MY_TURN, idLobby));

                if(FlagStartThread==0) {
                    this.chatAndMiscellaneusThreadSocket = new ChatAndMiscellaneusThreadSocket(view, cli, client, name, idLobby, typechosed);
                    chatAndMiscellaneusThreadSocket.start();
                    FlagStartThread=1;
                }
            }

            case WAITING_FOR_OTHER_PLAYERS -> {
                System.out.println("Waiting for other players");
            }

            case WAITING_FOR_YOUR_TURN -> {
                System.out.println("I'm waiting my turn");
            }
            case CHECK_YOUR_TURN -> {

                client.sendMessage(new Message(name, SocketMessages.IS_IT_MY_TURN, idLobby));

            }
            case MY_TURN->{

                chatAndMiscellaneusThreadSocket.setBufferEnd();
                System.out.println(ColorUI.BLUE_TEXT+this.name+" is your turn!"+ColorUI.RESET);
                view.showMatchInfo(message.getDashboard(), message.getCommonGoals(), message.getShelf(), message.getPg());
                //Points display at the end of the turn
                this.cli.displayPoints(message.getPoints(), message.getPgPoints());
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
                this.cli.displayPoints(message.getPoints(), message.getPgPoints());
                client.sendMessage(new Message(name, SocketMessages.MY_TURN_ENDED, idLobby));
                this.chatAndMiscellaneusThreadSocket = new ChatAndMiscellaneusThreadSocket(view, cli, client, name, idLobby, typechosed);
                chatAndMiscellaneusThreadSocket.start();
            }
            case GAME_ENDING -> {
                client.sendMessage(new Message(name, SocketMessages.HAVE_I_WON, idLobby));

            }
            case WINNER -> {
                view.endGame("Congratulation, you WON the match");
                Thread waitEnd = new Thread(()->{
                    try{
                        //Wait for a minute then disconnect the client
                        Thread.sleep(60000);
                        System.out.println("Timer ended, disconnection...");
                        System.exit(0);
                    }catch(InterruptedException e){

                    }
                });
                waitEnd.start();
                if(typechosed==2){
                    if(cli.pressAnyKey()) {
                        System.exit(0);
                    }
                }
            }
            case LOSER -> {
                view.endGame("Sorry, You Lost");
                Thread waitEnd = new Thread(()->{
                    try{
                        //Wait for a minute then disconnect the client
                        Thread.sleep(60000);
                        System.out.println("Timer ended, disconnection...");
                        System.exit(0);
                    }catch(InterruptedException e){

                    }
                });
                waitEnd.start();
                if(typechosed==2){
                    if(cli.pressAnyKey()) {
                        System.exit(0);
                    }
                }

            }
            case DISCONNECT -> {
                view.endGame("GAME ENDING FROM DISCONNECTION");
                client.disconnect();
            }

            //ASYNCHRONOUS REQUEST MANAGEMENT
            case DASHBOARD_RES -> {
                view.printDashboard(message.getDashboard());
            }
            case SHELF_RES -> {
                view.printShelf(message.getShelf());
            }
            case COMMONGOAL_RES -> {
                view.printCommonGoal(message.getCommonGoals());
            }
            case PERSONAL_GOAL_RES -> {
               view.printPersonalGoal(message.getPg());
            }
            case REFRESH_RES -> {
                view.showMatchInfo(message.getDashboard(), message.getCommonGoals(), message.getShelf(), message.getPg());
                this.cli.displayPoints(message.getPoints(), message.getPgPoints());
            }

        }
    }
    /**
     * Prompts the user to choose a column.
     *
     * @return the chosen column with the correct implementation of the method askColumn() in the CLI
     */
    public int chooseOnlyColumn(){
        return this.cli.askColumn();
    }


    /**
     * Prompts the user to choose tiles and returns their coordinates.
     *
     * @return a list containing the x and y coordinates of the chosen tiles,the return List contains xCoord, yCoord.
     */
    public List<List<Integer>> chooseOnlyTile(){
        int numberOfTilesToPick;
        List<Integer> tilesToPick;
        List<Integer> xCoord = new ArrayList<>();
        List<Integer> yCoord = new ArrayList<>();
        //Asking the number of tiles to pick
        numberOfTilesToPick=this.cli.askNumberOfTiles();
        tilesToPick=this.cli.askTilesToPick(numberOfTilesToPick);
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


    /**
     * Executes the game flow, including handling client input and reading messages from the server.
     *
     * @throws IOException if an I/O error occurs during the game flow.
     */
    public void gameFlow() throws IOException {
        Thread clientInputThread = new Thread(()->{
            if(!Thread.currentThread().isInterrupted()){
                this.name=this.cli.askNickname();
                //onUpdateName(this.name);
                if(this.cli.askNewGame()){
                    //New game to create
                    int numberOfPlayers = cli.askNumberOfPlayers();
                    onNewGameChoice(numberOfPlayers);
                }else{
                    System.out.println("Joining a lobby already created...");
                    //Join a lobby
                    onJoiningLobby();
                }

                // Parallel thread continuously reads messages from the server
                // Game flow handling to be managed in another method
                client.readMessage();
            }
        });
        clientInputThread.start();
    }
}
