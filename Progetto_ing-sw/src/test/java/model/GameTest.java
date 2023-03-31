package model;

import junit.framework.TestCase;
import model.cgoal.CommonGoals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.*;

public class GameTest extends TestCase {
    List<Player> playersTest = new ArrayList<>();
    //Case 2 players
    List<Player> player1 = Arrays.asList(
            new Player(0, "Arimondo"),
            new Player(1, "Lorenzo")
    );
    List<CommonGoals> commonGoalsTest = new ArrayList<>();

    @Test
    public void testGetCurrentPlayer() throws NoSuchElementException, NullPointerException {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        assertEquals(2, playersTest.size());
        assertEquals(playersTest, game1.getPlayers());
        assertEquals(game1.getCurrentPlayer(), playersTest.get(0));
    }

    @Test
    public void testSetCurrentPlayer() throws NoSuchElementException, NullPointerException {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        game1.setCurrentPlayer(player1.get(1));
        assertEquals(game1.getCurrentPlayer(), playersTest.get(1));
    }

    @Test
    public void testGetCommonGoals() throws NoSuchElementException, NullPointerException{
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        commonGoalsTest = game1.getCommonGoals();
        assertEquals(game1.getCommonGoals(), commonGoalsTest);
    }

    @Test
    public void testGetCommonGoalsPoints() throws NoSuchElementException, NullPointerException {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        commonGoalsTest = game1.getCommonGoals();
        assertEquals(2, game1.getCommonGoalsPoints(1).size());
        assertEquals(Arrays.asList(4, 8), game1.getCommonGoalsPoints(1));
        assertEquals(Arrays.asList(4, 8), game1.getCommonGoalsPoints(2));
    }

    @Test
    public void testSetCommonGoals() throws NoSuchElementException, NullPointerException {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        commonGoalsTest = game1.getCommonGoals();
        assertNotSame(commonGoalsTest.get(0), commonGoalsTest.get(1));
    }

    @Test
    @Disabled
    public void testUpdatePointsCommonGoals() throws NullPointerException{
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        commonGoalsTest = game1.getCommonGoals();
    }

    @Test
    public void testEndGameToken() {
        playersTest.addAll(player1);
        Dashboard dashboard = new Dashboard(playersTest.size(), new Bag());
        Game game1 = new Game(0, dashboard, playersTest);
        commonGoalsTest = game1.getCommonGoals();
        assertFalse(playersTest.get(0).isShelfCompleted());
        if(playersTest.get(0).isShelfCompleted()){
            playersTest.get(0).setShelfCompleted();
            assertTrue(playersTest.get(0).isShelfCompleted());
            int before = game1.getCurrentPlayer().points;
            assertFalse(game1.getEndGame());
            game1.endGameToken();
            assertEquals(before+1, game1.getCurrentPlayer().points);
            assertTrue(game1.getEndGame());
        }
    }
}