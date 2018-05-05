package sample.Model;

import java.io.File;
import java.util.ArrayList;

public class Model {
    private Player CurentPlayer;
    private ArrayList<Tower> TowerList;
    private Map map;

    public ArrayList<Tower> getTowerList() {
        return TowerList;
    }

    public Player getCurrentPlayer() {
        return CurentPlayer;
    }

    public Map getMap() {
        return map;
    }

    public Model(){
        map = new Map();
        TowerList = new ArrayList<>();
        CurentPlayer = new Player();

    }

    public void setPlayer(String name){
        CurentPlayer.setPlayer(name);
    }

    public void loadMap(File MapDescriptor){
        map.setMap(MapDescriptor);
    }

    public void setMap(Map map){
        this.map = map;
    }
}
