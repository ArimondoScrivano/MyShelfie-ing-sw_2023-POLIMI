package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;

import java.util.ArrayList;
import java.util.List;

//DONE



//2 columns All different tiles
//id=6
 public class twoColumnsCommonGoals extends CommonGoals {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public twoColumnsCommonGoals(List<Integer> CommonGoalpoints){

        this.points= new ArrayList<>();
        this.points.addAll(CommonGoalpoints);
        indexCurrentPoint=this.points.size()-1;
        current_point= this.points.get(indexCurrentPoint);

    }

    @Override
    public int getCurrent_point() {
        return current_point;
    }


    @Override
    public int Checker(Tile[][] matrix) {
        int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];
        for (int i = 0; i < 5; i++) {
            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;
            }
            countExc=0;
            for (int j = 0; j < 6; j++) {
                countEqualsTiles[matrix[j][i].getColor().compareTo(COLOR.BLANK)]++;
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
            if (indexCurrentPoint==-1){
                return 0;
            }else{
                int returnValue=current_point;
                indexCurrentPoint= indexCurrentPoint-1;
                if (indexCurrentPoint==-1){
                    current_point=0;
                }else{
                    current_point= points.get(indexCurrentPoint);
                }
                return returnValue;
            }
        }else{
            return 0;
        }
    }
}

