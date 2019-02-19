package cs4b.proj1;

import java.awt.*;
import java.net.URL;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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


        /*******
         *
        //WHAT FOLLOWS IS A SAMPLE WHICH SHOWS HOW TO SET UP THE GAME BOARD

        // This will just set up a basic, AI v. AI mode. In the end, this setup will be handled
        // by Naomi's main menu.
		BoardGUI board = new BoardGUI();

		// Actually construct our players. This is where we can choose which ones
        // are humans and which ones are AI.
        Player p1 = new Player('X', "Human1", new HPCLocal());
        //Player p1 = new Player('X', "AI1", new NPCEasy());

        //Player p2 = new Player('O', "Human2", new HPCLocal());
        Player p2 = new Player('O', "AI2", new NPCHard('X', 'O'));


        // Construct the game, using the players we've constructed up above.
        Game game = new Game(p1, p2);

        // The board must be able to let the player behavior know what has been clicked.
        // This is only important for human players, though, as AI can figure it out
        // on their own.
        if(p1.getPb() instanceof HPCLocal) {
            board.addSubscriber((HPCLocal) p1.getPb());
        }

        if(p2.getPb() instanceof HPCLocal) {
            board.addSubscriber((HPCLocal) p2.getPb());
        }


        // The game engine needs to let the board know when there's an update.
        game.addSubscriber(board);


        // What follows is UI setup stuff.
        primaryStage.setTitle("Hello World");
        board.requestFocus();
        primaryStage.setScene(new Scene(board, 300, 300));
        primaryStage.show();

        // Now that EVERYTHING is set up, the game can begin!
        game.startGame();

        */
    }
}


