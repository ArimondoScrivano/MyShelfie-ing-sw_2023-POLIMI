package source.model.cgoal;

// 4 vertical tiles equals four times
//id=5
 public class FourVerticalCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int flag=0;
        for(int i=0; i<3; i++){
            for (int j=0; j<5; j++){
                if (matrix[i][j]!= 0 && matrix[i][j] == matrix[i+1][j] && matrix[i][j] == matrix[i+2][j] && matrix[i][j] == matrix[i+3][j]){
                    flag++;
                }


            }
        }
        if (flag> 3) {
            return 1;
        }else{
            return 0;
        }


    }
}


