package cs4b.proj1;

import cs4b.proj1.observer.*;
import javafx.util.Pair;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class NPCEasyTest {

    private HashMap<Pair<Integer, Integer>, Integer> prepareMatchMap(Board b) {
        HashMap<Pair<Integer,Integer>, Integer> matches = new HashMap<>();
        for(int x = 0; x < b.BOARD_SIZE_X; x++) {
            for(int y = 0; y < b.BOARD_SIZE_Y; y++) {

                // Only add it if it's actually valid, e.g. blank.
                // Otherwise, leave it out.
                if(b.getPos(x, y) == ' ' ) {
                    matches.put(new Pair<>(x, y), 0);
                }
            }
        }

        return matches;
    }

    @Test public void testRange() {

        // Okay, this'll be a little wonk, but here me out.
        //
        // We need to make sure our AI will be able to pick every space.
        // However, it's random, so we gotta run the tests many times.
        // To calculate the odds of a given space NOT being picked is 8/9.
        // If we choose twice in a row (without filling a space), the odds
        // of a given space not being chosen are (8/9)^2 = 72/81.
        //
        // Along this same line of logic, if we want a 1 in 1,000,000
        // (i.e. 1 out of 1.0E6) chance of a space NOT being picked, we
        // need to run the test roughly 117 times.
        //
        // Thus, running the test 1,000 times, there's a 1 in 1.5E51
        // chance that a space will not be chosen.
        //
        // This assumes a perfectly random selection. Though our system
        // will not be perfectly random, even a biased system should still
        // yield all spaces being picked, so long as the bias is not severe.
        final int MAX_TESTS = 500;
        final boolean VERBOSE = false;

        Board testBoard = new Board();

        PlayerBehavior behavior = new NPCEasy();
        //NPCEasyObserver observer = new NPCEasyObserver();
        EventContainer<PlayerBehavior.MoveInfo> observer = new EventContainer<>(PlayerBehavior.MoveInfo.class);
        char token = 'X';

        behavior.addSubscriber(observer);


        HashMap<Pair<Integer,Integer>, Integer> matches = prepareMatchMap(testBoard);

        for(int i = 0; i < MAX_TESTS; i++) {
            //Pair<Integer, Integer> selection = behavior.getMove(testBoard);
            behavior.getMove(testBoard, token);
            Pair<Integer, Integer> selection = new Pair<Integer, Integer>(
                    observer.getEventInfo().getX(),
                    observer.getEventInfo().getY()
            );

            int matchCount = matches.getOrDefault(selection, 0);
            matches.put(selection, matchCount + 1);
        }

        if(VERBOSE) {
            StringBuilder builder = new StringBuilder();

            builder.append("Empty board:\n");
            matches.forEach((Pair<Integer, Integer> k, Integer v) -> builder.append(k + " was selected " + v + "\n"));

            System.out.println(builder.toString());
        }
        matches.forEach((Pair<Integer,Integer> k, Integer v) -> assertTrue(v > 0));


        testBoard.setPos(1, 1, 'O');
        matches = prepareMatchMap(testBoard);

        for(int i = 0; i < MAX_TESTS; i++) {
            behavior.getMove(testBoard, token);
            Pair<Integer, Integer> selection = new Pair<Integer, Integer>(
                    observer.getEventInfo().getX(),
                    observer.getEventInfo().getY()
            );

            int matchCount = matches.getOrDefault(selection, 0);
            matches.put(selection, matchCount + 1);
        }

        if(VERBOSE) {
            StringBuilder builder = new StringBuilder();

            builder.append("1-1 is taken:\n");
            matches.forEach((Pair<Integer, Integer> k, Integer v) -> builder.append(k + " was selected " + v + "\n"));

            System.out.println(builder);
        }
        matches.forEach((Pair<Integer,Integer> k, Integer v) -> assertTrue(v > 0));
        assertFalse(matches.containsKey(new Pair<>(1, 1)));
    }
} // End class
