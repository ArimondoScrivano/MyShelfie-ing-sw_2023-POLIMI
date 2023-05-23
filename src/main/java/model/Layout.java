package model;

public class Layout {
    private String position;
    private TileLayout tile;

    public Layout(String position, TileLayout tile){
        this.position=position;
        this.tile=tile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TileLayout getTile() {
        return tile;
    }

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
