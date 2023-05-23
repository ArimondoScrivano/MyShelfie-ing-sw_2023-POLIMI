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

            // check if there are tiles not initialized
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    if (testTiles[r][c] == null) {
                        flag = true;
                    }

                    assertFalse(flag);

                }
            }

            // checking if valid tiles are correctly initialized (not BLANK)
            boolean blankFlag = false;
            boolean zeroId = false;
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (testDashboard.getRefillable()[r][c].ordinal() < np-1
                            && testDashboard.getTiles()[r][c].getColor() == COLOR.BLANK) {
                        blankFlag = true;
                    }
                    if (testDashboard.getRefillable()[r][c].ordinal() < np-1
                            && testDashboard.getTiles()[r][c].getId() == 0) {
                        zeroId = true;
                    }
                }
            }
            assertFalse(blankFlag);
            assertFalse(zeroId);

            // checking if invalid tiles are correctly initialized (all BLANK)
            boolean checkBlank = true;
            boolean checkZeroId = true;
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (testDashboard.getRefillable()[r][c].ordinal() >= np-1
                            && testDashboard.getRefillable()[r][c].ordinal() < 4
                            && testDashboard.getTiles()[r][c].getColor() != COLOR.BLANK) {
                        checkBlank = false;
                    }
                    if (testDashboard.getRefillable()[r][c].ordinal() >= np-1
                            && testDashboard.getRefillable()[r][c].ordinal() < 4
                            && testDashboard.getTiles()[r][c].getId() != 0) {
                        checkZeroId = false;
                    }
                }
            }
            assertTrue(checkBlank);
            assertTrue(checkZeroId);

            // checking boolean refill initialization
            assertFalse(testDashboard.getRefill());
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


}