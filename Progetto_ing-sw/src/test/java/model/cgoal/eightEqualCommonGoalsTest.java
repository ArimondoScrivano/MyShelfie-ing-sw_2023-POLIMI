package model.cgoal;

import model.COLOR;
import model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class eightEqualCommonGoalsTest {
    CommonGoals test3 = new eightEqualCommonGoals();


    @Test
    public void checker() {
        Tile[][] matrix = new Tile[6][5];

        int i;
        int j;
        for(i = 0; i < 6; ++i) {
            for(j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLANK, 1);
            }
        }

        Assertions.assertEquals(0, this.test3.Checker(matrix));

        for(i = 0; i < 6; ++i) {
            for(j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLUE, 1);
            }
        }

        Assertions.assertEquals(1, this.test3.Checker(matrix));
    }
}
