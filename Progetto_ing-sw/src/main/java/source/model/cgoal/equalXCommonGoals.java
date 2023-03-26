package source.model.cgoal;
import source.model.*;

//DONE

//equal "x"
//id=10
 public class equalXCommonGoals implements CommonGoals {

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
            return 1;
        }
        return 0;
    }
}
