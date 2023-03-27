package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;

//DONE
// 5 Columns different highs
//id=12
 public class fiveColumnsCommonGoals implements CommonGoals {

    @Override
    public int Checker(Tile[][] matrix) {
        int flag = 0;

        if (!matrix[4][0].getColor().equals(COLOR.BLANK)
                && !matrix[3][1].getColor().equals(COLOR.BLANK)
                && !matrix[2][2].getColor().equals(COLOR.BLANK)
                && !matrix[1][3].getColor().equals(COLOR.BLANK)
                && !matrix[0][4].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }
        if (!matrix[4][4].getColor().equals(COLOR.BLANK)
                && !matrix[3][3].getColor().equals(COLOR.BLANK)
                && !matrix[2][2].getColor().equals(COLOR.BLANK)
                && !matrix[1][1].getColor().equals(COLOR.BLANK)
                && !matrix[0][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }

        if (flag == 1) {
            return 1;
        }else{
            return 0;
        }


    }
}
