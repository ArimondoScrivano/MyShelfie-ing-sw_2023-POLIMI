package model.cgoal;

import model.COLOR;
import model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class equalXCommonGoalsTest {
    CommonGoals test4 = new equalXCommonGoals();



    @Test
    void checker() {
        Tile[][] matrix = new Tile[6][5];

        for(int i = 0; i < 6; ++i) {
            for(int j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLANK, 1);
            }
        }

        Assertions.assertEquals(0, this.test4.Checker(matrix));
        matrix[2][1] = new Tile(COLOR.BLUE, 1);
        matrix[2][3] = new Tile(COLOR.BLUE, 1);
        matrix[4][1] = new Tile(COLOR.BLUE, 1);
        matrix[4][3] = new Tile(COLOR.BLUE, 1);
        matrix[3][2] = new Tile(COLOR.BLUE, 1);
        Assertions.assertEquals(1, this.test4.Checker(matrix));
        matrix[2][1] = new Tile(COLOR.BLANK, 1);
        matrix[2][3] = new Tile(COLOR.BLANK, 1);
        matrix[4][1] = new Tile(COLOR.BLANK, 1);
        matrix[4][3] = new Tile(COLOR.BLANK, 1);
        matrix[3][2] = new Tile(COLOR.BLANK, 1);
        matrix[5][0] = new Tile(COLOR.BLUE, 1);
        matrix[3][0] = new Tile(COLOR.BLUE, 1);
        matrix[3][2] = new Tile(COLOR.BLUE, 1);
        matrix[5][2] = new Tile(COLOR.BLUE, 1);
        matrix[4][1] = new Tile(COLOR.BLUE, 1);
        Assertions.assertEquals(1, this.test4.Checker(matrix));
        matrix[5][0] = new Tile(COLOR.BLANK, 1);
        matrix[3][0] = new Tile(COLOR.BLANK, 1);
        matrix[3][2] = new Tile(COLOR.BLANK, 1);
        matrix[5][2] = new Tile(COLOR.BLANK, 1);
        matrix[4][1] = new Tile(COLOR.BLANK, 1);
        matrix[0][4] = new Tile(COLOR.BLUE, 1);
        matrix[2][4] = new Tile(COLOR.BLUE, 1);
        matrix[2][2] = new Tile(COLOR.BLUE, 1);
        matrix[0][2] = new Tile(COLOR.BLUE, 1);
        matrix[1][3] = new Tile(COLOR.BLUE, 1);
        Assertions.assertEquals(1, this.test4.Checker(matrix));
    }
}
