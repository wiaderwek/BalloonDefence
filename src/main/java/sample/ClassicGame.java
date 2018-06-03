package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import sample.Controler.GameControler;

import java.beans.EventHandler;
import java.io.*;

public class ClassicGame extends Application {
    private int AvailableLevel;                             //maximum avilable level
    private GridPane root = new GridPane();

    public Parent createLevelChoiceWindow(){
        final Label Choice = new Label("Chose Level!");

        Button Levels[] = new Button[3];

        Levels[0] = new Button("First Level");
        Levels[1] = new Button("Second Level");
        Levels[2] = new Button("Third Level");

        try {
            BufferedReader BufferReader = new BufferedReader(new FileReader(new File("target\\classes\\AvailableLevel.txt")));
            int Available = Integer.parseInt(BufferReader.readLine());
            AvailableLevel = Available;
            BufferReader.close();
        }
        catch(FileNotFoundException e){
            AvailableLevel = 1;
        }
        catch(IOException e){
            AvailableLevel = 1;
        }

        for(int i=0; i<3; i++){
            Levels[i].fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 20));
            Levels[i].setStyle("-fx-background-color: transparent");
        }

        Levels[0].setTextFill(Color.ROYALBLUE);
        Levels[1].setTextFill(Color.CORAL);
        Levels[2].setTextFill(Color.LIMEGREEN);

        for(int i=AvailableLevel; i<3; ++i){
            Levels[i].setTextFill(Color.GREY);
        }

        for(int i=0; i<AvailableLevel; ++i){
            int finalI = i;
            Levels[i].setOnAction(event ->{
                close(Levels[finalI]);

                GameControler gc = new GameControler();
                gc.setMap(finalI+1, "player", 1);
            });
        }

        Choice.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 40));
        Choice.setStyle("-fx-background-color: transparent");
        Choice.setTextFill(Color.LIMEGREEN);

        Background back;
        try {                                               //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("OptionsBackground.png").toString());     //creating image with OptionsBackground.png
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){             //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }

        root.setBackground(back);

        root.setHgap(1);                                                  //setting in width of the horizontal gaps between columns in GridPane
        root.setVgap(1);                                                  //setting in height of the vertical gaps between rows in GridPane
        root.setPadding(new Insets(15, 25, 15, 25));  //setting margins around the whole grid

        root.add(Choice,3,1,3,1);
        for(int i=0; i<3; ++i){
            root.add(Levels[i],1,i+3, 2,1);
        }

        return root;
    }

    private void show(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Choice Window");
        stage.show();
    }

    private void close(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void ShowChoiceWindow(){
        show(createLevelChoiceWindow());
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
    }
}
