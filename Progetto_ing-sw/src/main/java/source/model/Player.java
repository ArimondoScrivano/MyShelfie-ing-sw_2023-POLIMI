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
        //this.points=
        return true;
    }

    public void updatePointAdjacentTiles(){
        int counter=1, t=1, i=0, j=0, c=7;
        boolean chainFinished=true;
        while(t<7){ //we analyse one type at a time.
            while(j<6){ //loop to change line.
                while(i<5) { //loop to change column.
                    if (this.myShelf.tilesShelf[i][j] == t) { //we look for the first tile with type t.
                        this.myShelf.tilesShelf[i][j] = c;
                        if (this.myShelf.tilesShelf[i][j + 1] == t) { //if the tile under our analysed tile is type t we count and mark it
                            this.myShelf.tilesShelf[i][j + 1] = c;
                            counter++;
                            chainFinished = false; //I need to check on the next line before I state the chain is finished.
                        }
                        if (this.myShelf.tilesShelf[i + 1][j] == t) { //we check on the next tile in line.
                            this.myShelf.tilesShelf[i + 1][j] = c;
                            counter++;
                            i++;
                            //if the type is the same counter is increased, the counted tile is marked with a c
                            // and we move on the next position on the line.
                            //-> we count and mark all the continuous tiles on the same line before moving to the new line.
                        } else {
                            j++; //when the tiles on the same line are finished I move on the next line.
                            chainFinished = true; //when on the next line I assume the chain is finished until I find a t-typed tile on the next line.
                            for (int d = 0; d <= i; d++) { //the index i represents the position on the previous line where the continuous
                                // horizontal chain stops, so I look for adjacent tiles in the same positions of
                                // the next line.
                                if (this.myShelf.tilesShelf[d][j] == c) {//I look for tiles adjacent to the tiles I already counted.
                                    if (this.myShelf.tilesShelf[d][j + 1] == t) {
                                        counter++;
                                        this.myShelf.tilesShelf[d][j + 1] = c;//I check on tiles on the next line.
                                        chainFinished = false;//I need to check on the next line.
                                    }
                                    if (this.myShelf.tilesShelf[d - 1][j] == t) {
                                        //I look for t- type tiles in the previous positions of the counted chain on the analysed line.
                                        this.myShelf.tilesShelf[d - 1][j] = c;
                                        counter++; //if I find one, I count it and mark it.
                                        if (this.myShelf.tilesShelf[d - 1][j + 1] == t) { //I check on the tile under the counted tile.
                                            chainFinished = false; //chain false indicates that I have to check on the new line for possible
                                            //tiles of the same type which are continuing the chain.
                                            this.myShelf.tilesShelf[d - 1][j + 1] = c;
                                            counter++; //I count and mark each type t tile I found.
                                        }
                                        for (int e = d - 2; e >= 0; e--) { //if tilesShelf[d-1][j] is t type, this might mean that there are more
                                            //t type tiles in the previous positions conitnuing the chain-> I run a check.
                                            if (this.myShelf.tilesShelf[e][j] == t) {
                                                this.myShelf.tilesShelf[e][j] = c;
                                                counter++; //each type t tile is counted and marked.
                                                if (this.myShelf.tilesShelf[e][j + 1] == t) { //for each tile I need to check on the type of the tile under.
                                                    chainFinished = false; //if I find a t type tile on the next ine I need to check on
                                                    //type t tiles continuing the chain on the next line.
                                                    this.myShelf.tilesShelf[e][j + 1] = c;
                                                    counter++; //each tile is counted and marked.
                                                }
                                            } else {
                                                e = -1; //when I find a different-typed tile I know that the chain is finshed and I need to stop the loop.
                                            }
                                        }
                                    }
                                    if (d != 4 && this.myShelf.tilesShelf[d + 1][j] == t) { //after checking on the previous positions I need to check on the following.
                                        counter++;
                                        this.myShelf.tilesShelf[d + 1][j] = c; // If I find one of the same tipe I count it and mark it.
                                        if (this.myShelf.tilesShelf[d + 1][j + 1] == t) {
                                            this.myShelf.tilesShelf[d + 1][j + 1] = c;
                                            counter++;
                                            chainFinished = false;
                                        }
                                        for (int e = d + 2; e < 5; e++) {
                                            //if I find a tile of the same type on [d+1][j] position, I need to check on the following positions
                                            // to determine whether the chain is really finished.
                                            if (this.myShelf.tilesShelf[e][j] == t) { //if I find an adjacent tile I count it and mark it.
                                                counter++;
                                                this.myShelf.tilesShelf[e + 1][j] = c;
                                                if (this.myShelf.tilesShelf[e][j + 1] == t) {
                                                    this.myShelf.tilesShelf[e][j + 1] = c;
                                                    counter++;
                                                    chainFinished = false;
                                                }
                                            } else { //if the next in line tile's type is not t,
                                                d = e + 1; //condition not to enter the first if.
                                                // there are no more horizontal adjacent tiles and I need to check on the next line.
                                                e = 6;
                                                j++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //after checking on all the possible coninuous chains of tiles I convert count into points.
                        if (chainFinished) {
                            if (counter == 3) this.points += 2;
                            if (counter == 4) this.points += 3;
                            if (counter == 5) this.points += 5;
                            if (counter >= 6) this.points += 8;
                        }
                    }else{i++;}
                }i = 0; j++; //I get back to the first tile and look for another chain of the t-type. I won't count again
                // the same chain because I marked the t type with a c type.
            }j++; i=0;
        }
        for(j=0; j<6; j++){ //before checking the following type I convert back the c tiles to t type.
            for(i=0; i<5; i++){
                if(this.myShelf.tilesShelf[i][j]==c){
                    this.myShelf.tilesShelf[i][j]=t;
                }
            }
        } t++;
    }
}
