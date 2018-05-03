package sample.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.Controler.MapControler;
import sample.Model.Map;
import sample.Model.Player;
import sample.Model.Tile;
import sample.Model.Tower;

import java.io.File;

import static javafx.scene.input.KeyCode.V;


public class View extends Application {
    private static Stage PrimaryStage;
    private static Scene ActualScene;
    private static double Width;
    private static double Height;
    private static Map Map;

    Group TileGroup = new Group();
    Group TowerGroup = new Group();

    //descriptor of Map.map table -- needed to create TileMap
    private static final char Path = 'p';
    private static final char Start = 's';
    private static final char End = 'e';
    private static final char Cloud = 'c';

    private static  Tile[][] TileMap;
    private static Player Player;
    private static int MAP_SIZE;

    //creating labels with informations about player and game
    private Label PlayerNick;
    private Label Gold;
    private Label Wave;
    private Label Missed;

    public View(File MapDescriptor, String Name, int DifficultyLevel){
        Map = new Map();
        Map.setMap(MapDescriptor);
        Player = new Player();
        Player.setPlayer(Name, DifficultyLevel);
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
                set(tile);
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

    public Parent CreatePlayerPanel(){
        GridPane root = new GridPane();

        //initializing labels with informations about player and game
        PlayerNick = new Label(Player.getNick());
        Gold = new Label("Gold: " + Player.getGold());
        Wave = new Label(" Wave: " +" 1" + " of " + "4");
        Missed = new Label("Missed: " + "0" + " of " + "30");

        root.setBackground(getBackground());

        PlayerNick.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
        PlayerNick.setStyle("-fx-background-color: transparent");
        PlayerNick.setTextFill(Color.ROYALBLUE);

        Gold.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
        Gold.setStyle("-fx-background-color: transparent");
        Gold.setTextFill(Color.CORAL);

        Wave.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
        Wave.setStyle("-fx-background-color: transparent");
        Wave.setTextFill(Color.LIMEGREEN);

        Missed.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
        Missed.setStyle("-fx-background-color: transparent");
        Missed.setTextFill(Color.ROYALBLUE);

        root.add(PlayerNick, 1,1);
        root.add(Gold,1,2,2,1);
        root.add(Wave,1,3,2,1);
        root.add(Missed,1,4, 3,1);

        return root;
    }

    public void showGameScene(){
        AnchorPane GameScene = new AnchorPane();

        Pane GameMap = (Pane) CreateGameMap();
        GridPane PlayerPanel = (GridPane) CreatePlayerPanel();

        GameScene.getChildren().addAll(GameMap,PlayerPanel);

        GameScene.setRightAnchor(GameMap, 0.0);
        GameScene.setBottomAnchor(GameMap,0.0);
        GameScene.setTopAnchor(GameMap,0.0);


        GameScene.setLeftAnchor(PlayerPanel, 0.0);
        GameScene.setBottomAnchor(PlayerPanel,0.0);
        GameScene.setTopAnchor(PlayerPanel,0.0);
        GameScene.setRightAnchor(PlayerPanel,640.0);

        show(GameScene);
    }

    public void showGameScene(Parent GameMap, Parent PlayerPanel){
        AnchorPane GameScene = new AnchorPane();

        GameScene.getChildren().addAll(GameMap,PlayerPanel);

        GameScene.setRightAnchor(GameMap, 0.0);
        GameScene.setBottomAnchor(GameMap,0.0);
        GameScene.setTopAnchor(GameMap,0.0);


        GameScene.setLeftAnchor(PlayerPanel, 0.0);
        GameScene.setBottomAnchor(PlayerPanel,0.0);
        GameScene.setTopAnchor(PlayerPanel,0.0);
        GameScene.setRightAnchor(PlayerPanel,640.0);

        show(GameScene);
    }

    private void buyTower(Tower.TowerType type, int row, int column){
        Tower tower = new Tower(type, row, column);
        setFilling(tower);

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

    //setting filling of the tower
    private void setFilling(Tower tower){
        if(tower.getTypeOfTower() == Tower.TowerType.FIRST)
        {
            try {
                tower.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("FirstTypeTower.jpg").toString())));
            }
            catch (NullPointerException e){
                tower.setFill(Color.GREY);
            }
        }
        else if(tower.getTypeOfTower() == Tower.TowerType.SECOND){
            try {
                tower.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("SecondTypeTower.jpg").toString())));
            }
            catch (NullPointerException e){
                tower.setFill(Color.GREEN);
            }
        }
        else{
            try {
                tower.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("ThirdTypeTower.jpg").toString())));
            }
            catch (NullPointerException e){
                tower.setFill(Color.RED);
            }
        }
    }

    private void WhichTower(int row, int column){

        AnchorPane root = new AnchorPane();

        root.setBackground(getBackground());

        //Label FirstTypeTowerProperties = new Label("cost: " + new Tower(Tower.TowerType.FIRST).getCost());

        Tower FirstTypeTower = new Tower(Tower.TowerType.FIRST, 1, 1);
        setFilling(FirstTypeTower);

        Tower SecondTypeTower = new Tower(Tower.TowerType.SECOND, 3, 1);
        setFilling(SecondTypeTower);

        Tower ThirdTypeTower = new Tower(Tower.TowerType.THIRD, 5 ,1);
        setFilling(ThirdTypeTower);

        //creating labels with properties of the towers
        Label[] FirstTypeTowerCDS = new Label[3];
        FirstTypeTowerCDS[0] = new Label("cost: " + FirstTypeTower.getCost());
        FirstTypeTowerCDS[1] = new Label("damage: " + FirstTypeTower.getDamage());
        FirstTypeTowerCDS[2] = new Label("speed: " + FirstTypeTower.getSpeed());

        Label[] SecondTypeTowerCDS = new Label[3];
        SecondTypeTowerCDS[0] = new Label("cost: " + SecondTypeTower.getCost());
        SecondTypeTowerCDS[1] = new Label("damage: " + SecondTypeTower.getDamage());
        SecondTypeTowerCDS[2] = new Label("speed: " + SecondTypeTower.getSpeed());

        Label[] ThirdTypeTowerCDS = new Label[3];
        ThirdTypeTowerCDS[0] = new Label("cost: " + ThirdTypeTower.getCost());
        ThirdTypeTowerCDS[1] = new Label("damage: " + ThirdTypeTower.getDamage());
        ThirdTypeTowerCDS[2] = new Label("speed: " + ThirdTypeTower.getSpeed());

        for(int i=0; i<3; i++){
            FirstTypeTowerCDS[i].fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
            FirstTypeTowerCDS[i].setStyle("-fx-background-color: transparent");

            SecondTypeTowerCDS[i].fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
            SecondTypeTowerCDS[i].setStyle("-fx-background-color: transparent");

            ThirdTypeTowerCDS[i].fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
            ThirdTypeTowerCDS[i].setStyle("-fx-background-color: transparent");

        }

        FirstTypeTowerCDS[0].setTextFill(Color.ROYALBLUE);
        FirstTypeTowerCDS[1].setTextFill(Color.CORAL);
        FirstTypeTowerCDS[2].setTextFill(Color.LIMEGREEN);

        SecondTypeTowerCDS[0].setTextFill(Color.ROYALBLUE);
        SecondTypeTowerCDS[1].setTextFill(Color.CORAL);
        SecondTypeTowerCDS[2].setTextFill(Color.LIMEGREEN);

        ThirdTypeTowerCDS[0].setTextFill(Color.ROYALBLUE);
        ThirdTypeTowerCDS[1].setTextFill(Color.CORAL);
        ThirdTypeTowerCDS[2].setTextFill(Color.LIMEGREEN);

        VBox FirstTypeTowerProperties = new VBox();
        VBox SecondTypeTowerProperties = new VBox();
        VBox ThirdTypeTowerProperties = new VBox();

        FirstTypeTowerProperties.getChildren().addAll(FirstTypeTowerCDS);
        SecondTypeTowerProperties.getChildren().addAll(SecondTypeTowerCDS);
        ThirdTypeTowerProperties.getChildren().addAll(ThirdTypeTowerCDS);


        FirstTypeTower.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(t.getButton()== MouseButton.PRIMARY){
                    closeWindow(FirstTypeTower);
                    buyTower(FirstTypeTower.getTypeOfTower(), row, column);
                }
                UpdateGameScene();
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
                UpdateGameScene();
            }

        });

        ThirdTypeTower.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(t.getButton()== MouseButton.PRIMARY){
                    buyTower(ThirdTypeTower.getTypeOfTower(), row, column);
                    closeWindow(ThirdTypeTower);
                }
                UpdateGameScene();
            }
        });

        Group TowerSelectGroup = new Group();
        TowerSelectGroup.getChildren().addAll(FirstTypeTower, SecondTypeTower, ThirdTypeTower);

        root.getChildren().addAll(TowerSelectGroup, FirstTypeTowerProperties, SecondTypeTowerProperties, ThirdTypeTowerProperties);
        root.setTopAnchor(TowerSelectGroup, 10.0);
        root.setLeftAnchor(TowerSelectGroup, 10.0);
        root.setBottomAnchor(TowerSelectGroup, 10.0);


        root.setTopAnchor(FirstTypeTowerProperties, 10.0);
        root.setLeftAnchor(FirstTypeTowerProperties, 100.0);
        root.setRightAnchor(FirstTypeTowerProperties, 10.0);

        root.setTopAnchor(SecondTypeTowerProperties, 138.0);
        root.setLeftAnchor(SecondTypeTowerProperties, 100.0);
        root.setRightAnchor(SecondTypeTowerProperties, 10.0);

        root.setTopAnchor(ThirdTypeTowerProperties, 266.0);
        root.setLeftAnchor(ThirdTypeTowerProperties, 100.0);
        root.setRightAnchor(ThirdTypeTowerProperties, 10.0);

        show(root);

    }

    private void closeWindow(Node node){
        if(node != null) {
            Stage stage = (Stage) node.getScene().getWindow(); // getting the actual stage
            stage.close();                                        //closing window
        }
    }

    private void show(Parent parent){
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void set(Tile tile){

        //setting filling of the Tile
        if(tile.getTypeOfTile()== Tile.TileType.SKY || tile.getTypeOfTile()== Tile.TileType.START || tile.getTypeOfTile()== Tile.TileType.END ){                                          //if it is sky tile, sky.jpg is set as a fill
            try {
                tile.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("sky.jpg").toString())));
            }
            catch (NullPointerException e){
                tile.setFill(Color.DEEPSKYBLUE);
            }
        }
        else{                                                                                                                                                                            //if it is cloud tile, cloud.jpg is ste as a fill
            try{
                tile.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("cloud.jpg").toString())));
            }
            catch (NullPointerException e){
                tile.setFill(Color.FLORALWHITE);
            }
        }
    }

    private Background getBackground(){
        Background back;
        try {                                               //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("OptionsBackground.png").toString());     //creating image with OptionsBackground.png
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){             //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }
        return back;
    }

    void UpdateGameScene(){
        PlayerNick = new Label(Player.getNick());
        Gold = new Label("Gold: " + Player.getGold());
        Wave = new Label(" Wave: " +" 1" + " of " + "4");
        Missed = new Label("Missed: " + "0" + " of " + "30");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
