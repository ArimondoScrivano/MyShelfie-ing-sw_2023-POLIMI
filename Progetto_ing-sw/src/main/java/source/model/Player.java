package source.model;
public class Player {
    //INTEGER TO IDENTIFY THE PLAYER
    private int id;
    //PLAYER'S NICKNAME
    private String name;
    //PLAYER'S SHELF
    private Shelf myShelf;
    //THE PERSONAL GOAL IS CONNECTED TO THE PLAYER INSTANCE
    private PersonalGoal myPersonalGoal;
    //NUMBER OF POINTS OF EACH PLAYER
    protected int points;
    //BOOLEAN WHICH IS TRUE WHETHER THE PLAYER'S SHELF IS FULL
    protected boolean shelfCompleted;

    //INSTANCE CONSTRUCTOR FOR PLAYER CLASS
    public Player(int id, String name){
        this.id=id;
        this.name=name;
        this.myShelf=new Shelf();
        this.myPersonalGoal=new PersonalGoal();
        this.points=0;
        this.shelfCompleted=false;
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
    public boolean commonGoalCompleted(int idCommonGoal){
        //checkCommonGoal(idCommonGoal, myShelf)? true : false;
        return true;
    }

    //METHOD TO RAISE POINTS FOR A CERTAIN NUMBER OF ADJACENT TILES OF THE SAME TYPE
    public void updatePointsAdjacentTiles(){
        //t represents the type of the tile(=the color), the assumption is that the six possible colors of the tiles are
        //associated with numbers(e.g. 1=WHITE, 2=BLU, 3=YELLOW,...). i represents the lines' index, j represents the
        //columns' index.
        int t=1, i=0, j=0;
        boolean AdjacentSameType=false; //boolean attribute to show if there's a continuous chain of tiles of the same type.
        while(t<7){ //while loop to count the continuous chains for each tile type.
            int counter=1; //integer variable to count the number of continuous tiles of the same type.
            for(j=0; j<=5; j++){ //loop to change line
                AdjacentSameType=false; //when a new line's analysis starts, the presence of adjacent tiles return false.
                for(i=0; i<=4; i++){ //loop to change columns.
                    if(this.myShelf.tilesShelf[i][j]==t){ //we analise one tile-type at a time.
                        if(i<4 && j<5) {
                            if (this.myShelf.tilesShelf[i][j] == this.myShelf.tilesShelf[i + 1][j]) {
                                counter++; AdjacentSameType=true;
                            }
                            if(this.myShelf.tilesShelf[i][j] == this.myShelf.tilesShelf[i][j + 1]){
                                counter++; AdjacentSameType=true;
                                //everytime we find an adjacent tile of the same type we turn the boolean to true
                            }
                        } else {
                            if(i==4 && j!=5){
                                if (this.myShelf.tilesShelf[i][j] == this.myShelf.tilesShelf[i][j + 1]) {
                                    counter++; AdjacentSameType=true;
                                }
                            }
                            if(i!=4 && j==5){
                                if(this.myShelf.tilesShelf[i][j]==this.myShelf.tilesShelf[i+1][j]){
                                    counter++; AdjacentSameType=true;
                                }
                            }

                        }

                    }
                    /*} else {
                        if(i<4) {
                            i++;
                        } else {
                            if(i==4 && j<=5){
                                i++; j++;
                            }
                        }
                    }*/
                }
                if(AdjacentSameType==false){
                    //if the analysis didn't report any adjacent tile of the same type in the line I just analysed it means the coninuous
                    //chain is terminated, the points are then updated and counter is taken back to 1 to start counting a new chain.
                    if(counter==3) this.points=this.points+2;
                    if(counter==4) this.points=this.points+3;
                    if(counter==5) this.points=this.points+5;
                    if(counter>=6) this.points=this.points+8;
                    counter=1;
                }
            }
        //after all the lines are being analysed, we analyse a new type.
        t++;}
    }
}
