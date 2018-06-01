package sample.Model;

import sample.View.View;

import java.io.*;
import java.util.ArrayList;

public class Level {
    private static int BalloonsForEachWave[][];

    public void loadLevel(int level){
        if(level==1){
            loadLevlProperties(new File("target\\classes\\FirstLevel.txt"));
        }
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

    public static int getNumberOfFirstTypeBalloon(int wave){
        return BalloonsForEachWave[wave-1][0];
    }

    public static int getNumberOfSecondTypeBalloon(int wave){
        return BalloonsForEachWave[wave-1][1];
    }

    public static int getNumberOfThirdTypeBalloon(int wave){
        return BalloonsForEachWave[wave-1][2];
    }

    public static int getNumberOfBalloonsForWave(int wave){
        int NumberOfBalloons = (getNumberOfFirstTypeBalloon(wave) + getNumberOfSecondTypeBalloon(wave) + getNumberOfThirdTypeBalloon(wave));
        return NumberOfBalloons;
    }
    public static int getNumberOfWaves(){
        return BalloonsForEachWave.length;
    }

    public static ArrayList<Balloon> getBalloonListforWave(int wave){
        ArrayList<Balloon> BalloonsForWave = new ArrayList<Balloon>();

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
        return BalloonsForWave;
    }
}
