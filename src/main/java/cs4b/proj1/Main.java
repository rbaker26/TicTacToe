package cs4b.proj1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // This will just set up a basic, AI v. AI mode. In the end, this setup will be handled
        // by Naomi's main menu.
		BoardGUI board = new BoardGUI();
		//board.addSubscriber(new DebugObserver("BoardGUI"));
		//board.addSubscriber(new HPCLocal());

        Player p1 = new Player('X', "Human1", new HPCLocal());
        //Player p1 = new Player('X', "AI2", new NPCEasy());
        Player p2 = new Player('O', "AI1", new NPCEasy());
        //Player p2 = new Player('O', "AI1", new HPCLocal());
        Game game = new Game(p1, p2);

        // The board must be able to let the player behavior know what has been clicked.
        board.addSubscriber((HPCLocal) p1.getPb());

        // The game engine needs to let the board know when there's an update.
        game.addSubscriber(board);


        primaryStage.setTitle("Hello World");
        board.requestFocus();
        primaryStage.setScene(new Scene(board, 300, 300));
        primaryStage.show();

        game.startGame();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
