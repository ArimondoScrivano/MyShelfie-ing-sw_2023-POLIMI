package source.model;
import java.util.*;


public class Bag {
    private Map<Integer,Tile>  tilesInGame;


    public Bag(Map<Integer,Tile> tilesStartGame){
        this.tilesInGame= tilesStartGame;

    }

    public void updateBag(Map<Integer,Tile> tilesMidGame){
        this.tilesInGame= tilesMidGame;
    }



}
