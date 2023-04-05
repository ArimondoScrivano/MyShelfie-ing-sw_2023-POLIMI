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
    int id;
    private Tile[][] layout;
    private List<Integer> points;
    //METHOD TO RETURN GOAL POINTS
    public PersonalGoal(int id){
        this.layout=new Tile[5][6];
        for(int i=0; i<5; i++){
            for(int j=0; j<6; j++){
                this.layout[i][j]=new Tile(COLOR.BLANK, 0);
            }
        }
        this.id=1;
        this.points=new ArrayList();
        points.add(0, 1);
        points.add(1, 2);
        points.add(2, 4);
        points.add(3, 6);
        points.add(4, 9);
        points.add(5, 12);
    }
    public int checkPersonalGoal(Player myPlayer, int[][] tileTypes) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        //Read from file
        try {
            //as an array
            int i=1;
            FileReader reader = new FileReader("C:\\Users\\loren\\OneDrive\\Desktop\\ing-sw-2023-scrivano-vercelloni-pavesi-sartini\\Progetto_ing-sw\\src\\main\\java\\model\\jsonFiles\\PersonalGoal"+i+".json");
            //as a list
            Type layoutListType=new TypeToken<ArrayList<Layout>>(){}.getType();
            List<Layout> layouts = gson.fromJson(reader, layoutListType);

            for(Layout l: layouts){
                layout[l.getTile().getX()][l.getTile().getY()]=new Tile(l.getTile().convert(), this.id);
                /*System.out.println(l.getPosition());
                System.out.println(l.getTile().getX());
                System.out.println(l.getTile().getY());
                System.out.println(l.getTile().getColor());*/
                //System.out.println(l);
            }
            for(Tile[] t : layout){
                System.out.println("new row");
                for(Tile t1 : t){
                    System.out.println(t1.getColor());
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public static void main( String[] args ) throws IOException {
        PersonalGoal pg=new PersonalGoal(0);
        pg.checkPersonalGoal(new Player(0, "Lorenzo"), new int[5][6]);
    }
}