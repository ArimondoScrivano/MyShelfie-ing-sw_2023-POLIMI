package source.model.cgoal;
import source.model.*;

//DONE

//2 rows of  All different tiles
//id=8
     public class twoRowsAllDifferentCommonGoals implements CommonGoals {

        @Override
        public int Checker(Tile[][] matrix) {int count = 0;
            int countExc = 0;
            int[] countEqualsTiles = new int[7];
            for (int i = 0; i < 5; i++) {

                for (int w=0; w<7; w++) {
                    countEqualsTiles[w]=0;
                }

                countExc=0;
                for (int j = 0; j < 6; j++) {
                    countEqualsTiles[matrix[i][j].getColor().compareTo(COLOR.BLANK)]++;
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

