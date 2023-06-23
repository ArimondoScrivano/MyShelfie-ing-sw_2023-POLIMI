package model.cgoal;

import model.COLOR;
import model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class equalXCommonGoalsTest {




    @Test
    void checker() {
        List<Integer> l1= new ArrayList<>();
        l1.add(1);
        l1.add(2);
        l1.add(3);
        CommonGoals test4 = new equalXCommonGoals(l1);
        Tile[][] matrix = new Tile[6][5];
        test4.printLayout();
        Assertions.assertEquals(
                3,test4.getCurrent_point() );
        for(int i = 0; i < 6; ++i) {
            for(int j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLANK, 1);
            }
        }

        Assertions.assertEquals(0, test4.Checker(matrix));
        matrix[2][1] = new Tile(COLOR.BLUE, 1);
        matrix[2][3] = new Tile(COLOR.BLUE, 1);
        matrix[4][1] = new Tile(COLOR.BLUE, 1);
        matrix[4][3] = new Tile(COLOR.BLUE, 1);
        matrix[3][2] = new Tile(COLOR.BLUE, 1);
        Assertions.assertEquals(3, test4.Checker(matrix));
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
        Assertions.assertEquals(2, test4.Checker(matrix));
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
        Assertions.assertEquals(1, test4.Checker(matrix));
        int a= test4.getId();
    }
}
