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

            Tile[][] testTiles = testDashboard.getTiles();
            boolean flag = true;

            // check that
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    if (testTiles[r][c] == null) {
                        flag = false;
                    }

                    assertTrue(flag);

                }
            }
        }
    }


    @Test
    // IMPORTANT: it's fundamental that the array pickedTiles passed as parameter in updateDashboard
    // is completely initialized.
    public void testUpdateDashboard2() {
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

    }

    //TODO
    @Test
    public void testRefillDashboard() {
        // test if the dashboard is refilled in the correct way

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
