package model.cgoal;
import model.COLOR;
import model.Tile;

import java.util.ArrayList;
import java.util.List;


//equals 4 corners
//id=3
 public class CornersEqualsCommonGoals implements CommonGoals {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;


    public CornersEqualsCommonGoals(List<Integer> CommonGoalPoints) {

        this.points= new ArrayList<>();
        this.points.addAll(CommonGoalPoints);
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
    }

    @Override
    public List<Integer> getScoreList() {
        return this.points;
    }

    @Override
    public void printLayout() {
        System.out.println("""
                ┌──┬─────────────┬──┐
                │==│             │==│
                ├──┘             └──┤
                │                   │
                │                   │
                │                   │
                │                   │
                ├──┐             ┌──┤
                │==│             │==│
                └──┴─────────────┴──┘
                """);
    }
}
