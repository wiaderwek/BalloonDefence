package sample.Model;

import java.io.*;

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
}
