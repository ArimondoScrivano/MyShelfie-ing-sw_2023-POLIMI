package model;

public class ConcreteCommand2 implements Command{
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
                convertPoints(myPlayer, oldChains, chains);
            }
            i++;
        }if(!verticalChainFinished){
            chains[startLine]=verticalChains[startLine]+horizontalChains[startLine]-1;
            convertPoints(myPlayer, oldChains, chains);
        }
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
