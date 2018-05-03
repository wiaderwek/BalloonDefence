package sample.Model;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapBuilder extends Application{
    public static final int HEIGH = 10;
    public static final int WIDTH = 10;
    public static final int TILE_SIZE=64;
    public static final int[][] map={
            {0,-3,1,-3,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0},
            {0,0,1,2,3,3,0,0,0,0},
            {0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,1,-1,0,0,0},
            {0,0,0,0,0,1,-2,0,0,0},
            {0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,0,0},
    };

    private Group TileGroup = new Group();
    private Group TowerGroup = new Group();
    private Group BalloonGroup  =  new Group();

    public Parent CreateGameScene()
    {
        Pane root = new Pane();

        root.setPrefSize(WIDTH*TILE_SIZE, HEIGH*TILE_SIZE);
        root.getChildren().addAll(TileGroup, TowerGroup, BalloonGroup);
        for(int i=0; i<HEIGH; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                Tile tile;
                if(map[i][j]>0) {
                    tile = new Tile(Tile.TileType.SKY, i,j);
                    if(map[i][j]>1){
                        Balloon balloon;
                        if(map[i][j]==2){
                            balloon = new Balloon(Balloon.BalloonType.GREEN, j* TILE_SIZE, i*TILE_SIZE);
                        }
                        else if(map[i][j]==3){
                            balloon = new Balloon(Balloon.BalloonType.RED, j* TILE_SIZE, i*TILE_SIZE);
                        }
                        else{
                            balloon = new Balloon(Balloon.BalloonType.PINK, j* TILE_SIZE, i*TILE_SIZE);
                        }

                        BalloonGroup.getChildren().add(balloon);

                    }
                }
                else{
                    tile = new Tile(Tile.TileType.CLOUD, i, j);
                    if(map[i][j]<0) {
                        Tower tower;
                        if (map[i][j] == -1) {
                            tower = new Tower(Tower.TowerType.FIRST, i, j);
                        } else if (map[i][j] == -2) {
                            tower = new Tower(Tower.TowerType.SECOND, i, j);
                        } else {
                            tower = new Tower(Tower.TowerType.THIRD, i, j);
                        }
                        TowerGroup.getChildren().add(tower);
                    }
                }
                TileGroup.getChildren().add(tile);

            }
        }
        return root;
    }

    public void start (Stage PrimaryStage){

        Scene scene = new Scene(CreateGameScene());
        PrimaryStage.setTitle("ExampleGameScene");
        PrimaryStage.setScene(scene);
        PrimaryStage.show();

    }

    public void start (Stage PrimaryStage, Parent root){

        Scene scene = new Scene(root);
        PrimaryStage.setTitle("ExampleGameScene");
        PrimaryStage.setScene(scene);
        PrimaryStage.show();

    }

}
