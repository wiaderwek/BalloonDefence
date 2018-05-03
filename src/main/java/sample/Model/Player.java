package sample.Model;

public class Player {
    private String Nick;
    private int Gold;

    public void setPlayer(String Name, int DifficultyLevel){
        Nick = Name;
        Gold = 100*DifficultyLevel;
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
