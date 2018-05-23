package sample.Controler;

import javafx.animation.AnimationTimer;
import sample.Model.Balloon;
import sample.Model.Level;
import sample.View.View;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelControler {
    private static sample.Model.Level Level;
    private static View view;
    private static AnimationTimer Spawntimer;
    private static AnimationTimer Movetimer;
    private static ArrayList<Balloon> BalloonList = new ArrayList<Balloon>();
    private static ArrayList<Balloon> ActualBalloonList = new ArrayList<Balloon>();

    public static void setView(View V) {
        view = V;
    }

    public static void setLevel(Level L){
        Level = L;
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
                /*
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
                */
                long ActualNow = now;
                for (Iterator<Balloon> iterator = ActualBalloonList.iterator(); iterator.hasNext();) {
                    Balloon balloon = iterator.next();
                    if((ActualNow - LasttimeMove) > 1000000) {
                            for(int i=0; i<balloon.getSpeed(); ++i) {
                                if(balloon.xPosition / 64 != view.getEndTile().xPosition || balloon.yPosition / 64 != view.getEndTile().yPosition) {
                                    balloon.move();
                                }
                                else {
                                    //iterator.remove();
                                    view.moveBalloonToStart(balloon);
                                    view.missed();
                                }
                            }
                    }
                }
                LasttimeMove = now;
            }
        };
    }

    public void start(){
        Movetimer.start();
        Spawntimer.start();
    }

    public void stop(){
        Spawntimer.stop();
        Movetimer.stop();
    }

    public void finish(){
        Spawntimer.stop();
        Movetimer.stop();
        BalloonList.clear();
        ActualBalloonList.clear();
        view.clear();
    }



}
