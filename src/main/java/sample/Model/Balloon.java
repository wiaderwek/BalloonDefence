package sample.Model;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.awt.*;

public class Balloon extends Circle{
    private static final double BALLOON_SIZE = 32;              //balloon size (radius)
    public double xPosition;                                    //x position of the balloon on the map
    public double yPosition;                                    //y position of the balloon on the map
    private BalloonType TypeOfBalloon;                          //Type of the balloon
    private int health;                                         //health points of the balloon
    private double speed;                                       //speed of the balloon
    private boolean isAlive;                                    //if this balloon is alive it's true
    private Tile SpawnTile;                                      //Tile on wich the balloon was spawned

    private Direction direction = null;

    private Tile[][] TileMap;

    private static final int PRIMARY_HEALTH = 20;
    private static final double PRIMARY_SPEED = 1;

    public enum BalloonType{
        RED, GREEN, PINK
    }

    public Balloon(BalloonType type, Tile Starttile, Tile[][] TileMap) {
        SpawnTile = Starttile;
        xPosition = Starttile.xPosition * 64;
        yPosition = Starttile.yPosition * 64;
        isAlive = true;

        setRadius(BALLOON_SIZE);
        relocate(xPosition, yPosition);

        //Setting right color for the balloon which depends on balloon type
        if (type == BalloonType.RED) {
            setFill(Color.RED);
            health = PRIMARY_HEALTH;
            speed = PRIMARY_SPEED;
        } else if (type == BalloonType.GREEN) {
            setFill(Color.LIMEGREEN);
            health = 2 * PRIMARY_HEALTH;
            speed = PRIMARY_SPEED;
        } else {
            setFill(Color.PINK);
            health = (int) (1.5 * PRIMARY_HEALTH);
            speed = 3 * PRIMARY_SPEED;
        }
        this.TileMap = TileMap;
        direction = null;
        setDirection();
    }

    public void move(int x, int y){
        xPosition += x;
        yPosition += y;
        relocate(xPosition, yPosition);
    }

    public void move() {
            setDirection();
            if (direction == Direction.UP) {
                move(0, -1);
            } else if (direction == Direction.DOWN) {
                move(0, 1);
            } else if (direction == Direction.RIGHT) {
                move(1, 0);
            } else if (direction == Direction.LEFT) {
                move(-1, 0);
            }

    }

    public BalloonType getBalloonType(){
        return TypeOfBalloon;
    }

    public enum Direction{
        UP, DOWN, LEFT, RIGHT;
    }

    private void setDirection(){
        if((xPosition)%64==0 && (yPosition)%64==0) {
            double x = Math.floor(xPosition / 64);
            double y = Math.floor(yPosition / 64);
            if (x == 0 && y == 0) {
                if ((TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.LEFT)
                    direction = Direction.RIGHT;
                else if ((TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.UP)
                    direction = Direction.DOWN;
            } else if (x == 0 && y == TileMap.length - 1) {
                if ((TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.LEFT)
                    direction = Direction.RIGHT;
                else if ((TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.DOWN)
                    direction = Direction.UP;
            } else if (x == TileMap.length - 1 && y == 0) {
                if ((TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                else if ((TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.UP)
                    direction = Direction.DOWN;
            } else if (x == TileMap.length - 1 && y == TileMap.length - 1) {
                if ((TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                else if ((TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.DOWN)
                    direction = Direction.UP;
            } else if (y == 0) {
                if ((TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                else if ((TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.UP)
                    direction = Direction.DOWN;
                else if ((TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.LEFT)
                    direction = Direction.RIGHT;
            } else if (x == 0) {
                if ((TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.UP)
                    direction = Direction.DOWN;
                else if ((TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.DOWN)
                    direction = Direction.UP;
                else if ((TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.LEFT)
                    direction = Direction.RIGHT;
            } else if(x == TileMap.length - 1) {
                if ((TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                else if ((TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.DOWN)
                    direction = Direction.UP;
                else if ((TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.UP)
                    direction = Direction.DOWN;
            } else if(y == TileMap.length - 1){
                if ((TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                else if ((TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.DOWN)
                    direction = Direction.UP;
                else if ((TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.LEFT)
                    direction = Direction.RIGHT;
            } else {
                if ((TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x - 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                else if ((TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y - 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.DOWN)
                    direction = Direction.UP;
                else if ((TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y][(int) x + 1].getTypeOfTile() == Tile.TileType.END) && direction != Direction.LEFT)
                    direction = Direction.RIGHT;
                else if ((TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.SKY || TileMap[(int) y + 1][(int) x].getTypeOfTile() == Tile.TileType.END) && direction != Direction.UP)
                    direction = Direction.DOWN;
            }
        }
    }

    public double getSpeed(){
        return speed;
    }

    public void set(double row, double col){
        xPosition=row*64;
        yPosition=col*64;
        relocate(xPosition,yPosition);
    }

    public Tile getSpawnTile(){
        return SpawnTile;
    }

}
