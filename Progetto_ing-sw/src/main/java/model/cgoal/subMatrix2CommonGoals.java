package model.cgoal;
import model.COLOR;
import model.Tile;

import java.util.ArrayList;
import java.util.List;


//equal sub-matrix 2x2
//id=7
 public class subMatrix2CommonGoals implements CommonGoals {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public subMatrix2CommonGoals (List<Integer> CommonGoalpoints){

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
        int flag = 0;
        int count= 0;

        // creating a support matrix so we can simplify corner cases
        Tile[][] matrixSupport= new Tile[8][7];
        for(int r= 0; r<8; r++) {
            for (int c = 0; c < 7; c++) {
                if (r > 0 && r < 7 && c > 0 && c < 6) {
                    matrixSupport[r][c] = new Tile(matrix[r - 1][c - 1].getColor(), 1);
                } else {
                    matrixSupport[r][c] = new Tile(COLOR.BLANK, 1);
                }
            }
        }

        // check for 2x2 with no adiacenses
        for (int i=1; i< 6; i++) {
            for (int j = 1; j < 5; j++) {


                if (!matrixSupport[i][j].getColor().equals(COLOR.BLANK)
                        //first check 2x2
                        && matrixSupport[i][j].getColor().equals(matrixSupport[i][j + 1].getColor())
                        && matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j].getColor())
                        && matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j + 1].getColor())) {
                    //check for no more than 2x2 (no same color adiacenses)

                    //general case
                    if (         //bottom layer
                            !matrixSupport[i][j].getColor().equals(matrixSupport[i - 1][j].getColor())
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i - 1][j + 1].getColor())
                                    //i-layer
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i][j + 2].getColor())
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i][j - 1].getColor())
                                    //i+1 layer
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j + 2].getColor())
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j - 1].getColor())
                                    //i+2 layer
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i + 2][j].getColor())
                                    && !matrixSupport[i][j].getColor().equals(matrixSupport[i + 2][j + 1].getColor())) {

                        count = count + 1;
                    }
                }
            }
        }
        if(count>1){
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
        }else {
            return 0;
        }

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

