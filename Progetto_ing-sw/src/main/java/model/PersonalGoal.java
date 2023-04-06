package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class PersonalGoal {
    private int id;

    private Tile[][] layout;
    private List<Integer> points;
    //METHOD TO RETURN GOAL POINTS
    public PersonalGoal(int id){
        //Initializing the layout
        this.layout=new Tile[5][6];
        for(int i=0; i<5; i++){
            for(int j=0; j<6; j++){
                this.layout[i][j]=new Tile(COLOR.BLANK, 0);
            }
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        //Read from file json and construct the personal goal
        try {
            int i=1;
            FileReader reader = new FileReader("Progetto_ing-sw/src/main/java/model/jsonFiles/PersonalGoal"+i+".json");
            //as a list
            Type layoutListType=new TypeToken<ArrayList<Layout>>(){}.getType();
            List<Layout> layouts = gson.fromJson(reader, layoutListType);

            for(Layout l: layouts){
                this.layout[l.getTile().getX()][l.getTile().getY()]=new Tile(l.getTile().convert(), this.id);
            }
            for(Tile[] t : this.layout){
                System.out.println("new row");
                for(Tile t1 : t){
                    System.out.println(t1.getColor());
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Personal goal's id
        this.id=id;
        //Initializing the points' list
        this.points=Arrays.asList(1,2,4,6,9,12);
    }

    public int getId() {
        return id;
    }

    public Tile[][] getLayout() {
        return layout;
    }

    public List<Integer> getPoints() {
        return points;
    }

    //Da implementare in player
    public int checkPersonalGoal(Player myPlayer, int[][] tileTypes) throws IOException {
        //Creare indice come attributo della classe che identifica il punteggio del personal goal

        return 0;
    }
}