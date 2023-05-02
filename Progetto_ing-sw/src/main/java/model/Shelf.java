package model;

import java.io.IOException;
import java.io.Serializable;

public class Shelf implements Serializable {
    protected Tile[][] tilesShelf;
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
                i++;
                j++;
            }
        }
        return true;
    }

    public void addTiles(Tile[] tiles, int column) {
        int freeFirstSpot = 0;

        for(int row=5; row>-1; row--){
            if(tilesShelf[row][column].getColor().equals(COLOR.BLANK)){
                freeFirstSpot=row;
            }
        }
        int insert=0;
        for(int j=freeFirstSpot; j>-1; j-- ){
            tilesShelf[freeFirstSpot][column]= new Tile(tiles[insert].getColor(),tiles[insert].getId());
            insert++;
        }
        myPlayer.convertPoints(tilesShelf);

        try {
            myPlayer.checkPersonalGoal(freeFirstSpot, column);
        } catch (IOException e) {
                    e.printStackTrace();
        }
    }
}
//write test