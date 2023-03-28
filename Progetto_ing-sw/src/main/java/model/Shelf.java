package model;

public class Shelf {
    //pattern command per tiles adiacenti
    protected int[][] tilesShelf;
    Player myPlayer;
    Command myCommand;
    //CONSTRUCTOR FOR THE SHELF CLASS
    public Shelf(){
        myCommand=new ConcreteCommand3();
    }
    //METHOD TO RETURN TRUE WHETHER ONE'S SHELF IS COMPLETED
    public boolean completeShelf(){
        return false;
    }
    public void addTiles(int tiles[], int column){
        if(tiles.length>3) System.out.println("too many tiles picked.");
        int counter=0;
        for(int j=0; j<=5; j++){
            if(tilesShelf[column][j]==0){
                counter++;
            }
        }if(tiles.length>counter){
            System.out.println("not enough vacant spaces on the selected column."); //lanciare eccezione?
        }else{
            int j=5;
            boolean updated=false;
            while(j>=0){
                if(tilesShelf[column][j]==0){
                    int firstLine=j;
                    updated=true;
                    int i=0;
                    for(i=0; i<tiles.length; i++){
                        tilesShelf[column][j]=tiles[i];
                        i++; j--;
                    } if(i==tiles.length){ j=-1;}
                    if(column==0) myCommand=new ConcreteCommand1();
                    if(column==4) myCommand=new ConcreteCommand2();
                    myCommand.adjacentTiles(column, firstLine, tiles, myPlayer);
                } j--;
            }
        }
    }

}

//scrovere casi di test