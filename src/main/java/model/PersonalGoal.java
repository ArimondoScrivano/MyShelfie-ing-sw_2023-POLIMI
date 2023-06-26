package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalGoal implements Serializable {
    private int id;

    private final Tile[][] layout;
    private final List<Integer> points;


    /**
     * Constructs a new PersonalGoal object with the specified ID.
     *
     * @param id the ID of the personal goal
     */
    public PersonalGoal(int id) {
        //Initializing the layout
        this.id = id;
        this.layout = new Tile[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                this.layout[i][j] = new Tile(COLOR.BLANK, 0);
            }
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        //Read from file json and construct the personal goal
        try {
            FileReader reader = new FileReader("src/main/resources/jsonFiles/PersonalGoal" + this.id + ".json");
            //as a list
            Type layoutListType = new TypeToken<ArrayList<Layout>>() {
            }.getType();
            List<Layout> layouts = gson.fromJson(reader, layoutListType);

            for (Layout l : layouts) {
                this.layout[l.getTile().getX()][l.getTile().getY()] = new Tile(l.getTile().convert(), this.id);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Personal goal's id
        this.id = id;
        //Initializing the points' list
        this.points = Arrays.asList(1, 2, 4, 6, 9, 12);
    }

    /**
     * Returns the ID of the personal goal.
     *
     * @return the ID of the personal goal
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the layout of the personal goal.
     *
     * @return the layout of the personal goal
     */
    public Tile[][] getLayout() {
        return layout;
    }

    /**
     * Returns the points associated with the specified index in the personal goal.
     *
     * @param i the index of the points to retrieve
     * @return the points associated with the specified index
     */
    public int getPoints(int i) {
        return this.points.get(i);
    }

}