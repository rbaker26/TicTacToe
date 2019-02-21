package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.ISubject;
import cs4b.proj1.observer.SubjectAssistant;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class Controller implements ISubject {

    //region ISubject *************************************************************

    private SubjectAssistant subjAssist;

    /**
     * Subscribes the given observer, causing its update function to be called
     * for the given event. As there can be a variety of modes, subjects are
     * expected to implement some kind of object (e.g. an enum) to allow
     * subscribers to select what kind of events they are interested in.
     * <p>
     * If an observer attempts to addSubscriber itself more than once, the first
     * subscription should be replaced. (Unless they are with differenct
     * modes, of course.)
     *
     * @param observer The observer which will be subscribed.
     * @author Daniel Edwards
     */
    @Override
    public void addSubscriber(IObserver observer) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.addSubscriber(observer);
    }

    /**
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    @Override
    public void removeSubscriber(IObserver observer) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.removeSubscriber(observer);
    }

    private void triggerUpdate(Object event) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.triggerUpdate(event);
    }

    //endregion ISubject ***********************************************************


    //On Mouse Click Options:

    //If the player wants to compete against another player
    //button1Click() will be incorporated.

    @FXML
    private TextField player1txt;

    @FXML
    private TextField player2txt;

    @FXML
    private void button1Click() {

        //Daniel signal- main captures, then it will call the board ui.


        // Sets up game engine stuff
        System.out.println("HELLO THERE");
        //move this to text editor function:
        String player1 = player1txt.getText();
        String player2 = player2txt.getText();

        if(player1 == "") {
            player1 = "Player 1";
        }

        if(player2 == "") {
            player2 = "Player 2";
        }



        Player p1 = new Player('X', player1, new HPCLocal());
        Player p2 = new Player('O', player2, new HPCLocal());

        Game game = new Game(p1, p2);


        subjAssist.triggerUpdate(game);




    }

    //If the player wants to compete against the AI in an easy level,
    //button2Click() will be incorporated.
    @FXML
    private void button2Click() {

        System.out.println("SUP");

    }

    //If the player wants to compete against the AI in a hard level,
    //button3Click() will be incorporated.
    @FXML
    private void button3Click() {



        System.out.println("HEY");

    }



}