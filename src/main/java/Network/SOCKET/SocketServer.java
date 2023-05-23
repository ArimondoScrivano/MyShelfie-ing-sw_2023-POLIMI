package Network.SOCKET;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public interface SocketServer {
    //int portNumber=0; //numero della porta a cui contattare il server
    //ServerSocket s= new ServerSocket(portNumber); //oggetto server socket che aspetta le richieste di connessione dei client
    //Socket soc=s.accept(); //serverSocket accetta la connessione dai client e crea un oggetto di tipo socket che consenta
                            //di stabilire la connessione.
    public int createLobby(int numPlayers); // param: String LobbyCreator?
    public int joinLobby();
    public int addPlayer(int index, String name);
    public boolean nameAleradyTaken(int index, String name,int id);
    public void changeName(int index, int id, String name);
    public Tile[][] getDashboard(int index);
    public Tile[][] getMyShelfie(int index, String playerName, int playerId);
    public Shelf getMyShelfieREF(int index, String playerName, int playerId);
    public int myPoints(int index, int playerId);
    public int myPGpoints(int index, int playerId);
    public PersonalGoal getMyPersonalGoal(int index, int playerId);
    public List<CommonGoals> getCommonGoals(int index);
    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord);
    public Tile[] getSelectedTiles(int index,int tilesToPick, List<Integer> xCoord, List<Integer> yCoord); // np RMI usage
    public boolean columnAvailable(int index, Tile[] tiles, Shelf myShelf, int selectedCol);
    public void finalPick(int index, List<Integer> xCord, List<Integer> yCord);
    public void insertTiles ( int index, Tile[] tilesToInsert, Shelf myShelf, int columnPicked);
    public String checkWinner(int index, int id);
    public int getCurrentPlayer( int index);
}
