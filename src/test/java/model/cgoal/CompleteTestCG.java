package model.cgoal;

import org.junit.jupiter.api.Test;

public class CompleteTestCG {

    @Test
    public  void testchecker(){
        CornersEqualsCommonGoalsTest a1= new CornersEqualsCommonGoalsTest();
        diagonalEqualCommonGoalsTest a2= new diagonalEqualCommonGoalsTest();
        eightEqualCommonGoalsTest a3 =new eightEqualCommonGoalsTest();
        equalXCommonGoalsTest a4= new equalXCommonGoalsTest();
        fiveColumnsCommonGoalsTest a5= new fiveColumnsCommonGoalsTest();
        fourRowsCommonGoalsTest a6= new fourRowsCommonGoalsTest() ;
        FourVerticalCommonGoalsTest a7= new FourVerticalCommonGoalsTest() ;
        SixPairsEqualCommonGoalsTest a8= new SixPairsEqualCommonGoalsTest() ;
        subMatrix2CommonGoalsTest a9= new subMatrix2CommonGoalsTest();
        threeDisegualColumnsCommonGoalsTest a10= new threeDisegualColumnsCommonGoalsTest();
        twoColumnsCommonGoalsTest a11= new twoColumnsCommonGoalsTest() ;
        twoRowsAllDifferentCommonGoalsTest a112= new twoRowsAllDifferentCommonGoalsTest();

        a1.testchecker();
        a2.checker();
        a3.checker();
        a4.checker();
        a5.checker();
        a6.checker();
        a7.checker();
        a8.checker();
        a9.checker();
        a10.checker();
        a11.checker();
        a112.checker();
    }
}
