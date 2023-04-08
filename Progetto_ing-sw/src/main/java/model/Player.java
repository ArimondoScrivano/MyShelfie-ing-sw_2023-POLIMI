package model;

import model.cgoal.CommonGoals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    //INTEGER TO IDENTIFY THE PLAYER
    private int id;
    //PLAYER'S NICKNAME
    private String name;
    //PLAYER'S SHELF
    protected Shelf myShelf;
    //THE PERSONAL GOAL IS CONNECTED TO THE PLAYER INSTANCE
    PersonalGoal myPersonalGoal;
    //NUMBER OF POINTS OF EACH PLAYER
    protected int points;
    //BOOLEAN WHICH IS TRUE WHETHER THE PLAYER'S SHELF IS FULL
    private boolean shelfCompleted;
    private boolean[] commonGoalsCompleted;
    Tile[][] tiles=new Tile[6][5];

    //INSTANCE CONSTRUCTOR FOR PLAYER CLASS
    public Player(int id, String name){
        Random rand= new Random();
        int personalGoalId= rand.nextInt(1, 12);
        this.id=id;
        this.name=name;
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                tiles[i][j]=new Tile(COLOR.BLANK, 1);
            }
        }
        this.myShelf=new Shelf(tiles, this);
        this.myPersonalGoal=new PersonalGoal(personalGoalId);
        this.points=0;
        this.shelfCompleted=false;
        this.commonGoalsCompleted = new boolean[2];
        commonGoalsCompleted[0]=false;
        commonGoalsCompleted[1]=false;
    }

    //METHOD TO GET A PLAYER'S NAME
    public String getName(){
        return this.name;
    }

    //METHOD TO GET THE SITUATION OF A PLAYER'S SHELF
    public Shelf getShelf(){
        return this.myShelf;
    }

    //METHOD TO GET THE PERSONAL GOAL ASSOCIATED WITH A PLAYER
    public PersonalGoal getPersonalGoal(){
        return this.myPersonalGoal;
    }
    public int getPoints(){return points;}

    public void setPointsEndGame(){
        this.points++;
    }

    //RETURNS TRUE IF THE PLAYER'S SHELF CONTAINS ONE OF THE COMMON GOALS' PATTERNS
    public boolean commonGoalCompleted(List<CommonGoals> commonGoals, int id){
        if(commonGoals.get(id).Checker(myShelf.tilesShelf)==1){
            commonGoalsCompleted[id]=true;
            return true;
        }
        return false;
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
        System.out.println("points "+this.points);
    }


    public boolean[] getCommonGoalsCompleted() {
        return commonGoalsCompleted;
    }

    public boolean isShelfCompleted() {
        return shelfCompleted;
    }

    public void setShelfCompleted(){
        this.shelfCompleted = true;
    }
    public int checkPersonalGoal(int freeFirstSpot, int column) throws IOException {
        //Creare indice come attributo della classe che identifica il punteggio del personal goal
        Tile[][] layout= new Tile[5][6];
        layout=myPersonalGoal.getLayout();
        Tile[][] myLayout= new Tile[6][5];
        for(int row=0; row<6; row++){
            for(int col=0; col<5; col++){
                myLayout[row][col]=myShelf.tilesShelf[row][col];
            }
        }
        int i=freeFirstSpot;
        int additionalPoints=0;
        while(!(myLayout[i][column].getColor().equals(COLOR.BLANK))&&i>=0){
            if(myLayout[i][column].getColor().equals(layout[i][column].getColor())){
                additionalPoints+=myPersonalGoal.getAdditionalPoints();
                i--;
            }else{i--;}
        }
        points+=additionalPoints;
        return 0;
    }
}
