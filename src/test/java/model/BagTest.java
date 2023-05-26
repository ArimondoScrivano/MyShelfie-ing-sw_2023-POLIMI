package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BagTest extends TestCase {
    @Test
    public void testCheckEmpty(){
        Bag testBag = new Bag();
        for(int i=2; i<5; i++){
            assertFalse(testBag.checkEmpty(i, 132));
        }
        List<Tile> tilesToRemove=new ArrayList<>();

        for(int i=1; i<24; i++){
            tilesToRemove.add(new Tile(COLOR.BLUE, i));
            testBag.updateBag(tilesToRemove);
            assertEquals(COLOR.BLANK,testBag.getTilesInGame().get(i).getColor());
        }
    }

    @Test
    public void testSetState(){
        Bag testBag = new Bag();
        assertFalse(testBag.getState());
        testBag.setState(true);
        assertTrue(testBag.getState());
    }

    @Test
    public void testGetRandomTile(){
        Bag testBag = new Bag();
        Tile a = testBag.getRandomTile();
        int index = a.getId();
        assertNull(testBag.getTilesInGame().get(index));
    }
}