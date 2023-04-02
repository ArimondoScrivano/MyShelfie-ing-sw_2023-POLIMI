package model.cgoal;
import model.Tile;
import model.*;
import java.lang.*;
import java.util.*;

//abstract class that has the strategy method
 public abstract class CommonGoals{


public abstract int getCurrent_point();

    // the result is: 0 or the current point
    public abstract   int Checker(Tile[][] matrix);

}
