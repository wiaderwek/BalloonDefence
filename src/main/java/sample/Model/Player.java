package sample.Model;

public class Player {
    private String Nick;
    private int Gold;

    private static final int PRIMARY_GOLD = 100;

    public void setPlayer(String Name){
        Nick = Name;
        Gold = PRIMARY_GOLD;
    }

    public int getGold() {
        return Gold;
    }

    public void Buy(int cost){
        Gold-=cost;
    }

    public String getNick() {
        return Nick;
    }
}
