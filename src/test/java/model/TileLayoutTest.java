package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileLayoutTest extends TestCase {

    @Test
    //TESTING METHOD getX(), getY(), getColor(), setX(), setY(), setColor()
    public void testCheckerGetX(){
        TileLayout myTileLayout= new TileLayout(1, 2, "blue");
        assertEquals(1, myTileLayout.getX());
        assertEquals(2, myTileLayout.getY());
        assertEquals("blue", myTileLayout.getColor());
        myTileLayout.setX(3);
        assertEquals(3, myTileLayout.getX());
        myTileLayout.setY(5);
        assertEquals(5, myTileLayout.getY());
        myTileLayout.setColor("red");
        assertEquals("red", myTileLayout.getColor());
        String myString=myTileLayout.toString();
        COLOR myColor= myTileLayout.convert();
        myTileLayout.setColor("Yellow");
        myColor= myTileLayout.convert();
        myTileLayout.setColor("Violet");
        myColor= myTileLayout.convert();
        myTileLayout.setColor("Blue");
        myColor= myTileLayout.convert();
        myTileLayout.setColor("Lightblue");
        myColor= myTileLayout.convert();
        myTileLayout.setColor("Green");
        myColor= myTileLayout.convert();
    }

}