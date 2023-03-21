package source.model;
//abstract class that has the strategy method
interface CommonGoals{

    // the result are 0 or 1: 0 no match, 1 match
    public int Checker(int[][] matrix);

}

// 6 pairs equal
//id=1
class SixPairsEqualCommonGoals implements CommonGoals {

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



// diagonal equals tiles
//id=2
class diagonalEqualCommonGoals implements CommonGoals {
    @Override
    public int Checker(int[][] matrix) {
        int flag = 0;
        if (matrix[4][0] == matrix[3][1] && matrix[4][0] == matrix[2][2] && matrix[4][0] == matrix[1][3] && matrix[4][0] == matrix[0][4] && matrix[4][0] !=0) {
            flag = 1;
        }
        if (matrix[4][4] == matrix[3][3] && matrix[4][4] == matrix[2][2] && matrix[4][4] == matrix[1][1] && matrix[4][4] == matrix[0][0] && matrix[4][4]!=0)
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


//equals 4 corners
//id=3
class CornersEqualsCommonGoals implements CommonGoals {


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




// 4 rows that have max 3 tiles egual
//id=4
class fourRowsCommonGoals implements CommonGoals {
    @Override
    public int Checker(int[][] matrix) {
        int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];
        for (int i = 0; i < 5; i++) {
            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;
            }
            for (int j = 0; j < 6; j++) {
                countEqualsTiles[matrix[i][j]]++;
            }
            for (int k = 1; k < 7; k++) {
                if (countEqualsTiles[k] > 1) {
                    countExc++;
                }
            }
            if (countExc < 4 && countEqualsTiles[0]==0) {
                count++;
            }

        }
        if (count > 3) {
            return 1;
        }else{
            return 0;
        }
    }
}


// 4 vertical tiles equals four times
//id=5
class FourVerticalCommonGoals implements CommonGoals {

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



//2 columns All different tiles
//id=6
class twoColumnsCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];
        for (int i = 0; i < 5; i++) {
            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;
            }
            countExc=0;
            for (int j = 0; j < 6; j++) {
                countEqualsTiles[matrix[j][i]]++;
            }
            for (int k = 1; k < 7; k++) {
                if (countEqualsTiles[k] > 1) {
                    countExc=1;
                }
            }
            if (countExc==0 && countEqualsTiles[0]==0) {
                count++;
            }

        }
        if (count > 1) {
            return 1;
        }else{
            return 0;
        }
    }
}



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


//2 rows of  All different tiles
//id=8
class twoRowsAllDifferentCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];
        for (int i = 0; i < 5; i++) {

            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;
            }

            countExc=0;
            for (int j = 0; j < 6; j++) {
                countEqualsTiles[matrix[i][j]]++;
            }
            for (int k = 1; k < 7; k++) {
                if (countEqualsTiles[k] > 1) {
                    countExc=1;
                }
            }
            if (countExc==0 && countEqualsTiles[0]==0) {
                count++;
            }

        }
        if (count > 1) {
            return 1;
        }else{
            return 0;
        }
    }

}




// 3 disegual columns
//id=9
class threeDisegualColumnsCommonGoals implements CommonGoals {

    @Override
    public int Checker(int[][] matrix) {
        int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];

        for (int i = 0; i < 5; i++) {

            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;

            }

            countExc = 0;
            for (int j = 0; j < 6; j++) {
                countEqualsTiles[matrix[j][i]]++;
            }

            for (int k = 1; k < 7; k++) {
                if (countEqualsTiles[k]  > 0) {
                    countExc+=1;

                }
            }


            if (countExc < 4 && countEqualsTiles[0]==0) {
                count+=1;
            }

        }
        if (count > 2) {
            return 1;
        }else{
            return 0;
        }
    }
}



//equal "x"
//id=10
// da rivedere
class equalXCommonGoals implements CommonGoals {

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



//8 equal tiles random order
//id=11
class eightEqualCommonGoals implements CommonGoals {


    @Override
    public int Checker(int[][] matrix) {
        int[] countEqualsTiles = new int[7];
        for (int w=0; w<7; w++) {
            countEqualsTiles[w]=0;

        }
        for (int i=0; i< 6; i++){
            for (int j=0; j< 5; j++){
                countEqualsTiles[matrix[i][j]]++;
            }
        }

        for(int k= 1; k<7; k++){
            if(countEqualsTiles[k]>7){
                return 1;
            }
        }
        return 0;
    }
}




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








