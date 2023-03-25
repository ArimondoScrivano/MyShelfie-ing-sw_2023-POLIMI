package source.model.cgoal;

//equal "x"
//id=10
//TODO
 public class equalXCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int flag=0;

        for (int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                if (matrix[i][j] == matrix[i][j+2] && matrix[i][j] == matrix[i+2][j] && matrix[i][j] == matrix[i+1][j+1] && matrix[i][j] == matrix[i+2][j+2]) {
                    flag=1;
                }
            }
        }

        if (flag> 0){
            return 1;
        }
        return 0;
    }
}
