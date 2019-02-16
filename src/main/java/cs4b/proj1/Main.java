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
        // This will just set up a basic, AI v. AI mode. In the end, this setup will be handled
        // by Naomi's main menu.
		BoardGUI board = new BoardGUI();
		//board.subscribe(new DebugObserver("BoardGUI"));
		//board.subscribe(new HPCLocal());

        Player p1 = new Player('X', "Human1", new HPCLocal());
        Player p2 = new Player('O', "AI1", new NPCEasy());
        //Player p2 = new Player('O', "AI1", new HPCLocal());

        p1.getPb().subscribe(board);
        board.subscribe((HPCLocal) p1.getPb());

        //p2.getPb().subscribe(board);
        //board.subscribe((HPCLocal) p2.getPb());

        Game g = new Game(p1, p2);
        g.subscribe(board);


        primaryStage.setTitle("Hello World");
        board.requestFocus();
        primaryStage.setScene(new Scene(board, 300, 300));
        primaryStage.show();

        g.startGame();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
