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

		// Actually construct our players. This is where we can choose which ones
        // are humans and which ones are AI.
        Player p1 = new Player('X', "Human1", new HPCLocal());
        //Player p1 = new Player('X', "AI1", new NPCEasy());

        //Player p2 = new Player('O', "Human2", new HPCLocal());
        Player p2 = new Player('O', "AI2", new NPCEasy());


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
    }


    public static void main(String[] args) {
        launch(args);
    }
}
