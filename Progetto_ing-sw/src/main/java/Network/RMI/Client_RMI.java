package Network.RMI;

import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Client_RMI implements Observer {
    // it indicates the Game where the player is
    private int LobbyReference;
    private String playerName;
    private int myId;
    private Server_RMI server;

    public Client_RMI(String name) throws RemoteException, NotBoundException {
        this.LobbyReference = 0;
        this.playerName = name;
        this.myId = 0;

        // ci si deve legare al registry e utilizzare l'istanza della classe ServerRMI (server)
        Registry registry= LocateRegistry.getRegistry();
        String remoteObjectName = "server";
        this.server = (Server_RMI) registry.lookup(remoteObjectName);

    }

    @Override
    public void update(Observable o, Object arg) {
        // si notifica il client in modi da decidere postumi
    }

    public void createLobby(int numPL) {
        try {
            this.LobbyReference = server.createLobby(numPL);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
        }
    }

    public void joinLobby() {
        try {
            this.LobbyReference = server.joinLobby();
        }catch (Exception e){
            System.out.println("ERROR, BAD CONNECTION");
        }
    }

    public void addPlayer(int index, String name, Observer Player) {
        try {
            this.myId = server.addPlayer(LobbyReference, name, this);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
        }
    }

    //TODO

    public boolean isItMyTourn(){
        int currentPlayer;
        try{
            currentPlayer=server.getCurrentPlayer(LobbyReference);
        }catch (Exception e){
            System.out.println("ERROR, BAD CONNECTION");
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
            System.out.println("ERROR, BAD CONNECTION");
            return null;
        }
    }

    public Tile[][] getMyShelfie(){

            try{
                return server.getMyShelfie(LobbyReference, playerName, myId);
            } catch (Exception e) {
                System.out.println("ERROR, BAD CONNECTION");
                return null;
            }
    }

    public PersonalGoal getMyPersonalGoal(){

            try {
                return server.getMyPersonalGoal(LobbyReference, myId);
            }catch (Exception e){
                System.out.println("ERROR, BAD CONNECTION");
                return null;
            }


    }

    public List<CommonGoals> getCommonGoals(){
        try {
            return server.getCommonGoals(LobbyReference);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
            return null;
        }
    }
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        try{
            return server.pickableTiles(LobbyReference, xCoord, yCoord);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
            return false;
        }

    }

    public boolean columnAvailable(Tile[] tiles, int selectedCol){
        try{
            return  server.columnAvailable(LobbyReference, tiles, server.getMyShelfieREF(LobbyReference,playerName, myId), selectedCol);
        } catch (Exception e){
            System.out.println("ERROR, BAD CONNECTION");
            return false;
        }

    }

    public void insertTiles ( Tile[] tilesToInsert, int columnPicked){
        try {
            server.insertTiles(LobbyReference, tilesToInsert, server.getMyShelfieREF(LobbyReference,playerName, myId), columnPicked);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
        }
    }
    public String checkWinner(int index, int id) {
        try {
            return server.checkWinner(LobbyReference, myId);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
            return "ERROR";
        }
    }

    public int myPoints(){
        try{
            return server.myPoints(LobbyReference, myId);
        } catch (Exception e) {
            System.out.println("ERROR, BAD CONNECTION");
            return -1;
        }

    }





}
