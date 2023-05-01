package model;

import java.io.Serializable;

public class Tile implements Serializable {
   private COLOR color;
   private int id;

   public Tile( COLOR color, int id){
       this.color=color;
       this.id=id;
   }

    public COLOR getColor() {
        return color;
    }

    public int getId(){
       return id;
    }

}
