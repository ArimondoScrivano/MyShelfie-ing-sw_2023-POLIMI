package source.model.cgoal;
//abstract class that has the strategy method
interface CommonGoals{

    // the result are 0 or 1: 0 no match, 1 match
    public int Checker(int[][] matrix);

}
