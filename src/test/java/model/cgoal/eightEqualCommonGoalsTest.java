package model.cgoal;

import model.COLOR;
import model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class eightEqualCommonGoalsTest {



    @Test
    public void checker() {

        List<Integer> l1= new ArrayList<>();
        l1.add(1);
        l1.add(2);
        l1.add(3);
        CommonGoals test3 = new eightEqualCommonGoals(l1);
        test3.printLayout();
        Tile[][] matrix = new Tile[6][5];
        Assertions.assertEquals(3, test3.getCurrent_point());
        int i;
        int j;
        for(i = 0; i < 6; ++i) {
            for(j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLANK, 1);
            }
        }

        Assertions.assertEquals(0, test3.Checker(matrix));

        for(i = 0; i < 6; ++i) {
            for(j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLUE, 1);
            }
        }

        int a= test3.getId();
        Assertions.assertEquals(3, test3.Checker(matrix));
    }

}
