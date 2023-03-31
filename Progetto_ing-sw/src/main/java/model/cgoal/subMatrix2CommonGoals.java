package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;


//DONE

//equalsubmatrix2*2
//id=7
 public class subMatrix2CommonGoals implements CommonGoals {

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
            return 1;
        }else {
            return 0;
        }

    }
}

