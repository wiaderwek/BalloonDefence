package sample.Model;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.*;

public class Map extends StackPane{
    private Tile [][] Tilemap = new Tile[10][10];
    private char[][] map = new char[10][10];
    private static final int MAP_SIZE = 10;


    public Map(File Mapdescriptor){
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

    }


    public int getMapSize(){
        return MAP_SIZE;
    }

    public char getCharFromMap(int row, int column){
        return map[row][column];
    }


}
