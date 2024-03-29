package model.cgoal;
import model.COLOR;
import model.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 5 Columns different height
//id=12
public class fiveColumnsCommonGoals implements CommonGoals, Serializable {
    private final List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public fiveColumnsCommonGoals(List<Integer> CommonGoalPoints){
        this.points= new ArrayList<>();
        this.points.addAll(CommonGoalPoints);
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
        // Diagonal left to right (1)
        if (!matrix[4][0].getColor().equals(COLOR.BLANK)
                && !matrix[3][1].getColor().equals(COLOR.BLANK)
                && !matrix[2][2].getColor().equals(COLOR.BLANK)
                && !matrix[1][3].getColor().equals(COLOR.BLANK)
                && !matrix[0][4].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }
        //Diagonal right to left (1)
        if (!matrix[4][4].getColor().equals(COLOR.BLANK)
                && !matrix[3][3].getColor().equals(COLOR.BLANK)
                && !matrix[2][2].getColor().equals(COLOR.BLANK)
                && !matrix[1][1].getColor().equals(COLOR.BLANK)
                && !matrix[0][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }

        //Diagonal right to left (2)
        if (matrix[5][4].getColor().equals(COLOR.BLANK)
                && matrix[4][3].getColor().equals(COLOR.BLANK)
                && matrix[3][2].getColor().equals(COLOR.BLANK)
                && matrix[2][1].getColor().equals(COLOR.BLANK)
                && !matrix[1][0].getColor().equals(COLOR.BLANK)) {
            flag = 1;
        }

        // Diagonal left to right (2)
        if (matrix[5][0].getColor().equals(COLOR.BLANK)
                && matrix[4][1].getColor().equals(COLOR.BLANK)
                && matrix[3][2].getColor().equals(COLOR.BLANK)
                && matrix[2][1].getColor().equals(COLOR.BLANK)
                && !matrix[1][0].getColor().equals(COLOR.BLANK)) {
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

    @Override
    public List<Integer> getScoreList() {
        return this.points;
    }

    @Override
    public void printLayout() {
        System.out.println("""
                ┌──┐
                ├──┼──┐
                ├──┼──┼──┐
                ├──┼──┼──┼──┐
                ├──┼──┼──┼──┼──┐
                └──┴──┴──┴──┴──┘
                """);
    }

    @Override
    public int getId() {
        return 12;
    }
}
