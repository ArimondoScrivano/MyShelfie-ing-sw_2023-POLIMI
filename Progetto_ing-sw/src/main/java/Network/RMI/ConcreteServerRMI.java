package Network.RMI;
import java.io.IOException;
import java.util.*;

import Network.messages.Message;
import controller.*;

import controller.GameController;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;

public class ConcreteServerRMI implements Server_RMI {
    private List<GameController> Lobby;
private List<Message> LobbyMessage;

    public int createLobby(int numPlayers) {
        GameController controller = new GameController(numPlayers, this);
        Lobby.add(controller);
        controller.setId(Lobby.indexOf(controller));
        int indexLobby = Lobby.indexOf(controller);
        return indexLobby;
    }

    public int joinLobby() {

        for (int i = 0; i < Lobby.size(); i++) {
            if (!Lobby.get(i).isFull()) {
                return i;
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        Lobby.add(controller);
        return Lobby.indexOf(controller);
    }

    @Override
    public int  addPlayer(int index, String name) {
        int IndexPlayer=Lobby.get(index).getPlayersFilled();
        Lobby.get(index).createPlayer(IndexPlayer, name);
        return IndexPlayer;
    }

    @Override
    public Tile[][] getDashboard(int index) {
        return Lobby.get(index).getDashboardTiles();
    }

    @Override
    public Tile[][] getMyShelfie(int index, String playerName, int playerId) {
        return Lobby.get(index).getPlayersList().get(playerId).getShelfMatrix();
    }

    @Override
      public PersonalGoal getMyPersonalGoal(int index, int playerId){
          return Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal();
        }
    @Override
    public List<CommonGoals> getCommonGoals(int index){
        return Lobby.get(index).getCommonGoals();
}

    public int myPoints(int index, int playerId){
        return Lobby.get(index).getPlayersList().get(playerId).getPoints();
}

    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord){
        return Lobby.get(index).tileAvailablePick(xCoord, yCoord);
    }

    public boolean columnAvailable(int index, Tile[] tiles, Shelf myShelf, int selectedCol) {
        //TODO
         Lobby.get(index).pickTiles(tiles);
        return Lobby.get(index).columnAvailable(tiles, myShelf, selectedCol);

    }

    public void insertTiles ( int index, Tile[] tilesToInsert, Shelf myShelf, int columnPicked){
        Lobby.get(index).chooseColumnShelf(columnPicked,tilesToInsert,myShelf);
    }


    public String checkWinner(int index, int id){
        if(Lobby.get(index).checkWinner().getId()== id){
            return "YOU WON";
        }
        return "YOU LOST";
    }

    @Override
    public void notify(int index, Message m) {

        // NOTIFY ALL THE CLIENTS IN THE INDEX LOBBY


    }
}


