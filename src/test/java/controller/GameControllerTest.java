package controller;

import Network.RMI.Client_RMI;
import Network.Server;
import Network.messages.MessageType;
import junit.framework.TestCase;
import model.COLOR;
import model.Player;
import model.Tile;
import model.cgoal.CommonGoals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class GameControllerTest extends TestCase {

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

        Client_RMI Franco = new Client_RMI("Franco","localhost");
        Franco.createLobby(2, "Franco");
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.LOBBYCREATED));
        Client_RMI Mario = new Client_RMI("Mario","localhost");
        Mario.joinLobby();
        Mario.addPlayer("Mario");
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.GAME_STARTING));
        List<Integer> x= new ArrayList<>();
        List<Integer> y= new ArrayList<>();
        x.add(5);
        y.add(2);
        Franco.columnAvailable(1,1);
        Franco.pickableTiles(x,y);
        Franco.FinalPick(1, x,y);
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.SOMETHINGCHANGED));
        List<Integer> x1= new ArrayList<>();
        List<Integer> y1= new ArrayList<>();
        x1.add(4);
        x1.add(5);

        y1.add(3);
        y1.add(3);
        Franco.pickableTiles(x1,y1);
        x1.add(6);
        y1.add(3);

        x1.set(0,6);
        x1.set(1,5);
        x1.set(2,4);

        Franco.pickableTiles(x1,y1);
        x1.set(0,6);
        x1.set(1,4);
        x1.set(2,5);

        Franco.pickableTiles(x1,y1);
        x1.set(0,5);
        x1.set(1,4);
        x1.set(2,6);

        Franco.pickableTiles(x1,y1);
        List<Integer> x5= new ArrayList<>();
        List<Integer> y5= new ArrayList<>();
        x5.add(6);
        x5.add(6);
        x5.add(6);
        x5.add(6);
        x5.add(6);
        x5.add(6);
        y5.add(5);
        y5.add(5);
        y5.add(5);
        y5.add(5);
        y5.add(5);
        y5.add(5);
        Mario.insertTiles(x5,y5,0);
        Mario.insertTiles(x5,y5,1);
        Mario.insertTiles(x5,y5,2);
        Mario.insertTiles(x5,y5,3);
        Mario.insertTiles(x5,y5,4);
        assertFalse(Mario.columnAvailable(1,0));
        assertFalse(Mario.columnAvailable(1,2));
        assertFalse(Mario.columnAvailable(1,3));
        assertFalse(Mario.columnAvailable(1,1));
        assertFalse(Mario.columnAvailable(1,4));
        Mario.FinalPick(1,x5,y5);
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
        assertTrue(Franco.notifyMe().getMessageType().equals(MessageType.GAME_ENDING));
        Client_RMI Mario2 = new Client_RMI("Mario","localhost");
        Client_RMI Mario3 = new Client_RMI("Mario","localhost");

        Mario2.createLobby(2,"Mario");
        Mario3.joinLobby();
        Mario3.addPlayer("Mario");
        assertTrue(Mario3.nameAlreadyTaken("Mario"));
        Mario3.changeName("Luigi");
        assertTrue(Mario3.notifyMe().getMessageType().equals(MessageType.GAME_STARTING));
        List<Integer> x12= new ArrayList<>();
        List<Integer> y12= new ArrayList<>();
        x12.add(8);
        x12.add(8);

        y12.add(4);
        y12.add(5);
        Mario2.pickableTiles(x12,y12);
        Mario2.FinalPick(2,x12, y12);
        List<Integer> x123= new ArrayList<>();
        List<Integer> y123= new ArrayList<>();
        x123.add(7);
        x123.add(7);
        x123.add(7);
        y123.add(4);
        y123.add(5);
        y123.add(6);
        Mario3.pickableTiles(x12,y12);
        y123.set(0,4);
        y123.set(1,6);
        y123.set(2,5);
        Mario3.pickableTiles(x12,y12);
        y123.set(0,5);
        y123.set(1,4);
        y123.set(2,6);
        Mario3.pickableTiles(x12,y12);
        y123.set(0,6);
        y123.set(1,4);
        y123.set(2,5);
        Mario3.pickableTiles(x12,y12);
        Mario3.FinalPick(2,x12, y12);
       assertTrue( Mario3.columnAvailable(3,4));
        assertTrue(Mario3.columnAvailable(2,4));
        Client_RMI Giuseppe= new Client_RMI("Giuseppe","localhost");
        Client_RMI Rita= new Client_RMI("Rita","localhost");
        Client_RMI Piehehetro= new Client_RMI("Piehehetro","localhost");
        Client_RMI Lorenzo= new Client_RMI("Lorenzo","localhost");

        Giuseppe.createLobby(4, "Giuseppe");
        Rita.joinLobby();
        Rita.addPlayer("Rita");
        Piehehetro.joinLobby();
        Piehehetro.addPlayer("Pieetro");
        Lorenzo.joinLobby();
        Lorenzo.addPlayer("Lorenzo");
        assertTrue(Giuseppe.notifyMe().getMessageType().equals(MessageType.GAME_STARTING));
        Client_RMI Rita1= new Client_RMI("Rita","localhost");
        Client_RMI Piehehetro1= new Client_RMI("Piehehetro","localhost");
        Client_RMI Lorenzo1= new Client_RMI("Lorenzo","localhost");
        Rita1.createLobby(3, "Giuseppe");
        Piehehetro1.joinLobby();
        Piehehetro1.addPlayer("Co");
        Lorenzo1.joinLobby();
        Lorenzo1.addPlayer("Lorenzo");
        assertTrue(Rita1.notifyMe().getMessageType().equals(MessageType.GAME_STARTING));
        assertFalse(Rita1.notifyMe().getMessageType().equals(MessageType.DISCONNECT));
    }


    //TESTING METHOD checkWinner()
    @Test
    public void testCheckerCheckWinner()throws RemoteException{
        GameController myGC= new GameController(3, new Server(16001));
        Player myPlayer;
        myGC.createPlayer(0, "paolo");
        myPlayer=myGC.checkWinner();
        myGC.finderPlayer("Giuseppe");
        Tile[][] prova = new Tile[11][11];
        for (int r = 0; r < 11; r++) {
            for (int c = 0; c < 11; c++) {
                if (r==3 &&( c==4 || c==5 || c==6) ) {
                    prova[r][c] =  new Tile(COLOR.BLUE, 0);

                } else {
                    prova[r][c] = new Tile(COLOR.BLANK, 0);
                }
            }
        }
        List<Integer> x123= new ArrayList<>();
        List<Integer> y123= new ArrayList<>();
        y123.add(3);
        x123.add(5);
        myGC.tileAvailablePick(y123,x123);
        myGC.tileAvailablePick(x123,y123);
        y123.add(3);
        x123.add(6);
        myGC.tileAvailablePick(y123,x123);
        y123.add(3);
        x123.add(4);
         Tile[][] prova1= myGC.getDashboardTiles();
        prova1= prova;

        myGC.tileAvailablePick(x123,y123);
        x123.set(0,4);
        x123.set(1,6);
        x123.set(2,5);
        myGC.tileAvailablePick(y123,x123);
        myGC.tileAvailablePick(x123,y123);
        x123.set(0,6);
        x123.set(1,4);
        x123.set(2,5);
        myGC.tileAvailablePick(y123,x123);
        myGC.tileAvailablePick(x123,y123);
        x123.set(0,5);
        x123.set(1,4);
        x123.set(2,6);
        myGC.tileAvailablePick(y123,x123);
        myGC.tileAvailablePick(x123,y123);
    }
}