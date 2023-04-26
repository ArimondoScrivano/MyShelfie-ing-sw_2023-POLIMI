package Network.RMI;
import java.util.*;
import java.rmi.*;
import model.*;
import controller.*;
import model.cgoal.CommonGoals;

// This class is server side for The RMI connection with the client

public interface Server_RMI extends Remote{
 public int createLobby(int numPlayers);
 public int joinLobby();
 public int addPlayer(int index, String name);
 public Tile[][] getDashboard(int index);
public Tile[][] getMyShelfie(int index, String playerName, int playerId);

public PersonalGoal getMyPersonalGoal(int index, int playerId);

public List<CommonGoals> getCommonGoals(int index);
}
