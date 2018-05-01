package sample.Model;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.awt.*;

public class Balloon extends Circle{
    private static final double BALLOON_SIZE = 16;              //balloon size (radius)
    public double xPosition;                                    //x position of the balloon on the map
    public double yPosition;                                    //y position of the balloon on the map
    private BalloonType TypeOfBalloon;                          //Type of the balloon
    private int health;                                         //health points of the balloon

    public enum BalloonType{
        RED, GREEN, PINK
    }

    public Balloon(BalloonType type, double x, double y){
        xPosition = x;
        yPosition = y;

        setRadius(BALLOON_SIZE);
        relocate(x, y);

        //Setting right color for the balloon which depends on balloon type
        if(type == BalloonType.RED){
            setFill(Color.RED);
        }
        else if(type == BalloonType.GREEN){
            setFill(Color.LIMEGREEN);
        }
        else{
            setFill(Color.PINK);
        }
    }
}
