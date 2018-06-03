package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.io.*;


public class Options extends Application {

    private GridPane root = new GridPane();                                   //creating GridPane
    private SoundtrackPlayer SoundPlayer;                                     //creating object SoundtrackPlayer to play music
    private boolean Volume;                                                   //actual volume (on/off)
    private boolean PrevVolume;                                               //volume after enter the options menu
    private int Difficulty;                                                   //actual difficulty level
    private int PrevDifficulty;                                               //difficulty level after enter options menu

    //copying SoundTrackPlayer created in Main and initialize variables
    Options(SoundtrackPlayer player){
        SoundPlayer=player;
        PrevVolume = player.getVolume() == 1;
        Volume=PrevVolume;
        Difficulty=PrevDifficulty;
    }
    public Parent CreateOptionsWindow() {

        Button ApplyBtn = new Button("Apply");          //creating Aplly button
        Button BackBtn = new Button("Back");            //Creating Back button

        BackBtn.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));  //setting font, color, size and other features of the button
        BackBtn.setStyle("-fx-background-color: transparent");
        BackBtn.setTextFill(Color.LIMEGREEN);

        ApplyBtn.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));
        ApplyBtn.setStyle("-fx-background-color: transparent");
        ApplyBtn.setTextFill(Color.CORAL);

        Background back;
        try {                                               //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("OptionsBackground.png").toString());     //creating image with OptionsBackground.png
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){             //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }

        //Apply button cause apllying options
        ApplyBtn.setOnAction(event -> {
            //after apply button we save new values of volume and difficulty
            PrevVolume=Volume;
            PrevDifficulty = Difficulty;

            //saving new StandardMode level
            try{
                PrintWriter SaveDifficulty = new PrintWriter(new FileWriter("target\\classes\\StandardModeLevel.txt"));
                SaveDifficulty.format("%d", PrevDifficulty);
                SaveDifficulty.close();
            }
            catch(FileNotFoundException e){
                System.out.println("coś nie tak");
            }
            catch (IOException e){
                System.out.println("Coś nie tego");
            }
        });

        // Quit button cause closing the window
        BackBtn.setOnAction(event -> {
            SoundPlayer.OnOffVolume(PrevVolume);                  //setting the PrevVolume value to the player
            Stage stage = (Stage) BackBtn.getScene().getWindow(); // getting the actual stage
            stage.close();                                        //closing window
        });

        root.setHgap(1);                                                  //setting in width of the horizontal gaps between columns in GridPane
        root.setVgap(1);                                                  //setting in height of the vertical gaps between rows in GridPane
        root.setPadding(new Insets(15, 25, 15, 25)); //setting margins around the whole grid

        root.add(BackBtn, 1, 40);              //adding Back button to GridPane
        root.add(ApplyBtn, 10, 40);             //adding Apply button to GridPane
        root.setBackground(back);                                //adding background to GridPane

        Highscores();                                            //loading information about highscores
        OnOffSound();                                            //adding slider to change volume value
        StandardModelLevel();

        return root;                                             //returning our GridPane
    }

    private void Highscores(){                                   //loading list of the best scores
        Label HighScores = new Label("Highscores: ");       //creating "Highscore" label
        Label Fst;                                              //creating label with first place
        Label Scnd;                                             //creating label with second place
        Label Thrd ;                                            //creating label with third place

        try {
            BufferedReader BufferReader =  new BufferedReader(new FileReader(new File("target\\classes\\Highscores.txt"))); //loadin file with highscores
            String FirstPlace = BufferReader.readLine();            //loading first place (name + score)
            String SecondPlace = BufferReader.readLine();           //loading second place (name + score)
            String ThirdPlace = BufferReader.readLine();            //loading third place (name + score)

            HighScores = new Label("Highscores: ");       //creating "Highscore" label
            Fst = new Label(FirstPlace);                      //creating label with first place
            Scnd = new Label(SecondPlace);                    //creating label with second place
            Thrd = new Label(ThirdPlace);                     //creating label with third place


            //if loading file causes problems
        } catch (FileNotFoundException e) {
            Fst = new Label("Unknown");                       //creating label with first place
            Scnd = new Label("Unknown");                      //creating label with second place
            Thrd = new Label("Unknown");                      //creating label with third place
        //if there is no information (or not full) in file
        } catch(IOException e){
            Fst = new Label("Unknown");                       //creating label with first place
            Scnd = new Label("Unknown");                      //creating label with second place
            Thrd = new Label("Unknown");                      //creating label with third place
        }

        HighScores.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));   //setting font, color, size and other features of the label
        HighScores.setStyle("-fx-background-color: transparent");
        HighScores.setTextFill(Color.LIMEGREEN);

        Fst.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 30));      //setting font, color, size and other features of the label
        Scnd.setStyle("-fx-background-color: transparent");
        Thrd.setTextFill(Color.ROYALBLUE);

        Scnd.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 30));     //setting font, color, size and other features of the label
        Scnd.setStyle("-fx-background-color: transparent");
        Scnd.setTextFill(Color.CORAL);

        Thrd.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 30));     //setting font, color, size and other features of the label
        Thrd.setStyle("-fx-background-color: transparent");
        Thrd.setTextFill(Color.LIMEGREEN);

        //adding labels to GridPane
        root.add(HighScores, 1, 1);
        root.add(Fst, 3,2);
        root.add(Scnd, 3,3);
        root.add(Thrd, 3,4);


    }


    private void OnOffSound(){

        Label VolumeLabel = new Label("Sound: ");

        VolumeLabel.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));     //setting font, color, size and other features of the label
        VolumeLabel.setStyle("-fx-background-color: transparent");
        VolumeLabel.setTextFill(Color.ROYALBLUE);

        //creating checkbx to turn on or off the sounf
        CheckBox VolumeOnOff[] = new CheckBox[2];
        VolumeOnOff[0] = new CheckBox("ON");
        VolumeOnOff[1] = new CheckBox("OFF");

        //setting the right checkbox selectef
        if(PrevVolume) {
            VolumeOnOff[0].setSelected(true);
            VolumeOnOff[1].setSelected(false);
        }else {
            VolumeOnOff[1].setSelected(true);
            VolumeOnOff[0].setSelected(false);
        }
        //setting font, color, size and other features of the chechbox
        for(int i=0; i<2; i++){
            VolumeOnOff[i].fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
            VolumeOnOff[i].setStyle("-fx-background-color: transparent");
        }

        VolumeOnOff[0].setTextFill(Color.CORAL);
        VolumeOnOff[1].setTextFill(Color.LIMEGREEN);

        for(int i=0; i<2; i++) {
            int finalI = i;
            VolumeOnOff[i].selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                if (new_val) {
                    VolumeOnOff[(finalI + 1) % 2].setSelected(!new_val);
                    VolumeOnOff[finalI].setSelected(new_val);
                    Volume = finalI == 0;
                    SoundPlayer.OnOffVolume(Volume);
                }
            });
        }

        HBox OnOff = new HBox();
        OnOff.getChildren().addAll(VolumeOnOff);
        root.add(VolumeLabel,1,15);
        root.add(OnOff, 3,15,5,1);




    }

    private void StandardModelLevel(){
        Label StadnadrModeLevelLabel = new Label("Difficulty:");

        StadnadrModeLevelLabel.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));     //setting font, color, size and other features of the label
        StadnadrModeLevelLabel.setStyle("-fx-background-color: transparent");
        StadnadrModeLevelLabel.setTextFill(Color.ROYALBLUE);
        try {
            BufferedReader BufferReader =  new BufferedReader(new FileReader(new File("target\\classes\\StandardModeLevel.txt"))); //loadin file with saved difficulty level
            int DifficultyLevel = Integer.parseInt(BufferReader.readLine());            //loading difficulty level
            PrevDifficulty = DifficultyLevel;
            BufferReader.close();
        } catch (FileNotFoundException e) {
            PrevDifficulty = 1;
        } catch (IOException e) {
            PrevDifficulty = 1;
        }

        CheckBox DifficultyLevels[] = new CheckBox[3];
        DifficultyLevels[0] = new CheckBox("EASY");
        DifficultyLevels[1] = new CheckBox("NORMAL");
        DifficultyLevels[2] = new CheckBox("HARD");

        DifficultyLevels[PrevDifficulty].setSelected(true);

        //setting font, color, size and other features of the chechbox
        for(int i=0; i<3; i++){
            DifficultyLevels[i].fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
            DifficultyLevels[i].setStyle("-fx-background-color: transparent");
        }

        DifficultyLevels[0].setTextFill(Color.ROYALBLUE);
        DifficultyLevels[1].setTextFill(Color.CORAL);
        DifficultyLevels[2].setTextFill(Color.LIMEGREEN);



        for(int i=0; i<3; i++) {
            int finalI = i;
            DifficultyLevels[i].selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                if (new_val) {
                    DifficultyLevels[(finalI + 1) % 3].setSelected(!new_val);
                    DifficultyLevels[(finalI + 2) % 3].setSelected(!new_val);
                    DifficultyLevels[finalI].setSelected(new_val);
                    Difficulty = finalI;
                }
            });
        }



        HBox Levels = new HBox();
        Levels.getChildren().addAll(DifficultyLevels);
        root.add(StadnadrModeLevelLabel,1,20, 2,1);
        root.add(Levels,3,20, 20, 1);
    }

    public void start (Stage PrimaryStage){

        Scene scene = new Scene(CreateOptionsWindow(), 741, 741);         //creating scene with GridPane returned in CreateOptionsWindow()
        PrimaryStage.setTitle("Options");                                             //setting title of the stage
        PrimaryStage.setScene(scene);                                                 //adding scene to the stage
        PrimaryStage.setResizable(false);                                             //prevent from window resizing
        PrimaryStage.initStyle(StageStyle.UNDECORATED);                               //setting stage with no decorations
        PrimaryStage.show();                                                          //showing the stage

    }
}
