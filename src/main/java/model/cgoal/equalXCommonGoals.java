package model.cgoal;
import model.COLOR;
import model.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//equal "x"
//id=10
public class equalXCommonGoals implements CommonGoals, Serializable {
    private final List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public equalXCommonGoals(List<Integer> CommonGoalPoints){

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
        int flag=0;

        for (int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                if (matrix[i][j].getColor().equals(matrix[i][j+2].getColor())
                        && matrix[i][j].getColor().equals(matrix[i+2][j].getColor())
                        && matrix[i][j].getColor().equals(matrix[i+1][j+1].getColor())
                        && matrix[i][j].getColor().equals(matrix[i+2][j+2].getColor())
                        && !matrix[i][j].getColor().equals(COLOR.BLANK)) {
                    flag=1;
                }
            }
        }

        if (flag> 0){
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
        }
        return 0;
    }

    @Override
    public List<Integer> getScoreList() {
        return this.points;
    }

    @Override
    public void printLayout() {
        System.out.println("""
                ┌──┐  ┌──┐
                │==│  │==│
                └──┼──┼──┘
                   │==│
                ┌──┼──┼──┐
                │==│  │==│
                └──┘  └──┘
                """);
    }

    @Override
    public int getId() {
        return 10;
    }
}
