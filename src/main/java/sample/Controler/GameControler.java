package sample.Controler;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Model.*;
import sample.View.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
// refactoring guru
public class GameControler {
    private static Model model = new Model();
    private static Player CurentPlayer;
    private static View view = new View();
    private static Map map = new Map();
    private Stage PrimaryStage;
    private static AnimationTimer Movetimer;
    private static ArrayList<Balloon> BalloonList = new ArrayList<Balloon>();
    private static ArrayList<Balloon> ActualBalloonList = new ArrayList<Balloon>();
    private static boolean isStopped = false;
    private static Level Level;
    private static LevelControler LevelControler;

    public void setMap(int i, String name){
        Level = new Level();
        LevelControler = new LevelControler();
        model = new Model();
        CurentPlayer = new Player();
        view = new View();
        map = new Map();
        BalloonList = new ArrayList<Balloon>();
        ActualBalloonList = new ArrayList<Balloon>();
        if(i == 1){
            map.setMap(new File("target\\classes\\FirstLevelMap.txt"));
            view.set(new File("target\\classes\\FirstLevelMap.txt"), name);
            Level.loadLevel(1);
        }
        else if(i==2){
            map.setMap(new File("target\\classes\\SecondLevelMap.txt"));
            view.set(new File("target\\classes\\SecondLevelMap.txt"), name);
            Level.loadLevel(1);
        }
        else if(i==3){
            //map.setMap(new File("target\\classes\\ThirdLevelMap.txt"));
            view.set(new File("target\\classes\\ThirdLevelMap.txt"), name);
            Level.loadLevel(1);
        }

        LevelControler.setView(view);
        LevelControler.setLevel(Level);

        for(i=0; i<Level.getNumberOfFirstTypeBalloon(2);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.RED, view.getStartTile().get(i%view.getStartTile().size()), view.getTileMap());
            BalloonList.add(balloon);
        }

        for(i=0; i<Level.getNumberOfSecondTypeBalloon(2);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.GREEN, view.getStartTile().get(i%view.getStartTile().size()), view.getTileMap());
            BalloonList.add(balloon);
        }

        for(i=0; i<Level.getNumberOfFirstTypeBalloon(2);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.PINK, view.getStartTile().get(i%view.getStartTile().size()), view.getTileMap());
            BalloonList.add(balloon);
        }

        model.setMap(map);
        model.setPlayer(name);
        LevelControler.spawn(BalloonList);
        LevelControler.move();
        LevelControler.start();
        //move();
        goToShop();
        //Spawntimer.start();
        //Movetimer.start();

        view.showGameScene();

    }

    private void goToShop(){
        for(int i=0; i<map.getMapSize()*map.getMapSize(); i++) {
            Tile tile = (Tile) view.getTileGroup().getChildren().get(i);
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (tile.getTypeOfTile() == Tile.TileType.CLOUD) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            //LevelControler.stop();
                            //Spawntimer.stop();
                            //Movetimer.stop();
                            view.WhichTower(tile.getxPosition(), tile.getyPosition());

                            //Spawntimer.start();
                            //Movetimer.start();
                            //LevelControler.start();
                        }
                    }
                }

            });
        }
    }

    /*
    private void spawn(ArrayList<Balloon> balloons ){
        Spawntimer = new AnimationTimer() {
            long Lasttime = 0;
            int i =0;
            @Override
            public void handle(long now) {
                if((now-Lasttime)/2 > 1000000000){
                    if(i<BalloonList.size()) {
                        Balloon balloon = BalloonList.get(i);
                        i++;
                        view.DrawBalloon(balloon);
                        ActualBalloonList.add(balloon);
                        Lasttime = now;
                    }
                }
            }

        };
    }
       */
    /*
    private void move(){
        Movetimer = new AnimationTimer() {
            long LasttimeMove = 0;
            @Override
            public void handle(long now) {
                if (now - LasttimeMove > 1000000) {
                    for (Iterator<Balloon> iterator = ActualBalloonList.iterator(); iterator.hasNext();) {
                        Balloon balloon = iterator.next();
                        if(balloon.xPosition/64 != view.getEndTile().xPosition || balloon.yPosition/64 != view.getEndTile().yPosition) {
                            balloon.move();
                        }
                        else{
                            iterator.remove();
                            view.removeBalloon(balloon);
                            view.missed();
                        }
                    }
                    LasttimeMove = now;
                }
            }
        };
    }
    */
    public static void stopTimers(){
        //Spawntimer.stop();
        //Movetimer.stop();
        LevelControler.stop();
        isStopped = true;
    }

    public static void StopPlay(){
        if(isStopped){
            //Spawntimer.start();
            //Movetimer.start();
            LevelControler.start();
            isStopped = false;
        }else{
            stopTimers();
        }
    }

    public static void endOfTheGame() throws Throwable {
        model = null;
        CurentPlayer = null;
        map.clear();
        view.clear();
        BalloonList.clear();
        ActualBalloonList.clear();
        LevelControler.finish();
        Level = null;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("obj żegna!");
        super.finalize();
    }




}
