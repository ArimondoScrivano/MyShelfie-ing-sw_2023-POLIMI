package model;
import java.util.*;


public class Bag {
    private Map<Integer,Tile>  tilesInGame;

    //it identifies if the bag is open (1) or not (0)
    private boolean state;


    public Bag(Map<Integer,Tile> tilesStartGame){
        this.tilesInGame.putAll(tilesStartGame);

    }

    public void updateBag(List<Tile> tilesRemoved ){

     for (int i=0; i< tilesRemoved.size(); i++){
         // the key in the map is the Tile - Id
         tilesInGame.remove(tilesRemoved.get(i).getId(), tilesRemoved.get(i));
         // insert of a blank tile
         Tile blankTile = new Tile(COLOR.BLANK, tilesRemoved.get(i).getId());
         tilesInGame.put(tilesRemoved.get(i).getId(),blankTile);

     }
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public boolean getState(){
        return  this.state;
    }

}
