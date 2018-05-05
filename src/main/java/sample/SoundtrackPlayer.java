package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundtrackPlayer {
    private static SoundtrackPlayer ourInstance = new SoundtrackPlayer();                       //only instance of the SoundtrackPlayer

    private static MediaPlayer player;                                                          //MediaPlayer class is to play the music

    private SoundtrackPlayer()
    {
        final String path = getClass().getResource("/soundtrack.mp3").toString();           //loading the path to the soundtrack.mp3

        try {
            player = new MediaPlayer(new Media(path));                                           //initializing MediaPlayer with soundtrack
            player.play();                                                                       //start playing the music
            player.setOnEndOfMedia(new Runnable() {                                              //repeat the music
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
        }
        catch(NullPointerException e){
        }
    }

    public static SoundtrackPlayer getOurInstance() {                                      //returning our only instance of the SoundtrackPlayer
        return ourInstance;
    }

    public void OnOffVolume(boolean OnOff){
        if(OnOff == true){
            player.setVolume(1);                                                           //turning on the soundtrack
        }
        else{
            player.setVolume(0);                                                           //turning off the soundtrack
        }

    }

    public int getVolume(){
        return (int) player.getVolume();
    }
}
