package view;

import model.Game;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;

public interface View {
    //Request RMI or Socket connection
    void askConnection();
    //Request nickname
    void askNickname();
    //Showing info
    void showMatchInfo(Tile[][] copy, int np, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg);
}
