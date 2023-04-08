package model;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest extends TestCase{
        @Test
        //TESTING METHOD getName()
        public void testGetter1(){
            Player myPlayer= new Player(1, "maurizio");
            String myPlayerName= new String();
            myPlayerName= myPlayer.getName();
            System.out.println(myPlayerName);
        }
        @Test
        //TESTING METHOD getShelf()
        public void testGetter2(){
            Player myPlayer= new Player(1, "maurizio");
            Tile[][] tiles=new Tile[6][5];
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    tiles[i][j]=new Tile(COLOR.VIOLET, 1);
                }
            }
            Shelf myShelf=new Shelf(tiles, myPlayer);
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    System.out.println(myShelf.tilesShelf[i][j].getColor());
                }
            }
            myShelf=myPlayer.getShelf();
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    System.out.println(myShelf.tilesShelf[i][j].getColor());
                }
            }

        }
        @Test
        //TESTING METHOD getPersonalGoal()
        public void testGetter3(){
            Player myPlayer= new Player(1, "maurizio");
            PersonalGoal personalGoal;
            personalGoal=myPlayer.getPersonalGoal();
            System.out.println(personalGoal.getId());

        }

        @Test
        //TESTING METHOD getPoints()
        public void testGetter4(){
            Player myPlayer= new Player(1, "maurizio");
            int myPoints;
            myPoints=myPlayer.getPoints();
            System.out.println(myPoints);
        }

        @Test
        //TESTING METHOD setPointsEndGame
        public void testSetter(){
            Player myPlayer= new Player(1, "maurizio");
            myPlayer.setPointsEndGame();
            System.out.println(myPlayer.getPoints());
        }

        @Test
        //TESTING METHOD getCommonGoalsCompelted
        public void testGetter5(){
            Player myPlayer= new Player(1, "maurizio");
            boolean[] completed= new boolean[2];
            completed= myPlayer.getCommonGoalsCompleted();
            System.out.println(completed[0]+" "+completed[1]);
        }
        @Test
        public void testShelfCompleted(){
            Player myPlayer= new Player(1, "maurizio");
            boolean completed;
            completed=myPlayer.isShelfCompleted();
            System.out.println(completed);
        }

        @Test
        //TESTING METHOD setShelfCompleted()
        public void testSetter2(){
            Player myPlayer= new Player(1, "maurizio");
            myPlayer.setShelfCompleted();
            System.out.println(myPlayer.isShelfCompleted());
        }

        @Test
        //TEST METHOD checkPersonalGoal()
        public void testCheckPersonalGoal(){
            Player myPlayer= new Player(1, "maurizio");
            Tile[] chosen=new Tile[3];
            chosen[0]= new Tile(COLOR.GREEN,1);
            chosen[1]= new Tile(COLOR.VIOLET,1);
            chosen[2]= new Tile(COLOR.BLUE,1);
            myPlayer.myShelf.addTiles(chosen, 0);
        }
}