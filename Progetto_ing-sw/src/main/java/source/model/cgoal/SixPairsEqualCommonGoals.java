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
        Tile[][] matrixSupport = new Tile[8][7];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 7; c++) {
                if (r > 0 && r < 7 && c > 0 && c < 6) {
                    matrixSupport[r][c] = new Tile(matrix[r - 1][c - 1].getColor(), 1);
                } else {
                    matrixSupport[r][c] = new Tile(COLOR.BLANK, 1);
                }
            }
        }
        List<Tile> tilesAlreadyIn = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 6; j++) {
                if (!tilesAlreadyIn.contains(matrixSupport[i][j]) && !matrixSupport[i][j].getColor().equals(COLOR.BLANK)) {

                    //check the adjacency
                    if (    //upper-case
                            matrixSupport[i][j].getColor().equals(matrixSupport[i+1][j].getColor())
                            //lower-case
                            || matrixSupport[i][j].getColor().equals(matrixSupport[i-1][j].getColor())
                            //right-case
                            || matrixSupport[i][j].getColor().equals(matrixSupport[i][j+1].getColor())
                            //left-case
                            || matrixSupport[i][j].getColor().equals(matrixSupport[i][j-1].getColor())) {

                        // check upper-case
                        if (!matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j].getColor())
                                || (matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i + 1][j]))) {

                            //insert upper-tile in the tilesAlreadyIn
                            if (matrixSupport[i][j].getColor().equals(matrixSupport[i + 1][j].getColor())) {
                                tilesAlreadyIn.add(matrixSupport[i + 1][j]);
                            }

                            // check lower-case
                            if (!matrixSupport[i][j].getColor().equals(matrixSupport[i - 1][j].getColor())
                                    || (matrixSupport[i][j].getColor().equals(matrixSupport[i - 1][j].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i - 1][j]))) {

                                // check right-case
                                if (!matrixSupport[i][j].getColor().equals(matrixSupport[i][j + 1].getColor())
                                        || (matrixSupport[i][j].getColor().equals(matrixSupport[i][j + 1].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i][j + 1]))) {

                                    // check left-case
                                    if (!matrixSupport[i][j].getColor().equals(matrixSupport[i][j - 1].getColor())
                                            || (matrixSupport[i][j].getColor().equals(matrixSupport[i][j - 1].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i][j - 1]))) {
                                        // ALL CASES COVERED, POSITIVE CASE
                                        count = count + 1;
                                        tilesAlreadyIn.add(matrixSupport[i][j]);


                                    }
                                }
                            }
                        }
                    }

                        if(matrixSupport[i][j].getColor().equals(matrixSupport[i+1][j].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i+1][j])){
                            tilesAlreadyIn.add(matrixSupport[i+1][j]);
                        }
                        if(matrixSupport[i][j].getColor().equals(matrixSupport[i-1][j].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i-1][j])){
                            tilesAlreadyIn.add(matrixSupport[i-1][j]);
                        }
                        if(matrixSupport[i][j].getColor().equals(matrixSupport[i][j+1].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i][j+1])){
                            tilesAlreadyIn.add(matrixSupport[i][j+1]);
                        }
                        if(matrixSupport[i][j].getColor().equals(matrixSupport[i+1][j].getColor()) && !tilesAlreadyIn.contains(matrixSupport[i][j-1])){
                            tilesAlreadyIn.add(matrixSupport[i][j-1]);
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
