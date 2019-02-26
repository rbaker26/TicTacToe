package cs4b.proj1;


import cs4b.proj1.observer.SubjectController;
import javafx.util.Pair;

import java.util.Map;
import java.util.TreeMap;

/**
 * The AI Behavior for the getMove functionality in the Player Class
 * This class uses static evaluation and minimax to determine the best
 * moves based on a given game state by assuming optimal moves for both players.
 * @author Bobby Baker
 */
public class NPCHard implements PlayerBehavior {

    /**
     * Player1's Character
     */
    private char player1Char = '\0';
    /**
     * Player2's Character
     */
    private char player2Char = '\0';
    /**
     * The max score the static evaluator can return.
     * Set to BoardSize + 1 (10)
     * This means Player1 won.
     */
    private Integer MAX_SCORE;
    /**
     * The min score the static evaluator can return.
     * Set to BoardSize + 1 (-10)
     * This means Player2 won.
     */
    private Integer MIN_SCORE;

    //***************************************************************************

    /**
     * The Default constructor for NPCHard.
     * This classes needs the charecters to insure that it knows for who it
     * is minimizing or maximizing.
     * @param p1 Player1's Character
     * @param p2 Player2's Character
     * @author Bobby Baker
     */
    NPCHard(char p1, char p2){
      
        player1Char = p1;
        player2Char = p2;


        Board b = new Board();
        MAX_SCORE =  ((b.BOARD_SIZE_X*b.BOARD_SIZE_Y) + 1);
        MIN_SCORE = -((b.BOARD_SIZE_X*b.BOARD_SIZE_Y) + 1);

    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Gets the move from an NPCHard player.  This means a minimax algorithm will
     * be used to find optimal move states and play those moves.  It will be
     * impossible to beat this AI and the best outcome is to tie or draw the AI.
     *
     * This function works by trying all available moves and choosing the one with
     * the lowest evaluation score.  This low score means that the move is best for
     * Player2, AKA the AI.  The higher the score, the worst the move is for the AI.
     *
     * @param b The current board state.
     * @param token Player's token or symbol.
     * @autor Bobby Baker
     */
    @Override
    public void getMove(Board b, char token) {

        // Default will minimize
        boolean isMax = false;
        int bestVal = Integer.MIN_VALUE; //
        int xBest = -1;
        int yBest = -1;
        // If Player2 is about to move, set isMax to true.
        if(token == player2Char) {
            isMax = true;
        }
        System.out.println("Suggested Move:\t" + "Col " + "\t" + "Row");

        // This tree map will hold all the scored moves with their values as the key.
        // It will be sorted so the best move will be the lowest score so it will
        // be the first element in the TreeMap
        Map<Integer, Pair<Integer,Integer>> moveMap = new TreeMap<>();
        // Try all possible moves and run minimax on those moves
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                    // Make the move
                    b.setPos(i,j,token);

                    // Get the move's evaluation score
                    int moveVal = minimax(b,0,isMax);

                    // Put the value and move coordinates into the TreeMap
                    moveMap.put(moveVal,new Pair(i,j));
                    System.out.println("Move Value:\t"+moveVal + "\t" +i +"\t\t"+ j);

                    // Undo the move
                    b.setPos(i,j,b.DEFAULT_VALUE);

                    // This is old code that will not work.
                    if(moveVal > bestVal) {
                        xBest = i;
                        yBest = j;
                    }
                }
            }
        }
        // Get the lowest scored moves from the TreeMap and make that move.
        int i =  ((TreeMap<Integer, Pair<Integer, Integer>>) moveMap).firstEntry().getValue().getKey();
        int j =  ((TreeMap<Integer, Pair<Integer, Integer>>) moveMap).firstEntry().getValue().getValue();
        // Debug Code &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        System.out.println("*****************************************");
        System.out.println("Suggested Move:\t" + "Col " + "\t" + "Row");
        // System.out.println("Suggested Move:\t" + xBest + "\t\t" + yBest);
        System.out.println("Suggested Move:\t" + i + "\t\t" + j);
        System.out.println("*****************************************");
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        // Using the Observer Pattern, make the move using i,j
        SubjectController.triggerUpdate(this, new PlayerBehavior.MoveInfo(i, j));
        System.out.println(b);

    }
    //***************************************************************************



    //***************************************************************************

    /**
     * A minimax algorithm optimizing moves on a tic-tac-toe board.
     *
     * @param b  The Board Object
     * @param depth The amount of times minimax has recursed
     * @param isMax Is minimax minimizing or maximizing
     * @return The evaluation of the best move
     *
     * @author Bobby Baker
     */
    public int minimax(Board b, int depth, boolean isMax) {

        // Evaluate the board for an end state and return the value
        // of said end state.
        int score = evalBoard(b);

        // If an end state was reached, return the result minus the
        // amount of moves required to reach the end state.
        // This way, paths to victory with less moves are favored.
        if(score == MAX_SCORE) {
            return score-depth;
        }
        if(score == MIN_SCORE) {
            return score+depth;
        }

        // If the board is a tie, return 0
        if(isBoardFull(b)) {
            return 0;
        }

        // Here we are going to try every possible open move and compute their eval scores.
        // If minimax is tasked to find the best move for Player1
        if(isMax) {
            // Find the move with the high score
            int best = Integer.MIN_VALUE;
            for(int i =0; i < b.BOARD_SIZE_X; i++) {
                for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                    if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                        // Make the move for Player1
                        b.setPos(i,j,player1Char);
                        // Run the minimaax on that move
                        best = Integer.max(best, minimax(b,depth+1, !isMax));
                        // Undo the move
                        b.setPos(i,j,b.DEFAULT_VALUE);
                    }
                }
            }
            return best;
        }
        // Else, minimax is tasked to find the best move for Player2,
        // or the worst move for Player1
        else {
            // Find the move with the lowest score
            int worst = Integer.MAX_VALUE;
            for(int i =0; i < b.BOARD_SIZE_X; i++) {
                for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                    if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                        // Make the move for Player1
                        b.setPos(i,j,player2Char);
                        // Run the minimaax on that move
                        worst = Integer.min(worst,minimax(b,depth+1, !isMax));
                        // Undo the move
                        b.setPos(i,j,b.DEFAULT_VALUE);
                    }
                }
            }
            return worst;

        }

    }
    //***************************************************************************


    //***************************************************************************

    /**
     * Returns true if the board is full
     * @param b The Board Object
     * @return boolean - is the board full
     * @author Bobby Baker
     */
    boolean isBoardFull(Board b) {
        int defaultSpaceCount = 0;
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                    defaultSpaceCount++;
                }
            }
        }
        if(defaultSpaceCount > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    //***************************************************************************

    //***************************************************************************

    /**
     * Returns a value if the board has reached a winning state, or return 0
     * if the game is a tie.
     * @param b The Board object
     * @return The board evaluation score
     * @author Bobby Baker
     */
    Integer evalBoard(Board b) {
        Integer rowWim = checkRowWin(b);
        if (rowWim != null) return rowWim;
        Integer colWin = checkColWin(b);
        if (colWin != null) return colWin;
        Integer digWin = checkDig(b);
        if (digWin != null) return digWin;
        return 0;
    }
    //***************************************************************************



    //***************************************************************************
    /**
     * Returns a value if the board has reached a winning state
     * @param b The Board object
     * @return The board evaluation score
     * @author Bobby Baker
     */
    Integer checkDig(Board b) {
        // Check dig's
        char[][] array = b.getBoardArray();
        if(array[0][0] == array[1][1] && array[1][1] == array[2][2]) {
            if(array[0][0] == player1Char) {
                return MAX_SCORE;
            }
            else if(array[0][0] == player2Char) {
                return MIN_SCORE;
            }
        }
        if(array[0][2]==array[1][1] && array[1][1] == array[2][0]) {
            if(array[0][2] == player1Char) {
                return MAX_SCORE;
            }
            else if(array[0][2] == player2Char) {
                return MIN_SCORE;
            }
        }
        return null;
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Returns a value if the board has reached a winning state
     * @param b The Board object
     * @return The board evaluation score
     * @author Bobby Baker
     */
    private Integer checkColWin(Board b) {
        // CheckWinCol
        char[][] array = b.getBoardArray();
        for(int j = 0; j < b.BOARD_SIZE_Y ;j++) {
            if(array[0][j] == array[1][j] && array[1][j] == array[2][j]) {
                if(array[0][j] == player1Char) {
                    return MAX_SCORE;
                }
                else if(array[0][j] == player2Char) {
                    return MIN_SCORE;
                }
            }
        }
        return null;
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Returns a value if the board has reached a winning state
     * @param b The Board object
     * @return The board evaluation score
     * @author Bobby Baker
     */
    private Integer checkRowWin(Board b) {
        // CheckWin Rows
        char[][] array = b.getBoardArray();
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            if(array[i][0] == array[i][1] && array[i][1] ==array [i][2]) {
                if(array[i][0] == player1Char) {
                    return MAX_SCORE;
                }
                else if(array[i][0] == player2Char){
                    return MIN_SCORE;
                }
            }
        }
        return null;
    }
    //***************************************************************************



    //***************************************************************************
    //***************************************************************************





}
