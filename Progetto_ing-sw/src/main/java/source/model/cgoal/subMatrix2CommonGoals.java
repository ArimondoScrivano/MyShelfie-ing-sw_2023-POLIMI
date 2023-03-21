package source.model.cgoal;

//equalsubmatrix2*2
//id=7
//da rivedere
class subMatrix2CommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int flag = 0;
        int[][] matrixSupport= new int[8][7];
        for(int r=1;r<7;r++ ){
            for(int c=1; c<6; c++){
                matrixSupport[r][c]= matrix[r-1][c-1];
            }
        }

        for (int w = 1; w < 7; w++) {
            flag = 0;

            for (int i = 1; i < 6; i++) {
                for (int j = 1; j <5 ; j++) {
                    if (matrixSupport[i][j] == w && matrixSupport[i][j] == matrixSupport[i][j + 1] && matrixSupport[i][j] == matrixSupport[i + 1][j] && matrixSupport[i][j] == matrixSupport[i + 1][j + 1]
                        && matrixSupport[i][j]!= matrixSupport[i-1][j] && matrixSupport[i][j]!= matrixSupport[i-1][j+1] && matrixSupport[i][j]!= matrixSupport[i][j+2] && matrixSupport[i][j]!= matrixSupport[i+1][j+2]
                            && matrixSupport[i][j]!= matrixSupport[i+2][j] && matrixSupport[i][j]!= matrixSupport[i+2][j+1] && matrixSupport[i][j]!= matrixSupport[i][j-1] && matrixSupport[i][j]!= matrixSupport[i+1][j-1]
                    ) {
                        flag += 1;
                    }
                }
            }
            if (flag > 1) {
                return 1;
            }
        }

        return 0;
    }
}

