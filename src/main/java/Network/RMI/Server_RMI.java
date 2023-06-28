package Network.RMI;

import Network.GameChat.GameMessage;
import Network.messages.Message;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//This class is server side for The RMI connection with the client
/**
 * The Server_RMI interface defines the contract for the RMI server.
 * It allows clients to interact with the server using remote method invocation.
 */
public interface Server_RMI extends Remote{
 /**
  * Creates a lobby with the specified number of players and creator's name.
  *
  * @param numPlayers   the number of players in the lobby
  * @param creatorLobby the name of the lobby creator
  * @param client       the client callback object
  * @return the reference ID of the created lobby
  * @throws RemoteException if a remote communication error occurs
  */
 int createLobby(int numPlayers, String creatorLobby, ClientCallback client) throws RemoteException;

 /**
  * Joins an existing lobby.
  *
  * @return the reference ID of the joined lobby
  * @throws RemoteException if a remote communication error occurs
  */
 int joinLobby() throws RemoteException;

 /**
  * Adds a player to the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @param name  the name of the player
  * @param Client the client callback object
  * @return the ID of the added player
  * @throws RemoteException if a remote communication error occurs
  */
 int addPlayer(int index, String name,ClientCallback Client) throws RemoteException;


 /**
  * Checks if a name is already taken in the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @param name  the name to check
  * @param id    the ID of the player
  * @return true if the name is already taken, false otherwise
  * @throws RemoteException if a remote communication error occurs
  */
 boolean nameAleradyTaken(int index, String name,int id)throws RemoteException;

 /**
  * Changes the name of a player in the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @param id    the ID of the player
  * @param name  the new name for the player
  * @throws RemoteException if a remote communication error occurs
  */
 void changeName(int index, int id, String name)throws RemoteException;

 /**
  * Retrieves the names of the players in the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @return the list of player names
  * @throws RemoteException if a remote communication error occurs
  */
 List<String> playersName(int index) throws RemoteException;


 /**
  * Retrieves the game chat messages in the specified lobby for a specific receiver.
  *
  * @param index       the reference ID of the lobby
  * @param id_receiver the ID of the message receiver
  * @return the list of game chat messages
  * @throws RemoteException if a remote communication error occurs
  */
 List<GameMessage> showGameChat(int index, String id_receiver) throws RemoteException;

 /**
  * Appends chat messages to the game chat in the specified lobby with the sender's name.
  *
  * @param index            the reference ID of the lobby
  * @param possibleChatmex  the list of chat messages to append
  * @param myname           the name of the sender
  * @throws RemoteException if a remote communication error occurs
  */
 void appendchatmex(int index, List<String> possibleChatmex, String myname)throws RemoteException;

 /**
  * Retrieves the dashboard of the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @return the dashboard as a 2D array of tiles
  * @throws RemoteException if a remote communication error occurs
  */
 Tile[][] getDashboard(int index) throws RemoteException;


 /**
  * Retrieves the shelf of the specified player in the specified lobby.
  *
  * @param index      the reference ID of the lobby
  * @param playerName the name of the player
  * @param playerId   the ID of the player
  * @return the shelf as a 2D array of tiles
  * @throws RemoteException if a remote communication error occurs
  */
 Tile[][] getMyShelfie(int index, String playerName, int playerId) throws RemoteException;


 /**
  * Retrieves the reference to the shelf of the specified player in the specified lobby.
  *
  * @param index      the reference ID of the lobby
  * @param playerName the name of the player
  * @param playerId   the ID of the player
  * @return the reference to the shelf object
  * @throws RemoteException if a remote communication error occurs
  */
 Shelf getMyShelfieREF(int index, String playerName, int playerId) throws RemoteException;


 /**
  * Retrieves the points of the specified player in the specified lobby.
  *
  * @param index    the reference ID of the lobby
  * @param playerId the ID of the player
  * @return the points of the player
  * @throws RemoteException if a remote communication error occurs
  */
 int myPoints(int index, int playerId) throws RemoteException;


 /**
  * Retrieves the public goal points of the specified player in the specified lobby.
  *
  * @param index    the reference ID of the lobby
  * @param playerId the ID of the player
  * @return the public goal points of the player
  * @throws RemoteException if a remote communication error occurs
  */
 int myPGpoints(int index, int playerId)  throws RemoteException;


 /**
  * Retrieves the personal goal of the specified player in the specified lobby.
  *
  * @param index    the reference ID of the lobby
  * @param playerId the ID of the player
  * @return the personal goal of the player
  * @throws RemoteException if a remote communication error occurs
  */
 PersonalGoal getMyPersonalGoal(int index, int playerId) throws RemoteException;


 /**
  * Retrieves the common goals of the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @return the list of common goals
  * @throws RemoteException if a remote communication error occurs
  */
List<CommonGoals> getCommonGoals(int index) throws RemoteException;

 /**
  * Checks if the specified tiles are pickable in the specified lobby.
  *
  * @param index  the reference ID of the lobby
  * @param xCoord the list of x coordinates of the tiles
  * @param yCoord the list of y coordinates of the tiles
  * @return true if the tiles are pickable, false otherwise
  * @throws RemoteException if a remote communication error occurs
  */
boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException;

 /**
  * Retrieves the selected tiles in the specified lobby based on the number of tiles to pick and their coordinates.
  *
  * @param index       the reference ID of the lobby
  * @param tilesToPick the number of tiles to pick
  * @param xCoord      the list of x coordinates of the tiles
  * @param yCoord      the list of y coordinates of the tiles
  * @return the array of selected tiles
  * @throws RemoteException if a remote communication error occurs
  */
 Tile[] getSelectedTiles(int index,int tilesToPick, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException;


 /**
  * Checks if a column is available to insert tiles in the specified lobby.
  *
  * @param index        the reference ID of the lobby
  * @param numTiles     the number of tiles to insert
  * @param myShelf      the shelf of the player
  * @param selectedCol  the selected column
  * @return true if the column is available, false otherwise
  * @throws RemoteException if a remote communication error occurs
  */
 boolean columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol) throws RemoteException;

 /**
  * Performs the final pick of tiles in the specified lobby based on their coordinates.
  *
  * @param index  the reference ID of the lobby
  * @param xCord  the list of x coordinates of the tiles
  * @param yCord  the list of y coordinates of the tiles
  * @throws RemoteException if a remote communication error occurs
  */
 void finalPick(int index, List<Integer> xCord, List<Integer> yCord)throws RemoteException;


 /**
  * Inserts tiles into a column in the specified lobby based on their coordinates.
  *
  * @param LobbyReference the reference ID of the lobby
  * @param xCoord         the list of x coordinates of the tiles
  * @param yCoord         the list of y coordinates of the tiles
  * @param column         the column to insert the tiles into
  * @throws RemoteException if a remote communication error occurs
  */
 void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column) throws RemoteException;

 /**
  * Checks the winner of the game in the specified lobby based on the player ID.
  *
  * @param index the reference ID of the lobby
  * @param id    the ID of the player
  * @return the winner's name, "DRAW" for a tie, or "NOT FINISHED" if the game is not finished
  * @throws RemoteException if a remote communication error occurs
  */
 String checkWinner(int index, int id) throws RemoteException;


 /**
  * Retrieves the ID of the current player in the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @return the ID of the current player
  * @throws RemoteException if a remote communication error occurs
  */
int getCurrentPlayer( int index) throws RemoteException;


 /**
  * Sets a message in the specified lobby.
  *
  * @param message the message to set
  * @throws RemoteException if a remote communication error occurs
  */
void setMessage( Message message) throws RemoteException;

 /**
  * Retrieves the message for the current player in the specified lobby.
  *
  * @param index the reference ID of the lobby
  * @return the message for the current player
  * @throws RemoteException if a remote communication error occurs
  */
 Message getMyMessage(int index) throws RemoteException;


}