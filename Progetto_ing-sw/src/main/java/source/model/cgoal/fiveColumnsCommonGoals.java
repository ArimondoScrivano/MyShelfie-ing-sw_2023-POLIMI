package source.model.cgoal;

// 5 Columns different highs
//id=12
class fiveColumnsCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int flag = 0;
        if (matrix[4][0] != 0 && matrix[3][1] != 0 && matrix[2][2] != 0 && matrix[1][3] != 0 && matrix[0][4] != 0) {
            flag = 1;
        }
        if (matrix[4][4] != 0 && matrix[3][3] != 0 && matrix[2][2] != 0 && matrix[1][1] != 0 && matrix[0][0] != 0) {
            flag = 1;
        }

        if (flag == 1) {
            return 1;
        }else{
            return 0;
        }


    }
}
