package model;

import java.util.Random;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class DashboardTest extends TestCase {

    @Test
    public void testDashboard() {
        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard testDashboard = new Dashboard(np, testInGameBag);

            // checking np correct initialization
            assertEquals(np, testDashboard.getPlayers());

            // don't need to check refillable initialization

            // checking tiles[][] initialization
            Tile[][] testTiles = testDashboard.getTiles();
            boolean flag = false;

            // check
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    if (testTiles[r][c] == null) {
                        flag = true;
                    }

                    assertFalse(flag);

                }
            }

            // checking boolean refill initialization
            assertFalse(testDashboard.getRefill());
        }
    }


    @Test
    // IMPORTANT: it's fundamental that the array pickedTiles passed as parameter in updateDashboard
    // is completely initialized.
    public void testUpdateDashboard() {
        for (int np = 2; np < 5; np++) {
            Bag testBagInGame = new Bag();
            Dashboard testDashboard = new Dashboard(np, testBagInGame);

            Tile[] pickedTiles = new Tile[3];

            Random random = new Random();
            // one-to-three pick
            int counter = random.nextInt(3) + 1;

            // array for saving random picks positions
            int[] position = new int[6];
            int i = 0;

            // insert random pick in pickedTiles
            int index = 0;
            while (index < counter) {

                int r = random.nextInt(9);
                int c = random.nextInt(9);

                if (testDashboard.getRefillable()[r][c].ordinal() < (np - 1)) {
                    pickedTiles[index] = testDashboard.getTiles()[r][c];

                    // saving tile position
                    position[i] = r;
                    position[i + 1] = c;
                    i += 2;

                    index++;
                } // else has been selected a non valid tile
            }

            testDashboard.updateDashboard(pickedTiles);

            // testing in the random tiles chose by the player have been updated

            if (counter == 1) {
                assertEquals(COLOR.BLANK, testDashboard.getTiles()[position[0]][position[1]].getColor());
                assertEquals(0, testDashboard.getTiles()[position[0]][position[1]].getId());
            }

            if (counter == 2) {
                assertEquals(COLOR.BLANK, testDashboard.getTiles()[position[2]][position[3]].getColor());
                assertEquals(0, testDashboard.getTiles()[position[2]][position[3]].getId());
            }

            if (counter == 3) {
                assertEquals(COLOR.BLANK, testDashboard.getTiles()[position[4]][position[5]].getColor());
                assertEquals(0, testDashboard.getTiles()[position[4]][position[5]].getId());
            }
        }
    }


    @Test
    public void testSetRefill() {
        // need to test if SetRefill call refillDashboard everytime it needs

        // case1: dashboard is full and don't need a refill
        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard testFullDashboard = new Dashboard(np, testInGameBag);

            // check if refill is correctly initialized
            assertFalse(testFullDashboard.getRefill());

            // saving initial tiles[][]
            Tile[][] prevTiles = testFullDashboard.getTiles();

            testFullDashboard.setRefill(testInGameBag);

            // checking if refill is still false
            assertFalse(testFullDashboard.getRefill());
            // checking if tails[][] has been modified
            assertEquals(prevTiles, testFullDashboard.getTiles());
        }

        // case 2: completely empty dashboard, needs refill
        // tested within testRefillDashboard

    }


    @Test
    public void testRefillDashboard() {
        // test if the dashboard is refilled in the correct way

        // case2: completely empty dashboard
        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard testDashboard = new Dashboard(np, testInGameBag);

            // empty dashboard's tiles
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    if (!(testDashboard.getTiles()[r][c].getColor().equals(COLOR.BLANK))) {
                        testDashboard.pickTile(r, c);
                        assertEquals(COLOR.BLANK, testDashboard.getTiles()[r][c].getColor());
                        assertEquals(0, testDashboard.getTiles()[r][c].getId());
                    }
                }
            }

            testDashboard.setRefill(testInGameBag);

            // passing test if setRefill set refill = true
            assertTrue(testDashboard.getRefill());

            // knowing refill == true
            boolean flag = false;
            // need to check if every BLANK tile in the dashboard has been replaced by a valid one
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    // if tile is set with default values
                    if (testDashboard.getTiles()[r][c].getColor() == COLOR.BLANK
                            && testDashboard.getTiles()[r][c].getId() == 0) {
                        // if tile is valid (not BLK)
                        if (testDashboard.getRefillable()[r][c].ordinal() < (np - 1))
                            flag = true;
                    }
                }
            }
            assertFalse(flag);
        }
    }


    @Test
    public void testGetTilesCopy() {

        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard dashboardTest = new Dashboard(np, testInGameBag);
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    assertEquals(dashboardTest.getTiles()[r][c].getColor(), dashboardTest.getTilesCopy()[r][c].getColor());
                    assertEquals(dashboardTest.getTiles()[r][c].getId(), dashboardTest.getTilesCopy()[r][c].getId());

                }
            }
        }
    }


    @Test
    public void testPickTile() {

        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard testDashboard = new Dashboard(np, testInGameBag);

            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    assertEquals(testDashboard.getTiles()[r][c].getColor(), testDashboard.pickTile(r, c).getColor());
                    assertEquals(testDashboard.getTiles()[r][c].getId(), testDashboard.pickTile(r, c).getId());
                }
            }
        }
    }

}