package Network.RMI;
import java.util.*;
import controller.*;

import controller.GameController;
import model.Tile;

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
    public void addPlayer(int index, String name) {
        Lobby.get(index).createPlayer(Lobby.get(index).getPlayersFilled(), name);
    }

    @Override
    public Tile[][] getDashboard(int index) {
        return Lobby.get(index).getDashboardTiles();
    }

    @Override
    public Tile[][] getMyShelfie(int index, String playerName) {
        for (int i = 0; i < Lobby.get(index).getPlayersFilled(); i++) {
            if (Lobby.get(index).getPlayersList().get(i).getName().equals(playerName)) {
                return Lobby.get(index).getPlayersList().get(i).getShelfMatrix();
            }
        }
        //no player found---ERROR---
        System.out.println("An error has occurred");
      return null;


    }

}
