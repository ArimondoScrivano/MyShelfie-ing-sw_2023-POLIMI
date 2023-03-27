package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;


//DONE
//8 equal tiles random order
//id=11
public class eightEqualCommonGoals implements CommonGoals {

    public int Checker(Tile[][] matrix) {
        int[] countEqualsTiles = new int[7];
        for (int w=0; w<7; w++) {
            countEqualsTiles[w]=0;

        }
        for (int i=0; i< 6; i++){
            for (int j=0; j< 5; j++){
                countEqualsTiles[matrix[i][j].getColor().compareTo(COLOR.BLANK)]++;
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

