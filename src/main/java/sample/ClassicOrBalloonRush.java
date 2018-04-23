package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClassicOrBalloonRush extends Application {
    private GridPane root = new GridPane();

    public Parent CreateChoiceWindow(){
        //creating label and buttons to chose mode
        final Label Choice = new Label("Chose the play Mode!");
        final Button Classic=  new Button("Classic Mode");
        final Button BalloonRush = new Button("BalloonRush");

        Choice.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));      //setting font, color, size and other features of the button
        Choice.setStyle("-fx-background-color: transparent");
        Choice.setTextFill(Color.ROYALBLUE);

        Classic.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));
        Classic.setStyle("-fx-background-color: transparent");
        Classic.setTextFill(Color.CORAL);

        BalloonRush.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 48));
        BalloonRush.setStyle("-fx-background-color: transparent");
        BalloonRush.setTextFill(Color.LIMEGREEN);

        Classic.setOnAction(event -> {


        });

        BalloonRush.setOnAction(event -> {


        });


        SetBackground();

        root.setHgap(1);                                                  //setting in width of the horizontal gaps between columns in GridPane
        root.setVgap(1);                                                  //setting in height of the vertical gaps between rows in GridPane
        root.setPadding(new Insets(15, 25, 15, 25));  //setting margins around the whole grid

        root.add(Choice,20,1);                              //adding Play button
        root.add(Classic,1,100);                           //adding Option button
        root.add(BalloonRush,40,100);                      //adding Quit button

        return root;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(CreateChoiceWindow(), 100, 100);        //creating a scene
        primaryStage.setTitle("ChoiceWidnow");                                     //setting title of the scene
        primaryStage.setScene(scene);                                             //creating stage with our scene
        primaryStage.setResizable(false);                                         //prevent from window resizing
        primaryStage.initStyle(StageStyle.UNDECORATED);                           //setting stage with no decorations
        primaryStage.show();                                                      //showing the stage
    }

    private void SetBackground(){                                                                                       //addind background to the GridPane (root)
        Background back;
        try {        //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("OptionsBackground.png").toString()); // creating image with MainWindowBackground.jpg
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){         //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }

        root.setBackground(back);                           //adding background image
    }
}
