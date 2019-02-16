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
        Player p2 = new Player('O', "AI1", new NPCEasy());
        //Player p2 = new Player('O', "AI1", new HPCLocal());

        //p1.getPb().addSubscriber(board);

        // The board must be able to let
        board.addSubscriber((HPCLocal) p1.getPb());

        //p2.getPb().addSubscriber(board);
        //board.addSubscriber((HPCLocal) p2.getPb());

        Game g = new Game(p1, p2);
        g.addSubscriber(board);


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
