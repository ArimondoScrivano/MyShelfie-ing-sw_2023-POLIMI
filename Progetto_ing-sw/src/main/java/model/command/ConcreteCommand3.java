package model.command;

import model.Player;
import model.command.Command;

public class ConcreteCommand3 implements Command {
    //caso non limite
    int[][] myShelf= new int[6][5];
    int[] chains={1,1,1,1,1,1};
    public ConcreteCommand3(int[][] myShelf){
        this.myShelf=myShelf;
    }
    public int adjacentTiles(int column, int row, int types[], Player myPlayer){
        int[][] horizontalChains=new int[6][5];
        int endline=0, index=0;
        int finalpoints=0;
        boolean verticalChainFinished=false;
        int i=row, j=0;
        for(i=0; i<6; i++){
            for(j=0;j<5;j++){
                horizontalChains[i][j]=1;
            }
        }
        i=row;

        while (i > row - types.length + 1 && index < types.length - 1) {
            myShelf[i][column] = types[index];
            index++;
            i--;
        }i++;//I'm on the highest position on the column
        //I start running checks on horizontal chains
        verticalChainFinished=true;
        int first=0, counter=1, k;
        boolean finished=false;
        while(i<5){
            if(myShelf[i][column]==myShelf[i][column+1]){
                endline=i;
                counter++;
                k=column+1;
                while(k<4){
                    if(myShelf[i][column]==myShelf[i+1][k+1]){
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
                myShelf[i][column+1]+=10;
            }
            if(myShelf[i][column]==myShelf[i][column-1]){
                endline=i;
                counter=1;
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
            }
            if(myShelf[i][column]==myShelf[i+1][column]){
                chains[i+1]+=chains[i];
                endline=i+1;
                verticalChainFinished=false;
                if(myShelf[i+1][column+1]==myShelf[i+1][column]){
                    endline=i+1;
                    counter=1;
                    verticalChainFinished=false;
                    k=column+1;
                    while(k<4){
                        if(myShelf[i+1][column]==myShelf[i+1][k+1]){
                            counter++; k++;
                        }else{
                            finished=true;
                            chains[i+1]+=counter;
                            counter=1;
                            break;
                        }
                    }if(!finished){
                        chains[i+1]+=counter;
                    }
                    myShelf[i+1][column+1]+=10;
                }
                if(myShelf[i+1][column]==myShelf[i+1][column-1]){
                    endline=i+1;
                    k=column-1;
                    finished=false;
                    while(k>0){
                        if(myShelf[i+1][column]==myShelf[i+1][k-1]){
                            counter++; k--;
                        }else{
                            finished=true;
                            chains[i+1]+=counter;
                            counter=1;
                            break;
                        }

                    }if(!finished) chains[i+1]+=counter;
                    myShelf[i+1][column-1]+=10;
                }

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
