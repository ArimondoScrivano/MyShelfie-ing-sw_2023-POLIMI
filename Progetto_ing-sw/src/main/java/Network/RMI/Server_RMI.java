package Network.RMI;
import java.util.*;
import java.rmi.*;
import Network.messages.Message;
import model.*;
import controller.*;
import model.cgoal.CommonGoals;

// This class is server side for The RMI connection with the client
public interface Server_RMI extends Remote{
 public int createLobby(int numPlayers, String creatorLobby) throws RemoteException;
 public int joinLobby() throws RemoteException;
 public int addPlayer(int index, String name) throws RemoteException;
 public boolean nameAleradyTaken(int index, String name,int id)throws RemoteException;
 public void changeName(int index, int id, String name)throws RemoteException;
 public Tile[][] getDashboard(int index) throws RemoteException;
public Tile[][] getMyShelfie(int index, String playerName, int playerId) throws RemoteException;
public Shelf getMyShelfieREF(int index, String playerName, int playerId) throws RemoteException;
public int myPoints(int index, int playerId) throws RemoteException;
 public int myPGpoints(int index, int playerId)  throws RemoteException;

public PersonalGoal getMyPersonalGoal(int index, int playerId) throws RemoteException;

public List<CommonGoals> getCommonGoals(int index) throws RemoteException;
public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException;
 public Tile[] getSelectedTiles(int index,int tilesToPick, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException;

 public boolean columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol) throws RemoteException;
 public void finalPick(int index, List<Integer> xCord, List<Integer> yCord)throws RemoteException;
 public void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column) throws RemoteException;
 public String checkWinner(int index, int id) throws RemoteException;

public int getCurrentPlayer( int index) throws RemoteException;

public void setMessage( Message message) throws RemoteException;


 public Message getMyMessage(int index) throws RemoteException;
}
