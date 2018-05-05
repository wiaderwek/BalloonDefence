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

public class GameControler {
    private Model model ;
    private Player CurentPlayer;
    private View view;
    private Map map;
    private Stage PrimaryStage;
    private AnimationTimer Spawntimer;
    private AnimationTimer Movetimer;
    private static ArrayList<Balloon> BalloonList = new ArrayList<Balloon>();
    private static ArrayList<Balloon> ActualBalloonList = new ArrayList<Balloon>();

    public void setMap(int i, String name){
        map = new Map();
        view = new View();
        model = new Model();
        if(i == 1){
            map.setMap(new File("target\\classes\\FirstLevelMap.txt"));
            view.set(new File("target\\classes\\FirstLevelMap.txt"), name);
        }
        else if(i==2){
            map.setMap(new File("target\\classes\\SecondLevelMap.txt"));
            view.set(new File("target\\classes\\SecondLevelMap.txt"), name);
        }
        else if(i==3){
            map.setMap(new File("target\\classes\\ThirdLevelMap.txt"));
            view.set(new File("target\\classes\\ThirdLevelMap.txt"), name);
        }

        for(i=0; i<10;++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.GREEN, view.getStartTile().xPosition, view.getStartTile().yPosition, view.getTileMap());
            BalloonList.add(balloon);
        }

        model.setMap(map);
        model.setPlayer(name);
        spawn(BalloonList);
        move();
        goToShop();
        Spawntimer.start();
        Movetimer.start();

        view.showGameScene();

    }

    private void goToShop(){
        for(int i=0; i<map.getMapSize()*map.getMapSize(); i++) {
            Tile tile = (Tile) view.getTileGroup().getChildren().get(i);
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        Spawntimer.stop();
                        Movetimer.stop();
                        view.WhichTower(tile.getxPosition(), tile.getyPosition());

                        Spawntimer.start();
                        Movetimer.start();
                    }
                }

            });
        }
    }

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




}
