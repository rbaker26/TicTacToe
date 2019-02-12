package cs4b.proj1;

import java.net.URL;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import cs4b.proj1.observer.DebugObserver;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
		BoardGUI board = new BoardGUI();
		board.subscribe(new DebugObserver("BoardGUI"), BoardGUI.SubjectMode.SelectedSpace);

        primaryStage.setTitle("Hello World");
        board.requestFocus();
        primaryStage.setScene(new Scene(board, 300, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
