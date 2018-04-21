package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
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
import java.util.Scanner;


public class Options extends Application {

    private GridPane root = new GridPane();                                   //creating GridPane
    int Volume;
    int Difficulty;



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

        });

        // Quit button cause closing the window
        BackBtn.setOnAction(event -> {
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
        Slider VolumeLevel = new Slider();                                                                 //creating slider to chagne volume's value
        VolumeLevel.setMin(0);                                                                             //setting the minimum level of the slider
        VolumeLevel.setMax(100);                                                                           //setting the maximum level of the slider
        VolumeLevel.setValue(50);                                                                          //setting the strating value of the slider

        Label VolumeLabel = new Label("Volume: ");
        Label VolumeValue = new Label(String.valueOf((int)VolumeLevel.getValue()));                        //creating label with the value of the slider

        VolumeLabel.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));     //setting font, color, size and other features of the label
        VolumeLabel.setStyle("-fx-background-color: transparent");
        VolumeLabel.setTextFill(Color.ROYALBLUE);

        VolumeValue.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 30));      //setting font, color, size and other features of the label
        VolumeValue.setStyle("-fx-background-color: transparent");
        VolumeValue.setTextFill(Color.ROYALBLUE);


        root.add(VolumeLabel,1,10);                                                     //adding VolumeLabel label to the GridPane
        root.add(VolumeLevel, 3,10);                                                    //adding slider to the GridPane
        root.add(VolumeValue, 10,10);                                                   //adding label with volume's value to the GridPane

        //updating VolumeValue label and Volume when value on the slider changed
        VolumeLevel.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                VolumeValue.textProperty().setValue(String.valueOf((int)VolumeLevel.getValue()));
                Volume = (int)VolumeLevel.getValue();

            }});

    }

    private void StandardModelLevel(){
        Label StadnadrModeLevelLabel = new Label("Difficulty:");

        StadnadrModeLevelLabel.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));     //setting font, color, size and other features of the label
        StadnadrModeLevelLabel.setStyle("-fx-background-color: transparent");
        StadnadrModeLevelLabel.setTextFill(Color.ROYALBLUE);
        try {
            BufferedReader BufferReader =  new BufferedReader(new FileReader(new File("target\\classes\\StandardModeLevel.txt"))); //loadin file with saved difficulty level
            int DifficultyLevel = Integer.parseInt(BufferReader.readLine());            //loading difficulty level
            Difficulty = DifficultyLevel;

        } catch (FileNotFoundException e) {
            Difficulty = 1;
        } catch (IOException e) {
            Difficulty = 1;
        }

        CheckBox DifficultyLevels[] = new CheckBox[3];
        DifficultyLevels[0] = new CheckBox("EASY");
        DifficultyLevels[1] = new CheckBox("NORMAL");
        DifficultyLevels[2] = new CheckBox("HARD");

        DifficultyLevels[Difficulty].setSelected(true);

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
