package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.SubjectAssistant;
import javafx.util.Pair;

import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;

/**
 * This is a stupid NPC which just selects a space randomly. Garunteed to
 * pick an empty space, though.
 *
 * @author Daniel Edwards
 */
public class NPCEasy implements PlayerBehavior {

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

    //endregion ISubject ***********************************************************


    NPCEasy() {
    }

    @Override
    public void getMove(Board b, char token) {

        Pair<Integer, Integer> result = null;
        char[][] boardArray = b.getBoardArray();

        ArrayList<Pair<Integer, Integer>> emptySpaces = new ArrayList<>();

        // Get all empty spaces
        for(int row = 0; row < boardArray.length; row++) {
            for(int col = 0; col < boardArray[row].length; col++) {
                if(boardArray[row][col] == b.DEFAULT_VALUE) {
                    emptySpaces.add(new Pair<>(row, col));
                }
            }
        }

        // Select one of the spaces
        if(emptySpaces.size() > 0) {
            Random r = new Random();

            result = emptySpaces.get(r.nextInt(emptySpaces.size()));
        }
        else {
            throw new IllegalStateException("If we hit this, there is a big problem!!!");
            // This state should not be possible.
        }

        //b.setPos(result.getKey(),result.getValue(),token);
        subjAssist.triggerUpdate(new PlayerBehavior.MoveInfo(result.getKey(), result.getValue()));

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPCEasy npcEasy = (NPCEasy) o;
        return Objects.equals(subjAssist, npcEasy.subjAssist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjAssist);
    }

    @Override
    public String toString() {
        return "NPCEasy{" +
                "subjAssist=" + subjAssist +
                '}';
    }
}
