package model.command;


import model.Player;
import model.Shelf;
import model.command.Command;

public class ConcreteCommand1 implements Command {
    //caso limite 1: 0
    int[][] myShelf = new int[6][5];
    int[] chains={1,1,1,1,1,1};

    //CLASS CONSTRUCTOR
    public ConcreteCommand1(int[][] myShelf) {
        this.myShelf = myShelf;
    }

    //METHOD TO COUNT THE ADJACENT TILES IN THE FIRST CORNER CASE
    public int adjacentTiles(int column, int row, int types[], Player myPlayer) {
        int finalpoints=0;
        int i = row, index = 0, endline = 0;
        while (i > row - types.length + 1 && index < types.length - 1) {
            myShelf[i][column] = types[index];
            index++;
            i--;
        }i++;//I'm on the highest position on the column
        //I start running checks on horizontal chains
        boolean verticalChainFinished=false;
        int counter=1, k;
        boolean finished=false;
        while(i<5){
            verticalChainFinished=true;
            if(myShelf[i][column]==myShelf[i][column+1]){
                endline=i;
                counter++;
                myShelf[i][column+1]+=10;
                k=column+1;
                while(k<4){
                    if(myShelf[i][column]==myShelf[i][k+1]){
                        counter++;k++;
                    }else{
                        finished=true;
                        chains[i]+=counter;
                        counter=1;
                        break;
                    }
                }if(!finished){
                    chains[i]+=counter;
                }
            }
            if(myShelf[i][column]==myShelf[i+1][column]){
                endline=i+1;
                counter=1;
                verticalChainFinished=false;
                if(myShelf[i+1][column+1]==myShelf[i+1][column]){
                    counter++;
                    myShelf[i+1][column+1]+=10;
                    k=column+1;
                    while(k<4){
                        if(myShelf[i+1][column]==myShelf[i+1][k+1]){
                            counter++;k++;
                        }else{
                            finished=true;
                            chains[i+1]+=counter;
                            counter=1;
                            break;
                        }
                    }if(!finished){
                        chains[i+1]+=counter;
                    }
                }
                chains[i+1]+=chains[i];
            }
            if(verticalChainFinished){
                finalpoints=myPlayer.convertPoints(myPlayer, chains, endline);
            }
            i++;

        }
        for(i=0; i<6; i++){
            for(int j=0; j<5; j++){
                myShelf[i][j]=myShelf[i][j]%10; //after the count I reset myShelfie
            }
        }
        finalpoints=myPlayer.convertPoints(myPlayer, chains, endline);
        finalpoints=myPlayer.getPoints();
        return finalpoints;
    }
}