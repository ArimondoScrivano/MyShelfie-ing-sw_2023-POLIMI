package model.cgoal;
import model.COLOR;
import model.Tile;
import model.*;


//DONE
// 4 vertical tiles equals four times
//id=5
 public class FourVerticalCommonGoals implements CommonGoals {

    @Override
    public int Checker(Tile[][] matrix) {
        int flag=0;
        for(int i=0; i<3; i++){
            for (int j=0; j<5; j++){

                if (!matrix[i][j].getColor().equals(COLOR.BLANK)
                        && matrix[i][j].getColor().equals(matrix[i+1][j].getColor())
                        && matrix[i][j].getColor().equals(matrix[i+2][j].getColor())
                        && matrix[i][j].getColor().equals(matrix[i+3][j].getColor())){
                    flag++;
                }


            }
        }
        if (flag> 3) {
            return 1;
        }else{
            return 0;
        }


    }
}


