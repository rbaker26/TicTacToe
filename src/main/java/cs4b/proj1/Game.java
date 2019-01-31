package cs4b.proj1;

import javafx.util.Pair;
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

    void makePlay(Player player, int x, int y) {
        // If we use this implamentation, we have to do it this way because board is not in the scope of PlayerBehavior
        // If the Type is NPC, the xy values will be discarded.
        if(player.pb instanceof HPC) {
            board.setPos(x,y,player.getSymbol());
        }
        else if(player.pb instanceof NPCEasy) {
            // Some random choice function
            Pair<Integer,Integer> pair = random(board);
            board.setPos(pair.getKey(),pair.getValue(),player.getSymbol());
        }
        else if(player.pb instanceof NPCHard) {
            // Use AI
            Pair<Integer,Integer> pair= minimax(board);
            board.setPos(pair.getKey(),pair.getValue(),player.getSymbol());

        }
    }

    private Pair<Integer,Integer> minimax (Board b) {

        return new Pair<>(1,1);
    }

    private Pair<Integer,Integer> random (Board b) {

        return new Pair<>(1,1);
    }

}
