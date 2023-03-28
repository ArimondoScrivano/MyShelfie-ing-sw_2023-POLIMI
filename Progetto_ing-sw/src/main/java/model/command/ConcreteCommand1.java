package model.command;


import model.Player;
import model.command.Command;

public class ConcreteCommand1 implements Command {
    //caso limite 1: 0
    public void adjacentTiles(int column, int row, int types[], Player myPlayer){
        int counter=1;
        int horizontalChains[]= new int[]{1};
        int oldHorizontalChains[]=new int[]{0};
        int verticalChains[]= new int[]{1};
        int oldVerticalChains[]= new int[]{0};
        int i=row, index=0;
        while(i>row-types.length){
            myShelf[column][i]=types[index];
            index++;
            i--;
        } //mi trovo sulla posizione più alta
            while(column<=4){
                if(myShelf[column][i]==myShelf[column+1][i]%10){
                    //la modifica di hc dipende dagli algoritmi  per column!=0
                    horizontalChains[i]=oldHorizontalChains[i]+1; column++; myShelf[column][i]=10+types[types.length-1];
                    //marchio le tiles che conto
                }else{
                    break;
                }
            }
            counter=1; column=0;
            while(i<=5){
                if(myShelf[column][i+1]==myShelf[column][i]){
                    counter++; i++;
                }else{
                    if(myShelf[column][i]==myShelf[column][i+1]%10){
                        for(int j=i+1; j<=5; j++){
                            if(myShelf[column][j+1]!=myShelf[column][j]) i=j;
                        }
                        verticalChains[i]=oldVerticalChains[i]+counter;
                    }
                    counter=1; i++;
                }
            }
            //partire dalla poszione più alta fino a quella più bassa e agiungere nella i
        for(int j=0; j<7; j++){
            oldChains[j]=oldVerticalChains[j]+oldHorizontalChains[j]-1;
            chains[j]=verticalChains[j]+horizontalChains[j]-1;
            convertPoints(myPlayer, oldChains, chains);
        }
       //non va bene perché conteggio solo per colonne e non per catene complete
        //ora dovrebbe andare ma controllare
    }
    public void convertPoints(Player myPlayer, int[]oldChains, int[] chains){
        for(int j=0; j<100; j++){
            if(oldChains[j]!=chains[j]){
                if(chains[j]==3) myPlayer.points+=2;
                if(oldChains[j]<3){
                    if(chains[j]==4) myPlayer.points+=3;
                    if(chains[j]==5) myPlayer.points+=5;
                    if(chains[j]>=6) myPlayer.points+=8;
                }
                if(oldChains[j]==3){
                    if(chains[j]==4) myPlayer.points+=1;
                    if(chains[j]==5) myPlayer.points+=3;
                    if(chains[j]>=6) myPlayer.points+=6;
                }
                if(oldChains[j]==4){
                    if(chains[j]==5) myPlayer.points+=2;
                    if(chains[j]>=6) myPlayer.points+=5;
                }
                if(oldChains[j]==5){
                    if(chains[j]>=6) myPlayer.points+=3;
                }
                oldChains[j]=chains[j];
            } chains[j]=1;
        }
    }
}

//salvare le catene orizzontali con riga+ colonna in cui finiscono