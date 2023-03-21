package source.model;
import java.util.*;

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
    public enum COLOR{
        BLUE, YELLOW, WHITE, LIGHTBLUE, VIOLET, GREEN
    }

    //Constructor given the number of players
    //TODO
    public Dashboard(int np){
        switch(np){
            case 2:
                //Setting the tiles for the dashboard-->matrix 9x9
            case 3:
                //Setting the tiles for the dashboard-->matrix 9x9
            case 4:
                //Setting the tiles for the dashboard-->matrix 9x9
        }
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

