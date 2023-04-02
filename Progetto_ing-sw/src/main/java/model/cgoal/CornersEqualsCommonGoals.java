package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;

import java.util.ArrayList;
import java.util.List;


//DONE
//equals 4 corners
//id=3
 public class CornersEqualsCommonGoals extends CommonGoals {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;


    public CornersEqualsCommonGoals(List<Integer> CommonGoalpoints) {

        this.points= new ArrayList<>();
        this.points.addAll(CommonGoalpoints);
        indexCurrentPoint=this.points.size()-1;
        current_point= this.points.get(indexCurrentPoint);

    }

    public int getCurrent_point() {
        return current_point;
    }

    @Override
    public int Checker(Tile[][] matrix) {
        if (matrix[0][0].getColor().equals(matrix[0][4].getColor())
                && matrix[0][0].getColor().equals(matrix[5][0].getColor())
                && matrix[0][0].getColor().equals(matrix[5][4].getColor())
                && !matrix[0][0].getColor().equals(COLOR.BLANK))
        {

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
    }}
