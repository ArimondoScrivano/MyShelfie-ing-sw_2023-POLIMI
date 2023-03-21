package source.model.cgoal;

// 4 rows that have max 3 tiles egual
//id=4
 public class fourRowsCommonGoals implements CommonGoals {
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

