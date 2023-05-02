package model;

import model.cgoal.CommonGoals;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Serializable {
    //INTEGER TO IDENTIFY THE PLAYER
    private final int id;
    //PLAYER'S NICKNAME
    private final String name;
    //PLAYER'S SHELF
    protected Shelf myShelf;
    //THE PERSONAL GOAL IS CONNECTED TO THE PLAYER INSTANCE
    PersonalGoal myPersonalGoal;
    //NUMBER OF POINTS OF EACH PLAYER
    protected int points;
    //BOOLEAN WHICH IS TRUE WHETHER THE PLAYER'S SHELF IS FULL
    private boolean shelfCompleted;
    private boolean[] commonGoalsCompleted;
    private boolean lastRound;

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    //INSTANCE CONSTRUCTOR FOR PLAYER CLASS
    public Player(int id, String name){
        lastRound=false;
        Random rand= new Random();
        int personalGoalId= rand.nextInt(1, 12);
        this.id=id;
        this.name=name;

        this.myShelf=new Shelf( this);
        this.myPersonalGoal=new PersonalGoal(personalGoalId);
        this.points=0;
        this.shelfCompleted=false;
        this.commonGoalsCompleted = new boolean[2];
    }

    //METHOD TO GET A PLAYER'S NAME
    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    //METHOD TO GET THE SITUATION OF A PLAYER'S SHELF
    public Shelf getShelf(){
        return this.myShelf;
    }

    //METHOD TO GET THE PERSONAL GOAL ASSOCIATED WITH A PLAYER
    public PersonalGoal getPersonalGoal(){
        return this.myPersonalGoal;
    }
    public int getPoints(){return this.points;}

    public void setPointsEndGame(){
        this.points++;
    }



    //salvare oldChain nella riga dell'adiacenza.
    public void convertPoints(Tile[][] matrix) {
        this.points = 0;
        int count = 0;
        // we have to use a support matrix to avoid corner cases and so we can modify the elements
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
    }

    // set the new score thanks to the Common Goal
public void setPoints(int points){
        this.points= this.points + points;
}

    public void setCommonGoalsCompleted(int indexCompleted) {
        this.commonGoalsCompleted[indexCompleted]= true;
    }

    public boolean[] getCommonGoalsCompleted() {
        return this.commonGoalsCompleted;
    }

    public boolean isShelfCompleted() {
        return shelfCompleted;
    }

    public void setShelfCompleted(){
        this.shelfCompleted = true;
    }


    public void checkPersonalGoal(int freeFirstSpot, int column) throws IOException {
        Tile[][] layout= myPersonalGoal.getLayout();
        Tile[][] myLayout= new Tile[6][5];
        myLayout= getShelf().getTilesShelf();

        if (layout[freeFirstSpot][column].getColor().equals(myLayout[freeFirstSpot][column].getColor())){
            setPoints((myPersonalGoal.getPoints()));
        }
    }

    public Tile[][] getShelfMatrix(){
        return this.myShelf.getTilesShelf();
    }

}
