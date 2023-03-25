package source.model.cgoal;


//TODO

// 6 pairs equal
//id=1
 public class SixPairsEqualCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int[][] matrixSupport= new int[8][7];
        for(int r=1;r<7;r++ ){
            for(int c=1; c<6; c++){
                matrixSupport[r][c]= matrix[r-1][c-1];
            }
        }
        int flag = 0;

        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 6; j++) {

                if (j<5) {
                    if (matrixSupport[i][j] != 0 && matrixSupport[i][j] == matrixSupport[i][j + 1] && matrixSupport[i][j] != matrixSupport[i - 1][j] && matrixSupport[i][j] != matrixSupport[i - 1][j + 1]
                            && matrixSupport[i][j] != matrixSupport[i][j - 1] && matrixSupport[i][j] != matrixSupport[i][j + 2] && matrixSupport[i][j] != matrixSupport[i + 1][j] && matrixSupport[i][j] != matrixSupport[i + 1][j + 1]) {
                        flag++;
                    }
                }


               if (i < 6) {
                   if (matrixSupport[i][j] != 0 && matrixSupport[i][j] == matrixSupport[i + 1][j] && matrixSupport[i][j] != matrixSupport[i - 1][j] && matrixSupport[i][j] != matrixSupport[i][j + 1]
                           && matrixSupport[i][j] != matrixSupport[i][j - 1] && matrixSupport[i][j] != matrixSupport[i + 1][j + 1] && matrixSupport[i][j] != matrixSupport[i + 1][j - 1] && matrixSupport[i][j] != matrixSupport[i + 2][j]) {
                       flag++;

                   }
               }

                }
            }

        if (flag > 5) {
            return 1;
        }
        return 0;
    }
}

