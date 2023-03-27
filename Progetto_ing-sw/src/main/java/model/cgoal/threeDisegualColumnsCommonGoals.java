package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;


// 3 disegual columns
    //id=9
     public class threeDisegualColumnsCommonGoals implements CommonGoals {

        @Override
        public int Checker(Tile[][] matrix) {
            int count = 0;
            int countExc = 0;
            int[] countEqualsTiles = new int[7];

            for (int i = 0; i < 5; i++) {

                for (int w=0; w<7; w++) {
                    countEqualsTiles[w]=0;

                }

                countExc = 0;
                for (int j = 0; j < 6; j++) {
                    countEqualsTiles[matrix[j][i].getColor().compareTo(COLOR.BLANK)]++;
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

