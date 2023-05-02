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
}
