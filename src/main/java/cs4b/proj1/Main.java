package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Callable;

/*
        // This will just set up a basic, AI v. AI mode. In the end, this setup will be handled
        // by Naomi's main menu.
		BoardGUI board = new BoardGUI();
		//board.subscribe(new DebugObserver("BoardGUI"));
		//board.subscribe(new HPCLocal());

        Player p1 = new Player('X', "Human1", new HPCLocal());
        Player p2 = new Player('O', "AI1", new NPCEasy());

        p1.getPb().subscribe(board);
        board.subscribe((HPCLocal) p1.getPb());

        Game g = new Game(p1, p2);
        board.subscribe(g);


        primaryStage.setTitle("Hello World");
        board.requestFocus();
        primaryStage.setScene(new Scene(board, 300, 300));
        primaryStage.show();

        g.startGame();
 */


public class Main extends Application implements IObserver {

    Stage primaryStage;

    //Controller controlObject;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Object eventInfo) {

        System.out.println("HII");
        if(eventInfo instanceof Game) {

            Game game = (Game)eventInfo;

            // This sets up the UI stuff
            BoardGUI board = new BoardGUI();
            if(game.getPlayer1().getPb() instanceof HPCLocal) {
                board.addSubscriber((HPCLocal) game.getPlayer1().getPb());
            }
            if(game.getPlayer2().getPb() instanceof HPCLocal) {
                board.addSubscriber((HPCLocal) game.getPlayer2().getPb());
            }

            board.addSubscriber(game);

            // TODO Make BoardGUI show



            board.requestFocus();
            Scene scene = new Scene(board, 360, 450);
            primaryStage.setScene(scene);

            // Last function to call when everything is ready
            game.startGame();
        }



    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        this.primaryStage = primaryStage;
        Controller.getInstance().addSubscriber(this);


        Scene scene = new Scene(root, 360, 450);


        primaryStage.setResizable(false);

        primaryStage.setScene(scene);

        primaryStage.show();






    }
}

