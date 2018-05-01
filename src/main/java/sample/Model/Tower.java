package sample.Model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tower extends Rectangle{
    private static final double TOWER_SIZE = 64;            //tile's with tower size (height and width)
    public int xPosition;                                   //number of column of the tile with Tower
    public int yPosition;                                   //number of row of the tile with Tower
    private TowerType TypeOfTower;                          //Type of the Tower
    private int cost;                                       //cost of the tower
    private int damage;                                     //single hit damage
    private double speed;                                   //number of hits per second


    public enum TowerType{
        FIRST, SECOND, THIRD
    }

    public Tower(TowerType type, int row, int column){
        setWidth(TOWER_SIZE);
        setHeight(TOWER_SIZE);

        relocate(column*TOWER_SIZE, row*TOWER_SIZE);

        TypeOfTower = type;

        //setting the filling of the tower tile depends on TowerType
        if(type == TowerType.FIRST)
        {
            try {
                setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("FirstTypeTower.jpg").toString())));
            }
            catch (NullPointerException e){
                setFill(Color.GREY);
            }
        }
        else if(type == TowerType.SECOND){
            try {
                setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("SecondTypeTower.jpg").toString())));
            }
            catch (NullPointerException e){
                setFill(Color.GREEN);
            }
        }
        else{
            try {
                setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("ThirdTypeTower.jpg").toString())));
            }
            catch (NullPointerException e){
                setFill(Color.RED);
            }
        }

        xPosition = column;
        yPosition = row;
    }

    public TowerType getTypeOfTower() {
        return TypeOfTower;
    }
}
