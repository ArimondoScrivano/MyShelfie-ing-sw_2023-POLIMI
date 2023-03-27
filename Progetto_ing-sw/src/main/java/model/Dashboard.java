package model;

public class Dashboard {
    //Dashboard model
    private int [][] tiles;
    //Setting the state of a tile
    private TILETYPE[][] refillable;
    private boolean refill;
    //Number of players
    private int players;

    public enum TILETYPE{
        TWO_PL, THREE_PL, FOUR_PL, BLK
    }


    //Constructor given the number of players
    //TODO
    public Dashboard(int np){
        switch(np){
            case 2:
                //Setting the tiles for the dashboard-->matrix 9x9 v2
                this.tiles = new int[][] {
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, 0, 0, -1, -1, -1, -1},
                        {-1, -1, -1, 0, 0, 0, -1, -1, -1},
                        {-1, -1, 0, 0, 0, 0, 0, 0, -1},
                        {-1, 0, 0, 0, 0, 0, 0, 0, -1},
                        {-1, 0, 0, 0, 0, 0, 0, -1, -1},
                        {-1, -1, -1, 0, 0, 0, -1, -1, -1},
                        {-1, -1, -1, -1, 0, 0, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1}
                };

            case 3:
                //Setting the tiles for the dashboard-->matrix 9x9 v3
                this.tiles = new int[][] {
                        {-1, -1, -1, 0, -1, -1, -1, -1, -1},
                        {-1, -1, -1, 0, 0, -1, -1, -1, -1},
                        {-1, -1, 0, 0, 0, 0, 0, -1, -1},
                        {-1, -1, 0, 0, 0, 0, 0, 0, 0},
                        {-1, 0, 0, 0, 0, 0, 0, 0, -1},
                        {0, 0, 0, 0, 0, 0, 0, -1, -1},
                        {-1, -1, 0, 0, 0, 0, 0, -1, -1},
                        {-1, -1, -1, -1, 0, 0, -1, -1, -1},
                        {-1, -1, -1, -1, -1, 0, -1, -1, -1}};

            case 4:
                //Setting the tiles for the dashboard-->matrix 9x9 v4
                this.tiles = new int[][] {
                        {-1, -1, -1, 0, 0, -1, -1, -1, -1},
                        {-1, -1, -1, 0, 0, 0, -1, -1, -1},
                        {-1, -1, 0, 0, 0, 0, 0, -1, -1},
                        {-1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, -1},
                        {-1, -1, 0, 0, 0, 0, 0, -1, -1},
                        {-1, -1, -1, 0, 0, 0, -1, -1, -1},
                        {-1, -1, -1, -1, 0, 0, -1, -1, -1}};
        }

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

        this.refill = false;
        this.players = np;
    }

    public void updateDashboard(int [] tiles){
        //After the turn of a player update the dashboard
    }

    public void refill(int np){
        //Checking if the dashboard has to be refilled
        refill=true;
        //Call of the refilling method
    }

    public Dashboard getDashboard(){
        return this;
    }
}

