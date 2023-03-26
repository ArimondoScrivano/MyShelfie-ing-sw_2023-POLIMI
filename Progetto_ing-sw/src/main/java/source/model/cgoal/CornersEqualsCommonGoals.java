package source.model.cgoal;
import source.model.*;



//DONE
//equals 4 corners
//id=3
 public class CornersEqualsCommonGoals implements CommonGoals {


    @Override
    public int Checker(Tile[][] matrix) {
        if (matrix[0][0].getColor().equals(matrix[0][4].getColor())
                && matrix[0][0].getColor().equals(matrix[5][0].getColor())
                && matrix[0][0].getColor().equals(matrix[5][4].getColor())
                && !matrix[0][0].getColor().equals(COLOR.BLANK))
        {

            return 1;
        }else{
            return 0;
        }
    }}
