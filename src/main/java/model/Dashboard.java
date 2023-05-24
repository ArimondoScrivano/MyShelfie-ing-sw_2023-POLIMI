package model;

import java.util.List;

public class Dashboard {
    //Dashboard model
    private Tile[][] tiles;
    //Setting the state of a tile
    private final TILETYPE[][] refillable;
    private boolean refill;
    //Number of players
    private int players;
    private Bag bagInGame;
    public enum TILETYPE {
        TWO_PL, THREE_PL, FOUR_PL, BLK
    }


    // CONSTRUCTOR (takes as parameters number of players and tile's bag)
    public Dashboard(int np, Bag bagInGame) {
        this.bagInGame= bagInGame;
        np -= 1;


        this.refillable = new TILETYPE[][] {
                {TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.THREE_PL, TILETYPE.FOUR_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.FOUR_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.BLK, TILETYPE.THREE_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.THREE_PL, TILETYPE.BLK, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.FOUR_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.THREE_PL,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.FOUR_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.FOUR_PL,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.THREE_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.FOUR_PL, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.BLK, TILETYPE.THREE_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.THREE_PL, TILETYPE.BLK, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.FOUR_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.FOUR_PL, TILETYPE.THREE_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK,TILETYPE.BLK},
                {TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK,TILETYPE.BLK}
        };


        this.tiles = new Tile[11][11];
        //creating the matrix of tiles in the correct spot

        for (int r = 0; r < 11; r++) {
            for (int c = 0; c < 11; c++) {
                if (refillable[r][c].ordinal() < np) {
                    this.tiles[r][c] = bagInGame.getRandomTile();

                } else if (refillable[r][c].ordinal() >= np && refillable[r][c].ordinal() < 4) {
                    this.tiles[r][c] = new Tile(COLOR.BLANK, 0);
                }
            }
        }

        this.refill = false;
        this.players = np + 1;
    }


    //Method that returns tiles matrix's reference
    public Tile[][] getTiles() {
        return this.tiles;
    }

    //This method returns a copy of tiles matrix.
    public Tile[][] getTilesCopy() {
        Tile[][] tilesCopy = new Tile[11][11];
        for (int r = 0; r < 11; r++){
            for( int c = 0; c < 11; c++){
                tilesCopy[r][c]= new Tile(tiles[r][c].getColor(), tiles[r][c].getId());
            }
        }
        return tilesCopy;
    }

    public TILETYPE[][] getRefillable() {
        return this.refillable;
    }

    public int getPlayers() {
        return this.players;
    }

    public boolean getRefill() {
        return this.refill;
    }



    public void setRefill() throws Exception{
        int flag = 0;

        // we have to use a support matrix to avoid corner cases, so we can modify the elements
        Tile[][] matrixSupport = getTilesCopy();

        for(int row=1; row<10 && flag==0; row++){
            for(int col=1; col<10 && flag==0; col++){

                if(!matrixSupport[row][col].getColor().equals(COLOR.BLANK)
                   &&( !matrixSupport[row+1][col].getColor().equals(COLOR.BLANK)
                        || !matrixSupport[row-1][col].getColor().equals(COLOR.BLANK)
                        || !matrixSupport[row][col+1].getColor().equals(COLOR.BLANK)
                        || !matrixSupport[row][col-1].getColor().equals(COLOR.BLANK)
                )){
                    flag=1;

                }
            }
        }
        if(flag==0){

                refillDashboard();


        }

    }



    public void refillDashboard() throws Exception {
        int countTiles=0;
        for(int row=1; row<11; row++){
            for(int col=1; col<11; col++){
                if(!tiles[row][col].getColor().equals(COLOR.BLANK)){
                    countTiles++;
                }
            }
        }


        if (!bagInGame.checkEmpty(players,countTiles)) {

            // scanning the dashboard cells
            for (int r = 1; r < 11; r++) {
                for (int c = 1; c < 11; c++) {

                    // if tile is BLANK, we get a random tile from the bag
                    if (this.tiles[r][c].getColor().equals(COLOR.BLANK) && this.refillable[r][c].ordinal() < (this.players-1))
                        this.tiles[r][c] = bagInGame.getRandomTile();
                }
            }
        }else{
            // In this case we don't have enough tiles
            throw new Exception();

        }
    }


    public void updateDashboard(List<Integer> yCoord, List<Integer> xCoord) throws Exception{

        for (int index = 0; index < yCoord.size(); index++) {

           tiles[xCoord.get(index)][yCoord.get(index)]= new Tile(COLOR.BLANK,0);
        }
        // checking if dashboard needs to be refilled
        setRefill();
    }
}
