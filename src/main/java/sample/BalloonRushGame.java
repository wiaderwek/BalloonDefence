package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.Controler.GameControler;

import java.awt.event.MouseEvent;

public class BalloonRushGame extends Application{
    private GridPane root = new GridPane();

    private Parent createNickWindow(){
        final Label NickLabel = new Label("Nick: ");
        TextField Nick = new TextField();

        final Button Play = new Button("Play");

        NickLabel.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 40));
        NickLabel.setStyle("-fx-background-color: transparent");
        NickLabel.setTextFill(Color.LIMEGREEN);

        Play.fontProperty().set(Font.font("Times New Roman", FontWeight.BOLD, 40));
        Play.setStyle("-fx-background-color: transparent");
        Play.setTextFill(Color.CORAL);

        HBox NickBlock = new HBox();

        NickBlock.getChildren().addAll(NickLabel, Nick);
        NickBlock.setSpacing(10);

        Background back;
        try {                                               //try to load background image
            Image img = new Image(getClass().getClassLoader().getResource("OptionsBackground.png").toString());     //creating image with OptionsBackground.png
            BackgroundImage bcg = new BackgroundImage(img, null, null, null, null);
            back = new Background(bcg);
        }
        catch (NullPointerException exception){             //if loafing background image failed then set white background
            back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))); //creating white plain background
        }

        Play.setOnAction(event->{
            if(Nick.getText() != null && !Nick.getText().isEmpty()){
                close(Nick);
                GameControler gc = new GameControler();
                gc.setMap(1, Nick.getText(), 2);
            }
        });
        root.setHgap(1);                                                  //setting in width of the horizontal gaps between columns in GridPane
        root.setVgap(1);                                                  //setting in height of the vertical gaps between rows in GridPane
        root.setPadding(new Insets(15, 25, 15, 25));  //setting margins around the whole grid

        root.setBackground(back);
        root.add(NickBlock, 3,2,5,1);
        root. add(Play, 3,5,1,1);
        return root;
    }

    private void show(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nick Window");
        stage.show();
    }

    private void close(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void ShowNickWindow(){
        show(createNickWindow());
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
