package cs4b.proj1;

import cs4b.proj1.observer.ISubject;
import cs4b.proj1.observer.SubjectController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller implements ISubject {

    public static Controller getInstance() {
        //System.out.println(instance);
        return instance;
    }
    private static Controller instance;


    //On Mouse Click Options:

    //If the player wants to compete against another player
    //button1Click() will be incorporated.

    public Controller() {
        instance = this;
    }



    @FXML
    private TextField player1txt;

    @FXML
    private TextField player2txt;


    public String getPlayer1() {
        String player1 = player1txt.getText();

        if(player1 == "") {
            player1 = "Player 1";
        }

        return player1;
    }

    public String getPlayer2() {
        String player2 = player2txt.getText();

        if(player2 == "") {
            player2 = "Player 2";
        }

        return player2;
    }

    @FXML
    private void button1Click() {


        Player p1 = new Player('X', getPlayer1(), new HPCLocal());
        Player p2 = new Player('O', getPlayer2(), new HPCLocal());
        Game game = new Game(p1, p2);

        //triggerUpdate(game);
        SubjectController.triggerUpdate(this, game);
    }

    //If the player wants to compete against the AI in an easy level,
    //button2Click() will be incorporated.
    @FXML
    private void button2Click() {

        Player p1 = new Player('X', getPlayer1(), new HPCLocal());
        Player p2 = new Player('O', getPlayer2(), new NPCEasy());
        Game game = new Game(p1, p2);

        SubjectController.triggerUpdate(this, game);
    }

    //If the player wants to compete against the AI in a hard level,
    //button3Click() will be incorporated.
    @FXML
    private void button3Click() {

        Player p1 = new Player('X', getPlayer1(), new HPCLocal());
        Player p2 = new Player('O', getPlayer2(), new NPCHard('X', 'O'));
        Game game = new Game(p1, p2);

        SubjectController.triggerUpdate(this, game);
    }



}