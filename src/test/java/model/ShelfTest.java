package model;

import org.junit.Assert;
import org.junit.Test;
import junit.framework.TestCase;
import model.COLOR;
import model.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void testCompleteShelf(){
        assertFalse(myShelf.completeShelf());
        //Completing the shelf adding tiles
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                Tile a0=new Tile(COLOR.YELLOW, 0);
                Tile[] tiles = new Tile[1];
                tiles[0]=a0;
                myShelf.addTiles(tiles, j);
            }
        }
        assertTrue(myShelf.completeShelf());
    }

    @Test
    public void testAddTiles(){
        //Adding 1 tile
        Tile a0 = new Tile(COLOR.YELLOW, 3);
        List<Tile> tileList1 = new ArrayList<>();
        tileList1.add(a0);
        Tile[] tiles = new Tile[tileList1.size()];
        for(int i=0; i<tiles.length; i++){
            tiles[i] = tileList1.get(i);
        }
        //Checking if the shelf is empty
        Tile[][] copy = myShelf.getTilesShelf();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                assertEquals(COLOR.BLANK, myShelf.getTilesShelf()[i][j].getColor());
            }
        }
        assertNotSame(myShelf.getTilesShelf(), copy);
        //Adding the tile to column 1
        myShelf.addTiles(tiles, 1);
        assertEquals(myShelf.getTilesShelf()[5][1].getColor(), a0.getColor());
        //Adding 2 tiles
        Tile a1 = new Tile(COLOR.LIGHTBLUE, 1);
        Tile a2 = new Tile(COLOR.BLUE, 2);
        List<Tile> tileList = new ArrayList<>();
        tileList.add(a1);
        tileList.add(a2);
        Tile [] tilesToAdd = new Tile[tileList.size()];
        for(int i=0; i<tilesToAdd.length; i++){
            tilesToAdd[i] = tileList.get(i);
        }
        //Checking if the shelf has only 1 tile added
        assertEquals(myShelf.getTilesShelf()[5][1].getColor(), a0.getColor());
        //Adding tiles to column 0
        myShelf.addTiles(tilesToAdd, 0);
        assertEquals(myShelf.getTilesShelf()[5][0].getColor(), a1.getColor());
        assertEquals(myShelf.getTilesShelf()[4][0].getColor(), a2.getColor());
    }
}