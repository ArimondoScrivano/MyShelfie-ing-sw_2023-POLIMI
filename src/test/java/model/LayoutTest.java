package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LayoutTest extends TestCase {

    @Test
    public void testCheckerLayout(){
        TileLayout myTileLayout=new TileLayout(1, 2, "Blue");
        Layout myLayout= new Layout("position", myTileLayout);
        assertEquals("position", myLayout.getPosition());
        assertEquals(myTileLayout, myLayout.getTile());
        myLayout.setPosition("new position");
        assertEquals("new position", myLayout.getPosition());
        TileLayout newTileLayout= new TileLayout(3, 3, "red");
        myLayout.setTile(newTileLayout);
        assertEquals(newTileLayout, myLayout.getTile());
        String myString= myLayout.toString();
    }

}