package model.cgoal;
import model.Tile;
import model.*;

//abstract class that has the strategy method
 public interface  CommonGoals{


    // the result is: 0 or 1.
    public   int Checker(Tile[][] matrix);

}
