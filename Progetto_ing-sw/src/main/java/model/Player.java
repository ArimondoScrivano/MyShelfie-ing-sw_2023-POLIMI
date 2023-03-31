package model;

import model.cgoal.CommonGoals;

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
    protected boolean shelfCompleted;
    protected boolean[] commonGoalsCompleted;

    //INSTANCE CONSTRUCTOR FOR PLAYER CLASS
    public Player(int id, String name){
        this.id=id;
        this.name=name;
        this.myShelf=new Shelf();
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

    //RETURNS TRUE IF THE PLAYER'S SHELF CONTAINS ONE OF THE COMMON GOALS' PATTERNS
    public boolean commonGoalCompleted(CommonGoals[] commonGoals, int id){
        if(commonGoals[id].Checker(myShelf.tilesShelf)==1){
            commonGoalsCompleted[id]=true;
            return true;
        }
        return false;
    }
    public void convertPoints(Player myPlayer, int[]oldChains, int[] chains){
        for(int j=0; j<100; j++){
            if(oldChains[j]!=chains[j]){
                if(chains[j]==3) myPlayer.points+=2;
                if(oldChains[j]<3){
                    if(chains[j]==4) myPlayer.points+=3;
                    if(chains[j]==5) myPlayer.points+=5;
                    if(chains[j]>=6) myPlayer.points+=8;
                }
                if(oldChains[j]==3){
                    if(chains[j]==4) myPlayer.points+=1;
                    if(chains[j]==5) myPlayer.points+=3;
                    if(chains[j]>=6) myPlayer.points+=6;
                }
                if(oldChains[j]==4){
                    if(chains[j]==5) myPlayer.points+=2;
                    if(chains[j]>=6) myPlayer.points+=5;
                }
                if(oldChains[j]==5){
                    if(chains[j]>=6) myPlayer.points+=3;
                }
                oldChains[j]=chains[j];
            } chains[j]=1;
        }
    }
}
