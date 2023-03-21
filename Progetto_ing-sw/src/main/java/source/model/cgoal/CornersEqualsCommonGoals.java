package source.model.cgoal;

//equals 4 corners
//id=3
 public class CornersEqualsCommonGoals implements CommonGoals {


    @Override
    public int Checker(int[][] matrix) {
        if (matrix[0][0] == matrix[0][4] && matrix[0][0] == matrix[5][0] && matrix[0][0] == matrix[5][4] && matrix[0][0]!=0)
        {
            return 1;
        }else{
            return 0;
        }
    }
}
