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

public class ShelfTest extends TestCase{
@Test
//inserimento 3 tiles in una colonna vuota
    public void testAddTiles1(){
    Player myPlayer=new Player(1, "paolo");
    Tile[][] matrix= new Tile[6][5];
    for(int i=0; i<6; i++){
        for(int j=0; j<5; j++){
            matrix[i][j]=new Tile(COLOR.BLANK, 1);
        }
    }
    Shelf test1=new Shelf(matrix, myPlayer);
    Tile[] scelte=new Tile[3];
    for(int i=0; i<3; i++){
        scelte[i]=new Tile(COLOR.GREEN, 1);
    }
    test1.addTiles(scelte, 0);

}
    @Test
    //inserimento 3 tiles in una colonna con 1 tile dello stesso colore.
    public void testAddTiles2(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        prima[0]=new Tile(COLOR.GREEN,1);
        test1.addTiles(prima, 0);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 0);

    }
    @Test
    public void testAddTiles3(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        prima[0]=new Tile(COLOR.GREEN,1);
        test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 3);

    }
    @Test
    public void testAddTiles4(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        prima[0]=new Tile(COLOR.GREEN,1);
        test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 3);

    }
    @Test
    public void testAddTiles5(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        prima[0]=new Tile(COLOR.VIOLET,1);
        test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 3);

    }

    @Test
    public void testAddTiles6(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        prima[0]=new Tile(COLOR.VIOLET,1);
        test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 3);
        prima[0]=new Tile(COLOR.VIOLET,1);
        test1.addTiles(prima, 2);
        test1.addTiles(prima, 4);
    }
    @Test
    public void testAddTiles7(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        //prima[0]=new Tile(COLOR.VIOLET,1);
        //test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 1);
        //prima[0]=new Tile(COLOR.VIOLET,1);
        //test1.addTiles(prima, 2);
        //test1.addTiles(prima, 4);
        test1.addTiles(scelte, 2);
        test1.addTiles(scelte, 3);
    }
    @Test
    public void testAddTiles8(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        //prima[0]=new Tile(COLOR.VIOLET,1);
        //test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[6];
        for(int i=0; i<6; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 1);
        //prima[0]=new Tile(COLOR.VIOLET,1);
        //test1.addTiles(prima, 2);
        //test1.addTiles(prima, 4);
        //test1.addTiles(scelte, 2);
        //test1.addTiles(scelte, 3);
    }
    @Test
    public void testAddTiles9(){
        Player myPlayer=new Player(1, "paolo");
        Tile[][] matrix= new Tile[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                matrix[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        Tile[] prima= new Tile[1];
        Shelf test1=new Shelf(matrix, myPlayer);
        //prima[0]=new Tile(COLOR.VIOLET,1);
        //test1.addTiles(prima, 3);
        Tile[] scelte=new Tile[3];
        for(int i=0; i<3; i++){
            scelte[i]=new Tile(COLOR.GREEN, 1);
        }
        test1.addTiles(scelte, 1);
        //prima[0]=new Tile(COLOR.VIOLET,1);
        //test1.addTiles(prima, 2);
        //test1.addTiles(prima, 4);
        test1.addTiles(scelte, 2);
        test1.addTiles(scelte, 3);
        test1.addTiles(scelte,3);
        test1.addTiles(scelte, 3);
    }
}