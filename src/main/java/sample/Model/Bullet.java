package sample.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.View.View;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Bullet extends Circle{
    private static final int BULLET_SIZE = 4;
    private double xPosition;
    private double yPosition;
    private Tower source;
    private Balloon target;
    private int damage;
    private int speed;
    private double Distance;

    double txPosition;
    double tyPosition;

    public Bullet(Tower tower, Balloon balloon){
        source = tower;
        target = balloon;
        damage = tower.getDamage();
        speed = (int) tower.getSpeed();

        xPosition = (source.getxPosition()*64) + 32;
        yPosition = (source.getyPosition()*64) + 32;

        txPosition = balloon.xPosition+32;
        tyPosition = balloon.yPosition+32;


        Distance = Math.floor(Math.sqrt(Math.pow(xPosition - (target.xPosition+32), 2) + Math.pow(yPosition - (target.yPosition+32), 2)));

        setRadius(BULLET_SIZE);
        //System.out.println("X: " + (xPosition-32)/64 + " Y: " + (yPosition-32)/64);
        relocate(xPosition, yPosition);
        setFill(Color.BLACK);
    }

    public void move(){
        for(int i=0; i<speed; ++i) {

            double tyPosition = target.yPosition + 32;
            double txPosition = target.xPosition + 32;
            if (yPosition == tyPosition) {
                if (xPosition > txPosition) {
                    xPosition -= 1;
                } else {
                    xPosition += 1;
                }
                relocate(xPosition, yPosition);
            } else if (xPosition == txPosition) {
                if (yPosition > tyPosition) {
                    yPosition -= 1;
                } else {
                    yPosition += 1;
                }
                relocate(xPosition, yPosition);

            } else {
                //double a = (yPosition-tyPosition)/(xPosition-txPosition);
                //double b = tyPosition - (a*txPosition);

                double Xdistance = abs(xPosition - txPosition);
                double Ydistance = abs(yPosition - tyPosition);
                double distance = sqrt((Xdistance * Xdistance) + (Ydistance * Ydistance));

                double Ymove = (Ydistance/distance );
                double Xmove = (Xdistance/distance );
                //System.out.println("Xmove: "+Xmove + " Ymove: " + Ymove);

                int xdir;
                int ydir;
                if (xPosition > txPosition) {
                    xdir = -1;
                } else {
                    xdir = 1;
                }

                if (yPosition > tyPosition) {
                    ydir = -1;
                } else {
                    ydir = 1;
                }

                xPosition += xdir * Xmove;
                yPosition += ydir * Ymove;
                relocate(xPosition, yPosition);


            }
            //System.out.println("X: " + (xPosition-32)/64 + " Y: " + (yPosition-32)/64 + " move");

        }
    }

    public boolean reachTarget(){
        if(xPosition>=target.xPosition && xPosition<=(target.xPosition+target.getBalloonSize())
                && yPosition>=target.yPosition && yPosition<=(target.yPosition+target.getBalloonSize())){
            //System.out.println("reached " + View.getTowerList().indexOf(source));
            return true;
        }

        return false;
    }

    public Balloon getTarget(){
        return target;
    }

    public int getDamage(){
        return damage;
    }
}
