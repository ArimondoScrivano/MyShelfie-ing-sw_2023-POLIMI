package controller;

import Network.RMI.Client_RMI;
import Network.SOCKET.Sock;
import Network.SOCKET.SocketClientHandler;
import Network.Server;
import Network.messages.MessageType;
import junit.framework.TestCase;
import model.Game;
import model.Player;
import model.Shelf;
import model.cgoal.CommonGoals;
import org.junit.jupiter.api.Test;
import model.Tile;
import view.TextualUI;
import view.View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    public void testCheckerSomethingChanged() throws IOException, NotBoundException {
        Server server = new Server(16001);
        try {
        Registry registry = LocateRegistry.createRegistry(16000);
        System.out.println("Server is booting....");
        registry.rebind("server", server);
        Thread thread = new Thread(server, "server");
        thread.start();
        System.out.println("Server created");
    } catch (Exception e) {
        System.out.println("Something went wrong :( " + e);
    }

        Client_RMI Franco = new Client_RMI("Franco");
        server.createLobby(2, "Franco", Franco);
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.LOBBYCREATED));
        Client_RMI Mario = new Client_RMI("Mario");
        server.addPlayer(0,"Mario",Mario );
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.GAME_STARTING));
        List<Integer> x= new ArrayList<>();
        List<Integer> y= new ArrayList<>();
        x.add(5);
        y.add(2);
        Franco.columnAvailable(1,1);
        Franco.pickableTiles(x,y);
        Franco.FinalPick(1, x,y);
        List<Integer> x1= new ArrayList<>();
        List<Integer> y1= new ArrayList<>();
        x1.add(4);
        x1.add(5);
        x1.add(6);
        y1.add(3);
        y1.add(3);
        y1.add(3);
        Franco.pickableTiles(x1,y1);
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.SOMETHINGCHANGED));
        //Inserting the tiles
        x.set(0,5);
        y.set(0,2);
        Mario.insertTiles(x,y ,1);
        //removing the tiles from the dashboard
        Mario.FinalPick(1,x,y);
        assertFalse(Franco.pickableTiles(x,y));
        Mario.getDashboard();
        Mario.getCommonGoals();
        Mario.getMyShelfie();
        Mario.getMyPersonalGoal();
        Mario.myPoints();
        Mario.myPGpoints();
        assertFalse(Franco.columnAvailable(10,1));
        assertFalse(Franco.notifyMe().getMessageType().equals(MessageType.GAME_ENDING));
        Client_RMI Mario2 = new Client_RMI("Mario");
        Client_RMI Mario3 = new Client_RMI("Mario");
        Mario2.createLobby(3,"Mario");
        Mario3.joinLobby();
        Mario3.addPlayer("Mario");
        assertTrue(Mario3.nameAlreadyTaken("Mario"));
        Mario3.changeName("Luigi");
        assertFalse(Mario3.notifyMe().getMessageType().equals(MessageType.GAME_STARTING));
       /* TextualUI view = new TextualUI();
        view.init();
        ClientControllerV2 clientControllerV2 = new ClientControllerV2(view, "localhost", 16001);
        //clientControllerV2.gameFlow();
        GameController myGC= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "Paolo");
        int paoloIndex=myGC.finderPlayer("Paolo");
        int marcoIndex=myGC.finderPlayer("Marco");

        GameController myGC2= new GameController(3, new Server(16001));
        myGC.createPlayer(0, "Giovanni");
        myGC.playerTurn();*/
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