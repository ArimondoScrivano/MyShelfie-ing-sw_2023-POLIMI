package model;

public class ConcreteCommand3 implements Command{
    //caso non limite
    public void adjacentTiles(int column, int row, int tiles[], Player myPlayer){
        int horizontalChains[]=new int[]{1};
        int verticalChains[]=new int[]{1};
        int startLine=row-tiles.length+1;
        boolean verticalChainFinished=false;
        int i=startLine, j=0;
        while(i<5){ //parto dalla posizione più alta e le guardo tutte
            myShelf[column][i]=tiles[j];
            j++;
        }//mi trovo sulla posizione più alta.
        while(i<5){ //per ogni tessera aggiunta controllo quella sotto (aggiunta insieme ad essa per condizione del while)
            //quella a destra e aggiungo eventuali catene, quella a sinistra e aggiungo eventuali catene
            if(myShelf[column][i]==myShelf[column][i+1]){//coonteggio delle tile sotto le mie che ho appena inserito
                verticalChains[startLine]++;//colonna + riga di inizio
                //evitare di marchiare le tile? tutto ciò che ho intorno tranne sotto è già stato contato
            }else{
                verticalChainFinished=true;
            }
            if(myShelf[column+1][i]==myShelf[column][i]){ //salvare le orizzontali nella colonna di fine + riga
                while(j<5){
                    if(myShelf[j][i]==myShelf[j+1][i]){
                        horizontalChains[startLine]++; j++;
                    }else{
                        break;
                    }
                }
            }
            if(myShelf[column][i]==myShelf[column-1][i]){
                horizontalChains[i]++;
                j=column-1;
                while(j>0){
                    if(myShelf[j][i]==myShelf[j-1][i]){
                        horizontalChains[startLine]++;
                        j--;
                    }else{
                        break;
                    }
                }
            } if(verticalChainFinished){
                for(j=0; j<7; j++){
                    chains[i]=verticalChains[i]+horizontalChains[i]-1;
                }
                convertPoints(myPlayer, oldChains, chains);
            }
            i++;
        }if(!verticalChainFinished){
                chains[startLine]=verticalChains[startLine]+horizontalChains[startLine]-1;
            convertPoints(myPlayer, oldChains, chains);
        }
        //sistemare i parametri che ho modificato
        //sono sulla posizione più bassa
        //vertical identificate con colonna+riga di inizio
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
//controllare prima catene orizzontali e implementare counter menre conto catena verticale, alla fine di catena verticale aggiungere con indicatore colonna
//dopo la conversione turno per turno copiare chains in old chains e rimettere chains TUTTO a uno per evitare tiles contate due volte
//colonna sempre la stessa in uno stesso turno, gestire caso tessere uguali su righe adiacenti
//riportare il conteggio hc sulla prima colonna per evitare chains contate due volte
//chains e oldc come attributi di command, copiare ch in old solo se la cella è diversa