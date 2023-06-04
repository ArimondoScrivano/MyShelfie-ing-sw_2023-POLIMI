package view;

import java.util.List;

//USER INPUT
public interface UI {

    public void displayPoints(int myPoint, int myPGpoints);

    public int askColumn();

    public List<Integer> askTilesToPick(int numberOfTile);

    public int askNumberOfTiles();

    //public List<String> askNewChatMessage(List<String> playersName, String myplayername);

    public int askNumberOfPlayers();

    public boolean askNewGame();

    public String askNickname();

    public int askConnection();
}
