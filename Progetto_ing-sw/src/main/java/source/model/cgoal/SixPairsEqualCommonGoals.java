package source.model.cgoal;
import source.model.*;
import java.util.*;
//TODO

// 6 pairs equal
//id=1
 public class SixPairsEqualCommonGoals implements CommonGoals {

    @Override
    public int Checker(Tile[][] matrix) {
        int count = 0;
        // we have to use a support matrix to avoid corner cases
        Tile[][] matrixSupport= new Tile[8][7];
        for(int r= 0; r<8; r++) {
            for (int c = 0; c < 7; c++) {
                if (r > 0 && r < 7 && c > 0 && c < 6) {
                    matrixSupport[r][c] = new Tile(matrix[r - 1][c - 1].getColor(), 1);
                } else {
                    matrixSupport[r][c] = new Tile(COLOR.BLANK, 1);
                }
            }
        }
        List<Tile> tilesAlreadyIn= new ArrayList<Tile>();
        for (int i=1; i< 7; i++) {
            for (int j = 1; j < 6; j++) {
                if (!tilesAlreadyIn.contains(matrixSupport[i][j]) && !matrixSupport[i][j].getColor().equals(COLOR.BLANK)) {

                // up
                    if (!tilesAlreadyIn.contains(matrixSupport[i][j])
                        && matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j].getColor())) {
                        if (!tilesAlreadyIn.contains(matrixSupport[i + 1][j])) {
                            count = count + 1;
                        }
                        tilesAlreadyIn.add(matrixSupport[i + 1][j]);
                        tilesAlreadyIn.add(matrixSupport[i][j]);
                    }
                // down
                    if (!tilesAlreadyIn.contains(matrixSupport[i][j])
                        && matrixSupport[i][j].getColor().equals(matrixSupport[i-1][j].getColor())){
                        if (!tilesAlreadyIn.contains(matrixSupport[i-1][j])) {
                            count = count + 1;
                        }
                        tilesAlreadyIn.add(matrixSupport[i-1][j]);
                        tilesAlreadyIn.add(matrixSupport[i][j]);
                    }

                //left corner
                    if(!tilesAlreadyIn.contains(matrixSupport[i][j])
                     &&matrixSupport[i][j].getColor().equals(matrixSupport[i][j-1].getColor())){
                        if (!tilesAlreadyIn.contains(matrixSupport[i][j-1])) {
                            count = count + 1;
                        }
                    tilesAlreadyIn.add(matrixSupport[i][j-1]);
                    tilesAlreadyIn.add(matrixSupport[i][j]);
                    }
                // right corner
                if(!tilesAlreadyIn.contains(matrixSupport[i][j])
                    && matrixSupport[i][j].getColor().equals(matrixSupport[i][j+1].getColor())){
                    if (!tilesAlreadyIn.contains(matrixSupport[i][j+1])) {
                            count = count + 1;
                        }
                        tilesAlreadyIn.add(matrixSupport[i][j+1]);
                        tilesAlreadyIn.add(matrixSupport[i][j]);
                    }

                }
            }
        }

        if (count > 5) {
            return 1;
        }
    return 0;
    }
}
