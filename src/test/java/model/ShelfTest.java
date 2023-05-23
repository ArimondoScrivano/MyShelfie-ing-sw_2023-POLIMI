package model;

import org.junit.Assert;
import org.junit.Test;
import junit.framework.TestCase;
import model.COLOR;
import model.Tile;

import java.io.IOException;

import model.Shelf;
import model.Player;
import org.junit.jupiter.api.BeforeEach;

public class ShelfTest extends TestCase {
    Player myPlayer = new Player(0, "Lorenzo");
    Shelf myShelf = new Shelf(myPlayer);
    @Test
    public void testGetTiles(){

        assertEquals(myShelf.tilesShelf, myShelf.getTiles());
    }

    @Test
    public void testGetTilesShelf(){
        Tile[][] copy = myShelf.getTilesShelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(COLOR.BLANK, myShelf.getTilesShelf()[i][j].getColor());
            }
        }
        assertNotSame(myShelf.getTilesShelf(), copy);
    }
    @Test
    public void testAddTiles(){

    }
}