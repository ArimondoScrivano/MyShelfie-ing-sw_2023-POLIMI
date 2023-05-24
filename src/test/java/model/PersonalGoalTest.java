package model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest extends TestCase {
    @Test
    //TESTING METHOD getId()
    public void testCheckerGetId(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(1, myPersonalGoal.getId());
    }

    @Test
    //TESTING METHOD getPoints(int i)
    public void testCheckerGetPoints0(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(1, myPersonalGoal.getPoints(0));
    }

    @Test
    //TESTING METHOD getPoints(int i)
    public void testCheckerGetPoints1(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(2, myPersonalGoal.getPoints(1));
    }

    @Test
    //TESTING METHOD getPoints(int i)
    public void testCheckerGetPoints2(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(4, myPersonalGoal.getPoints(2));
    }

    @Test
    //TESTING METHOD getPoints(int i)
    public void testCheckerGetPoints3(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(6, myPersonalGoal.getPoints(3));
    }

    @Test
    //TESTING METHOD getPoints(int i)
    public void testCheckerGetPoints4(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(9, myPersonalGoal.getPoints(4));
    }

    @Test
    //TESTING METHOD getPoints(int i)
    public void testCheckerGetPoints5(){
        PersonalGoal myPersonalGoal= new PersonalGoal(1);
        assertEquals(12, myPersonalGoal.getPoints(5));
    }
}