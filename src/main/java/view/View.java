package view;

import Network.GameChat.GameMessage;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;

public interface View {
    //Showing info
    void showMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg);
    void shownewMex();
    public void showGameChat(List<GameMessage> listToDisplay);
    public void printDashboard(Tile[][] copy);
    public void printPersonalGoal(PersonalGoal pg);
    void printCommonGoal(List<CommonGoals> commonGoals);
    public void printShelf(Tile[][] myShelf);

    public void init();
    public void initGame();
    public void endGame(String esito);

}
