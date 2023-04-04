package model;

import model.cgoal.CommonGoals;

import java.util.List;

public class Player {
    //INTEGER TO IDENTIFY THE PLAYER
    private int id;
    //PLAYER'S NICKNAME
    private String name;
    //PLAYER'S SHELF
    private Shelf myShelf;
    //THE PERSONAL GOAL IS CONNECTED TO THE PLAYER INSTANCE
    PersonalGoal myPersonalGoal;
    //NUMBER OF POINTS OF EACH PLAYER
    protected int points;
    //BOOLEAN WHICH IS TRUE WHETHER THE PLAYER'S SHELF IS FULL
    private boolean shelfCompleted;
    private boolean[] commonGoalsCompleted;
    Tile[][] tiles=new Tile[6][5];

    //INSTANCE CONSTRUCTOR FOR PLAYER CLASS
    public Player(int id, String name){
        this.id=id;
        this.name=name;
        this.myShelf=new Shelf(tiles, this);
        this.myPersonalGoal=new PersonalGoal();
        this.points=0;
        this.shelfCompleted=false;
        this.commonGoalsCompleted = new boolean[2];
    }

    //METHOD TO GET A PLAYER'S NAME
    public String getName(){
        return this.name;
    }

    //METHOD TO GET THE SITUATION OF A PLAYER'S SHELF
    public Shelf getShelf(){
        return this.myShelf;
    }

    //METHOD TO GET THE PERSONAL GOAL ASSOCIATED WITH A PLAYER
    public PersonalGoal getPersonalGoal(){
        return this.myPersonalGoal;
    }
    public int getPoints(){return points;}

    public void setPointsEndGame(){
        this.points++;
    }

    //RETURNS TRUE IF THE PLAYER'S SHELF CONTAINS ONE OF THE COMMON GOALS' PATTERNS
    public boolean commonGoalCompleted(List<CommonGoals> commonGoals, int id){
        if(commonGoals.get(id).Checker(myShelf.tilesShelf)==1){
            commonGoalsCompleted[id]=true;
            return true;
        }
        return false;
    }
    //salvare oldChain nella riga dell'adiacenza
    public int convertPoints(Player myPlayer, int[] chains, int endline){
        myPlayer.points=0;
        if(chains[endline]==3){
            myPlayer.points+=2;
        }
        if(chains[endline]==4){
            myPlayer.points+=3;
        }
        if(chains[endline]==5){
            myPlayer.points+=5;
        }
        if(chains[endline]>=6){
            myPlayer.points+=8;
        }

        //TODO SISTEMARE TIPO UML*/
        return myPlayer.points;
    }

    public boolean[] getCommonGoalsCompleted() {
        return commonGoalsCompleted;
    }

    public boolean isShelfCompleted() {
        return shelfCompleted;
    }

    public void setShelfCompleted(){
        this.shelfCompleted = true;
    }
}
