package sample.Model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
    private static final double TILE_SIZE = 64.0;                    //Tile's width and height
    public int xPosition;                                      //column position
    public int yPosition;                                      //row position
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

        //setting filling of the Tile
        if(type==TileType.SKY || type==TileType.START || type==TileType.END ){                                          //if it is sky tile, sky.jpg is set as a fill
            try {
                setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("sky.jpg").toString())));
            }
            catch (NullPointerException e){
                setFill(Color.DEEPSKYBLUE);
            }
        }
        else{                                                                                                           //if it is cloud tile, cloud.jpg is ste as a fill
            try{
                setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("cloud.jpg").toString())));
            }
            catch (NullPointerException e){
                setFill(Color.FLORALWHITE);
            }
        }

        yPosition = row;
        xPosition = column;
    }


    public TileType getTypeOfTile() {
        return TypeOfTile;
    }
}
