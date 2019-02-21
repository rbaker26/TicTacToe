package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.io.File;

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

    //Observer Signal- main captures, then it will call the board ui.

    Stage primaryStage;
    Parent root;
    Scene startScene;
    Scene boardScene;
    BoardGUI board;

    //Controller controlObject;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Object eventInfo) {

        if(eventInfo instanceof Game) {

            Game game = (Game)eventInfo;
            MenuBar menuBar = new MenuBar();
            Menu menu = new Menu("Menu");
            MenuItem quitGame = new MenuItem("Quit Game");
            menu.getItems().add(quitGame);
            quitGame.setOnAction(event -> {
                File file = new File("gameState");
                if(file.exists()) {
                    file.delete();
                }
                primaryStage.setScene(startScene);
            });

            menuBar.getMenus().add(menu);
            VBox vbox = new VBox();
            board = new BoardGUI();
            vbox.getChildren().addAll(menuBar, board);
            // This sets up the UI stuff
            if(game.getPlayer1().getPb() instanceof HPCLocal) {
                board.addSubscriber((HPCLocal) game.getPlayer1().getPb());
            }
            if(game.getPlayer2().getPb() instanceof HPCLocal) {
                board.addSubscriber((HPCLocal) game.getPlayer2().getPb());
            }
            board.addSubscriber(game);
            board.addSubscriber(this);
            game.addSubscriber(board);

            board.requestFocus();
            boardScene = new Scene(vbox, 300, 320);
            primaryStage.setScene(boardScene);

            // Last function to call when everything is ready
            game.startGame();
        }

        if(eventInfo instanceof BoardGUI.Finished) {
            primaryStage.setScene(startScene);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        this.primaryStage = primaryStage;
        Controller.getInstance().addSubscriber(this);

        startScene = new Scene(root, 360, 450);


        primaryStage.setResizable(true);

        primaryStage.setScene(startScene);

        primaryStage.show();


    }
}

