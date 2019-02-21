package cs4b.proj1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        BoardGUI board = new BoardGUI();

        board.requestFocus();
          


       // ImageView imgObject = new ImageView("img\\gameImage.jpg");

       // imgObject.fitWidthProperty().bind(primaryStage.widthProperty());

       // paneObject.setCenterShape(imgObject);
        // Construct the game, using the players we've constructed up above.
        Player p1 = new Player('X', "player 1", new HPCLocal());
        Player p2 = new Player('O', "player 2", new NPCHard('X', 'O'));
        Game game = new Game(p1, p2);
        File saveFile = new File("gameState");
//        if(saveFile.exists()) {
//                game.loadGameState();
//        }
        if(game.getPlayer1().getPb() instanceof HPCLocal) {
            board.addSubscriber((HPCLocal)game.getPlayer1().getPb());
        }

        if(game.getPlayer2().getPb() instanceof HPCLocal) {
            board.addSubscriber((HPCLocal)game.getPlayer2().getPb());
        }
       // Scene scene = new Scene(paneObject);

       // primaryStage.setScene(scene);
        //primaryStage.show();

       // StackPane root2 = new StackPane();
       // root.setId("pane");
        Scene scene = new Scene(board, 360, 450);

      //  scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 389, 450));

        primaryStage.setResizable(true);

        primaryStage.setScene(scene);

        primaryStage.show();



    }
}


