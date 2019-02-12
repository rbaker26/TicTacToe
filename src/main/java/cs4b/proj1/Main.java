package cs4b.proj1;

import java.net.URL;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class Main extends Application {




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
          BoardGUI board = new BoardGUI();
      //  primaryStage.setTitle("Hello World");
          board.requestFocus();


        //  primaryStage.setScene(new Scene(root, 500, 400));
        //  primaryStage.show();





       // StackPane root2 = new StackPane();
        root.setId("pane");
        Scene scene = new Scene(root, 500, 400);

        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}


