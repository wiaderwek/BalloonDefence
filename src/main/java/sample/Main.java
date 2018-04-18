package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(CreateMainWindow(), 626, 626);        //creating a scene
        primaryStage.setTitle("Hello World");                       //setting title of the scene
        primaryStage.setScene(scene);                               //creating stage with our scene
        primaryStage.setResizable(false);                           //prevent from window resizing
        primaryStage.initStyle(StageStyle.UNDECORATED);             //setting stage with no decorations
        primaryStage.show();                                        //showing the stage
    }

    public Parent CreateMainWindow(){   //creating GridPane with main buttons
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

        Image img = new Image(getClass().getClassLoader().getResource("MainWindowBackground.jpg").toString()); // creating image with MainWindowBackground.jpg
        ImageView iv = new ImageView(img);
        BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
        Background back = new Background(bcg);

        PlayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*
                GameScene gs = new GameScene();
                Scene scene = new Scene(gs.CreateGameScene());
                Stage stage = new Stage();
                stage.setTitle("ExampleGameScene");
                stage.setScene(scene);
                stage.show();
                */
            }
        });

        OptionBtn.setOnAction(new EventHandler<ActionEvent>() {         //Option button cause opening options window
            @Override
            public void handle(ActionEvent event) {

                Options op = new Options();
                Scene scene = new Scene(op.CreateOptionsWindow(), 700, 700);    //creating options scene
                Stage stage = new Stage();                                                  //creating new stage
                stage.setTitle("Options");                                                  //setting the title of the stage
                stage.setScene(scene);                                                      //adding the scene to the stage
                stage.setResizable(false);                                                  //prevent from window resizing
                stage.show();

            }
        });

        QuitBtn.setOnAction(new EventHandler<ActionEvent>() {         // Quit button cause closing the window
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) QuitBtn.getScene().getWindow(); // getting the actual stage
                stage.close();                                        //closing window
            }
        });


        GridPane root = new GridPane();
        root.setBackground(back);                           //adding background image
        root.setHgap(1);                                    //setting in width of the horizontal gaps between columns in GridPane
        root.setVgap(1);                                    //setting in height of the vertical gaps between rows in GridPane
        root.setPadding(new Insets(15, 25, 15, 25)); //setting margins around the whole grid

        root.add(PlayBtn,200,299,1,1);    //adding Play button
        root.add(OptionBtn,200,300,1,1);  //adding Option button
        root.add(QuitBtn,200,301);                      //adding Quit button

        return root;                 //returning our GridPane
    }

    public static void main(String[] args) {
        launch(args);
    }
}
