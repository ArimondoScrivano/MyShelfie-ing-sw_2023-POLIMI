package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;


//DONE
// 4 rows that have max 3 tiles egual
//id=4
 public class fourRowsCommonGoals implements CommonGoals {
    @Override
    public int Checker(Tile[][] matrix) {
        int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];

        //check the rows
        for (int i = 0; i < 6; i++) {

            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;
            }

            //check the specific row with index i
            for (int j = 0; j < 5; j++) {
                countEqualsTiles[matrix[i][j].getColor().compareTo(COLOR.BLANK)]++;
            }

            //check in the array if we have more than different colored tiles
            for (int k = 1; k < 7; k++) {
                if (countEqualsTiles[k] > 1) {
                    countExc++;
                }
            }
            // check if the row is compatible
            if (countExc < 4 && countEqualsTiles[0]==0) {
                count++;
                countExc=0;
            }

        }
        if (count > 3) {
            return 1;
        }else{
            return 0;
        }
    }
}

