package model.command;

import model.Player;
import model.command.Command;

public class ConcreteCommand2 implements Command {
    //caso limite 2: 4
    public void adjacentTiles(int column, int row, int[] types, Player myPlayer){
        int startLine=row+types.length-1;
        int i=startLine, j=0;
        int[] verticalChains=new int[]{1};
        int[] horizontalChains=new int[]{1};
        boolean verticalChainFinished=false;
        while(i<5){
            if(myShelf[column][i]==myShelf[column][i+1]){
                verticalChains[startLine]++;
            }else{
                verticalChainFinished=true;
            }
            if(myShelf[column][i]==myShelf[column-1][i]){
                horizontalChains[startLine]++;
                for(j=column-1; j>0; j--){
                    if(myShelf[j][i]==myShelf[j-1][i]){
                        horizontalChains[startLine]++;
                    }else{
                        break;
                    }
                }
            }if(verticalChainFinished){
                chains[startLine]=verticalChains[startLine]+horizontalChains[startLine]-1;
                myPlayer.convertPoints(myPlayer, oldChains, chains);
            }
            i++;
        }if(!verticalChainFinished){
            chains[startLine]=verticalChains[startLine]+horizontalChains[startLine]-1;
            myPlayer.convertPoints(myPlayer, oldChains, chains);
        }
    }
}
