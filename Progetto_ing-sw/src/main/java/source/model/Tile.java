package source.model;

public class Tile {
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
