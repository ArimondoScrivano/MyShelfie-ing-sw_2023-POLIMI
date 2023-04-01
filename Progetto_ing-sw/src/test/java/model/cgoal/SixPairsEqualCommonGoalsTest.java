package model.cgoal;

import model.COLOR;
import model.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SixPairsEqualCommonGoalsTest {
    CommonGoals test8 = new SixPairsEqualCommonGoals();

    @Test
    void checker() {
        Tile[][] matrix = new Tile[6][5];
        Tile a00 = new Tile(COLOR.GREEN, 1);
        matrix[0][0] = a00;
        Tile a01 = new Tile(COLOR.GREEN, 1);
        matrix[0][1] = a01;
        Tile a02 = new Tile(COLOR.GREEN, 1);
        matrix[0][2] = a02;
        Tile a03 = new Tile(COLOR.VIOLET, 1);
        matrix[0][3] = a03;
        Tile a04 = new Tile(COLOR.VIOLET, 1);
        matrix[0][4] = a04;
        Tile a10 = new Tile(COLOR.GREEN, 1);
        matrix[1][0] = a10;
        Tile a11 = new Tile(COLOR.BLANK, 1);
        matrix[1][1] = a11;
        Tile a12 = new Tile(COLOR.BLANK, 1);
        matrix[1][2] = a12;
        Tile a13 = new Tile(COLOR.GREEN, 1);
        matrix[1][3] = a13;
        Tile a14 = new Tile(COLOR.BLANK, 1);
        matrix[1][4] = a14;
        Tile a20 = new Tile(COLOR.BLANK, 1);
        matrix[2][0] = a20;
        Tile a21 = new Tile(COLOR.GREEN, 1);
        matrix[2][1] = a21;
        Tile a22 = new Tile(COLOR.GREEN, 1);
        matrix[2][2] = a22;
        Tile a23 = new Tile(COLOR.GREEN, 1);
        matrix[2][3] = a23;
        Tile a24 = new Tile(COLOR.BLANK, 1);
        matrix[2][4] = a24;
        Tile a30 = new Tile(COLOR.GREEN, 1);
        matrix[3][0] = a30;
        Tile a31 = new Tile(COLOR.BLANK, 1);
        matrix[3][1] = a31;
        Tile a32 = new Tile(COLOR.BLANK, 1);
        matrix[3][2] = a32;
        Tile a33 = new Tile(COLOR.BLANK, 1);
        matrix[3][3] = a33;
        Tile a34 = new Tile(COLOR.GREEN, 1);
        matrix[3][4] = a34;
        Tile a40 = new Tile(COLOR.GREEN, 1);
        matrix[4][0] = a40;
        Tile a41 = new Tile(COLOR.GREEN, 1);
        matrix[4][1] = a41;
        Tile a42 = new Tile(COLOR.GREEN, 1);
        matrix[4][2] = a42;
        Tile a43 = new Tile(COLOR.GREEN, 1);
        matrix[4][3] = a43;
        Tile a44 = new Tile(COLOR.GREEN, 1);
        matrix[4][4] = a44;
        Tile a50 = new Tile(COLOR.BLUE, 1);
        matrix[5][0] = a50;
        Tile a51 = new Tile(COLOR.BLUE, 1);
        matrix[5][1] = a51;
        Tile a52 = new Tile(COLOR.WHITE, 1);
        matrix[5][2] = a52;
        Tile a53 = new Tile(COLOR.WHITE, 1);
        matrix[5][3] = a53;
        Tile a54 = new Tile(COLOR.BLANK, 1);
        matrix[5][4] = a54;

        assertEquals(1, this.test8.Checker(matrix));
        for(int i = 0; i < 6; ++i) {
            for(int j = 0; j < 5; ++j) {
                matrix[i][j] = new Tile(COLOR.BLANK, 1);
            }
        }

        assertEquals(0, this.test8.Checker(matrix));

    }
}