package Network.RMI;

import Network.GameChat.GameMessage;
import Network.messages.Message;
import Network.messages.MessageType;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Client_RMI extends UnicastRemoteObject implements ClientCallback {
    // it indicates the Game where the player is
    private int LobbyReference;
    private String playerName;
    private int myId;
    private Server_RMI server;

     public void CheckConnectionClient() {
         //just to see the connection
     }

    /**
     * Constructs a new Client_RMI object with the specified name.
     *
     * @param name the name of the client
     * @throws RemoteException      if a remote communication error occurs
     * @throws NotBoundException    if the server is not bound in the RMI registry
     * @throws MalformedURLException if the URL for the RMI registry is malformed
     */
    public Client_RMI(String name) throws RemoteException, NotBoundException, MalformedURLException {
        this.LobbyReference = 0;
        this.playerName = name;
        this.myId = 0;

        Remote lookup = Naming.lookup("rmi://" +"localhost" +":16000/server");
        this.server = (Server_RMI) lookup;
    }

    /**
     * Creates a lobby with the specified number of players and the name of the lobby's creator.
     *
     * @param numPL        the number of players in the lobby
     * @param creatorLobby the name of the lobby's creator
     */
    public void createLobby(int numPL, String creatorLobby) {
        try {
            this.LobbyReference = server.createLobby(numPL, creatorLobby, this);
            this.myId=0;
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the reference to the lobby.
     *
     * @return the reference to the lobby
     */
    public int getLobbyReference(){
        return this.LobbyReference;
    }


    /**
     * method that implements the Client request of joining a lobby
     */
    public void joinLobby() {
        try {
            this.LobbyReference = server.joinLobby();
        }catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }


    /**
     * Adds a player to the lobby with the specified name.
     *
     * @param name the name of the player to add
     */
    public void addPlayer(String name) {
        try {
            this.myId = server.addPlayer(LobbyReference, name, this);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }

    /**
     * Checks if a given name is already taken in the lobby.
     *
     * @param name the name to check
     * @return true if the name is already taken, false otherwise
     */
    public boolean nameAlreadyTaken(String name){
        try {
            return server.nameAleradyTaken(LobbyReference, name, myId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Changes the name of the player in the lobby.
     *
     * @param name the new name to set for the player
     */
    public void changeName(String name){
        try {
            server.changeName(LobbyReference, myId, name);
            playerName= name;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a list of player names in the lobby.
     *
     * @return a list of player names in the lobby, or null if an exception occurs
     */
    public List<String> playersName(){
        try{
            return server.playersName(LobbyReference);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Retrieves a list of game chat messages from the lobby.
     *
     * @return a list of game chat messages, or null if an exception occurs
     */
    public List<GameMessage> showGameChat() {
        try {
            return server.showGameChat(LobbyReference, playerName);

        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Appends a chat message to the game chat in the lobby.
     *
     * @param possibleChatmex the list of receiver names for the chat message
     * @param myname          the sender name of the chat message
     */
    public void appendchatmex(List <String> possibleChatmex, String myname) {

        try{
            //the parameters are: index of the lobby, List of receiver and the effective message and in the end the sender
            server.appendchatmex(LobbyReference, possibleChatmex, myname );
    } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if it is the player's turn in the lobby.
     *
     * @return true if it is the player's turn, false otherwise or if an exception occurs
     */
    public boolean isItMyTurn(){
        int currentPlayer;
        try{
            currentPlayer=server.getCurrentPlayer(LobbyReference);
        }catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }
        if(currentPlayer== myId){
            return true;
        }
        return false;
    }

    /**
     * Retrieves the dashboard from the lobby.
     *
     * @return the dashboard as a 2D array of tiles, or null if an exception occurs
     */
    public Tile[][] getDashboard() {
        try {
            return server.getDashboard(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Retrieves the personal shelfie of the player from the lobby.
     *
     * @return the player's shelfie as a 2D array of tiles, or null if an exception occurs
     */
    public Tile[][] getMyShelfie(){

            try{
                return server.getMyShelfie(LobbyReference, playerName, myId);
            } catch (Exception e) {
                //System.out.println("ERROR, BAD CONNECTION");
                e.printStackTrace();
                return null;
            }
    }


    /**
     * Retrieves the personal goal of the player from the lobby.
     *
     * @return the player's personal goal, or null if an exception occurs
     */
    public PersonalGoal getMyPersonalGoal(){

            try {
                return server.getMyPersonalGoal(LobbyReference, myId);
            }catch (Exception e){
                //System.out.println("ERROR, BAD CONNECTION");
                e.printStackTrace();
                return null;
            }

    }


    /**
     * Retrieves the list of common goals from the lobby.
     *
     * @return the list of common goals, or null if an exception occurs
     */
    public List<CommonGoals> getCommonGoals(){
        try {
            return server.getCommonGoals(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Checks if the specified tiles at given coordinates are pickable in the lobby.
     *
     * @param xCoord the list of x-coordinates of the tiles to check
     * @param yCoord the list of y-coordinates of the tiles to check
     * @return true if the tiles at the specified coordinates are pickable, false otherwise or if an exception occurs
     */
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        try{
            return server.pickableTiles(LobbyReference, xCoord, yCoord);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Checks if the specified column is available to place a certain number of tiles in the lobby.
     *
     * @param numTiles      the number of tiles to be placed
     * @param selectedCol   the selected column index
     * @return true if the column is available, false otherwise or if an exception occurs
     */
    public boolean columnAvailable(int numTiles, int selectedCol){
        try{
            return  server.columnAvailable(LobbyReference, numTiles, server.getMyShelfieREF(LobbyReference,playerName, myId), selectedCol);
        } catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Performs the final pick of tiles at the specified coordinates in the lobby.
     *
     * @param tilesToPick the number of tiles to pick
     * @param xCord       the list of x-coordinates of the tiles to pick
     * @param yCord       the list of y-coordinates of the tiles to pick
     */
    public void FinalPick(int tilesToPick, List<Integer> xCord,List<Integer> yCord ){
        try{
            server.finalPick(LobbyReference,xCord, yCord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Inserts the specified tiles at the given coordinates into the specified column in the lobby.
     *
     * @param xCoord  the list of x-coordinates of the tiles to insert
     * @param yCoord  the list of y-coordinates of the tiles to insert
     * @param column  the column index where the tiles should be inserted
     */
    public void insertTiles ( List<Integer> xCoord, List<Integer> yCoord, int column){
        try {
            server.insertTiles(LobbyReference, xCoord,yCoord,column);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }

    /**
     * Checks the winner of the game in the lobby.
     *
     * @return the name of the winner, or "ERROR" if an exception occurs
     */
    public String checkWinner() {
        try {
            return server.checkWinner(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * Retrieves the points earned by the player in the lobby.
     *
     * @return the points earned by the player, or -1 if an exception occurs
     */
    public int myPoints(){
        try{
            return server.myPoints(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Retrieves the PG (Personal Goal) points earned by the player in the lobby.
     *
     * @return the PG points earned by the player, or -1 if an exception occurs
     */
    public int myPGpoints(){
        try{
            return server.myPGpoints(LobbyReference,myId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * Retrieves the latest message for the player in the lobby.
     *
     * @return the latest message received by the player, or a default disconnect message if the server is unavailable
     */
    public Message notifyMe(){
        try{
            return server.getMyMessage(LobbyReference);
        } catch (RemoteException e) {
            System.err.println("SERVER CRASHED");
            return new Message(LobbyReference, MessageType.DISCONNECT);
        }
    }

    /**
     * Controls the disconnection of the player in the lobby.
     *
     * @return null
     */
    public Runnable controlDisconnection(){
        Thread controllingDisconnection=new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                if(notifyMe().getMessageType().equals(MessageType.DISCONNECT)){
                    System.out.println("GAME ENDING FROM DISCONNECTION");
                    Thread.currentThread().interrupt();
                    System.exit(-1);
                }
            }
        });
        controllingDisconnection.start();
        return null;
    }
}
