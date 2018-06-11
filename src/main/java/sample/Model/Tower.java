package sample.Model;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

public class Tower extends Rectangle{
    private static final double TOWER_SIZE = 64;            //tile's with tower size (height and width)
    private int xPosition;                                   //number of column of the tile with Tower
    private int yPosition;                                   //number of row of the tile with Tower
    private TowerType TypeOfTower;                          //Type of the Tower
    private int cost;                                       //cost of the tower
    private int damage;                                     //single hit damage
    private double speed;                                   //number of hits per second
    private Balloon target;                                 //target of the tower

    private static final int PRIMARY_COST = 10;
    private static final int PRIMARY_DAMAGE = 10;
    private static final int PRIMARY_SPEED = 5;



    public enum TowerType{
        FIRST, SECOND, THIRD
    }

    public Tower(TowerType type, int row, int column){
        setWidth(TOWER_SIZE);
        setHeight(TOWER_SIZE);

        relocate(column*TOWER_SIZE, row*TOWER_SIZE);

        TypeOfTower = type;

        setProperties(type);


        xPosition = column;
        yPosition = row;
    }

    //setting properies of the tower
    private void setProperties(TowerType type){
        if(type == TowerType.FIRST) {
            cost=PRIMARY_COST;
            damage = PRIMARY_DAMAGE;
            speed = PRIMARY_SPEED;
        }
        else if(type == TowerType.SECOND){
            cost=2*PRIMARY_COST;
            damage = 2*PRIMARY_DAMAGE;
            speed = 1.5 *PRIMARY_SPEED;
        }
        else{
            cost=3*PRIMARY_COST;
            damage = (int) (2.5 * PRIMARY_DAMAGE);
            speed = 2 * PRIMARY_SPEED;
        }

    }

    public void set(int x, int y){
        xPosition = x;
        yPosition = y;
    }

    public TowerType getTypeOfTower() {
        return TypeOfTower;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage(){
        return damage;
    }

    public double getSpeed(){
        return speed;
    }

    //if our balloon is dead find another
    public void setTarget(ArrayList<Balloon> TargetList){
        if(target!=null){
            if(target.isAlive()){
                return;
            }
            else{
                FindNearestTarget(TargetList);
            }
        }
        else{
            FindNearestTarget(TargetList);
        }
    }

    //finding nearest balloon
    public void FindNearestTarget(ArrayList<Balloon> TargetList){
        double distance = 9999;

        double yPos = yPosition*64;
        double xPos = xPosition*64;

        for(Iterator<Balloon> iterator = TargetList.iterator(); iterator.hasNext();){
            Balloon balloon = iterator.next();
            if(target==null){
                target = balloon;
                distance = (Math.sqrt(Math.pow(target.yPosition-yPos,2.0) + Math.pow(target.xPosition - xPos,2.0)));
            } else{
                if(distance > (Math.sqrt(Math.pow(balloon.yPosition-yPos,2.0) + Math.pow(balloon.xPosition - xPos,2.0)))){
                    target = balloon;
                    distance = (Math.sqrt(Math.pow(balloon.yPosition-yPos,2.0) + Math.pow(balloon.xPosition - xPos,2.0)));
                }
            }
        }
    }



    public Balloon getTarget() {
        return target;
    }

    public int getxPosition(){
        return xPosition;
    }

    public int getyPosition(){
        return yPosition;
    }


}
