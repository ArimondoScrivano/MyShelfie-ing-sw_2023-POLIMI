package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public void testGetTiles() {

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
    public void testGetTilesCopy(){
        for (int np = 2; np < 5; np++) {
            Bag testInGameBag = new Bag();
            Dashboard dashboardTest = new Dashboard(np, testInGameBag);
            for (int r = 0; r < 11; r++) {
                for (int c = 0; c < 11; c++) {

                    assertEquals(dashboardTest.getTilesCopy()[r][c].getColor(), dashboardTest.getTilesCopy()[r][c].getColor());
                    assertEquals(dashboardTest.getTilesCopy()[r][c].getId(), dashboardTest.getTilesCopy()[r][c].getId());

                }
            }
        }
    }

    @Test
    public void testSetRefill() throws Exception {
        Dashboard dashboardTest = new Dashboard(2, new Bag());
        for(int i=0; i<11; i++){
            for(int j=0; j<11; j++){
                dashboardTest.getTiles()[i][j]=new Tile(COLOR.BLANK, 0);
            }
        }
        Tile a=new Tile(COLOR.BLANK, 0);
        for(int i=0; i<11; i++){
            for(int j=0; j<11; j++){
                assertEquals(dashboardTest.getTiles()[i][j].getColor(), a.getColor());
            }
        }
        Tile a2 = new Tile(COLOR.YELLOW, 1);
        dashboardTest.getTiles()[4][2]=a2;


        dashboardTest.setRefill();
    }

    @Test
    public void testUpdateDashboard() throws Exception {
        Dashboard dashboardTest = new Dashboard(2, new Bag());
        List<Integer> yCoord = Arrays.asList(4, 5);
        List<Integer> xCoord = Arrays.asList(2,2);
        dashboardTest.updateDashboard(yCoord, xCoord);
        Tile a = new Tile(COLOR.BLANK, 0);
        assertEquals(dashboardTest.getTiles()[2][4].getColor(), a.getColor());
        assertEquals(dashboardTest.getTiles()[2][5].getColor(), a.getColor());
    }

}