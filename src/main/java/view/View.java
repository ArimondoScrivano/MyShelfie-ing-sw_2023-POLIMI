package view;

import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;

public interface View {
    //Showing info
    void showMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg);
}
