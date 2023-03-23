package source.model.cgoal;
import source.model.*;


//DONE
// diagonal equals tiles
//id=2
 public class diagonalEqualCommonGoals implements CommonGoals {
    @Override
    public int Checker(Tile[][] matrix) {
        int flag = 0;
        if (matrix[4][0].getColor().equals(matrix[3][1].getColor()) && matrix[4][0].getColor().equals(matrix[2][2].getColor())
                && matrix[4][0].getColor().equals(matrix[1][3].getColor()) && matrix[4][0].getColor().equals(matrix[0][4].getColor())
                && !matrix[4][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }
        if (matrix[4][4].getColor().equals(matrix[3][3].getColor()) && matrix[4][4].getColor().equals(matrix[2][2].getColor())
                && matrix[4][4].getColor().equals(matrix[1][1].getColor())&& matrix[4][4].getColor().equals(matrix[0][0].getColor())
                && !matrix[4][4].getColor().equals(COLOR.BLANK))
        {
            flag = 1;
        }

        if (flag == 1) {
            return 1;
        }else{
            return 0;
        }


    }

}
