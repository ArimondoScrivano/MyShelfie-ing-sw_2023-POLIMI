package model.cgoal;
import model.COLOR;
import model.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//8 equal tiles random order
//id=11
public class eightEqualCommonGoals implements CommonGoals, Serializable {
    private final List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public eightEqualCommonGoals(List<Integer> CommonGoalPoints){

        this.points= new ArrayList<>();
        this.points.addAll(CommonGoalPoints);
        indexCurrentPoint=this.points.size()-1;
        current_point= this.points.get(indexCurrentPoint);

    }

    @Override
    public int getCurrent_point() {
        return current_point;
    }

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
                    ┌──┐   ┌──┐
                    │==│   │==│
                    └──┘   └──┘
                ┌──┐   ┌──┐   ┌──┐
                │==│   │==│   │==│
                └──┘   └──┘   └──┘
                ┌──┐   ┌──┐   ┌──┐
                │==│   │==│   │==│
                └──┘   └──┘   └──┘
                """);
    }

    @Override
    public int getId() {
        return 9;
    }
}

