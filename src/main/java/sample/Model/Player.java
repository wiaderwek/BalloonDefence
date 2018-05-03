package sample.Model;

public class Player {
    private String Nick;
    private int Gold;

    public Player(String Name, int DifficultyLevel){
        Nick = Name;
        Gold = 100*DifficultyLevel;
    }

    public int getGold() {
        return Gold;
    }

    public void Buy(int cost){
        Gold-=cost;
    }
}
