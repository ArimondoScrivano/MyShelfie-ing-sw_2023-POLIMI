package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Serializable {
    //INTEGER TO IDENTIFY THE PLAYER
    private final int id;
    //PLAYER'S NICKNAME
    private  String name;
    //PLAYER'S SHELF
    protected Shelf myShelf;
    //THE PERSONAL GOAL IS CONNECTED TO THE PLAYER INSTANCE
     private final PersonalGoal myPersonalGoal;
    //NUMBER OF POINTS OF EACH PLAYER
    private int PGpoints;

    protected  int CGPoints;
    protected int points;
    //BOOLEAN WHICH IS TRUE WHETHER THE PLAYER'S SHELF IS FULL
    private boolean shelfCompleted;
    private boolean[] commonGoalsCompleted;
    private boolean lastRound;

    /**
     * Sets the flag indicating whether it is the last round of a game.
     *
     * @param lastRound true if it is the last round, false otherwise.
     */
    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    /**
     * Sets the name of an object.
     *
     * @param name the new name to be set.
     */
    public void setName(String name){
        this.name= name;
    }


    /**
     * Checks whether it is the last round of a game.
     *
     * @return true if it is the last round, false otherwise.
     */
    public boolean isLastRound() {
        return lastRound;
    }


    //INSTANCE CONSTRUCTOR FOR PLAYER CLASS
    /**
     * Constructs a new Player object with the provided ID and name.
     *
     * @param id   the unique identifier of the player.
     * @param name the name of the player.
     */
    public Player(int id, String name){
        lastRound=false;
        Random rand= new Random();
        int personalGoalId= rand.nextInt(1, 12);
        this.id=id;
        this.name=name;
        this.myShelf=new Shelf( this);
        this.myPersonalGoal=new PersonalGoal(personalGoalId);
        this.PGpoints=0;
        this.points=0;
        this.shelfCompleted=false;
        this.commonGoalsCompleted = new boolean[2];
    }




    //METHOD TO GET A PLAYER'S NAME
    /**
     * Retrieves the name of the player.
     *
     * @return the name of the player.
     */
    public String getName(){
        return this.name;
    }




    /**
     * Retrieves the ID of the player.
     *
     * @return the ID of the player.
     */
    public int getId(){
        return this.id;
    }




    //METHOD TO GET THE SITUATION OF A PLAYER'S SHELF
    /**
     * Retrieves the shelf of the player.
     *
     * @return the shelf of the player.
     */
    public Shelf getShelf(){
        return this.myShelf;
    }





    //METHOD TO GET THE PERSONAL GOAL ASSOCIATED WITH A PLAYER
    /**
     * Retrieves the personal goal of the player.
     *
     * @return the personal goal of the player.
     */
    public PersonalGoal getPersonalGoal(){
        return this.myPersonalGoal;
    }





    /**
     * Retrieves the points earned by the player.
     *
     * @return the points earned by the player.
     */
    public int getPoints(){return this.points;}




    /**
     * Retrieves the personal goal points earned by the player.
     *
     * @return the personal goal points earned by the player.
     */
    public int getPGpoints(){return this.PGpoints;}





    /**
     * Increases the points earned by the player at the end of the game.
     * This method is called to increment the player's points by one.
     */
    public void setPointsEndGame(){
        this.points++;
    }




    /**
     * Converts the points for the player based on the given matrix of tiles.
     * The method calculates the points by finding groups of tiles of the same color and assigning points based on the group size.
     * The calculated points are added to the player's existing points.
     *
     * @param matrix the matrix of tiles representing the game board.
     */
    public void convertPoints(Tile[][] matrix) {
        this.points = 0;
        //We use a support matrix to avoid corner cases, so we can modify the elements
        Tile[][] matrixSupport = new Tile[8][7];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 7; c++) {

                if (r > 0 && r < 7 && c > 0 && c < 6) {
                    matrixSupport[r][c] = new Tile(matrix[r - 1][c - 1].getColor(), 1);
                } else {
                    matrixSupport[r][c] = new Tile(COLOR.BLANK, 1);
                }
            }
        }

        for (int color = 1; color < 7; color++) {

            for (int row = 1; row < 7; row++) {
                for (int col = 1; col < 6; col++) {

                    //check if the tile is the right color
                    if (matrixSupport[row][col].getColor().compareTo(COLOR.BLANK) == color) {
                        List<Tile> groupFound = new ArrayList<>();
                        matrixSupport[row][col] = new Tile(COLOR.BLANK, 1);
                        groupFound.add(matrixSupport[row][col]);

                        for (int index = 0; index < groupFound.size(); index++) {

                            //search the correct tile
                            for (int i = 1; i < 7; i++) {
                                for (int j = 1; j < 6; j++) {
                                    //found the correct tile
                                    if (matrixSupport[i][j].equals(groupFound.get(index))) {

                                        //check the adjacency
                                        //upper-case
                                        if (matrixSupport[i + 1][j].getColor().compareTo(COLOR.BLANK) == color) {
                                            matrixSupport[i + 1][j] = new Tile(COLOR.BLANK, 1);
                                            groupFound.add(matrixSupport[i + 1][j]);

                                        }
                                        //lower-case
                                        if (matrixSupport[i - 1][j].getColor().compareTo(COLOR.BLANK) == color) {
                                            matrixSupport[i - 1][j] = new Tile(COLOR.BLANK, 1);
                                            groupFound.add(matrixSupport[i - 1][j]);

                                        }
                                        //right-case
                                        if (matrixSupport[i][j + 1].getColor().compareTo(COLOR.BLANK) == color) {
                                            matrixSupport[i][j + 1] = new Tile(COLOR.BLANK, 1);
                                            groupFound.add(matrixSupport[i][j + 1]);

                                        }
                                        //left-case
                                        if (matrixSupport[i][j - 1].getColor().compareTo(COLOR.BLANK) == color) {
                                            matrixSupport[i][j - 1] = new Tile(COLOR.BLANK, 1);
                                            groupFound.add(matrixSupport[i][j - 1]);

                                        }
                                    }
                                }
                            }
                        }
                        if (groupFound.size() == 3) {
                            points = points + 2;
                        }
                        if (groupFound.size() == 4) {
                            points = points + 3;
                        }
                        if (groupFound.size() == 5) {
                            points = points + 5;
                        }
                        if (groupFound.size() >= 6) {
                            points = points + 8;
                        }
                    }
                }

            }
        }
        points+=CGPoints;
    }



    // set the new score thanks to the Common Goal
    /**
     * Sets the points earned by the player.
     *
     * @param points the points to be set and added to the player's existing points.
     */
    public void setPoints(int points){
        this.points= this.points + points;
}





    /**
     * Sets the common goal points earned by the player.
     *
     * @param points the common goal points to be set for the player.
     */
    public void setCGPoints(int points){ this.CGPoints= points;}





    /**
     * Sets the completion status of a common goal for the player.
     *
     * @param indexCompleted the index of the common goal to mark as completed.
     */
    public void setCommonGoalsCompleted(int indexCompleted) {
        this.commonGoalsCompleted[indexCompleted]= true;
    }



    /**
     * Retrieves the completion status of the common goals for the player.
     *
     * @return an array representing the completion status of the common goals.
     */
    public boolean[] getCommonGoalsCompleted() {
        return this.commonGoalsCompleted;
    }



    /**
     * Checks if the player's shelf is completed and returns the completion status.
     * If the shelf is completed, it updates the completion status accordingly.
     *
     * @return true if the player's shelf is completed, false otherwise.
     */
    public boolean isShelfCompleted() {
            setShelfCompleted(myShelf.completeShelf());
        return shelfCompleted;
    }



    /**
     * Sets the completion status of the player's shelf.
     *
     * @param stato the completion status of the player's shelf.
     */
    public void setShelfCompleted( boolean stato){
        this.shelfCompleted = stato;
    }



    /**
     * Retrieves the completion status of the player's shelf.
     *
     * @return true if the player's shelf is completed, false otherwise.
     */
    public boolean getShelfCompleted(){
        return this.shelfCompleted;
    }



    /**
     * Checks if the player's shelf satisfies their personal goal and updates the personal goal points accordingly.
     * The player's shelf layout is compared with the personal goal layout to count the matching tiles and determine the points.
     */
    public void checkPersonalGoal(){
        Tile[][] layout= myPersonalGoal.getLayout();
        Tile[][] myLayout=getShelf().getTilesShelf();
        int i=-1;
       for(int row=0; row<6; row++){
           for(int col=0; col<5; col++){
               if(!myLayout[row][col].getColor().equals(COLOR.BLANK)){
                   if(myLayout[row][col].getColor().equals(layout[row][col].getColor())){
                       i++;
                   }
               }

           }
       }
       if(i>-1){
           this.PGpoints= myPersonalGoal.getPoints(i);
       }

    }


    /**
     * Sum up the player's points by adding the personal goal points to the total points.
     */
    public void sumUpPoints(){
    this.points= points+ PGpoints;
}



    /**
     * Updates the player's total points by adding the personal goal points.
     * The personal goal points are added to the existing total points.
     */
    public Tile[][] getShelfMatrix(){
        return this.myShelf.getTilesShelf();
    }



    /**
     * Removes the common goal points from the player's total points.
     * The common goal points are subtracted from the existing total points.
     */
    public void removeCGPoints(){
        this.points-= this.CGPoints;
    }



    /**
     * Retrieves the common goal points earned by the player.
     *
     * @return the common goal points earned by the player.
     */
    public int getCGpoints(){
        return this.CGPoints;
    }


}
