package cs4b.proj1;

import cs4b.proj1.observer.DebugObserver;
import cs4b.proj1.observer.EventContainer;
import javafx.util.Pair;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

public class GameTest {

    /**
     * This does the following:
     *  Makes sure that the game ends at all.
     *  Makes sure that turn order is maintained.
     *
     * It depends on NPCEasy.
     */
    @Test
    public void testTurnOrder() {

        // Welp, this is annoying, but we have no good way of testing this
        // without plugging in SOME kind of behavior. May as well make it
        // the most simple one we have: the Easy NPC.
        //
        // Because of that, if NPCEasy were to break, so could this. But
        // there's no time for caution!
        //
        // Need to repeat because of randomness.
        final int REPEAT_COUNT = 1000;

        for(int i = 0; i < REPEAT_COUNT; i++) {

            Player p1 = new Player('X', "AI1", new NPCEasy());
            Player p2 = new Player('O', "AI2", new NPCEasy());

            Game g = new Game(p1, p2);

            EventContainer<Game.MoveInfo> obs = new EventContainer<>(Game.MoveInfo.class);
            g.addSubscriber(obs);

            g.startGame();

            // Given that we have AI, they should finish the game the moment
            // we call the "startGame" method. Thus, it's a problem if the
            // game isn't over by now.
            assertTrue(g.gameOver());

            // Okay, if that worked, then we'll make sure the turn order is
            // working correctly.
            //
            // Before the game began, nextPlayer was player1. It should
            // alternate every turn.
            Player prevPlayer = p1;

            for (Game.MoveInfo info : obs.getAllEvents()) {
                assertNotEquals(prevPlayer, info.getNextPlayer());
                prevPlayer = info.getNextPlayer();
            }
        }
    }

    @Test
    public void testRandom() {
        Game game2 = new Game(new NPCEasy(), new NPCEasy());

        Pair<Integer,Integer> pair;
        pair = game2.random();
        char c =  game2.board.getPos(pair.getKey(),pair.getValue());
        if(c != game2.board.DEFAULT_VALUE) {
            fail();
        }

        char newBoard[][] = {{'x','x','x'},{'x',' ','x'},{'x','x','x'}};
        game2.board = new Board(newBoard);

        pair = game2.random();
        c =  game2.board.getPos(pair.getKey(),pair.getValue());
        if(c != game2.board.DEFAULT_VALUE) {
            fail();
        }

        game2.board = null;
        game2.board = new Board();
        for(int i = 0; i < 9;++i) {
            pair = game2.random();
            char c2 =  game2.board.getPos(pair.getKey(),pair.getValue());
            System.out.println(c);
            if(c2 != game2.board.DEFAULT_VALUE) {
                fail();
            }
            game2.board.setPos(pair.getKey(),pair.getValue(),'X');
        }
    }
}
