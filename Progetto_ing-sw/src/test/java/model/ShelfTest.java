package model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import junit.framework.TestCase;
import model.COLOR;
import model.Tile;

import java.io.IOException;
import model.COLOR;
import model.Tile;
import model.Shelf;
import model.Player;
import model.command.Command;
import model.command.ConcreteCommand1;
//matrix[6][5]

public class ShelfTest extends TestCase{
    Tile[][] matrix=new Tile[6][5];
    Player myPlayer= new Player(1, "paolo");
    Shelf test1= new Shelf(matrix, myPlayer);
    int[][] previous=new int[6][5];
    @Test
    public void testChecker() throws IOException {
        //Tile a05=new Tile(COLOR.GREEN, 1);
        //Tile a15=new Tile(COLOR.VIOLET, 1);
        //Tile a25=new Tile(COLOR.BLUE, 1);
        //gli passo tre tiles verdi nella colonna 0, mi aspetto 4 tiles verdi uguali->3 punti
        previous[5][0]=1;
        previous[5][1]=5;
        previous[5][2]=6;
        Tile[] scelte= new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        Assert.assertEquals(2, test1.addTiles(scelte, 0));
        /*scelte=new Tile[4];
        for(int i=0; i<4; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        Assert.assertEquals(3, test1.addTiles(scelte, 0));*/
    }
    @Test
    public void testChecker2() throws IOException{
        Tile[] prima=new Tile[1];
        prima[0]=new Tile(COLOR.GREEN, 1);
        Assert.assertEquals(0, test1.addTiles(prima, 0));
        /*Tile[] scelte=new Tile[4];
        for(int i=0; i<4; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        Assert.assertEquals(3, test1.addTiles(scelte, 0));*/
    }
    @Test
    public void testChecker3() throws IOException{
        Tile[] prima=new Tile[1];
        prima[0]=new Tile(COLOR.GREEN, 1);
        test1.addTiles(prima, 0);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        Assert.assertEquals(3, test1.addTiles(scelte, 0));
    }
    @Test
    public void testChecker4() throws IOException{
        //caso di test misto
        Tile[] prima= new Tile[1];
        prima[0]=new Tile(COLOR.GREEN, 1);
        test1.addTiles(prima, 0);
        test1.addTiles(prima, 1);
        test1.addTiles(prima, 2);
        test1.addTiles(prima, 2);
        test1.addTiles(prima, 1);
        //assertEquals(8, test1.addTiles(prima, 0));



    }
    @Test
    public void testChecker5() throws IOException{
        Tile[] scelte=new Tile[3];
        for(int i=0; i<scelte.length; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        assertEquals(2, test1.addTiles(scelte, 4));
    }
    @Test
    public void testChecker6()throws IOException{
        Tile[] prima=new Tile[1];
        prima[0]=new Tile(COLOR.GREEN,1);
        assertEquals(0, test1.addTiles(prima, 4));
    }
    @Test
    public void testChecker7()throws IOException{
        Tile[] prima=new Tile[1];
        prima[0]=new Tile(COLOR.GREEN,1);
        test1.addTiles(prima,4);
        Tile[] scelte= new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN,1);
        }
        assertEquals(3, test1.addTiles(scelte, 4));
    }
    @Test
    public void testChecker8() throws IOException{
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        assertEquals(2, test1.addTiles(scelte, 3));
    }

    @Test
    public void testChecker9() throws IOException{
        Tile[] prima=new Tile[1];
        prima[0]=new Tile(COLOR.GREEN,1);
        test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN,1);
        }
        assertEquals(3, test1.addTiles(scelte, 3));
    }

    @Test
    public void testChecker10() throws IOException{
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN,1);
        }
        test1.addTiles(scelte,1);
        test1.addTiles(scelte, 2);
        assertEquals(8, test1.addTiles(scelte, 3));
    }

    @Test
    public void testChecker11() throws IOException{
        Tile[] scelte= new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN,1);
        }
        test1.addTiles(scelte,2);
        test1.addTiles(scelte,3);
        assertEquals(8, test1.addTiles(scelte,4));
    }

    @Test
    public void testChecker12() throws IOException{
        Tile[] scelte= new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN,1);
        }
        test1.addTiles(scelte,2);
        test1.addTiles(scelte,4);
        assertEquals(8, test1.addTiles(scelte,3));
    }



}