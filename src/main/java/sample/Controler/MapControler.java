package sample.Controler;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import sample.Model.Map;
import sample.Model.Player;
import sample.Model.Tile;
import sample.Model.Tower;

import java.awt.*;
import java.io.File;

public class MapControler {
    private Map Map;
    private int MAP_SIZE;
    private Tile[][] TileMap;
    private Player Player;

    Group TileGroup = new Group();
    Group TowerGroup = new Group();

    //descriptor of Map.map table -- needed to create TileMap
    private static final char Path = 'p';
    private static final char Start = 's';
    private static final char End = 'e';
    private static final char Cloud = 'c';

    public MapControler(File MapDescriptor, String Name, int DifficultyLevel){
        Map = new Map(MapDescriptor);
        Player = new Player(Name, DifficultyLevel);
    }

    public Parent CreateGameMap(){
        Pane root = new Pane();
        root.getChildren().addAll(TileGroup, TowerGroup);

        MAP_SIZE=Map.getMapSize();
        TileMap = new Tile[MAP_SIZE][MAP_SIZE];

        for(int i=0; i<MAP_SIZE; i++){
            for(int j=0; j<MAP_SIZE; j++){
                Tile tile;
                if(Map.getCharFromMap(i,j) == Path){
                    tile = new Tile(Tile.TileType.SKY, i, j);
                }
                else if(Map.getCharFromMap(i,j) == Start){
                    tile =  new Tile(Tile.TileType.START, i, j);
                }
                else if(Map.getCharFromMap(i,j) == End){
                    tile = new Tile(Tile.TileType.END, i, j);
                }
                else if(Map.getCharFromMap(i,j)== Cloud){
                    tile = new Tile(Tile.TileType.CLOUD, i, j);
                }
                else{
                    tile = tile = new Tile(Tile.TileType.SKY, i, j);
                }
                TileGroup.getChildren().add(tile);
                TileMap[i][j] = tile;
                int finalI = i;
                int finalJ = j;
                tile.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        if(TileMap[finalI][finalJ].getTypeOfTile()== Tile.TileType.CLOUD && t.getButton()== MouseButton.SECONDARY) {
                            WhichTower(finalI,finalJ);
                        }

                    }
                });
            }
        }
        return root;
    }


    private void buyTower(Tower.TowerType type, int row, int column){
        Tower tower = new Tower(type, row, column);
        if(tower.getCost() <= Player.getGold() ){
            TowerGroup.getChildren().add(tower);
            Player.Buy(tower.getCost());
        }

        /*
        tower.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY) {
                    TowerGroup.getChildren().remove(tower);
                }
            }

        });
        */

    }

    private void WhichTower(int row, int column){

        Pane root = new Pane();
        Background back;
        try {                                               //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("OptionsBackground.png").toString());     //creating image with OptionsBackground.png
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){             //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }

        root.setBackground(back);

        Tower FirstTypeTower = new Tower(Tower.TowerType.FIRST, 1, 1);
        Tower SecondTypeTower = new Tower(Tower.TowerType.SECOND, 3, 1);
        Tower ThirdTypeTower = new Tower(Tower.TowerType.THIRD, 5 ,1);

        FirstTypeTower.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(t.getButton()== MouseButton.PRIMARY){
                    closeWindow(FirstTypeTower);
                    buyTower(FirstTypeTower.getTypeOfTower(), row, column);
                }
            }
        });

        //buy tower when one is choosen
        SecondTypeTower.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(t.getButton()== MouseButton.PRIMARY){
                    buyTower(SecondTypeTower.getTypeOfTower(), row, column);
                    closeWindow(SecondTypeTower);
                }
            }
        });

        ThirdTypeTower.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(t.getButton()== MouseButton.PRIMARY){
                    buyTower(ThirdTypeTower.getTypeOfTower(), row, column);
                    closeWindow(ThirdTypeTower);
                }
            }
        });

        Group TowerSelectGroup = new Group();
        TowerSelectGroup.getChildren().addAll(FirstTypeTower, SecondTypeTower, ThirdTypeTower);

        root.getChildren().add(TowerSelectGroup);

        show(root);

    }

    private void closeWindow(Node node){
        Stage stage = (Stage) node.getScene().getWindow(); // getting the actual stage
        stage.close();                                        //closing window
    }

    private void show(Parent parent){
        Scene scene = new Scene(parent,741,741);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
