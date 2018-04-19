package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        root.add(BackBtn, 1, 10);              //adding Back button to GridPane
        root.add(ApplyBtn, 10, 10);             //adding Apply button to GridPane
        root.setBackground(back);                                //adding background to GridPane

        Highscores();                                            //loading information about highscores

        return root;                                             //returning our GridPane
    }

    private void Highscores(){                                   //loading list of the best scores
        try {
            BufferedReader BufferReader =  new BufferedReader(new FileReader(new File("target\\classes\\Highscores.txt"))); //loadin file with highscores
            String FirstPlace = BufferReader.readLine();            //loading first place (name + score)
            String SecondPlace = BufferReader.readLine();           //loading second place (name + score)
            String ThirdPlace = BufferReader.readLine();            //loading third place (name + score)

            Label HighScores = new Label("Highscores: ");       //creating "Highscore" label
            Label Fst = new Label(FirstPlace);                      //creating label with first place
            Label Scnd = new Label(SecondPlace);                    //creating label with second place
            Label Thrd = new Label(ThirdPlace);                     //creating label with third place

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

            //if loading file causes problems
        } catch (FileNotFoundException e) {
            HighscoresProblems();
        //if there is no information (or not full) in file
        } catch(IOException e){
            HighscoresProblems();
        }


    }

    private void HighscoresProblems(){
        Label HighScores = new Label("Highscores: ");           //creating "Highscore" label
        Label Fst = new Label("Unknown");                       //creating label with first place
        Label Scnd = new Label("Unknown");                      //creating label with second place
        Label Thrd = new Label("Unknown");                      //creating label with third place

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
    public void start (Stage PrimaryStage){

        Scene scene = new Scene(CreateOptionsWindow(), 741, 741);         //creating scene with GridPane returned in CreateOptionsWindow()
        PrimaryStage.setTitle("Options");                                             //setting title of the stage
        PrimaryStage.setScene(scene);                                                 //adding scene to the stage
        PrimaryStage.setResizable(false);                                             //prevent from window resizing
        PrimaryStage.initStyle(StageStyle.UNDECORATED);                               //setting stage with no decorations
        PrimaryStage.show();                                                          //showing the stage

    }
}
