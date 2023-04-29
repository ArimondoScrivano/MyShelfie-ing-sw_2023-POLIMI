package Network.RMI;

import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Client_RMI implements Observer {
    // it indicates the Game where the player is
    private int LobbyReference;
    private String playerName;
    private int myId;

    public Client_RMI(String name) {
        this.LobbyReference = 0;
        this.playerName = name;
        this.myId = 0;

        // ci si deve legare al registry e utilizzare l'istanza della classe ServerRMI (server)


    }


    @Override
    public void update(Observable o, Object arg) {
        // si notifica il client in modi da decidere postumi
    }

    public void createLobby() {
        this.LobbyReference = server.createLobby();
    }

    public void joinLobby() {
        this.LobbyReference = server.joinLobby();
    }

    public void addPlayer(int index, String name, Observer Player) {
        this.myId = server.addPlayer(LobbyReference, name, this);

    }

    //TODO

    public boolean isItMyTourn(){
        if(server.getCurrentPlayer(LobbyReference)== myId){
            return true;
        }
        return false;
    }

    public Tile[][] getDashboard(){
        return server.getDashboard(LobbyReference);
    }
    public Tile[][] getMyShelfie(){
        return server.getMyShelfie(LobbyReference, name, myId);
    }

    public PersonalGoal getMyPersonalGoal(){
        return server.getMyPersonalGoal(LobbyReference, myId);
    }

    public List<CommonGoals> getCommonGoals(){
        return server.getCommonGoals(LobbyReference);
    }
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        return server.pickableTiles(LobbyReference, xCoord, yCoord);
    }

    public boolean columnAvailable(Tile[] tiles, int selectedCol){
      return  server.columnAvailanble(LobbyReference, tiles, getMyShelfie(), selectedCol);
    }

    public void insertTiles ( Tile[] tilesToInsert, int columnPicked){
        server.insertTiles(LobbyReference, tilesToInsert, getMyShelfie(), columnPicked);
    }
    public String checkWinner(int index, int id){
        return server.checkWinner(LobbyReference, myId);
    }

    public int myPoints(){
        return server.myPoints(LobbyReference, myId);
    }





}
