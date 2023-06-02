package model.cgoal;
import model.COLOR;
import model.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


// 4 rows that have max 3 tiles equal
//id=4
 public class fourRowsCommonGoals implements CommonGoals, Serializable {
    private List<Integer> points;
    private int current_point;
    private int indexCurrentPoint;

    public fourRowsCommonGoals (List<Integer> CommonGoalPoints){

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
        int count = 0;
        int countExc = 0;
        int[] countEqualsTiles = new int[7];

        //check the rows
        for (int i = 0; i < 6; i++) {

            for (int w=0; w<7; w++) {
                countEqualsTiles[w]=0;
            }

            //check the specific row with index i
            for (int j = 0; j < 5; j++) {
                countEqualsTiles[matrix[i][j].getColor().compareTo(COLOR.BLANK)]++;
            }

            //check in the array if we have more than different colored tiles
            for (int k = 1; k < 7; k++) {
                if (countEqualsTiles[k] > 1) {
                    countExc++;
                }
            }
            // check if the row is compatible
            if (countExc < 4 && countEqualsTiles[0]==0) {
                count++;
                countExc=0;
            }

        }
        if (count > 3) {
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
                        ┌──┬──┬──┬──┬──┐
                        └──┴──┴──┴──┴──┘
                        \t      ┌──┐
                        \tMAX 3 │≠≠│
                        \t      └──┘
                        \t    X4
                        """);
    }

    @Override
    public int getId() {
        return 7;
    }
}

