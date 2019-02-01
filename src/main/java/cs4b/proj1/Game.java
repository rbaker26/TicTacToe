package cs4b.proj1;

import javafx.util.Pair;
import java.util.Random;

/**
 * The Game Engine
 *
 */
public class Game {
    private Player player1;
    private Player player2;
    Board board;

    public Game(PlayerBehavior player1Behavior, PlayerBehavior player2Behavior) {
        // Init Player Behaviors
        this.player1 = new Player(player1Behavior);
        this.player2 = new Player(player2Behavior);

        this.board = new Board();
    }

    void makePlay(Player player) {
        // For AI Plays.  Calls the same makePlay(Player player, int x, int y)
        makePlay(player,0, 0);
    }
    void makePlay(Player player, int x, int y) {
        // If we use this implamentation, we have to do it this way because board is not in the scope of PlayerBehavior
        // If the Type is NPC, the xy values will be discarded.
        if(player.pb instanceof HPC) {
            board.setPos(x,y,player.getSymbol());
        }
        else if(player.pb instanceof NPCEasy) {
            // Some random choice function
            Pair<Integer,Integer> pair = random();
            board.setPos(pair.getKey(),pair.getValue(),player.getSymbol());
        }
        else if(player.pb instanceof NPCHard) {
            // Use AI
            Pair<Integer,Integer> pair= minimax_helper(board);
            board.setPos(pair.getKey(),pair.getValue(),player.getSymbol());

        }
    }

    Pair<Integer,Integer> minimax_helper (Board b) {

        minimax();
        return new Pair<>(1,1);
    }

    private Pair<Integer,Integer> minimax(){
        //TODO
        // write the minimax
        return new Pair<>(1,1);
    }
//    private void staticEvaluator() {
//
//    }

    Pair<Integer,Integer> random (/*Board b*/) {
        Random rand = new Random();
        char p1 = player1.getSymbol();
        char p2 = player2.getSymbol();
        int x;
        int y;
        do {
            x = rand.nextInt(3);
            y = rand.nextInt(3);
        }while(board.getPos(x,y) != board.DEFAULT_VALUE/*board.getPos(x,y) == p1 || board.getPos(x,y) == p2*/);

        return new Pair<>(x,y);
    }

}
