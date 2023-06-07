package view.SWING;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

class GraphicalUITest extends TestCase{


@Test
    public void Checker()throws Exception{
    GraphicalUI provatest= new GraphicalUI();
    System.out.println("Chiedo connessione");
    int connection= provatest.askConnection();
    System.out.println(connection);
    System.out.println("Chiedo num giocatori");
   connection= provatest.askNumberOfPlayers();
    System.out.println(connection);
    String name= provatest.askNickname();

   // provatest.printShelf(new Tile[10][10]);
    //provatest.printCommonGoal(null);
    System.out.println(name);
    provatest.initGame();
    /*Bag bgtest= new Bag();
    Shelf shelftest= new Shelf(new Player(1, "Pietro"));
    Tile[] tilestoinsert= new Tile[6];
    tilestoinsert[0]= new Tile(COLOR.WHITE,1);
    tilestoinsert[1]= new Tile(COLOR.VIOLET,1);
    tilestoinsert[2]= new Tile(COLOR.LIGHTBLUE,1);
    tilestoinsert[3]= new Tile(COLOR.BLUE,1);
    tilestoinsert[4]= new Tile(COLOR.YELLOW,1);
    tilestoinsert[5]= new Tile(COLOR.GREEN,1);
    shelftest.addTiles(tilestoinsert,1);
    Dashboard dtest= new Dashboard(3,bgtest);
   provatest.printDashboard(dtest.getTiles());
    provatest.printShelf(shelftest.getTiles());
    List<CommonGoals> mycg= new ArrayList<>();
    List<Integer> cgPoints= new ArrayList<>();
    cgPoints.add(12);
    twoRowsAllDifferentCommonGoals cg1= new twoRowsAllDifferentCommonGoals(cgPoints);
    threeDisegualColumnsCommonGoals cg2= new threeDisegualColumnsCommonGoals(cgPoints);
    mycg.add(cg1);
    mycg.add(cg2);
    provatest.printCommonGoal(mycg);
    PersonalGoal myPg= new PersonalGoal(3);
    provatest.printPersonalGoal(myPg);
    System.out.println("chiedo il numero di tiles");
   System.out.println(provatest.askNumberOfTiles());
   List<Integer> tileprese= provatest.askTilesToPick(2);
   for(int i=0; i< tileprese.size(); i++){
       System.out.println(tileprese.get(i));
   }
   System.out.println("Sto chiedendo la colonna");
   System.out.println(provatest.askColumn());*/
while (true){

}
}


}