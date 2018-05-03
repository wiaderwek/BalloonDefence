package sample.Model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tower extends Rectangle{
    private static final double TOWER_SIZE = 64;            //tile's with tower size (height and width)
    private int xPosition;                                   //number of column of the tile with Tower
    private int yPosition;                                   //number of row of the tile with Tower
    private TowerType TypeOfTower;                          //Type of the Tower
    private int cost;                                       //cost of the tower
    private int damage;                                     //single hit damage
    private double speed;                                   //number of hits per second

    private static final int PRIMARY_COST = 10;
    private static final int PRIMARY_DAMAGE = 10;
    private static final int PRIMARY_SPEED = 2;


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

}
