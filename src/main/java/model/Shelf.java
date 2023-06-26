package model;

import java.io.Serializable;

public class Shelf implements Serializable {
    protected Tile[][] tilesShelf;
    private Player myPlayer;

    //CONSTRUCTOR FOR THE SHELF CLASS
    /**
     * Constructs a new Shelf object for the specified player.
     *
     * @param myPlayer the player associated with the shelf.
     */
    public Shelf( Player myPlayer) {
        this.myPlayer = myPlayer;
        tilesShelf= new Tile[6][5];
        for(int i =0; i<6; i++){
            for(int j=0;j<5;j++){
                tilesShelf[i][j]= new Tile(COLOR.BLANK,1);
            }

        }


    }


    /**
     * Retrieves a copy of the tiles on the shelf.
     *
     * @return a 2D array representing the tiles on the shelf.
     */
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
    /**
     * Checks if the shelf is complete, i.e., if all positions on the shelf are filled with tiles.
     *
     * @return true if the shelf is complete, false otherwise.
     */
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



    /**
     * Adds the given tiles to the specified column on the shelf.
     * The tiles are added starting from the bottom of the column and moving upward.
     *
     * @param tiles  an array of tiles to be added to the shelf.
     * @param column the column index where the tiles should be added.
     */
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



    /**
     * Retrieves the 2D array of tiles representing the shelf.
     *
     * @return the 2D array of tiles representing the shelf.
     */
    public Tile[][] getTiles(){
        return this.tilesShelf;
    }
}