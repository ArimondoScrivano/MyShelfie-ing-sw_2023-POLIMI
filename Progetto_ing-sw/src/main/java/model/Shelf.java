package model;

import java.io.Serializable;

public class Shelf implements Serializable {
    protected Tile[][] tilesShelf;
    private Player myPlayer;

    //CONSTRUCTOR FOR THE SHELF CLASS
    public Shelf( Player myPlayer) {
        this.myPlayer = myPlayer;
        tilesShelf= new Tile[6][5];
        for(int i =0; i<6; i++){
            for(int j=0;j<5;j++){
                tilesShelf[i][j]= new Tile(COLOR.BLUE,1);
            }
            tilesShelf[0][0]= new Tile(COLOR.BLANK,1);
            tilesShelf[1][0]= new Tile(COLOR.BLANK,1);
        }

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
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                if (tilesShelf[i][j].getColor().compareTo(COLOR.BLANK) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addTiles(Tile[] tiles, int column) {
        int freeFirstSpot = 0;
        int flagfound=0;
        for(int row=5; row>-1; row--){
            if(tilesShelf[row][column].getColor().equals(COLOR.BLANK) && flagfound==0){
                freeFirstSpot=row;
                flagfound=1;
            }
        }
        int insert=0;
        int tilesToInsert= tiles.length;
        for(int j=freeFirstSpot; j>-1 &&  insert< tilesToInsert; j-- ){
            tilesShelf[freeFirstSpot][column]= new Tile(tiles[insert].getColor(),tiles[insert].getId());
            insert++;
            freeFirstSpot--;
        }
        myPlayer.convertPoints(tilesShelf);
        myPlayer.checkPersonalGoal();
    }
}