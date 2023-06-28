package model;
import java.util.*;

/**
 * The Bag class represents a collection of tiles in the game.
 */
public class Bag {
    private final Map<Integer,Tile>  tilesInGame;


    //it identifies if the bag is open (1) or not (0)
    private boolean state;

    /**
     * Constructs a new Bag object.
     * Initializes the tilesInGame HashMap with tiles representing different colors and numbers.
     * The tiles are assigned to their corresponding color categories and inserted into the HashMap.
     */
    public Bag(){
        tilesInGame= new HashMap<>();
       for(int i=1; i< 133; i++) {
           if (i<24) {
               //case BLUE
            Tile tileToInsert= new Tile(COLOR.BLUE,i);
            tilesInGame.put(i,tileToInsert);

           } else if (i<46) {
               //case YELLOW
               Tile tileToInsert= new Tile(COLOR.YELLOW,i);
               tilesInGame.put(i,tileToInsert);

           } else if (i<68) {
               //case WHITE
               Tile tileToInsert= new Tile(COLOR.WHITE,i);
               tilesInGame.put(i,tileToInsert);

           } else if (i<90) {
               //case LIGHT-BLUE
               Tile tileToInsert= new Tile(COLOR.LIGHTBLUE,i);
               tilesInGame.put(i,tileToInsert);

           } else if (i<112) {
               //case VIOLET
               Tile tileToInsert= new Tile(COLOR.VIOLET,i);
               tilesInGame.put(i,tileToInsert);

           }else {
               //else case(GREEN-CASE)
               Tile tileToInsert= new Tile(COLOR.GREEN,i);
               tilesInGame.put(i,tileToInsert);

           }

       }
    }

    //Check if the bag is empty for a future refill, true if it's empty
    /**
     * Checks if the bag is empty and needs to be refilled based on the number of players and the remaining tiles.
     *
     * @param np          the number of players
     * @param remainTiles the number of remaining tiles
     * @return true if the bag is empty and needs to be refilled, false otherwise
     */
    public boolean checkEmpty(int np, int remainTiles){
        int tilesToRefill;
        if (np==2){
            //tiles that we need for the refill with 2 players
            tilesToRefill= 29-remainTiles;
        } else if (np==3) {
            //tiles that we need for the refill with 3 players
            tilesToRefill= 37- remainTiles;
        }else{
            //tiles that we need for the refill with 4 players
            tilesToRefill= 45- remainTiles;
        }

        return tilesInGame.size() < tilesToRefill;
    }

    /**
     * Updates the bag by removing the specified list of tiles from the game and inserting blank tiles in their place.
     *
     * @param tilesRemoved the list of tiles to be removed from the game
     */
    public void updateBag(List<Tile> tilesRemoved ){

        for (Tile tile : tilesRemoved) {
            // the key in the map is the Tile - Id
            tilesInGame.remove(tile.getId(), tile);
            // insert of a blank tile
            Tile blankTile = new Tile(COLOR.BLANK, tile.getId());
            tilesInGame.put(tile.getId(), blankTile);
        }
    }


    /**
     * Sets the state of the game.
     *
     * @param state The new state of the game.
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Retrieves the current state of the game.
     *
     * @return The current state of the game.
     */
    public boolean getState(){
        return  this.state;
    }

    /**
     * Retrieves a random tile from the game board.
     *
     * @return A random tile from the game board.
     */
    public Tile getRandomTile(){
        Random rand= new Random();
        int id;
        int approved=0;
        id= rand.nextInt(132)+1;
        while(approved==0) {
            if (tilesInGame.containsKey(id)) {
                approved=1;
            }else{
                id= rand.nextInt(132)+1;
            }
        }
        Tile tileGiven= new Tile(tilesInGame.get(id).getColor(),id);
        tilesInGame.remove(id,tilesInGame.get(id));
        return tileGiven;
    }
    /**
     * Retrieves a map of tiles currently in the game.
     *
     * @return A map of tiles currently in the game.
     */
    public Map<Integer,Tile> getTilesInGame(){
        return this.tilesInGame;
    }

}
