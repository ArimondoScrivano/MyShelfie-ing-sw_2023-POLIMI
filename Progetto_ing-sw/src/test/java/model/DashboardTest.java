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

            // check that
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
        for(int np = 2; np < 5; np++) {
            Bag testBagInGame = new Bag();
            Dashboard testDashboard = new Dashboard(np, testBagInGame);

            Tile[] pickedTiles = new Tile[] {null, null, null};

            Random random = new Random();
            // one-to-three pick
            int counter = random.nextInt(3)+1;

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
                    position[i+1] = c;
                    i += 2;

                    index++;
                } // else has been selected a non valid tile
            }

            testDashboard.updateDashboard(pickedTiles, testBagInGame);

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


    //TODO
    @Test
    public void testSetRefill() {
        // need to test if SetRefill call refillDashboard everytime it needs

        // case1: dashboard is full and don't need a refill
        for(int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard testFullDashboard = new Dashboard(np, testInGameBag);

            // check if refill is correctly initialized
            assertFalse(testFullDashboard.getRefill());

            // saving initial tiles[][]
            Tile[][] prevTiles = testFullDashboard.getTiles();

            testFullDashboard.setRefill(testInGameBag);

            assertFalse(testFullDashboard.getRefill());
            assertEquals(prevTiles, testFullDashboard.getTiles());
        }

        //TODO before i need to test the refillDashboard method

        // case 2: dashboard not completely full/empty, but no refill needed


        // case 3: dashboard not completely full/empty and needs to be refilled

        // case 4: dashboard completely empty


    }

    //TODO
    @Test
    public void testRefillDashboard() {
        // test if the dashboard is refilled in the correct way

        // case1: completely empty dashboard
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

            // refill, assume setRefill() as correct method
            testDashboard.setRefill(testInGameBag);

            // passes the test, so setRefill works and set refill = true
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

        // case2: partly empty dashboard needs refill
        for(int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard testDashboard = new Dashboard(np, testInGameBag);

            // need to pick some random tails from the dashboard
            // can try to make a loop that randomly pick one-to-#validtail from the dashboard
            // and check everytime the refill

            // pickedTiles <- #random number of tiles picked from dashboard during the match
            Random random = new Random();
            if(np == 2) {
                int picksNumber = random.nextInt(29)+1;
            } else if(np == 3) {
                int picksNumber = random.nextInt(37)+1;
            } else if(np == 4) {
                int picksNumber = random.nextInt(45)+1;
            }

            //int pickedTiles = picksNumber; //why error? initialization due to condition?
            // for each n in pickedTiles relate a random (valid) tile on the dashboard
            //for(int counter = 1; counter < pickedTiles; counter++) {

        }
    }


    @Test
    public void testGetDashboard() {

        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard dashboardTest = new Dashboard(np, testInGameBag);
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    assertEquals(dashboardTest.getTiles()[r][c].getColor(), dashboardTest.getDashboard()[r][c].getColor());
                    assertEquals(dashboardTest.getTiles()[r][c].getId(), dashboardTest.getDashboard()[r][c].getId());

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
