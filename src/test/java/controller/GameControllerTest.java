package controller;

import Network.Server;
import junit.framework.TestCase;
import model.Game;
import model.Player;
import model.Shelf;
import model.cgoal.CommonGoals;
import org.junit.jupiter.api.Test;
import model.Tile;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest extends TestCase {

    //TESTING SETTERS AND GETTERS
    @Test
    public void testCheckerSetterGetter(){
        Server serverCreator;
        try{
            serverCreator=new Server(16001);
        }catch (Exception e){
            e.printStackTrace();
            serverCreator=null;
        }
        GameController myGC= new GameController(2, serverCreator, "creatorLobby");
        myGC.setId(1);
        assertEquals(1, myGC.getId());
        myGC.setEnd(0);
        assertEquals(0, myGC.getEnd());
        GameController otherGC= new GameController(3, serverCreator);
        int np= myGC.getPlayersFilled();
        List<CommonGoals> myCommonGoals= null;
        myCommonGoals=myGC.getCommonGoals();
        List<Player> myPlayers=null;
        myPlayers=myGC.getPlayersList();
        Tile[][] myDashboard=null;
        myDashboard= myGC.getDashboardTiles();
        int numPlayers= myGC.getNumPlayers();

    }

    //TESTING METHOD createPlayer(int id_new, String np)
    @Test
    public void testCheckerCreatePlayer() throws RemoteException {
        GameController myGC= new GameController(4, new Server(16001));
        myGC.createPlayer(0, "Paolo");
    }

    //TESTING METHOD isFull()
    @Test
    public void testCheckerIsFull() throws RemoteException {
        GameController myGC= new GameController(1, new Server(16001));
        assertFalse(myGC.isFull());
    }

    //TESTING METHOD somethingChanged()
    @Test
    public void testCheckerSomethingChanged() throws RemoteException {
        GameController myGC= new GameController(1, new Server(16001));
        myGC.somethingChanged();
    }

    //TESTING METHOD started()
    @Test
    public void testCheckerStarted() throws RemoteException {
        GameController myGC= new GameController(4, new Server(16001));
        myGC.createPlayer(0, "paolo");
        myGC.createPlayer(1, "pietro");
        myGC.createPlayer(2, "giovanni");
        myGC.createPlayer(3, "tommaso");
        myGC.started();
        GameController otherGC= new GameController(2, new Server(16001));
        otherGC.createPlayer(0, "paolo");
        otherGC.createPlayer(1, "pietro");
        otherGC.started();
        GameController GC= new GameController(3, new Server(16001));
        GC.createPlayer(0, "paolo");
        GC.createPlayer(1, "pietro");
        GC.createPlayer(2, "giovanni");
        GC.started();
    }

    //TESTING METHOD ended()
    @Test
    public void testChecker() throws RemoteException {
        GameController myGC= new GameController(3, new Server(16001));
        myGC.ended();
    }

    //TESTING METHOD changeName(int id, String name)
    @Test
    public void testCheckerChangeName() throws RemoteException {
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "Giovanni");
        myGC.changeName(0, "Paolo");
        myGC.createPlayer(1, "Giovanni");
        myGC.createPlayer(2, "Tommaso");
        myGC.changeName(2, "Pietro");
    }

    //TESTING METHOD finderPlayer(String name)
    @Test
    public void testCheckerFinderPlayer() throws RemoteException {
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "Paolo");
        int paoloIndex=myGC.finderPlayer("Paolo");
        int marcoIndex=myGC.finderPlayer("Marco");
    }

    //TESTING METHOD pickNextPlayer()
    @Test
    public void testCheckerPickNextPlayer() throws RemoteException {
        GameController myGC=new GameController(3, new Server(16001));
        myGC.createPlayer(0, "Giovannni");
        myGC.createPlayer(1, "Rita");
        myGC.createPlayer(2, "Paolo");
        myGC.pickNextPlayer();
    }

    //TESTING METHOD playerTurn()
    @Test
    public void testCheckerPlayerTurn() throws RemoteException {
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "Giovanni");
        myGC.playerTurn();
    }

    //TESTING METHOD pickTiles(List<Integer> xCord, List<Integer> yCord)
    @Test
    public void testCheckerPickTiles() throws RemoteException {
        GameController myGC= new GameController(3, new Server(16001));
        List<Integer> xCord= new ArrayList<>();
        xCord.add(1);
        xCord.add(2);
        List<Integer> yCord= new ArrayList<>();
        yCord.add(1);
        yCord.add(2);
        myGC.pickTiles(xCord, yCord);

    }

    //TESTING METHOD tileAvailablePick(List<Integer> xCord, List<Integer> yCord)
    @Test
    //completare
    public void testCheckerAvailablePick()throws RemoteException{
        GameController myGC= new GameController(3, new Server(16001));
        List<Integer> xCord= new ArrayList<>();
        xCord.add(1);
        xCord.add(2);
        List<Integer> yCord= new ArrayList<>();
        yCord.add(1);
        yCord.add(2);
        myGC.tileAvailablePick(xCord, yCord);
        xCord.add(6);
        xCord.add(6);
        yCord.add(6);
        yCord.add(5);
        myGC.tileAvailablePick(xCord, yCord);
    }

    //TESTING METHOD insertTiles(List<Integer> xCord, List<Integer> yCord, int column)
    @Test
    public void testCheckerInsertTiles() throws RemoteException{
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "giovanni");
        myGC.createPlayer(1, "rita");
        myGC.createPlayer(2, "emma");
        List<Integer> xCord= new ArrayList<>();
        xCord.add(1);
        xCord.add(2);
        List<Integer> yCord= new ArrayList<>();
        yCord.add(1);
        yCord.add(2);
        myGC.insertTiles(xCord, yCord, 3);
    }


    //TESTING METHOD columnAvailable(int numTiles, Shelf myShelf, int column)
    @Test
    public void testCheckerColumnAvailable() throws RemoteException {
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "paolo");
        myGC.createPlayer(1, "pietro");
        myGC.createPlayer(2, "giovanni");
        Shelf myShelf=myGC.getPlayersList().get(0).getShelf();
        myGC.columnAvailable(3, myShelf, 2);
        myGC.columnAvailable(2, myShelf, 2);
        myGC.columnAvailable(1, myShelf, 2);
    }

    //TESTING METHOD checkPoints()
    @Test
    public void testCheckerCheckPoints() throws RemoteException{
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "paolo");
        myGC.createPlayer(1, "pietro");
        myGC.createPlayer(2, "giovanni");
        myGC.checkPoints();
    }

    //TESTING METHOD checkWinner()
    @Test
    public void testCheckerCheckWinner()throws RemoteException{
        GameController myGC= new GameController(3, new Server(16001));
        Player myPlayer;
        myGC.createPlayer(0, "paolo");
        myPlayer=myGC.checkWinner();
    }
}