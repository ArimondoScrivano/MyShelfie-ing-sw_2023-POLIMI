package controller;

import Network.Server;
import Network.messages.Message;
import Network.messages.MessageType;
import model.*;
import model.cgoal.CommonGoals;

import java.util.ArrayList;
import java.util.List;



public class GameController {
    //Model
    private final Game currentGame;
    private final int NumPlayers;
    private int id;


    //final attribute to the server implementation
    private final Server server;

    // 0 if the game is NOT ended or 1 if the Game Ended
    private int end;

    /**
     * Sets the ID of the object.
     *
     * @param id the ID to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the object.
     *
     * @return the ID of the object.
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the end value.
     *
     * @param end the value to set as the end value.
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Retrieves the end value.
     *
     * @return the end value.
     */
    public int getEnd(){
        return end;
    }

    /**
     * Constructs a new GameController instance with the specified number of players, server, and creator lobby.
     *
     * @param NumPlayers     the number of players in the game.
     * @param serverCreator  the server associated with the game.
     * @param creatorLobby   the creator lobby of the game.
     */
    public GameController(int NumPlayers, Server serverCreator, String creatorLobby) {
        this.server= serverCreator;
        this.NumPlayers= NumPlayers;
        this.end=0;
        id=0;
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        playersList.add(new Player(0, creatorLobby));
        Dashboard dashboard = new Dashboard(NumPlayers, new Bag());
        this.currentGame = new Game(0, dashboard, playersList,NumPlayers);
    }

    /**
     * Constructs a new GameController instance with the specified number of players and server.
     *
     * @param NumPlayers    the number of players in the game.
     * @param serverCreator the server associated with the game.
     */
    public GameController(int NumPlayers, Server serverCreator) {
        this.server= serverCreator;
        this.NumPlayers= NumPlayers;
        this.end=0;
        id=0;
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        Dashboard dashboard = new Dashboard(NumPlayers, new Bag());
        this.currentGame = new Game(0, dashboard, playersList,NumPlayers);
    }


    /**
     * Creates a new player with the specified ID and player name, and adds it to the current game.
     * If the number of players reaches the specified total number of players, the game is started.
     *
     * @param id_new the ID of the new player.
     * @param np     the name of the new player.
     */
    public void createPlayer(int id_new, String np){
        Player NewPlayer= new Player(id_new, np);
        currentGame.getPlayers().add(id_new,NewPlayer);
        System.out.println("il numero dei giocatori è: " + currentGame.getPlayers().size());
        if(currentGame.getPlayers().size()==NumPlayers) {
            started();
        }
    }


    /**
     * Retrieves the total number of players in the game.
     *
     * @return the total number of players.
     */
    public int getNumPlayers() {
        return this.NumPlayers;
    }


    /**
     * Retrieves the number of players currently filled in the game.
     *
     * @return the number of players currently filled.
     */
    public int getPlayersFilled(){
        return currentGame.getPlayers().size();
}


    /**
     * Retrieves the list of players in the current game.
     *
     * @return the list of players in the current game.
     */
    public List<Player> getPlayersList(){
        return this.currentGame.getPlayers();
    }

    /**
     * Retrieves the list of common goals in the current game.
     *
     * @return the list of common goals in the current game.
     */
    public List<CommonGoals> getCommonGoals(){
        return currentGame.getCommonGoals();
    }

    /**
     * Checks if the game is full, i.e., if the number of players equals the specified total number of players.
     *
     * @return true if the game is full, false otherwise.
     */
    public boolean isFull(){
        return currentGame.getPlayers().size() == NumPlayers;
    }

    /**
     * Notifies the server that something has changed in the game.
     * It sends a message to the server with the updated information.
     */
    public void somethingChanged(){
        MessageType m= MessageType.SOMETHINGCHANGED;
        Message msg= new Message(id, m);
            server.setMessage(msg);

    }

    /**
     * Starts the game by setting the first player and checking for uniques names conditions.
     * Determines the starting player based on the number of players and their unique names.
     * Sends a game starting message to the server if the starting conditions are met.
     */
    public void started(){
        int flagStartincoming=0;
        //Setting the first player
        currentGame.setCurrentPlayer(currentGame.getPlayers().get(0));
        if(NumPlayers==2){
            if(!currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(1).getName())){
              flagStartincoming=1;
            }
        }

        if(NumPlayers==3){
            if(!currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(1).getName())
                && !currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(2).getName())
                && !currentGame.getPlayers().get(1).getName().equals(currentGame.getPlayers().get(2).getName())){
                    flagStartincoming=1;
            }
        }
        if(NumPlayers==4){
            if(!currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(1).getName())
               && !currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(2).getName())
               && !currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(3).getName())
               && !currentGame.getPlayers().get(1).getName().equals(currentGame.getPlayers().get(2).getName())
               && !currentGame.getPlayers().get(1).getName().equals(currentGame.getPlayers().get(3).getName())
               && !currentGame.getPlayers().get(2).getName().equals(currentGame.getPlayers().get(3).getName())){
                flagStartincoming=1;

            }
        }

        if(flagStartincoming==1) {
            MessageType m = MessageType.GAME_STARTING;
            Message msg = new Message(id, m);
            server.setMessage(msg);
        }

    }


    /**
     * Ends the game by setting the end flag, sending a game ending message to the server, and updating the end status locally.
     * This method is called when the game has ended and no further actions can be taken.
     */
    public void ended() {
        setEnd(1);
        //create a notify message
        MessageType m = MessageType.GAME_ENDING;
        Message msg = new Message(id, m);
        this.end = 1;
        server.setMessage(msg);
    }

    /**
     * Changes the name of a player identified by the specified ID.
     * Updates the player's name in the current game.
     * If the number of players in the game is equal to the specified total number of players,
     * the game is started.
     *
     * @param id   the ID of the player to change the name for
     * @param name the new name for the player
     */
    public void changeName(int id, String name){
        currentGame.getPlayers().get(id).setName(name);
        if(currentGame.getPlayers().size()==NumPlayers) {
            started();
        }
    }

    /**
     * Finds the player with the specified name and returns their ID.
     *
     * @param name the name of the player to find
     * @return the ID of the player with the specified name, or 0 if not found
     */
  public int finderPlayer(String name){
        List<Player> playerList= getPlayersList();
        for(int i=0; i<playerList.size(); i++){
            if(playerList.get(i).getName().equals(name)){
                return i;
            }
        }
        return 0;
  }

    /**
     * Modifies and picks the next player when the current player has finished their turn.
     * - Checks if the old current player has completed some common goals.
     * - Checks if the current player's shelf is completed.
     * - Sets last round status for players who can no longer play.
     * - Handles the end of the game and calculates final points.
     * - Updates the current player to the next player in the list.
     * - Notifies of the change in the game state.
     */
    public void pickNextPlayer(){
        //CHECK IF THE OLD CURRENT PLAYER HAS COMPLETED SOME COMMON GOALS
        checkPoints();
        //check finish
        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
            int pos= finderPlayer(playerTurn().getName());

            //the players before me can't play
            for(int k=0; k<pos; k++){
                currentGame.getPlayers().get(k).setLastRound(true);
            }
            if(!currentGame.getEndGame()){
                currentGame.setEndGameTrue();
                playerTurn().setPointsEndGame();
            }

            // I completed the game and I'm the last in the List
            if(pos==currentGame.getPlayers().size()-1 ){
                for(int p=0; p<currentGame.getPlayers().size(); p++){
                    currentGame.getPlayers().get(p).sumUpPoints();
                }
                ended();
                return;
            }

        }
        int pos= finderPlayer(playerTurn().getName());
        int previous= pos -1;
        if (previous != -1) {
            if(currentGame.getPlayers().get(previous).isLastRound()){
                playerTurn().setLastRound(true);
                if(pos==currentGame.getPlayers().size() -1){
                    for(int p=0; p<currentGame.getPlayers().size(); p++){
                        currentGame.getPlayers().get(p).sumUpPoints();
                    }
                    ended();
                    return;
                }
            }
        }

        // this is a control for the end of the list
        if(currentGame.getPlayers().get(currentGame.getPlayers().size()-1).equals(playerTurn())){
            currentGame.setCurrentPlayer(currentGame.getPlayers().get(0));

        }else{
            int flag=0;
            for(int i =0; i< currentGame.getPlayers().size() -1 ; i++){
                if(currentGame.getPlayers().get(i).equals(currentGame.getCurrentPlayer())){
                    flag=i+1;
                }
            }
            currentGame.setCurrentPlayer(currentGame.getPlayers().get(flag));
        }

        somethingChanged();

    }

    /**
     * Returns the current player whose turn it is.
     *
     * @return The current player.
     */
    public Player playerTurn(){

        return currentGame.getCurrentPlayer();
    }

    /**
     * Picks the tiles specified by the given x and y coordinates and updates the dashboard.
     * After picking the tiles, it proceeds to pick the next player.
     *
     * @param xCord The x coordinates of the tiles to pick.
     * @param yCord The y coordinates of the tiles to pick.
     */
    public void pickTiles(List<Integer> xCord, List<Integer> yCord){
        try {
            currentGame.updateDashboard(xCord, yCord);
            pickNextPlayer();
        }catch (Exception e){
            ended();
        }
    }


    /**
     * Checks if the specified tiles are available for picking on the dashboard.
     *
     * @param yCord The y coordinates of the tiles to check.
     * @param xCord The x coordinates of the tiles to check.
     * @return True if the tiles are available for picking, false otherwise.
     */
    public boolean tileAvailablePick(List<Integer> yCord, List<Integer> xCord){

        //checking if the player selected blk tiles
        for(int i=0; i<xCord.size(); i++){
            if(currentGame.getDashboardMatrix()[xCord.get(i)][yCord.get(i)].getColor().equals(COLOR.BLANK)){
                return false;
            }
        }

        //check one tile picked
        if(xCord.size()==1){
            return currentGame.getDashboardMatrix()[xCord.get(0) + 1][yCord.get(0)].getColor().equals(COLOR.BLANK)
                    || currentGame.getDashboardMatrix()[xCord.get(0) - 1][yCord.get(0)].getColor().equals(COLOR.BLANK)
                    || currentGame.getDashboardMatrix()[xCord.get(0)][yCord.get(0) + 1].getColor().equals(COLOR.BLANK)
                    || currentGame.getDashboardMatrix()[xCord.get(0)][yCord.get(0) - 1].getColor().equals(COLOR.BLANK);

        }else {
            for(int i=0; i<xCord.size(); i++){
                if(!currentGame.getDashboardMatrix()[xCord.get(i)+1][yCord.get(i)].getColor().equals(COLOR.BLANK)
                        && !currentGame.getDashboardMatrix()[xCord.get(i)-1][yCord.get(i)].getColor().equals(COLOR.BLANK)
                        && !currentGame.getDashboardMatrix()[xCord.get(i)][yCord.get(i)+1].getColor().equals(COLOR.BLANK)
                        && !currentGame.getDashboardMatrix()[xCord.get(i)][yCord.get(i)-1].getColor().equals(COLOR.BLANK)){
                    return false;

                }
            }
            //check the line adjacency of the selected Tiles
            if (xCord.size()==2){
                //check  vertical line
                if(xCord.get(0).equals(xCord.get(1))){
                    if(yCord.get(0)== yCord.get(1) +1
                       || yCord.get(0)== yCord.get(1) -1){
                        return true;
                    }
                }
                //check horizontal line
                if(yCord.get(0).equals(yCord.get(1))){
                    return xCord.get(0) == xCord.get(1) + 1
                            || xCord.get(0) == xCord.get(1) - 1;
                }
                return false;

            }else{
                //check  vertical line
                if(xCord.get(0).equals(xCord.get(1)) && xCord.get(0).equals(xCord.get(2))) {
                    //1-2-3 <---< 3-2-1 <---< 3-1-2 <---< 2-1-3 <---< 1-3-2 <---< 2-3-1
                    if((yCord.get(0)== yCord.get(1)-1 && yCord.get(0)== yCord.get(2)-2)
                        || (yCord.get(0)== yCord.get(1)+1 && yCord.get(0)== yCord.get(2)+2)
                        || (yCord.get(0)== yCord.get(1)+2 && yCord.get(0)== yCord.get(2)+1)
                        || (yCord.get(0)== yCord.get(1)+1 && yCord.get(0)== yCord.get(2)-1)
                        || (yCord.get(0)== yCord.get(1)-2 && yCord.get(0)== yCord.get(2)-1)
                        || (yCord.get(0)== yCord.get(1)-1 && yCord.get(0)== yCord.get(2)+1)){
                        return true;
                }
                }
                //check  Horizontal line
                if(yCord.get(0).equals(yCord.get(1)) && yCord.get(0).equals(yCord.get(2))) {
                    //1-2-3 <---< 3-2-1 <---< 3-1-2 <---< 2-1-3 <---< 1-3-2 <---< 2-3-1
                    return (xCord.get(0) == xCord.get(1) - 1 && xCord.get(0) == xCord.get(2) - 2)
                            || (xCord.get(0) == xCord.get(1) + 1 && xCord.get(0) == xCord.get(2) + 2)
                            || (xCord.get(0) == xCord.get(1) + 2 && xCord.get(0) == xCord.get(2) + 1)
                            || (xCord.get(0) == xCord.get(1) + 1 && xCord.get(0) == xCord.get(2) - 1)
                            || (xCord.get(0) == xCord.get(1) - 2 && xCord.get(0) == xCord.get(2) - 1)
                            || (xCord.get(0) == xCord.get(1) - 1 && xCord.get(0) == xCord.get(2) + 1);
                }

            }
        return false;
        }

    }

    /**
     * Inserts the picked tiles from the dashboard into the specified column of the player's shelf.
     *
     * @param xCoord  The x coordinates of the tiles on the dashboard to be inserted.
     * @param yCoord  The y coordinates of the tiles on the dashboard to be inserted.
     * @param column  The column in the player's shelf where the tiles will be inserted.
     */
    public void insertTiles( List<Integer> xCoord, List<Integer> yCoord, int column){
        Shelf shelfToModify= currentGame.getCurrentPlayer().getShelf();
        Tile[] tilesPicked= new Tile[xCoord.size()];
        for(int i=0; i< xCoord.size(); i++){
            tilesPicked[i]= currentGame.getDashboard().getTiles()[yCoord.get(i)][xCoord.get(i)];
        }
        currentGame.addTiles(shelfToModify, tilesPicked,column);

        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
        }


    }

    /**
     * Checks if the specified column in the player's shelf is available to insert the given number of tiles.
     *
     * @param numTiles  The number of tiles to be inserted.
     * @param myShelf   The player's shelf to check.
     * @param column    The column to check for availability.
     * @return {@code true} if the column is available; {@code false} otherwise.
     */
    public boolean columnAvailable(int numTiles, Shelf myShelf, int column) {
        boolean flag = false;
       Tile[][] tilesShelf= myShelf.getTilesShelf();
        if (numTiles == 3) {
            if (tilesShelf[2][column].getColor().equals(COLOR.BLANK)) {
                flag = true;
            }
        }
        if (numTiles == 2) {
            if (tilesShelf[1][column].getColor().equals(COLOR.BLANK)) {
                flag = true;
            }
        }
        if (numTiles== 1) {
            if (tilesShelf[0][column].getColor().equals(COLOR.BLANK)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Checks and updates the points for the current player based on the completion of common goals.
     * This method checks the first and second common goals and updates the player's points accordingly.
     */
    public void checkPoints() {
        //check first common goal
        if (!currentGame.getCurrentPlayer().getCommonGoalsCompleted()[0]) {
            int partialSum = currentGame.getCommonGoals().get(0).Checker(currentGame.getCurrentPlayer().getShelf().getTilesShelf());
            if (partialSum > 0) {
                currentGame.getCurrentPlayer().removeCGPoints();
                currentGame.getCurrentPlayer().setCGPoints(partialSum);
                currentGame.getCurrentPlayer().setPoints(currentGame.getCurrentPlayer().getCGpoints());
                currentGame.getCurrentPlayer().setCommonGoalsCompleted(0);
            }
        }
        //check second common goal
        if (!currentGame.getCurrentPlayer().getCommonGoalsCompleted()[1]) {
            int partialSecondSum = currentGame.getCommonGoals().get(1).Checker(currentGame.getCurrentPlayer().getShelf().getTilesShelf());
            if (partialSecondSum > 0) {
                currentGame.getCurrentPlayer().removeCGPoints();
                currentGame.getCurrentPlayer().setCGPoints(partialSecondSum);
                currentGame.getCurrentPlayer().setPoints(currentGame.getCurrentPlayer().getCGpoints());
                currentGame.getCurrentPlayer().setCommonGoalsCompleted(1);
            }
        }

    }

    /**
     * Checks and returns the player with the highest points, determining the winner of the game.
     *
     * @return The player with the highest points, indicating the winner.
     */
    public Player checkWinner(){
        //Searching the player with the most points
        Player winner=currentGame.getPlayers().get(0);
        for(Player p : currentGame.getPlayers()){
            if(winner.getPoints()<=p.getPoints()){
                winner=p;
            }
        }
        return winner;
    }


    /**
     * Retrieves the current dashboard tiles of the game.
     *
     * @return The 2D array representing the current dashboard tiles.
     */
    public Tile[][] getDashboardTiles(){
        return currentGame.getDashboardMatrix();
    }
}
