package model;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest extends TestCase{
        @Test
        //TESTING METHOD getName()
        public void testGetter1(){
            Player myPlayer= new Player(1, "maurizio");
            String myPlayerName= new String();
            myPlayerName= myPlayer.getName();
            System.out.println(myPlayerName);
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
        //TEST METHOD checkPersonalGoal()
        public void testCheckPersonalGoal(){
            Player myPlayer= new Player(1, "maurizio");
            Tile[] chosen=new Tile[3];
            chosen[0]= new Tile(COLOR.GREEN,1);
            chosen[1]= new Tile(COLOR.VIOLET,1);
            chosen[2]= new Tile(COLOR.BLUE,1);
            myPlayer.myShelf.addTiles(chosen, 0);
        }

        @Test
        //TESTING METHOD setLastRound( boolean lastRound)
        public void testCheckerSetLastRound(){
            Player myPlayer=new Player(1, "Rita");
            boolean lastRound= false;
            myPlayer.setLastRound(lastRound);
            assertFalse(myPlayer.isLastRound());
            lastRound=false;
            myPlayer.setLastRound(lastRound);
            assertFalse(myPlayer.isLastRound());
        }

        @Test
        //TESTING METHOD setName(String name)
        public void testCheckerSetName(){
            Player myPlayer= new Player(1, "Rita");
            myPlayer.setName("Arimondo");
            assertEquals(myPlayer.getName(), "Arimondo");
        }

        @Test
        //TESTING METHOD getId()
        public void testCheckerGetId(){
            Player myPlayer=new Player(1, "Rita");
            assertEquals(myPlayer.getId(), 1);
        }

        @Test
        //TESTING METHOD getPersonalGoal()
        public void testCheckerGetPersonalGoal(){
            Player myPlayer= new Player(1, "Rita");
            PersonalGoal myPersonalGoal;
            myPersonalGoal=myPlayer.getPersonalGoal();
            assertEquals(myPersonalGoal, myPlayer.getPersonalGoal());
        }

        @Test
        //TESTING METHOD getPGPoints()
        public void testCheckerGetPGPoints(){
            Player myPlayer=new Player(1, "Rita");
            assertEquals(myPlayer.getPGpoints(), 0);
        }

        @Test
        //TESTING METHOD setPoints(int points)
        public void testCheckerSetPoints(){
            Player myPlayer=new Player(1, "Rita");
            myPlayer.setPoints(10);
            assertEquals(myPlayer.getPoints(), 10);
        }

        @Test
        //TESTING METHOD setCommonGoalsCompleted(int indexCompleted)
        public void testCheckerSetCommonGoalsCompleted(){
            Player myPlayer= new Player(1, "Rita");
            myPlayer.setCommonGoalsCompleted(0);
            assertTrue(myPlayer.getCommonGoalsCompleted()[0]);
            myPlayer.setCommonGoalsCompleted(1);
            assertTrue(myPlayer.getCommonGoalsCompleted()[1]);
        }

        @Test
        //TESTING METHOD sumUpPoints()
        public void testCheckerSumUpPoints(){
            Player myPlayer=new Player(1, "Rita");
            myPlayer.setPoints(10);
            myPlayer.sumUpPoints();
            assertEquals(myPlayer.getPoints(), 10);
        }

        @Test
        //TESTING METHOD getShelfMatrix()
        public void testCheckerGetShelfMatrix(){
            Player myPlayer= new Player(1, "Rita");
            Tile[][] myTile;
            myTile= myPlayer.getShelfMatrix();
        }

        @Test
        //TESTING METHOD checkPersonalGoal()
        public void testCheckerCheckPersonalGoal(){
            Player myPlayer= new Player(1, "Rita");
            Shelf myShelf= new Shelf(myPlayer);
            Tile[][] myMatrix= new Tile[6][5];
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    myMatrix[i][j]=new Tile(COLOR.BLUE, 1);

                }
            }
            myShelf.tilesShelf=myMatrix;
            myPlayer.checkPersonalGoal();
        }

        @Test
        //TESTING METHOD getShelfCompleted()
        public void testCheckerGetShelfCompleted(){
            Player myPlayer= new Player(1,"Rita");
            assertFalse(myPlayer.getShelfCompleted());
        }
}