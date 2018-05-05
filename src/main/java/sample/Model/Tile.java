package sample.Model;

import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
    private static final double TILE_SIZE = 64.0;                    //Tile's width and height
    public int xPosition;                                            //column position
    public int yPosition;                                            //row position
    private TileType TypeOfTile;

    //type of the tile
    // SKY - path for the balloons or place where player can not build tower
    //CLOUD - place for the towers
    //START - Tile where balloons are spawned
    //END - Tile where balloons disappear
    public enum TileType{
        SKY, CLOUD, START, END
    }

    //constructor of the Tile
    public Tile(TileType type, int row, int column){
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);

        relocate(column*TILE_SIZE, row*TILE_SIZE);

        TypeOfTile  = type;


        yPosition = row;
        xPosition = column;

    }

    public TileType getTypeOfTile() {
        return TypeOfTile;
    }

    public int getxPosition(){
        return xPosition;
    }

    public int getyPosition(){
        return yPosition;
    }

    public void set(int column, int row){
        relocate(column*TILE_SIZE, row*TILE_SIZE);

        yPosition = row;
        xPosition = column;
    }


}
