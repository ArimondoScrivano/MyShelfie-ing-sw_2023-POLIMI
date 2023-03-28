package model;

public class Dashboard {
    //Dashboard model
    private  Tile[][] tiles;
    //Setting the state of a tile
    private TILETYPE[][] refillable;
    private boolean refill;
    //Number of players
    private int players;

    public enum TILETYPE{
        TWO_PL, THREE_PL, FOUR_PL,BLK
    }


    //Constructor given the number of players
    //TODO
    public Dashboard(int np, Bag bagInGame){
        //correct format
        np-=1;


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


                //Setting the tiles for the dashboard-->matrix 9x9 v2
                this.tiles = new Tile[9][9];
                //creating the matrix of tiles in the correct spot

                for (int r=0; r< 9; r++){
                    for (int c=0; c<9; c++ ){
                        if(refillable[r][c].ordinal()<np){
                            tiles[r][c]=bagInGame.getRandomTile();

                        } else if (refillable[r][c].equals(TILETYPE.BLK)) {
                            tiles[r][c]= new Tile(COLOR.BLANK,0);


                        }
                    }
                }



        this.refill = false;
        this.players = np+1;
    }

    //TODO
    public void updateDashboard(Tile[] tiles){
        //After the turn of a player update the dashboard
    }

    //TODO
    public void setRefill(int np){
        //Checking if the dashboard has to be refilled
        refill=true;
        //Call of the refilling method
    }
    //TODO
    public void refillDashboard(){
        //
    }


    public Dashboard getDashboard(){
        return this;
    }
}

