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

public class Client_RMI extends UnicastRemoteObject implements Runnable, ClientCallback {
    // it indicates the Game where the player is
    private int LobbyReference;
    private String playerName;
    private int myId;
    private Server_RMI server;

     public void CheckConnectionClient() {
         //just to see the connection
     }
    public Client_RMI(String name) throws RemoteException, NotBoundException, MalformedURLException {
        this.LobbyReference = 0;
        this.playerName = name;
        this.myId = 0;

        Remote lookup = Naming.lookup("rmi://localhost:16000/server");
        this.server = (Server_RMI) lookup;
    }


    public void createLobby(int numPL, String creatorLobby) {
        try {
            this.LobbyReference = server.createLobby(numPL, creatorLobby, this);
            this.myId=0;
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }

    public int getLobbyReference(){
        return this.LobbyReference;
    }

    public void joinLobby() {
        try {
            this.LobbyReference = server.joinLobby();
        }catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }

    public void addPlayer(String name) {
        try {
            this.myId = server.addPlayer(LobbyReference, name, this);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }


    public boolean nameAlreadyTaken(String name){
        try {
            return server.nameAleradyTaken(LobbyReference, name, myId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void changeName(String name){
        try {
            server.changeName(LobbyReference, myId, name);
            playerName= name;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
public List<String> playersName(){
        try{
            return server.playersName(LobbyReference);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }

}

    public List<GameMessage> showGameChat() {
        try {
            return server.showGameChat(LobbyReference, playerName);

        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void appendchatmex(List <String> possibleChatmex, String myname) {

        try{
            //the parameters are: index of the lobby, List of receiver and the effective message and in the end the sender
            server.appendchatmex(LobbyReference, possibleChatmex, myname );
    } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
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

    public Tile[][] getDashboard() {
        try {
            return server.getDashboard(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }
    }

    public Tile[][] getMyShelfie(){

            try{
                return server.getMyShelfie(LobbyReference, playerName, myId);
            } catch (Exception e) {
                //System.out.println("ERROR, BAD CONNECTION");
                e.printStackTrace();
                return null;
            }
    }

    public PersonalGoal getMyPersonalGoal(){

            try {
                return server.getMyPersonalGoal(LobbyReference, myId);
            }catch (Exception e){
                //System.out.println("ERROR, BAD CONNECTION");
                e.printStackTrace();
                return null;
            }


    }

    public List<CommonGoals> getCommonGoals(){
        try {
            return server.getCommonGoals(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }
    }
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        try{
            return server.pickableTiles(LobbyReference, xCoord, yCoord);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }

    }



    public boolean columnAvailable(int numTiles, int selectedCol){
        try{
            return  server.columnAvailable(LobbyReference, numTiles, server.getMyShelfieREF(LobbyReference,playerName, myId), selectedCol);
        } catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }

    }

    public void FinalPick(int tilesToPick, List<Integer> xCord,List<Integer> yCord ){
        try{
            server.finalPick(LobbyReference,xCord, yCord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void insertTiles ( List<Integer> xCoord, List<Integer> yCoord, int column){
        try {
            server.insertTiles(LobbyReference, xCoord,yCoord,column);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }
    }

    public String checkWinner() {
        try {
            return server.checkWinner(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return "ERROR";
        }
    }

    public int myPoints(){
        try{
            return server.myPoints(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return -1;
        }

    }

    public int myPGpoints(){
        try{
            return server.myPGpoints(LobbyReference,myId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }



    public Message notifyMe(){
        try{
            return server.getMyMessage(LobbyReference);
        } catch (RemoteException e) {
            //System.out.println("ERROR BAD CONNECTION");
            e.printStackTrace();
            return null;
        }

    }

    public Runnable controlDisconnection(){
        Thread controllingDisconnection=new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                if(notifyMe().getMessageType().equals(MessageType.DISCONNECT)){
                    System.out.println("GAME ENDING FROM DISCONNECTION");
                    System.exit(-1);
                }
            }
        });
        controllingDisconnection.start();
        return null;
    }

    @Override
    public void run(){

    }

}
