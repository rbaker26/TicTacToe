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
import javafx.scene.layout.Pane;
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

          board.requestFocus();



       // ImageView imgObject = new ImageView("img\\gameImage.jpg");

       // imgObject.fitWidthProperty().bind(primaryStage.widthProperty());

       // paneObject.setCenterShape(imgObject);


       // Scene scene = new Scene(paneObject);

       // primaryStage.setScene(scene);
        //primaryStage.show();

       // StackPane root2 = new StackPane();
       // root.setId("pane");
        Scene scene = new Scene(root, 360, 450);

        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 389, 450));

        primaryStage.setResizable(false);

        primaryStage.setScene(scene);

        primaryStage.show();




    }


}


