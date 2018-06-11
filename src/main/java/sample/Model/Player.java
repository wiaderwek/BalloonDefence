package sample.Model;

public class Player {
    private static String Nick;                 //nick of the player
    private static int Gold;                    //gold of the player
    private static int Score;                   //score of the player

    private static final int PRIMARY_GOLD = 100;        //amount of gold on the begining

    public void setPlayer(String Name){
        Nick = Name;
        Gold = PRIMARY_GOLD;
        Score = 0;
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

    //getting the gold from killed balloon
    public void getGold(Balloon balloon){
        Gold+=balloon.getGold();
        Score += balloon.getGold();
    }


    public int getScore(){
        return Score;
    }
}
