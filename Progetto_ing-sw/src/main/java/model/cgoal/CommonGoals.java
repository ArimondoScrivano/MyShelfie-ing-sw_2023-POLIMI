package model.cgoal;
import model.Tile;
import model.*;
import java.lang.*;
import java.util.*;

//abstract class that has the strategy method
 public interface  CommonGoals{


    public int getCurrent_point();

    // the result is: 0 or the current point
    public int Checker(Tile[][] matrix);

    public List<Integer> getScoreList();

    public void printLayout();

}
