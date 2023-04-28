package Network.RMI;
import java.util.*;
import java.rmi.*;

import Network.messages.Message;
import model.*;
import controller.*;
import model.cgoal.CommonGoals;

// This class is server side for The RMI connection with the client
//TODO: associare il client observer al server observable quando inzia/joina una partita
public interface Server_RMI extends Remote{
 public int createLobby(int numPlayers);
 public int joinLobby();
 public int addPlayer(int index, String name);
 public Tile[][] getDashboard(int index);
public Tile[][] getMyShelfie(int index, String playerName, int playerId);

public PersonalGoal getMyPersonalGoal(int index, int playerId);

public List<CommonGoals> getCommonGoals(int index);
public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord);

 public boolean columnAvailable(int index, Tile[] tiles, Shelf myShelf, int selectedCol);

 public void insertTiles ( int index, Tile[] tilesToInsert, Shelf myShelf, int columnPicked);
 public String checkWinner(int index, int id);

 public void notify(int index, Message m);


}
