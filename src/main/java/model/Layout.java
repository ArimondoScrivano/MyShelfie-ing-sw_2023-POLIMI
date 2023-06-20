package model;

public class Layout {
    private String position;
    private TileLayout tile;

    /**
     * Constructs a new Layout object with the specified position and tile layout.
     *
     * @param position the position of the layout
     * @param tile the tile layout
     */
    public Layout(String position, TileLayout tile){
        this.position=position;
        this.tile=tile;
    }


    /**
     * Retrieves the position of the layout.
     *
     * @return the position of the layout
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the layout.
     *
     * @param position the position to be set for the layout
     */
    public void setPosition(String position) {
        this.position = position;
    }


    /**
     * Retrieves the tile of the layout.
     *
     * @return the tile of the layout
     */
    public TileLayout getTile() {
        return tile;
    }

    /**
     * Sets the tile of the layout.
     *
     * @param tile the tile to set
     */
    public void setTile(TileLayout tile) {
        this.tile = tile;
    }

    @Override
    public String toString() {
        return "Layout{" +
                "position='" + position + '\'' +
                ", tile=" + tile +
                '}';
    }
}
