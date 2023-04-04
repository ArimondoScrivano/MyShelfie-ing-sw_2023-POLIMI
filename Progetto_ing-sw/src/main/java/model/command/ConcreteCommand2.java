package model.command;

import model.Player;
import model.command.Command;

public class ConcreteCommand2 implements Command {
    //caso limite 2: 4
    int[][] myShelf= new int[6][5];
    int[] chains={1,1,1,1,1,1};
    public ConcreteCommand2(int[][] myShelf){
        this.myShelf=myShelf;
    }
    public int adjacentTiles(int column, int row, int[] types, Player myPlayer){
        int i=row, j=0, index=0;
        int endline=0;
        int finalpoints=0;
        boolean verticalChainFinished=false;
        while (i > row - types.length + 1 && index < types.length - 1) {
            myShelf[i][column] = types[index];
            index++;
            i--;
        }i++;//I'm on the highest position on the column

        int k=0, counter=1;
        boolean finished=false;
        while(i<5){
            if(myShelf[i][column]==myShelf[i][column-1]){
                endline=i;
                k=column-1;
                finished=false;
                while(k>0){
                    if(myShelf[i][column]==myShelf[i][k-1]){
                        counter++;
                        k--;
                    }else{
                        finished=true;
                        chains[i]+=counter;
                        counter=1;
                        break;
                    }

                }if(!finished) chains[i]+=counter;
                myShelf[i][column-1]+=10;
            }
            if(myShelf[i][column]==myShelf[i+1][column]){
                endline=i+1;
                verticalChainFinished=false;
                if(myShelf[i+1][column]==myShelf[i+1][column-1]){
                    k=column-1;
                    finished=false;
                    while(k>0){
                        if(myShelf[i][column]==myShelf[i][k-1]){
                            counter++; k--;
                        }else{
                            finished=true;
                            chains[i]+=counter;
                            counter=1;
                            break;
                        }

                    }if(!finished) chains[i]+=counter;
                    myShelf[i][column-1]+=10;

                }chains[i+1]+=chains[i];
            }
            if(verticalChainFinished){
                finalpoints=myPlayer.convertPoints(myPlayer, chains, endline);
            }
            i++;

        }
        for(i=0; i<6; i++){
            for(j=0; j<5; j++){
                myShelf[i][j]=myShelf[i][j]%10; //after the count I reset myShelfie
            }
        }

        finalpoints=myPlayer.convertPoints(myPlayer, chains, endline);

        return finalpoints;
    }
}
