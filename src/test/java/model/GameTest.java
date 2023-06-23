package model;

import junit.framework.TestCase;
import model.cgoal.CommonGoals;
import model.cgoal.CompleteTestCG;
import org.junit.jupiter.api.Test;
import java.util.*;

public class GameTest extends TestCase {
    List<Player> playersTest = new ArrayList<>();
    //Case 2 players
    List<Player> player1 = Arrays.asList(
            new Player(0, "Arimondo"),
            new Player(1, "Lorenzo")
    );
    //Case 3 players
    List<Player> player2 = Arrays.asList(
            new Player(0, "Arimondo"),
            new Player(1, "Lorenzo"),
            new Player(2, "Pietro")
    );
    //Case 4 players
    List<Player> player3 = Arrays.asList(
            new Player(0, "Arimondo"),
            new Player(1, "Lorenzo"),
            new Player(2, "Pietro"),
            new Player(3, "Rita")
    );
    List<CommonGoals> commonGoalsTest = new ArrayList<>();

    @Test
    public void testGetCurrentPlayer() throws NoSuchElementException, NullPointerException {
        BagTest b1= new BagTest();
        b1.testCheckEmpty();
        b1.testGetRandomTile();
        b1.testSetState();
        DashboardTest D1= new DashboardTest();
        D1.testDashboard();
        D1.testGetTiles();
        D1.testGetTilesCopy();
        try {
            D1.testUpdateDashboard();
            D1.testSetRefill();
        }catch (Exception e){

        }
        LayoutTest l1= new LayoutTest();
        l1.testCheckerLayout();
        PersonalGoalTest p1= new PersonalGoalTest();
        p1.testCheckerGetId();
        p1.testCheckerGetPoints0();
        p1.testCheckerGetPoints1();
        p1.testCheckerGetPoints2();
        p1.testCheckerGetPoints3();
        p1.testCheckerGetPoints4();
        p1.testCheckerGetPoints5();
        PlayerTest p2= new PlayerTest();
        p2.testCheckerCheckPersonalGoal();
        p2.testCheckerGetId();
        p2.testCheckerGetPGPoints();
        p2.testCheckerGetPersonalGoal();
        p2.testGetter1();
        p2.testCheckerGetShelfCompleted();
        p2.testCheckerGetShelfMatrix();
        p2.testCheckerSetCommonGoalsCompleted();
        p2.testCheckerSetLastRound();
        p2.testCheckerSetName();
        p2.testCheckerSetPoints();
        p2.testCheckerSumUpPoints();
        p2.testCheckPersonalGoal();
        p2.testGetter3();
        p2.testGetter4();
        p2.testGetter5();
        p2.testShelfCompleted();
        ShelfTest s1= new ShelfTest();
        s1.testGetTiles();
        s1.testAddTiles();
        s1.testCompleteShelf();
        TileLayoutTest t1= new TileLayoutTest();
        t1.testCheckerGetX();
        TileTest t2 = new TileTest();
        t2.testCheckerTile();
        CompleteTestCG cc= new CompleteTestCG();
        cc.testchecker();


        //2 players
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        assertEquals(2, playersTest.size());
        assertEquals(playersTest, game1.getPlayers());
        for(Player o:playersTest){
            game1.setCurrentPlayer(o);
            assertEquals(game1.getCurrentPlayer(), o);
        }
        //3 players
        playersTest.clear();
        playersTest.addAll(player2);
        Dashboard dashboard2 = new Dashboard(playersTest.size(), new Bag());
        Game game2 = new Game(0, dashboard2, playersTest, playersTest.size());
        assertEquals(3, playersTest.size());
        assertEquals(playersTest, game2.getPlayers());
        for(Player o:playersTest){
            game1.setCurrentPlayer(o);
            assertEquals(game1.getCurrentPlayer(), o);
        }
        //4 players
        playersTest.clear();
        playersTest.addAll(player3);
        Dashboard dashboard3 = new Dashboard(playersTest.size(), new Bag());
        Game game3 = new Game(0, dashboard3, playersTest, playersTest.size());
        assertEquals(4, playersTest.size());
        assertEquals(playersTest, game3.getPlayers());
        for(Player o:playersTest){
            game1.setCurrentPlayer(o);
            assertEquals(game1.getCurrentPlayer(), o);
        }
    }

    @Test
    public void testSetCurrentPlayer() throws NoSuchElementException, NullPointerException {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        game1.setCurrentPlayer(player1.get(1));
        assertEquals(game1.getCurrentPlayer(), playersTest.get(1));
    }

    @Test
    public void testGetCommonGoals() throws NoSuchElementException, NullPointerException{
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        commonGoalsTest = game1.getCommonGoals();
        assertEquals(game1.getCommonGoals(), commonGoalsTest);
    }

    @Test
    public void testGetCommonGoalsPoints() throws NoSuchElementException, NullPointerException {
        //2 players
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        commonGoalsTest = game1.getCommonGoals();
        List<Integer> commonGoalPointsTest = switch (playersTest.size()) {
            case 2 -> Arrays.asList(4, 8);
            case 3 -> Arrays.asList(4, 6, 8);
            case 4 -> Arrays.asList(2, 4, 6, 8);
            default -> new ArrayList<>();
        };
        //Checking with the player size
        switch(playersTest.size()){
            case 2 ->{
                assertEquals(2, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(4, 8), commonGoalPointsTest);
            }
            case 3 ->{
                assertEquals(3, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(4, 6, 8), commonGoalPointsTest);
            }
            case 4->{
                assertEquals(4, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(2, 4, 6, 8), commonGoalPointsTest);
            }
        }
        //3 players
        playersTest.clear();
        playersTest.addAll(player2);
        Dashboard dashboard2 = new Dashboard(playersTest.size(), new Bag());
        Game game2 = new Game(0, dashboard2, playersTest, playersTest.size());
        commonGoalsTest = game2.getCommonGoals();
        commonGoalPointsTest = switch (playersTest.size()) {
            case 2 -> Arrays.asList(4, 8);
            case 3 -> Arrays.asList(4, 6, 8);
            case 4 -> Arrays.asList(2, 4, 6, 8);
            default -> new ArrayList<>();
        };
        //Checking with the player size
        switch(playersTest.size()){
            case 2 ->{
                assertEquals(2, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(4, 8), commonGoalPointsTest);
            }
            case 3 ->{
                assertEquals(3, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(4, 6, 8), commonGoalPointsTest);
            }
            case 4->{
                assertEquals(4, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(2, 4, 6, 8), commonGoalPointsTest);
            }
        }
        //4 players
        playersTest.clear();
        playersTest.addAll(player3);
        Dashboard dashboard3 = new Dashboard(playersTest.size(), new Bag());
        Game game3 = new Game(0, dashboard3, playersTest, playersTest.size());
        commonGoalsTest = game2.getCommonGoals();
        commonGoalPointsTest = switch (playersTest.size()) {
            case 2 -> Arrays.asList(4, 8);
            case 3 -> Arrays.asList(4, 6, 8);
            case 4 -> Arrays.asList(2, 4, 6, 8);
            default -> new ArrayList<>();
        };
        //Checking with the player size
        switch(playersTest.size()){
            case 2 ->{
                assertEquals(2, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(4, 8), commonGoalPointsTest);
            }
            case 3 ->{
                assertEquals(3, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(4, 6, 8), commonGoalPointsTest);
            }
            case 4->{
                assertEquals(4, commonGoalPointsTest.size());
                assertEquals(Arrays.asList(2, 4, 6, 8), commonGoalPointsTest);
            }
        }
    }

    @Test
    public void testSetCommonGoals() throws NoSuchElementException, NullPointerException {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        commonGoalsTest = game1.getCommonGoals();
        assertNotSame(commonGoalsTest.get(0), commonGoalsTest.get(1));
    }

    @Test
    public void testEndGameToken() {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        commonGoalsTest = game1.getCommonGoals();
        game1.setCurrentPlayer(playersTest.get(0));
        assertFalse(playersTest.get(0).isShelfCompleted());
        game1.getCurrentPlayer().setShelfCompleted(true);
        if(playersTest.get(0).getShelfCompleted()){
            assertTrue(playersTest.get(0).getShelfCompleted());
            int before = game1.getCurrentPlayer().getPoints();
            assertFalse(game1.getEndGame());
            game1.getCurrentPlayer().setPointsEndGame();
            assertEquals(before+1, game1.getCurrentPlayer().getPoints());
            game1.setEndGameTrue();
            assertTrue(game1.getEndGame());
        }
    }

    @Test
    public void testEndGame(){
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        commonGoalsTest = game1.getCommonGoals();
        assertFalse(game1.getEndGame());
        game1.setEndGameTrue();
        assertTrue(game1.getEndGame());
    }

    @Test
    public void testGetDashboard(){
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        assertEquals(dashboard, game1.getDashboard());
    }

    @Test
    public void testGetDashboardMatrix(){
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest, playersTest.size());
        assertEquals(dashboard.getTiles(), game1.getDashboardMatrix());
    }
}