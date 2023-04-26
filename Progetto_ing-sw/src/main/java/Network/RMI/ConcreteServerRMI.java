package Network.RMI;
import java.util.*;
import controller.*;

import controller.GameController;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;

public class ConcreteServerRMI implements Server_RMI {
    private List<GameController> Lobby;


    public int createLobby(int numPlayers) {
        GameController controller = new GameController(numPlayers);
        Lobby.add(controller);
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
        GameController controller = new GameController(2);
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


      public PersonalGoal getMyPersonalGoal(int index, int playerId){
          return Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal();
        }

public List<CommonGoals> getCommonGoals(int index){
        return Lobby.get(index).getCommonGoals();
}



    }


