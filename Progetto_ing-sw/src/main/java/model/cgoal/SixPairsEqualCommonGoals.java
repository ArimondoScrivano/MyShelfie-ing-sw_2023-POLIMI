package model.cgoal;
import model.COLOR;
import model.Tile;
import java.util.*;

// 6 pairs equal
//id=1
 public class SixPairsEqualCommonGoals implements CommonGoals {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public SixPairsEqualCommonGoals  (List<Integer> CommonGoalpoints){

        this.points= new ArrayList<>();
        this.points.addAll(CommonGoalpoints);
        indexCurrentPoint=this.points.size()-1;
        current_point= this.points.get(indexCurrentPoint);

    }

    @Override
    public int getCurrent_point() {
        return current_point;
    }

    @Override
    public int Checker(Tile[][] matrix) {
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

        for(int color=1; color< 7; color++){

            for(int row=1; row<7; row++){
                for(int col=1; col<6; col++){

                    //check if the tile is the right color
                    if(matrixSupport[row][col].getColor().compareTo(COLOR.BLANK)==color){
                        List<Tile> groupFound = new ArrayList<>();
                        matrixSupport[row][col]= new Tile(COLOR.BLANK, 1);
                        groupFound.add(matrixSupport[row][col]);

                        for(int index=0; index<groupFound.size(); index++ ){

                            //search the correct tile
                            for (int i = 1; i < 7; i++) {
                                for (int j = 1; j < 6; j++) {
                                    //found the correct tile
                                    if (matrixSupport[i][j].equals(groupFound.get(index))){

                                        //check the adjacency
                                        //upper-case
                                        if (matrixSupport[i+1][j].getColor().compareTo(COLOR.BLANK)==color) {
                                            matrixSupport[i + 1][j]= new Tile(COLOR.BLANK,1);
                                            groupFound.add(matrixSupport[i + 1][j]);

                                        }
                                        //lower-case
                                        if (matrixSupport[i-1][j].getColor().compareTo(COLOR.BLANK)==color) {
                                            matrixSupport[i - 1][j]= new Tile(COLOR.BLANK,1);
                                            groupFound.add(matrixSupport[i - 1][j]);

                                        }
                                        //right-case
                                        if (matrixSupport[i][j +1].getColor().compareTo(COLOR.BLANK)==color) {
                                            matrixSupport[i][j + 1]= new Tile(COLOR.BLANK,1);
                                            groupFound.add(matrixSupport[i ][j+1]);

                                        }
                                        //left-case
                                        if (matrixSupport[i][j - 1].getColor().compareTo(COLOR.BLANK)==color) {
                                            matrixSupport[i][j - 1]= new Tile(COLOR.BLANK,1);
                                            groupFound.add(matrixSupport[i][j-1]);

                                        }
                                    }
                                }
                            }
                        }
                        if (groupFound.size()>1){
                            count++;
                        }

                    }
                }

            }
        }

        if (count > 5) {
            if (indexCurrentPoint==-1){
                return 0;
            }else{
                int returnValue=current_point;
                indexCurrentPoint= indexCurrentPoint-1;
                if (indexCurrentPoint==-1){
                    current_point=0;
                }else{
                    current_point= points.get(indexCurrentPoint);
                }
                return returnValue;
            }
        }
        return 0;


    }

    @Override
    public void printLayout() {
        System.out.println("------------------\n" +
                "|                 |\n" +
                "|                 |\n" +
                "|                 |\n" +
                "|                 |\n" +
                "|                 |\n" +
                "|                 |\n" +
                "------------------\n");
    }
}
