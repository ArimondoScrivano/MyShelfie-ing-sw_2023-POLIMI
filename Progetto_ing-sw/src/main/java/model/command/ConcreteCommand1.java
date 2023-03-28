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
            myPlayer.convertPoints(myPlayer, oldChains, chains);
        }
       //non va bene perché conteggio solo per colonne e non per catene complete
        //ora dovrebbe andare ma controllare
    }
}

//salvare le catene orizzontali con riga+ colonna in cui finiscono