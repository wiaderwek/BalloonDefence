package sample.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.StageStyle;
import sample.Controler.GameControler;
import sample.Model.*;

import java.io.File;
import java.util.ArrayList;

import static sample.Model.Tile.TileType.*;


public class View extends Application {
    private static Stage PrimaryStage = new Stage();
    private static Scene ActualScene;
    private static double Width;
    private static double Height;
    private static Map map;
    private static Model model;

    private static Pane GameMap;


    private static Group TileGroup = new Group();
    private static Group TowerGroup = new Group();
    private static Group BalloonGroup = new Group();

    static{
        GameMap = new Pane();
        GameMap.getChildren().addAll(TileGroup, TowerGroup, BalloonGroup);
    }

    private static int NumberOfBalloons = 0;

    private static boolean Shopping = false;
    private int rowShoping;
    private int colShoping;
    private static Scene Shopscene;
    private static Stage Shopstage;


    private static  Tile[][] TileMap;
    private static Player Player;
    private static int MAP_SIZE;
    private static ArrayList<Tile> StartTile = new ArrayList<Tile>();
    private static ArrayList<Tile> EndTile = new ArrayList<Tile>();

    //creating labels with informations about player and game
    private Label PlayerNick;
    private Label Gold;
    private Label Wave;
    private Label Missed;

    private int NumberOfWaves;
    private static int ActualWave = 1;
    private static int NumberOfMissed = 0;
    private int MaxMissed;

    public void set(File MapDescriptor, String Name){
        map = new Map();
        map.setMap(MapDescriptor);
        Player = new Player();
        Player.setPlayer(Name);
        model = new Model();
        model.loadMap(MapDescriptor);
        model.setPlayer(Name);
        //GameMap.getChildren().addAll(TileGroup, TowerGroup, BalloonGroup);
        DrawMap(map);
    }

    public void DrawMap(Map map){
        String tileName = null;
        TileMap = new Tile[map.getMapSize()][map.getMapSize()];
        for(int i=0; i<map.getMapSize(); i++){
            for(int j=0; j<map.getMapSize(); j++) {
                Tile tile = map.getTileMap()[i][j];
                switch (tile.getTypeOfTile()) {
                    case START:
                        tileName = "sky.jpg";
                        StartTile.add(tile);
                        break;
                    case END:
                        tileName = "sky.jpg";
                        EndTile.add(tile);
                        break;
                    case SKY:
                        tileName = "sky.jpg";
                        break;
                    case CLOUD:
                        tileName = "cloud.jpg";
                        break;
                }
                TileMap[i][j] = tile;
                Image ImageToTile = new Image(getClass().getClassLoader().getResource(tileName).toString());
                if (ImageToTile != null) {
                    tile.setFill(new ImagePattern(ImageToTile));
                }
                TileGroup.getChildren().add(tile);
            }
        }
    }

    public void DrawBalloon(Balloon balloon){
        if(balloon.getBalloonType() == Balloon.BalloonType.GREEN){
            balloon.setFill(Color.LIMEGREEN);
        }
        else if(balloon.getBalloonType() == Balloon.BalloonType.RED){
            balloon.setFill(Color.RED);
        }
        else if(balloon.getBalloonType() == Balloon.BalloonType.PINK){
            balloon.setFill(Color.PINK);
        }
        BalloonGroup.getChildren().add(balloon);
        ++NumberOfBalloons;

    }

    public void init(Stage stage){
        PrimaryStage=stage;
        PrimaryStage.initStyle(StageStyle.UNDECORATED);
    }

    private Parent CreatePlayerPanel(){
        GridPane root = new GridPane();

        //initializing labels with informations about player and game
        Label PlayerNick = new Label(Player.getNick());
        Label Gold = new Label("Gold: " + Player.getGold());
        Label Wave = new Label(" Wave: " +ActualWave + " of " + NumberOfWaves);
        Label Missed = new Label("Missed: " + NumberOfMissed + " of " + MaxMissed);
        Button Finish = new Button("End");

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

        Finish.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
        Finish.setStyle("-fx-background-color: transparent");
        Finish.setTextFill(Color.CORAL);

        Finish.setOnAction(event -> {
            Stage stage = (Stage) Finish.getScene().getWindow(); // getting the actual stage
            stage.close();
            GameControler.StopPlay();
            try {
                GameControler.endOfTheGame();
            }
            catch (Throwable throwable){

            }
            UpdateGameScene();

        });

        root.add(PlayerNick, 1,1);
        root.add(Gold,1,2,2,1);
        root.add(Wave,1,3,2,1);
        root.add(Missed,1,4, 3,1);
        root.add(Finish,1,8,1,1);

        return root;
    }



    /*
    tile.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        if(TileMap[finalI][finalJ].getTypeOfTile()== Tile.TileType.CLOUD && t.getButton()== MouseButton.SECONDARY) {
                            WhichTower(finalI,finalJ);
                        }

                    }
                });
     */


    private Parent CreateGameScene(){
        AnchorPane GameScene = new AnchorPane();

        GridPane PlayerPanel = (GridPane) CreatePlayerPanel();

        GameScene.getChildren().addAll(GameMap,PlayerPanel);

        GameScene.setLeftAnchor(GameMap, 0.0);
        GameScene.setBottomAnchor(GameMap,0.0);
        GameScene.setTopAnchor(GameMap,0.0);


        GameScene.setRightAnchor(PlayerPanel, 0.0);
        GameScene.setBottomAnchor(PlayerPanel,0.0);
        GameScene.setTopAnchor(PlayerPanel,0.0);
        GameScene.setLeftAnchor(PlayerPanel,640.0);

        return GameScene;

    }


    public void showGameScene(){
        AnchorPane GameScene = new AnchorPane();

        GridPane PlayerPanel = (GridPane) CreatePlayerPanel();

        GameScene.getChildren().addAll(GameMap,PlayerPanel);

        GameScene.setLeftAnchor(GameMap, 0.0);
        GameScene.setBottomAnchor(GameMap,0.0);
        GameScene.setTopAnchor(GameMap,0.0);


        GameScene.setRightAnchor(PlayerPanel, 0.0);
        GameScene.setBottomAnchor(PlayerPanel,0.0);
        GameScene.setTopAnchor(PlayerPanel,0.0);
        GameScene.setLeftAnchor(PlayerPanel,640.0);

        show(GameScene);
    }


    private void buyTower(Tower.TowerType type, int row, int column){
            Tower tower = new Tower(type, column, row);
            setFilling(tower);

            if (tower.getCost() <= Player.getGold()) {
                TowerGroup.getChildren().add(tower);
                Player.Buy(tower.getCost());
                UpdateGameScene();
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

    public void WhichTower(int row, int column){
        Shopping = true;
        rowShoping = row;
        colShoping = column;

        if(Shopstage!=null) {
            Shopstage.close();
        }
        AnchorPane root = new AnchorPane();

        root.setBackground(getBackground());

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
                    Shopping=false;
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
                    Shopping=false;
                    buyTower(SecondTypeTower.getTypeOfTower(), row, column);
                    closeWindow(SecondTypeTower);
                }
            }

        });

        ThirdTypeTower.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t){
                if(t.getButton()== MouseButton.PRIMARY){
                    Shopping=false;
                    buyTower(ThirdTypeTower.getTypeOfTower(), row, column);
                    closeWindow(ThirdTypeTower);
                }
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

        showShop(root);

    }

    private void closeWindow(Node node){
        if(node != null) {
            Stage stage = (Stage) node.getScene().getWindow(); // getting the actual stage
            stage.close();                                        //closing window
        }
    }

    private void show(Parent parent){
        ActualScene = new Scene(parent);
        PrimaryStage.setScene(ActualScene);
        PrimaryStage.show();
    }

    private void showShop(Parent parent){
        Shopscene = new Scene(parent);
        Shopstage = new Stage();
        Shopstage.setScene(Shopscene);
        Shopstage.show();
    }

    private void showShop(){
        WhichTower(rowShoping,colShoping);
    }
    public void set(Tile tile){

        //setting filling of the Tile
        if(tile.getTypeOfTile()== SKY || tile.getTypeOfTile()== START || tile.getTypeOfTile()== END ){                                          //if it is sky tile, sky.jpg is set as a fill
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

    public void UpdateGameScene(){
        ActualScene = new Scene(CreateGameScene());
        PrimaryStage.setScene(ActualScene);
        if(Shopping){
            showShop();
        }
    }

    public static Map getMap(){
        return map;
    }


    public static int getMapSize() {
        return MAP_SIZE;
    }

    public static Player getPlayer(){
        return Player;
    }

    public static Group getTileGroup(){
        return TileGroup;
    }

    public static Group getBalloonGroup(){
        return BalloonGroup;
    }

    public static int getNumberOfBalloons(){
        return NumberOfBalloons;
    }

    public static ArrayList<Tile> getStartTile(){
        return StartTile;
    }

    public static ArrayList<Tile> getEndTile(){
        return EndTile;
    }

    public static void removeBalloon(Balloon balloon){
        BalloonGroup.getChildren().remove(balloon);
    }

    public static void moveBalloonToStart(Balloon balloon){
        balloon.set(balloon.getSpawnTile().xPosition, balloon.getSpawnTile().yPosition);
    }

    public void missed(){
        ++NumberOfMissed;
        this.UpdateGameScene();
        --NumberOfBalloons;
    }

    public Tile[][] getTileMap(){
        return TileMap;
    }

    public Tile getTileInTileMap(int x, int y){
        return TileMap[y][x];
    }

    public void clear(){
        PrimaryStage = new Stage();
        //ActualScene = new Scene();
        TileGroup = new Group();
        TowerGroup = new Group();
        BalloonGroup = new Group();
        GameMap = new Pane();
        GameMap.getChildren().addAll(TileGroup, TowerGroup, BalloonGroup);
        NumberOfBalloons = 0;
        ActualWave = 1;
        NumberOfMissed = 0;
        StartTile.clear();
        EndTile.clear();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
