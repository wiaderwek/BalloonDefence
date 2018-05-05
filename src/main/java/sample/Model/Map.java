package sample.Model;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Model.Tile.TileType;

import java.io.*;
import java.util.ArrayList;

public class Map extends StackPane{
    private static final int MAP_SIZE = 10;
    private static Tile[][] TileMap = new Tile[MAP_SIZE][MAP_SIZE];
    private static char[][] map = new char[MAP_SIZE][MAP_SIZE];

    private static final int GridHeight = MAP_SIZE;
    private static final int GridWidth = MAP_SIZE;

    private int xStartPosition;
    private int yStartPosition;

    //descriptor of Map.map table -- needed to create TileMap
    private static final char Path = 'p';
    private static final char Start = 's';
    private static final char End = 'e';
    private static final char Cloud = 'c';



    public void setMap(File Mapdescriptor){
        try {
            BufferedReader BufferReader =  new BufferedReader(new FileReader(Mapdescriptor));                 //loadin file with amp descriptor

            //loading map descriptor
            String temp;
            for(int i=0; i<MAP_SIZE; i++){
                temp = BufferReader.readLine();
                for(int j=0; j<temp.length(); j++){
                    map[i][j] = temp.charAt(j);
                }
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        CreateGameMap();

    }

    private void CreateGameMap(){
        for(int i=0; i<MAP_SIZE; i++){
            for(int j=0; j<MAP_SIZE; j++){
                Tile tile;
                if(map[i][j] == Path){
                    setTileToTileMap(TileType.SKY, i, j);
                }
                else if(map[i][j] == Start){
                    setTileToTileMap(Tile.TileType.START, i, j);
                    xStartPosition = i;
                    yStartPosition = j;
                }
                else if(map[i][j] == End){
                    setTileToTileMap(Tile.TileType.END, i, j);
                }
                else if(map[i][j]== Cloud){
                    setTileToTileMap(Tile.TileType.CLOUD, i, j);
                }
                else{
                    setTileToTileMap(Tile.TileType.SKY, i, j);
                }


            }
        }

    }



    private void setTileToTileMap (TileType type, int column, int row){
        Tile tile = new Tile(type, column, row);
        TileMap[column][row] = tile;

    }

    public Tile[][] getTileMap(){
        return TileMap;
    }


    public int getMapSize(){
        return MAP_SIZE;
    }



}
