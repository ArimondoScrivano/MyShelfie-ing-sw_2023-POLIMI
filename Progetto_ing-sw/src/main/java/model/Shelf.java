package model;

import java.io.IOException;

public class Shelf {
    //pattern command per tiles adiacenti
    protected Tile[][] tilesShelf;
    int[][] tileTypes = new int[6][5];
    Player myPlayer;

    //CONSTRUCTOR FOR THE SHELF CLASS
    public Shelf(Tile[][] tilesShelf, Player myPlayer) {
        this.myPlayer = myPlayer;
        this.tilesShelf = tilesShelf;
    }

    //METHOD TO RETURN TRUE WHETHER ONE'S SHELF IS COMPLETED
    public boolean completeShelf() {
        int i = 0, j = 0;
        while (i < 5) {
            while (j < 6) {
                if (tilesShelf[i][j].getColor().compareTo(COLOR.BLANK) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addTiles(Tile tiles[], int column) {
        int freeFirstSpot = 0;
        int flag = 0;
        boolean firstSpotFound=false;
        int types[];
        if (tiles.length > 4) System.out.println("too many tiles picked.");
        if (tiles.length == 3) {
            if (tilesShelf[2][column].getColor().equals(COLOR.BLANK)) {
                flag = 1;
            }
        }
        if (tiles.length == 2) {
            if (tilesShelf[1][column].getColor().equals(COLOR.BLANK)) {
                flag = 1;
            }
        }
        if (tiles.length == 1) {
            if (tilesShelf[0][column].getColor().equals(COLOR.BLANK)) {
                flag = 1;
            }
        }
        if(flag==0) System.out.println("too little room on this column");
        int col=1;

        if (flag == 1) {
            while(col<6){
                if(tilesShelf[col-1][column].getColor().equals(COLOR.BLANK) && !(tilesShelf[col][column].getColor().equals(COLOR.BLANK))){
                    freeFirstSpot=col-1;
                    firstSpotFound=true;
                    break;
                }else{col++;}
            }
            if(!firstSpotFound) freeFirstSpot=5;
            for (int i = 0; i < tiles.length; i++) {
                tilesShelf[freeFirstSpot - i][column] = new Tile(tiles[i].getColor(), tiles[i].getId());
            }
        }
        myPlayer.convertPoints(tilesShelf);
        int[][] matrixSupport = new int[6][5];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                matrixSupport[r][c] = this.tilesShelf[r][c].getColor().compareTo(COLOR.BLANK);
            }
        }

        try {
            myPlayer.myPersonalGoal.checkPersonalGoal(this.myPlayer, matrixSupport);
        } catch (IOException e) {

        }
    }
}
//write test