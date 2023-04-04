package model;

import model.command.Command;
import model.command.ConcreteCommand1;
import model.command.ConcreteCommand2;
import model.command.ConcreteCommand3;

import java.io.IOException;

public class Shelf {
    //pattern command per tiles adiacenti
    protected Tile[][] tilesShelf;
    int[][] tileTypes=new int[6][5];
    Player myPlayer;
    Command myCommand;
    //CONSTRUCTOR FOR THE SHELF CLASS
    public Shelf(Tile[][] tilesShelf, Player myPlayer){
        this.myPlayer=myPlayer;
        this.tilesShelf=tilesShelf;
    }
    //METHOD TO RETURN TRUE WHETHER ONE'S SHELF IS COMPLETED
    public boolean completeShelf(){
        int i=0, j=0;
        while(i<5){
            while(j<6){
                if(tilesShelf[i][j].getColor().compareTo(COLOR.BLANK)==0){
                    return false;
                }
            }
        }
        return true;
    }
    public int addTiles(Tile tiles[], int column) throws IOException {
        int k=0;
        int types[];
        if(tiles.length>4) System.out.println("too many tiles picked.");
        int counter=0;
        for(int j=0; j<=5; j++){
            if(tileTypes[j][column]==0){
                counter++;
            }
        }if(tiles.length>counter){
            System.out.println("not enough vacant spaces on the selected column."); //lanciare eccezione?
        }else{
            types=new int[tiles.length+1];
            for(int mover=0; mover<tiles.length; mover++){
                types[mover]=tiles[mover].getColor().compareTo(COLOR.BLANK);
            }
            //System.out.println("ho accettato le tiles scelte");
            int j=5;
            while(j>=0){ //ATTENZIONE PERCHè HO INVERTITO TUTTO
                if(tileTypes[j][column]==0){
                    //System.out.println("ho trovato il primo spazio libero");
                    int firstLine=j;
                    int i=0;
                    for(i=0; i<tiles.length-1; i++){
                        tileTypes[j][column]=tiles[i].getColor().compareTo(COLOR.BLANK);//mi ritorna un int
                        types[i]=tiles[i].getColor().compareTo(COLOR.BLANK);
                        //System.out.println("ho registrato la tessera"+i);
                        j--;
                    }//System.out.println("check del personal goal");
                    //myPlayer.myPersonalGoal.checkPersonalGoal(myPlayer, tileTypes);
                    if(i==tiles.length){ j=+1;}//quando mi trovo sulla posizione i=tiles.lenght significa che la jè stata diminuita una volta di troppo
                    if(column==0) myCommand=new ConcreteCommand1(tileTypes);
                    if(column==4) myCommand=new ConcreteCommand2(tileTypes);
                    if(column!=0 && column!=4) myCommand=new ConcreteCommand3(tileTypes);
                    k=myCommand.adjacentTiles(column, firstLine, types, myPlayer); //trovare il modo di far ritornare oldchains a shelf così che non perdo l'info sul turno precedente
                    System.out.println(k);
                    //System.out.println("ho chiamato il comando");
                    j=-1;
                    //System.out.println(j);
                    //System.out.println(k);
                }else{j--;}
            }
        }
        return k;
    }

}

//scrivere casi di test