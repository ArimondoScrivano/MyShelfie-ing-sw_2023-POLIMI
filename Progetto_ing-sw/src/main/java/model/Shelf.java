package model;

public class Shelf {
    //pattern command per tiles adiacenti
    protected Tile[][] tilesShelf;
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
    public void addTiles(Tile tiles[], int column){
        int tileTypes[][]=new int[6][7];
        int[] types= new int[4];
        if(tiles.length>3) System.out.println("too many tiles picked.");
        int counter=0;
        for(int j=0; j<=5; j++){
            if(tileTypes[column][j]==0){
                counter++;
            }
        }if(tiles.length>counter){
            System.out.println("not enough vacant spaces on the selected column."); //lanciare eccezione?
        }else{
            int j=5;
            boolean updated=false;
            while(j>=0){
                if(tileTypes[column][j]==0){
                    int firstLine=j;
                    updated=true;
                    int i=0;
                    for(i=0; i<tiles.length; i++){
                        tileTypes[column][j]=tiles[i].getColor().compareTo(COLOR.BLANK);
                        types[i]=tiles[i].getColor().compareTo(COLOR.BLANK);
                        i++; j--;
                    } if(i==tiles.length){ j=-1;}
                    if(column==0) myCommand=new ConcreteCommand1();
                    if(column==4) myCommand=new ConcreteCommand2();
                    myCommand.adjacentTiles(column, firstLine, types, myPlayer);
                } j--;
            }
        }
    }

}

//scrivere casi di test