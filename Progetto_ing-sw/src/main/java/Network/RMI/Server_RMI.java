package Network.RMI;
import java.util.*;
import java.rmi.*;

import Network.messages.Message;
import model.*;
import controller.*;
import model.cgoal.CommonGoals;

// This class is server side for The RMI connection with the client
public interface Server_RMI extends Remote, Observer {
 public int createLobby(int numPlayers) throws RemoteException;
 public int joinLobby() throws RemoteException;
 public int addPlayer(int index, String name) throws RemoteException;
 public Tile[][] getDashboard(int index) throws RemoteException;
public Tile[][] getMyShelfie(int index, String playerName, int playerId) throws RemoteException;
public Shelf getMyShelfieREF(int index, String playerName, int playerId) throws RemoteException;
public int myPoints(int index, int playerId) throws RemoteException;
public PersonalGoal getMyPersonalGoal(int index, int playerId) throws RemoteException;

public List<CommonGoals> getCommonGoals(int index) throws RemoteException;
public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException;

 public boolean columnAvailable(int index, Tile[] tiles, Shelf myShelf, int selectedCol) throws RemoteException;

 public void insertTiles ( int index, Tile[] tilesToInsert, Shelf myShelf, int columnPicked) throws RemoteException;
 public String checkWinner(int index, int id) throws RemoteException;

public int getCurrentPlayer( int index) throws RemoteException;

 public void update(Observable o, Object message);

 public Message getMyMessage(int index) throws RemoteException;
}
