package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
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
    File file = new File("gameState");

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

        Game game = new Game();
        if(file.exists()) {
            Alert resume = new Alert(Alert.AlertType.CONFIRMATION);
            resume.setTitle("Old Game Found");
            resume.setHeaderText(null);
            resume.setContentText("Resume old game?");
            resume.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            resume.setResizable(true);
            Optional<ButtonType> result = resume.showAndWait();
            if(result.get() == ButtonType.OK) {
                game.loadGameState();
                Player p1;
                Player p2;
                Player next;
                p1 = new Player(game.getPlayer1().getSymbol(), game.getPlayer1().getName(), new HPCLocal());
                if(game.getPlayer2().getPb() instanceof HPCLocal) {
                    p2 = new Player(game.getPlayer2().getSymbol(), game.getPlayer2().getName(), new HPCLocal());
                }
                else if(game.getPlayer1().getPb() instanceof NPCEasy){
                    p2 = new Player(game.getPlayer2().getSymbol(), game.getPlayer2().getName(), new NPCEasy());
                }
                else {
                    p2 = new Player(game.getPlayer2().getSymbol(), game.getPlayer2().getName(), new NPCHard(p1.getSymbol(), game.getPlayer2().getSymbol()));
                }
                if(game.getNextPlayer().getSymbol() == 'X') {
                    next = p1;
                }
                else {
                    next = p2;
                }
                game = new Game(p1, p2, next, game.getBoard());
                this.update(game);
            }
            else {
                file.delete();
            }
        }
    }
}

