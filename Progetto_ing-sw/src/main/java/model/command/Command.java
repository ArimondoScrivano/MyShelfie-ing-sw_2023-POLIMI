
package model.command;

import model.Player;

public interface Command {
    int[][] myShelf= new int[6][5];
    int chains[]=new int[7];
    int oldChains[]= new int[7];
    public default int adjacentTiles(int column, int row, int[] tiles, Player myPlayer){return 0;}
}
