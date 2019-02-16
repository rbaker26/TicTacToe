package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import javafx.util.Pair;
import org.junit.Test;

import java.io.InvalidClassException;
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

    private Pair<Integer, Integer> findSelectedSpace(Board b, char token) {

        Pair<Integer, Integer> selection = null;

        // Yes, I'm putting more than one check inside the loop.
        // I'm not sorry.
        for(int x = 0; x < b.BOARD_SIZE_X && selection == null; x++) {
            for(int y = 0; y < b.BOARD_SIZE_Y && selection == null; y++) {

                if(b.getPos(x, y) == token) {
                    b.setPos(x, y, b.DEFAULT_VALUE);
                    selection = new Pair<>(x, y);
                }
            }
        }

        return selection;
    }

    private static class NPCEasyObserver implements IObserver {

        // These are public because, tbh, we don't care because this is a private class.
        Pair<Integer, Integer> location;

        @Override
        public void update(Object eventInfo) {
            if(eventInfo instanceof PlayerBehavior.MoveInfo) {
                PlayerBehavior.MoveInfo mInfo = (PlayerBehavior.MoveInfo) eventInfo;

                location = new Pair<>(mInfo.getX(), mInfo.getY());
            }
            else {
                throw new ClassCastException("NPCEasy class should not emit any other objects");
            }
        }
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
        final boolean VERBOSE = true;

        Board testBoard = new Board();

        PlayerBehavior behavior = new NPCEasy();
        NPCEasyObserver observer = new NPCEasyObserver();
        char token = 'X';

        behavior.subscribe(observer);


        HashMap<Pair<Integer,Integer>, Integer> matches = prepareMatchMap(testBoard);

        for(int i = 0; i < MAX_TESTS; i++) {
            //Pair<Integer, Integer> selection = behavior.getMove(testBoard);
            behavior.getMove(testBoard, token);
            Pair<Integer, Integer> selection = observer.location;

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
            Pair<Integer, Integer> selection = observer.location;

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
