package model;
import java.util.*;

public class Bag {
    private Map<Integer,Tile>  tilesInGame;

    private boolean full;
    //it identifies if the bag is open (1) or not (0)
    private boolean state;


    public Bag(){
        full=true;
        tilesInGame= new HashMap<>();
       for(int i=1; i< 133; i++) {


           if (i<24) {
               //case BLUE
            Tile tiletoinsert= new Tile(COLOR.BLUE,i);
            tilesInGame.put(i,tiletoinsert);

           } else if (i<46) {
               //case YELLOW
               Tile tiletoinsert= new Tile(COLOR.YELLOW,i);
               tilesInGame.put(i,tiletoinsert);

           } else if (i<68) {
               //case WHITE
               Tile tiletoinsert= new Tile(COLOR.WHITE,i);
               tilesInGame.put(i,tiletoinsert);

           } else if (i<90) {
               //case LIGHT-BLUE
               Tile tiletoinsert= new Tile(COLOR.LIGHTBLUE,i);
               tilesInGame.put(i,tiletoinsert);

           } else if (i<112) {
               //case VIOLET
               Tile tiletoinsert= new Tile(COLOR.VIOLET,i);
               tilesInGame.put(i,tiletoinsert);

           }else {
               //else case(GREEN-CASE)
               Tile tiletoinsert= new Tile(COLOR.GREEN,i);
               tilesInGame.put(i,tiletoinsert);

           }

       }
    }

    //Check if the bag is empty
    public void checkEmpty(){
        if(tilesInGame.isEmpty()){
            full=false;
        }
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
        Tile tilegiven= new Tile(tilesInGame.get(id).getColor(),id);
        tilesInGame.remove(id,tilesInGame.get(id));
        checkEmpty();
        return tilegiven;

    }

}
