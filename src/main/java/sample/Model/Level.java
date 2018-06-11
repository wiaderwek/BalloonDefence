package sample.Model;

import sample.View.View;

import java.io.*;
import java.util.ArrayList;

public class Level {
    private static int BalloonsForEachWave[][];                         //table with number of each balloon for each wave
    private static ArrayList<int[]> BalloonsForEachWaveInBalloonRush;
    private static int LevelNumber = 1;                                 //actula level number
    private static int DifficultyLevel;                                 //actual difficulty
    private static int GameType;                                        //choosen game mode

    public void loadLevel(int level){
        LevelNumber = level;
        GameType = level==0 ? 0 : 1;
        if(level==0){
            LoadBalloonRushProperties();
        }
        else if(level==1){
            loadLevlProperties(new File("target\\classes\\FirstLevel.txt"));
        }
        else if(level==2){
            loadLevlProperties(new File("target\\classes\\FirstLevel.txt"));
        }
        else if(level==3){
            loadLevlProperties(new File("target\\classes\\FirstLevel.txt"));
        }

        LoadDifficultyLevel();

    }

    //loading number of waves and number of each balloon for each wave
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

    //loading the properties to generate balloons for waves in BalloonRuch mode
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

    //creating list with all the balloon to spawn for each wave in Classic mode
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

    //creating list with all the balloon to spawn for each wave in BalloonRush mode
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

    //loading difficulty level from the file
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
