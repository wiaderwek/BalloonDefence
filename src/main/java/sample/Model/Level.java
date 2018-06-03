package sample.Model;

import sample.View.View;

import java.io.*;
import java.util.ArrayList;

public class Level {
    private static int BalloonsForEachWave[][];
    private static ArrayList<int[]> BalloonsForEachWaveInBalloonRush;
    private static int LevelNumber = 1;
    private static int DifficultyLevel;
    private static int GameType;

    public void loadLevel(int level){
        LevelNumber = level;
        GameType = level==0 ? 0 : 1;
        if(level==0){
            LoadBalloonRushProperties();
        }
        else if(level==1){
            loadLevlProperties(new File("target\\classes\\FirstLevel.txt"));
        }

        LoadDifficultyLevel();

    }

    void loadLevlProperties(File LevelDescriptor){
        try{
            BufferedReader BufferReader =  new BufferedReader(new FileReader(LevelDescriptor));                 //loadin file with level descriptor
            int NumberOfWaves = Integer.parseInt(BufferReader.readLine());                                      //loading number of waves
            BalloonsForEachWave = new int[NumberOfWaves][3];
            for(int i=0; i<NumberOfWaves; ++i){
                BalloonsForEachWave[i][0]= Integer.parseInt(BufferReader.readLine());                           //loading number of first type balloons in each wave
                BalloonsForEachWave[i][1]= Integer.parseInt(BufferReader.readLine());                           //loading number of second type balloons in each wave
                BalloonsForEachWave[i][2]= Integer.parseInt(BufferReader.readLine());                           //loading number of third type balloons in each wave
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    void LoadBalloonRushProperties(){
        try{
            BufferedReader BufferReader = new BufferedReader(new FileReader(new File("target\\classes\\BalloonRushProperties.txt")));
            BalloonsForEachWaveInBalloonRush = new ArrayList<>();
            int RootBalloonsProperties[] = new int[3];

            RootBalloonsProperties[0] = Integer.parseInt(BufferReader.readLine());
            RootBalloonsProperties[1] = Integer.parseInt(BufferReader.readLine());
            RootBalloonsProperties[2] = Integer.parseInt(BufferReader.readLine());

            BalloonsForEachWaveInBalloonRush.add(RootBalloonsProperties);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static int getNumberOfFirstTypeBalloon(int wave){
        if(GameType==1) {
            return BalloonsForEachWave[wave - 1][0] * (DifficultyLevel + 1);
        }
        return wave*BalloonsForEachWaveInBalloonRush.get(0)[0];
    }

    public static int getNumberOfSecondTypeBalloon(int wave){
        if(GameType==1) {
            return BalloonsForEachWave[wave - 1][1] * (DifficultyLevel + 1);
        }
        return wave*BalloonsForEachWaveInBalloonRush.get(0)[1];
    }

    public static int getNumberOfThirdTypeBalloon(int wave){
        if(GameType==1) {
            return BalloonsForEachWave[wave - 1][2] * (DifficultyLevel + 1);
        }
        return wave*BalloonsForEachWaveInBalloonRush.get(0)[2];
    }

    public static int getNumberOfBalloonsForWave(int wave){
        int NumberOfBalloons = (getNumberOfFirstTypeBalloon(wave) + getNumberOfSecondTypeBalloon(wave) + getNumberOfThirdTypeBalloon(wave));
        return NumberOfBalloons;
    }
    public static int getNumberOfWaves(){
        if(GameType==1) {
            return BalloonsForEachWave.length;
        }
        return Integer.MAX_VALUE;
    }

    public static ArrayList<Balloon> getBalloonListforWave(int wave){
        ArrayList<Balloon> BalloonsForWave = new ArrayList<Balloon>();

            if(GameType==1){
                getBalloonListForWaveInClassicGame(BalloonsForWave, wave);
            }else{
                getBalloonListForWaveInBalloonRush(BalloonsForWave, wave);
            }
        return BalloonsForWave;
    }

    public static void getBalloonListForWaveInClassicGame(ArrayList<Balloon> BalloonsForWave, int wave){
        for(int i=0; i<Level.getNumberOfFirstTypeBalloon(wave)*(DifficultyLevel+1);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.RED, View.getStartTile().get(i%View.getStartTile().size()), View.getTileMap());
            BalloonsForWave.add(balloon);
        }

        for(int i=0; i<Level.getNumberOfSecondTypeBalloon(wave)*(DifficultyLevel+1);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.GREEN, View.getStartTile().get(i%View.getStartTile().size()), View.getTileMap());
            BalloonsForWave.add(balloon);
        }

        for(int i=0; i<Level.getNumberOfThirdTypeBalloon(wave)*(DifficultyLevel+1);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.PINK, View.getStartTile().get(i%View.getStartTile().size()), View.getTileMap());
            BalloonsForWave.add(balloon);
        }
    }

    public static void getBalloonListForWaveInBalloonRush(ArrayList<Balloon> BalloonsForWave, int wave){
        for(int i=0; i<Level.getNumberOfFirstTypeBalloon(wave);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.RED, View.getStartTile().get(i%View.getStartTile().size()), View.getTileMap());
            BalloonsForWave.add(balloon);
        }

        for(int i=0; i<Level.getNumberOfSecondTypeBalloon(wave);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.GREEN, View.getStartTile().get(i%View.getStartTile().size()), View.getTileMap());
            BalloonsForWave.add(balloon);
        }

        for(int i=0; i<Level.getNumberOfThirdTypeBalloon(wave);++i){
            Balloon balloon = new Balloon(Balloon.BalloonType.PINK, View.getStartTile().get(i%View.getStartTile().size()), View.getTileMap());
            BalloonsForWave.add(balloon);
        }
    }


    public int getLevelNumber(){
        return LevelNumber;
    }

    private void LoadDifficultyLevel(){
        try {
            BufferedReader BufferReader =  new BufferedReader(new FileReader(new File("target\\classes\\StandardModeLevel.txt"))); //loadin file with saved difficulty level
            int Difficulty = Integer.parseInt(BufferReader.readLine());            //loading difficulty level
            DifficultyLevel = Difficulty;

        } catch (FileNotFoundException e) {
            DifficultyLevel = 1;
        } catch (IOException e) {
            DifficultyLevel = 1;
        }
    }

    public int getGameType(){
        return GameType;
    }
}
