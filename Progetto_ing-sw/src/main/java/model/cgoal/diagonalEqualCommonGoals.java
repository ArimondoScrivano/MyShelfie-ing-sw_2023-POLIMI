package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;


//DONE
// diagonal equals tiles
//id=2
 public class diagonalEqualCommonGoals implements CommonGoals {
    @Override
    public int Checker(Tile[][] matrix) {
        int flag = 0;
        // Diagonal left to right (1)
        if (matrix[4][0].getColor().equals(matrix[3][1].getColor())
                && matrix[4][0].getColor().equals(matrix[2][2].getColor())
                && matrix[4][0].getColor().equals(matrix[1][3].getColor())
                && matrix[4][0].getColor().equals(matrix[0][4].getColor())
                && !matrix[4][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }
        //Diagonal right to left (1)
        if (matrix[4][4].getColor().equals(matrix[3][3].getColor())
                && matrix[4][4].getColor().equals(matrix[2][2].getColor())
                && matrix[4][4].getColor().equals(matrix[1][1].getColor())
                && matrix[4][4].getColor().equals(matrix[0][0].getColor())
                && !matrix[4][4].getColor().equals(COLOR.BLANK))
        {
            flag = 1;
        }
        //Diagonal right to left (2)
        if (matrix[5][4].getColor().equals(matrix[4][3].getColor())
                && matrix[5][4].getColor().equals(matrix[3][2].getColor())
                && matrix[5][4].getColor().equals(matrix[2][1].getColor())
                && matrix[5][4].getColor().equals(matrix[1][0].getColor())
                && !matrix[5][4].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }

        // Diagonal left to right (2)
        if (matrix[5][0].getColor().equals(matrix[4][1].getColor())
                && matrix[5][0].getColor().equals(matrix[3][2].getColor())
                && matrix[5][0].getColor().equals(matrix[2][1].getColor())
                && matrix[5][0].getColor().equals(matrix[1][0].getColor())
                && !matrix[5][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }
        if (flag == 1) {
            return 1;
        }else{
            return 0;
        }


    }

}
