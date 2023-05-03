package Network.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import Network.messages.Message;
import Network.messages.MessageType;

import controller.GameController;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;
import java.util.List;

public class ConcreteServerRMI extends UnicastRemoteObject implements Server_RMI{
    private List<GameController> Lobby;
    private List<Message> LobbyMessage;

    protected ConcreteServerRMI() throws RemoteException {
        super();
        Lobby= new ArrayList<>();
        LobbyMessage= new ArrayList<>();
    }

    @Override
    public int createLobby(int numPlayers, String creatorLobby) throws RemoteException {
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    @Override
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
    public int addPlayer(int index, String name) throws RemoteException {
        int IndexPlayer=Lobby.get(index).getPlayersFilled();
        Lobby.get(index).createPlayer(IndexPlayer, name);
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

    @Override
    public int myPoints(int index, int playerId) throws RemoteException {
        return Lobby.get(index).getPlayersList().get(playerId).getPoints();
    }

    @Override
    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException {
        return Lobby.get(index).tileAvailablePick(xCoord, yCoord);
    }

    @Override
    public Tile[] getSelectedTiles(int index,int tilesToPick, List<Integer> yCoord, List<Integer> xCoord) throws RemoteException {
        Tile[] returnedTiles= new Tile[tilesToPick];
        int x=0;
        for(int i=0; i<tilesToPick; i++){

            returnedTiles[i]= new Tile(Lobby.get(index).getDashboardTiles()[xCoord.get(x)][yCoord.get(x)].getColor(), Lobby.get(index).getDashboardTiles()[xCoord.get(x)][yCoord.get(x)].getId());
            x++;
        }
        return returnedTiles;
    }

    @Override
    public boolean columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol) throws RemoteException{
             return Lobby.get(index).columnAvailable(numTiles, myShelf, selectedCol);


    }
    //this method removes the tiles from the dashboard
    @Override
    public void finalPick(int index, List<Integer> xCord, List<Integer> yCord)throws RemoteException{
        Lobby.get(index).pickTiles(xCord,yCord);
    }



    //This method adds the tiles in the shelf
    public void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column) throws RemoteException {
        Lobby.get(LobbyReference).insertTiles(xCoord,yCoord,column);
    }


    @Override
    public String checkWinner(int index, int id) throws RemoteException {
        if(Lobby.get(index).checkWinner().getId()== id){
            return "YOU WON";
        }
        return "YOU LOST";
    }

    @Override
    public int getCurrentPlayer( int index) throws RemoteException {
        return Lobby.get(index).playerTurn().getId();
    }

    @Override
    public void setMessage( Message message){
        LobbyMessage.add(message.getId(), message);
    }

    @Override
    public Message getMyMessage(int index) throws RemoteException{
        return LobbyMessage.get(index);
    }
}