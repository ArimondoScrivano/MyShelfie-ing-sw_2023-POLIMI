package Network.RMI;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Network.messages.Message;
import controller.*;

import controller.GameController;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;
import java.util.List;

public class ConcreteServerRMI extends UnicastRemoteObject implements Server_RMI, Observer {
    private List<GameController> Lobby;

    protected ConcreteServerRMI() throws RemoteException {
        Lobby= new ArrayList<>();
    }

    public int createLobby(int numPlayers) throws RemoteException {
        GameController controller = new GameController(numPlayers, this);
        Lobby.add(controller);
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    public int joinLobby() throws RemoteException {

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
    public int  addPlayer(int index, String name) throws RemoteException {
        int IndexPlayer=Lobby.get(index).getPlayersFilled();
        Lobby.get(index).createPlayer(IndexPlayer, name);
        //We added the player in the Observer List
        return IndexPlayer;
    }

    @Override
    public Tile[][] getDashboard(int index) throws RemoteException{
        return Lobby.get(index).getDashboardTiles();
    }

    @Override
    public Tile[][] getMyShelfie(int index, String playerName, int playerId) throws RemoteException {
        return Lobby.get(index).getPlayersList().get(playerId).getShelfMatrix();
    }
    public Shelf getMyShelfieREF(int index, String playerName, int playerId) throws RemoteException{
        return Lobby.get(index).getPlayersList().get(playerId).getShelf();
    }
    @Override
      public PersonalGoal getMyPersonalGoal(int index, int playerId) throws RemoteException {
          return Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal();
        }
    @Override
    public List<CommonGoals> getCommonGoals(int index) throws RemoteException {
        return Lobby.get(index).getCommonGoals();
}

    public int myPoints(int index, int playerId) throws RemoteException {
        return Lobby.get(index).getPlayersList().get(playerId).getPoints();
}

    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException {
        return Lobby.get(index).tileAvailablePick(xCoord, yCoord);
    }

    public boolean columnAvailable(int index, Tile[] tiles, Shelf myShelf, int selectedCol) throws RemoteException{
        //TODO
         Lobby.get(index).pickTiles(tiles);
        return Lobby.get(index).columnAvailable(tiles, myShelf, selectedCol);

    }

    public void insertTiles ( int index, Tile[] tilesToInsert, Shelf myShelf, int columnPicked) throws RemoteException {
        Lobby.get(index).chooseColumnShelf(columnPicked,tilesToInsert,myShelf);
    }


    public String checkWinner(int index, int id) throws RemoteException {
        if(Lobby.get(index).checkWinner().getId()== id){
            return "YOU WON";
        }
        return "YOU LOST";
    }

    public int getCurrentPlayer( int index) throws RemoteException {
        return Lobby.get(index).playerTurn().getId();
    }



    //TODO
    @Override
    public void update(Observable o, Object message) {
        // NOTIFY ALL THE CLIENTS IN THE INDEX LOBBY
        if(message instanceof Message){
            // bisogna comunicare ai players che è successo qualcosa;

        }
    }
}


