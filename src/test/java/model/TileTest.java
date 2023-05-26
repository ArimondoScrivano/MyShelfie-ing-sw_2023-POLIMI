package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest extends TestCase {

    @Test
    //TESTING METHOD Tile(COLOR color, int id)
    public void testCheckerTile(){
        Tile myTile= new Tile(COLOR.VIOLET, 1);
        assertEquals(COLOR.VIOLET, myTile.getColor());
        assertEquals(1, myTile.getId());
    }

}