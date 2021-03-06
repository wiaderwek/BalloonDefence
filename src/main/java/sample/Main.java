package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;


public class Main extends Application {
    static SoundtrackPlayer SoundtrackPlayer = sample.SoundtrackPlayer.getOurInstance();
    GridPane root = new GridPane();
    @Override
    public void start(Stage primaryStage){

        Scene scene = new Scene(CreateMainWindow(), 626, 626);        //creating a scene
        primaryStage.setTitle("MainWindow");                                     //setting title of the scene
        primaryStage.setScene(scene);                                             //creating stage with our scene
        primaryStage.setResizable(false);                                         //prevent from window resizing
        primaryStage.initStyle(StageStyle.UNDECORATED);                           //setting stage with no decorations
        primaryStage.show();                                                      //showing the stage

        //SoundtrackPlayer = new SoundtrackPlayer();                                //creating new object of the SoundtrackPlayer to play the music
        SoundtrackPlayer.OnOffVolume(true);                                       //turning on the music
    }

    private Parent CreateMainWindow(){   //creating GridPane with main buttons
        Button PlayBtn = new Button("Play");            //creating "Play" button
        Button OptionBtn = new Button("Options");
        Button QuitBtn = new Button("Quit");

        PlayBtn.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));      //setting font, color, size and other features of the button
        PlayBtn.setStyle("-fx-background-color: transparent");
        PlayBtn.setTextFill(Color.ROYALBLUE);

        OptionBtn.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));
        OptionBtn.setStyle("-fx-background-color: transparent");
        OptionBtn.setTextFill(Color.CORAL);

        QuitBtn.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));
        QuitBtn.setStyle("-fx-background-color: transparent");
        QuitBtn.setTextFill(Color.LIMEGREEN);


        PlayBtn.setOnAction(event -> {

            ClassicOrBalloonRush ChoiceWindow = new ClassicOrBalloonRush();
            Stage stage = new Stage();
            ChoiceWindow.start(stage);
            /*
            Scene scene = new Scene(ChoiceWindow.CreateChoiceWindow());
            Stage stage = new Stage();
            stage.setTitle("ExampleGameScene");
            stage.setScene(scene);
            stage.show();
            */

        });

        //Option button cause opening options window
        OptionBtn.setOnAction(event -> {

            Options op = new Options(SoundtrackPlayer);
            Stage stage = new Stage();                                                  //creating new stage
            op.start(stage);
        });

        // Quit button cause closing the window
        QuitBtn.setOnAction(event -> {
            Stage stage = (Stage) QuitBtn.getScene().getWindow(); // getting the actual stage
            stage.close();                                        //closing window
        });


        SetBackground();
        root.setHgap(1);                                    //setting in width of the horizontal gaps between columns in GridPane
        root.setVgap(1);                                    //setting in height of the vertical gaps between rows in GridPane
        root.setPadding(new Insets(15, 25, 15, 25)); //setting margins around the whole grid

        root.add(PlayBtn,200,299,1,1);    //adding Play button
        root.add(OptionBtn,200,300,1,1);  //adding Option button
        root.add(QuitBtn,200,301);                      //adding Quit button

        return root;                 //returning our GridPane
    }

    private void SetBackground(){
        Background back;
        try {        //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("MainWindowBackground.jpg").toString()); // creating image with MainWindowBackground.jpg
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){         //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }

        root.setBackground(back);                           //adding background image
    }


    public static void main(String[] args) {
        launch(args);
    }
}
