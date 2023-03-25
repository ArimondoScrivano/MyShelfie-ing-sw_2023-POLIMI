package source.model;

import source.model.cgoal.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GameTest extends TestCase {
    List<Player> playersTest = new ArrayList<>();
    List<Player> player1 = Arrays.asList(
            new Player(0, "Arimondo"),
            new Player(1, "Lorenzo"),
            new Player(2, "Pietro"),
            new Player(3, "Rita")
    );

    @Test
    public void testGetCurrentPlayer() throws NoSuchElementException {
        Dashboard dashboard = new Dashboard(2);
        playersTest.addAll(player1);
        Game game1 = new Game(0, dashboard, playersTest);
        assertEquals(game1.getCurrentPlayer(), playersTest.get(0));
    }

    @Test
    public void testSetCurrentPlayer() throws NoSuchElementException {
        Dashboard dashboard = new Dashboard(2);
        playersTest.addAll(player1);
        Game game1 = new Game(0, dashboard, playersTest);
        game1.setCurrentPlayer(player1.get(1));
        assertEquals(game1.getCurrentPlayer(), playersTest.get(1));
    }

    @Test
    void getCommonGoals() {
    }

    @Test
    void getCommonGoalsPoints() {
    }

    @Test
    void setCommonGoals() {
    }

    @Test
    void updatePoints() {
    }

    @Test
    void endGameToken() {
    }
}