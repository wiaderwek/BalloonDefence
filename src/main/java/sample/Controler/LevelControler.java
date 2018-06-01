package sample.Controler;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sample.Model.*;
import sample.View.View;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelControler {
    private static sample.Model.Level Level;
    private static View view;
    private static AnimationTimer Spawntimer;
    private static AnimationTimer Movetimer;
    private static AnimationTimer Bullettimer;
    private static AnimationTimer ShootTimer;
    //private static ArrayList<AnimationTimer> TowerTimers;
    private static ArrayList<Balloon> BalloonList = new ArrayList<Balloon>();
    private static ArrayList<Balloon> ActualBalloonList = new ArrayList<Balloon>();
    private static int NumOfKilledBalloons = 0;
    private static int ActualWave = 0;

    public static void setView(View V) {
        view = V;
    }

    public static void setLevel(Level L){
        Level = L;
    }

    public void spawn(){
        spawn(Level.getBalloonListforWave(ActualWave+1));
    }

    public void spawn(ArrayList<Balloon> balloons ){
        Spawntimer = new AnimationTimer() {
            long Lasttime = 0;
            int i =0;
            @Override
            public void handle(long now) {
                if((now-Lasttime)/2 > 1000000000){
                    if(i<balloons.size()) {
                        Balloon balloon = balloons.get(i);
                        i++;
                        view.DrawBalloon(balloon);
                        ActualBalloonList.add(balloon);
                        Lasttime = now;
                    }
                }
            }

        };
    }

    public void move(){
        Movetimer = new AnimationTimer() {
            long LasttimeMove = 0;
            @Override
            public void handle(long now) {

                long ActualNow = now;
                if(NumOfKilledBalloons < Level.getNumberOfBalloonsForWave(ActualWave+1)) {
                    for (Iterator<Balloon> iterator = ActualBalloonList.iterator(); iterator.hasNext(); ) {
                        Balloon balloon = iterator.next();
                        if ((ActualNow - LasttimeMove) > 1000000) {
                            for (int i = 0; i < balloon.getSpeed(); ++i) {
                                if (view.getTileInTileMap((int) (balloon.xPosition / 64), (int) (balloon.yPosition / 64)).getTypeOfTile() != Tile.TileType.END) {
                                    balloon.move();
                                } else {
                                    //iterator.remove();
                                    view.moveBalloonToStart(balloon);
                                    view.missed();
                                }
                            }
                        }
                    }
                    LasttimeMove = now;
                }
                else{
                    NumOfKilledBalloons = 0;
                    View.nextWave();
                    ActualWave+=1;
                    GameControler.stopTimers();
                    spawn();
                }
            }
        };
    }

    public void goToShop(){
        for(int i=0; i<view.getMap().getMapSize()*view.getMap().getMapSize(); i++) {
            Tile tile = (Tile) view.getTileGroup().getChildren().get(i);
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (tile.getTypeOfTile() == Tile.TileType.CLOUD) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            view.WhichTower(tile.getxPosition(), tile.getyPosition());
                        }
                    }
                }

            });
        }
    }

    public void shoot(){
        ShootTimer = new AnimationTimer() {
            long Lastshoot = 0;
            @Override
            public void handle(long now) {

                if(now-Lastshoot > 999999999){
                    for(Iterator<Tower> TowerIterator =  view.getTowerList().iterator(); TowerIterator.hasNext();){
                        Tower ActualTower = TowerIterator.next();
                        ActualTower.setTarget(ActualBalloonList);
                        Balloon target = ActualTower.getTarget();
                       if(target!=null){
                            Bullet bullet = new Bullet(ActualTower, target);
                            view.DrawBullet(bullet);
                        }
                    }
                    Lastshoot = now;
                }

            }
        };
    }

    public void moveBullets(){
        Bullettimer = new AnimationTimer() {
            long Lastmove = 0;
            @Override
            public void handle(long now) {
                if(now - Lastmove > 99999){
                    for(Iterator<Bullet> BulletIterator = view.getBulletList().iterator(); BulletIterator.hasNext();){
                        Bullet ActualBullet = BulletIterator.next();
                        if(ActualBullet.getTarget()!=null) {
                            if (ActualBullet.reachTarget()) {
                                ActualBullet.getTarget().getDamage(ActualBullet.getDamage());
                                BulletIterator.remove();
                                view.RemoveBullet(ActualBullet);
                                if (!ActualBullet.getTarget().alive()) {
                                    View.killBalloon();
                                    GameControler.addGold(ActualBullet.getTarget());
                                    ActualBalloonList.remove(ActualBullet.getTarget());
                                    view.removeBalloon(ActualBullet.getTarget());
                                    ++NumOfKilledBalloons;
                                }
                            } else {
                                if (!ActualBullet.getTarget().alive()) {
                                    BulletIterator.remove();
                                    view.RemoveBullet(ActualBullet);
                                } else {
                                    ActualBullet.move();
                                }
                            }
                        }
                        else {
                            BulletIterator.remove();
                            view.RemoveBullet(ActualBullet);

                        }
                    }
                    Lastmove = now;
                }
            }
        };
    }
    /*
    public void shoot(){
        int i=0;
        for(Iterator<AnimationTimer> timerIterator = TowerTimers.iterator(); timerIterator.hasNext();){
            AnimationTimer timer = timerIterator.next();
            timer = new AnimationTimer() {
                long lastShoot = 0;
                @Override
                public void handle(long now) {
                    if(now-lastShoot>10000){
                        lastShoot = now;
                        view.shot(int i);
                    }
                }
            };
            ++i;
        }
    }

    public void addTower(){
        AnimationTimer timer = new AnimationTimer() {
            long lastShoot = 0;
            @Override
            public void handle(long now) {
                if(now-lastShoot>10000){
                    lastShoot = now;
                    view.shot(int i);
                }
            }
        };
    }
    */
    public void start(){
        Movetimer.start();
        Spawntimer.start();
        ShootTimer.start();
        Bullettimer.start();
    }

    public void stop(){
        Spawntimer.stop();
        Movetimer.stop();
        ShootTimer.stop();
        Bullettimer.stop();
    }

    public void finish(){
        stop();
        NumOfKilledBalloons = 0;
        BalloonList.clear();
        ActualBalloonList.clear();
        view.clear();
    }



}
