package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;

import java.util.ArrayList;
import java.util.List;

//DONE
// 5 Columns different highs
//id=12
 public class fiveColumnsCommonGoals implements CommonGoals {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public fiveColumnsCommonGoals(List<Integer> CommonGoalpoints){

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
        int flag = 0;

        if (!matrix[4][0].getColor().equals(COLOR.BLANK)
                && !matrix[3][1].getColor().equals(COLOR.BLANK)
                && !matrix[2][2].getColor().equals(COLOR.BLANK)
                && !matrix[1][3].getColor().equals(COLOR.BLANK)
                && !matrix[0][4].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }

        if (!matrix[4][4].getColor().equals(COLOR.BLANK)
                && !matrix[3][3].getColor().equals(COLOR.BLANK)
                && !matrix[2][2].getColor().equals(COLOR.BLANK)
                && !matrix[1][1].getColor().equals(COLOR.BLANK)
                && !matrix[0][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }



        if (flag == 1) {
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
