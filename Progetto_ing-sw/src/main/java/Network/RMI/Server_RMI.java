package Network.RMI;
import java.util.*;
import java.rmi.*;
import model.*;
import controller.*;

// This class is server side for The RMI connection with the client

public interface Server_RMI extends Remote{
 public int createLobby(int numPlayers);
 public int joinLobby();
 public void addPlayer(int index, String name);
 public Tile[][] getDashboard(int index);
public Tile[][] getMyShelfie(int index, String playerName);
}
