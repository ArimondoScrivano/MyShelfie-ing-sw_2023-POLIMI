package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The PersonalGoal class represents a personal goal in the game.
 * It implements the Serializable interface to support serialization and deserialization.
 */
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
            InputStream in = getClass().getResourceAsStream("/jsonFiles/PersonalGoal"+this.id+".json");
                 BufferedReader read = new BufferedReader(new InputStreamReader(in));
                // Use resource
                File f = new File("output.json");
                try (FileWriter fileWriter = new FileWriter(f);
                     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

                    String line;
                    while ((line = read.readLine()) != null) {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            FileReader reader = new FileReader(f);

            //as a list
            Type layoutListType = new TypeToken<ArrayList<Layout>>() {
            }.getType();
            List<Layout> layouts = gson.fromJson(reader, layoutListType);

            for (Layout l : layouts) {
                this.layout[l.getTile().getX()][l.getTile().getY()] = new Tile(l.getTile().convert(), this.id);
            }
            f.deleteOnExit();
            System.out.println("File deleted");
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