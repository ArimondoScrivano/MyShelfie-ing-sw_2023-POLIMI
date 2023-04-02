package model;

public class Dashboard {
    //Dashboard model
    private Tile[][] tiles;
    //Setting the state of a tile
    private TILETYPE[][] refillable;
    private boolean refill;
    //Number of players
    private int players;

    public enum TILETYPE {
        TWO_PL, THREE_PL, FOUR_PL, BLK
    }


    //Constructor given the number of players
    //TODO
    public Dashboard(int np, Bag bagInGame) {
        //correct format
        np -= 1;


        this.refillable = new TILETYPE[][] {
                {TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.THREE_PL, TILETYPE.FOUR_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK},
                {TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.FOUR_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK},
                {TILETYPE.BLK, TILETYPE.BLK, TILETYPE.THREE_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.THREE_PL, TILETYPE.BLK, TILETYPE.BLK},
                {TILETYPE.BLK, TILETYPE.FOUR_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.THREE_PL},
                {TILETYPE.FOUR_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.FOUR_PL},
                {TILETYPE.THREE_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.FOUR_PL, TILETYPE.BLK},
                {TILETYPE.BLK, TILETYPE.BLK, TILETYPE.THREE_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.THREE_PL, TILETYPE.BLK, TILETYPE.BLK},
                {TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.FOUR_PL, TILETYPE.TWO_PL, TILETYPE.TWO_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK},
                {TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.FOUR_PL, TILETYPE.THREE_PL, TILETYPE.BLK, TILETYPE.BLK, TILETYPE.BLK}
        };


        this.tiles = new Tile[9][9];
        //creating the matrix of tiles in the correct spot

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (refillable[r][c].ordinal() < np) {
                    this.tiles[r][c] = bagInGame.getRandomTile();

                } else if (refillable[r][c].equals(TILETYPE.BLK)) {
                    this.tiles[r][c] = new Tile(COLOR.BLANK, 0);


                }
            }
        }

        this.refill = false;
        this.players = np + 1;
    }

    //TODO
    public void updateDashboard(Tile[] pickedTiles, Bag bagInGame) {
        // After the turn of a player update the dashboard
        // function gets the array composed of one-to-three tails picked by the player

        // then searches those tails on the dashboard
        for (int index = 0; index < 3; index++) {

            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    if (this.tiles[r][c].equals(pickedTiles[index])) {

                        this.tiles[r][c] = new Tile(COLOR.BLANK, 0);
                        // method equals should compare the status, so tail's color and id
                    }
                }
            }
        }
        // checking if dashboard needs to be refilled
        setRefill(bagInGame);
    }


    //TODO
    public void setRefill(Bag bagInGame) {
        // Checking if the dashboard has to be refilled
        // scanning dashboard's cells
        for (int r = 0; r < 9; r++) {

            for (int c = 0; c < 9; c++) {

                // analyzing only the tiles not picked (!= BLANK)
                // if there is at least one adjacent tail with COLOR != BLANK, the dashboard doesn't need to be refilled
                if (!(this.tiles[r][c].getColor().equals(COLOR.BLANK))) {

                    if (c > 0 && !(this.tiles[r][c - 1].getColor().equals(COLOR.BLANK))) {
                        refill = false;
                        return;
                    } else if (c < 8 && !(this.tiles[r][c + 1].getColor().equals(COLOR.BLANK))) {
                        refill = false;
                        return;
                    } else if (r > 0 && !(this.tiles[r - 1][c].getColor().equals(COLOR.BLANK))) {
                        refill = false;
                        return;
                    } else if (r < 8 && !(this.tiles[r + 1][c].getColor().equals(COLOR.BLANK))) {
                        refill = false;
                        return;
                    }
                }
            }
        }

        // if the function has not returned until this point, it means that there are no more adjacent tails
        // dashboard needs to be refilled
        refill = true;
        refillDashboard(bagInGame);
    }

    public boolean getRefill() {
        return this.refill;
    }

    //TODO
    public void refillDashboard(Bag bagInGame) {
        if (!bagInGame.checkEmpty(players) && getRefill()) {


            // scanning the dashboard cells
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    // if tile is BLANK, we get a random tile from the bag
                    if (this.tiles[r][c].getColor().equals(COLOR.BLANK)) {
                        this.tiles[r][c] = bagInGame.getRandomTile();
                    }
                }
            }
        }else{
            // In this case we don't have enough tiles
            System.out.println("THE GAME IS OVER");
        }
    }


    //This method returns a new matrix of tiles, copy of tiles. In this case we don't pass any reference
    public Tile[][] getDashboard() {
        Tile[][] tiles_copy = new Tile[9][9];
        for (int r=0; r<9; r++){
            for( int c=0; c<9; c++){
                tiles_copy[r][c]=new Tile(tiles[r][c].getColor(),tiles[r][c].getId());
            }
        }
        return tiles_copy;

    }


    // method that returns tiles[][] ref.
    public Tile[][] getTiles() {
        return this.tiles;
    }


    // method that picks a tile and return a new tile, with same color and id
    public Tile pickTile(int r, int c) {
        // pickedTile to put into the shelf
        Tile pickedTile = new Tile(tiles[r][c].getColor(), tiles[r][c].getId());

        // reset on the dashboard the tile that has been picked
        // this.tiles[r][c].setColor(BLANK);  ?
        // this.tiles[r][c].setId(0);   ?

        // otherwise methods setRefill, refill and update can't work

        return pickedTile;
    }

}
