package model;

import java.io.IOException;
import java.io.Serializable;

public class Shelf implements Serializable {
    protected Tile[][] tilesShelf;
    int[][] tileTypes = new int[6][5];
    Player myPlayer;

    //CONSTRUCTOR FOR THE SHELF CLASS
    public Shelf(Tile[][] tilesShelf, Player myPlayer) {
        this.myPlayer = myPlayer;
        this.tilesShelf = tilesShelf;
    }

    public Tile[][] getTilesShelf(){
        Tile[][] tilesCopy= new Tile[6][5];
        for(int row=0; row<6; row++){
            for(int col=0; col<5; col++) {
                tilesCopy[row][col]= new Tile(tilesShelf[row][col].getColor(),tilesShelf[row][col].getId());
            }
            }
        return tilesCopy;
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

        try {
            myPlayer.checkPersonalGoal(freeFirstSpot, column);
        } catch (IOException e) {

        }
    }
}
//write test