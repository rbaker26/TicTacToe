package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application implements IObserver {

    Controller controlObject = new Controller();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Object eventInfo) {

        if(eventInfo instanceof Game) {

            Game game = (Game)eventInfo;

            // This sets up the UI stuff
            BoardGUI board = new BoardGUI();

            board.requestFocus();

            if(p1.getPb() instanceof HPCLocal) {
                board.addSubscriber((HPCLocal) p1.getPb());
            }

            if(p2.getPb() instanceof HPCLocal) {
                board.addSubscriber((HPCLocal) p2.getPb());
            }

            // Last function to call when everything is ready
            game.startGame();
        }



    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

          

        Scene scene = new Scene(root, 360, 450);


        primaryStage.setResizable(false);

        primaryStage.setScene(scene);

        primaryStage.show();






    }
}


