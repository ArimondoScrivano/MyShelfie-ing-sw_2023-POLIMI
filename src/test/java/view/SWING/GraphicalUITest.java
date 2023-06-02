package view.SWING;

import junit.framework.TestCase;
import model.*;
import org.junit.jupiter.api.Test;

class GraphicalUITest extends TestCase{


@Test
    public void Checker()throws Exception{
    GraphicalUI provatest= new GraphicalUI();
  /*  System.out.println("Chiedo connessione");
    int connection= provatest.askConnection();
    System.out.println(connection);
    System.out.println("Chiedo num giocatori");
   connection= provatest.askNumberOfPlayers();
    System.out.println(connection);
    String name= provatest.askNickname();

   // provatest.printShelf(new Tile[10][10]);
    //provatest.printCommonGoal(null);
    System.out.println(name);*/
    provatest.initGame();
    Bag bgtest= new Bag();
    Shelf shelftest= new Shelf(new Player(1, "Pietro"));
    Tile[] tilestoinsert= new Tile[6];
    tilestoinsert[0]= new Tile(COLOR.WHITE,1);
    tilestoinsert[1]= new Tile(COLOR.VIOLET,1);
    tilestoinsert[2]= new Tile(COLOR.LIGHTBLUE,1);
    tilestoinsert[3]= new Tile(COLOR.BLUE,1);
    tilestoinsert[4]= new Tile(COLOR.YELLOW,1);
    tilestoinsert[5]= new Tile(COLOR.GREEN,1);
    shelftest.addTiles(tilestoinsert,3);
    Dashboard dtest= new Dashboard(4,bgtest);
   provatest.printDashboard(dtest.getTiles());
    provatest.printShelf(shelftest.getTiles());
while (true){

}
}


}