package cs4b.proj1;

import javafx.util.Pair;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class GameTest {

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
