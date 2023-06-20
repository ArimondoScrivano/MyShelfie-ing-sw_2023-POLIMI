package model;

import java.io.Serializable;

public class Tile implements Serializable {
   private COLOR color;
   private int id;


    /**
     * Constructs a new Tile with the specified color and ID.
     *
     * @param color the color of the tile
     * @param id    the ID of the tile
     */
    public Tile(COLOR color, int id) {
        this.color = color;
        this.id = id;
    }

    /**
     * Returns the color of the tile.
     *
     * @return the color of the tile
     */
    public COLOR getColor() {
        return color;
    }

    /**
     * Returns the ID of the tile.
     *
     * @return the ID of the tile
     */
    public int getId() {
        return id;
    }
}
