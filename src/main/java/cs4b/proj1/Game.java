package cs4b.proj1;

import cs4b.proj1.observer.*;
import javafx.util.Pair;
import java.io.*;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * The Game Engine
 */
public class Game implements ISubject, IObserver {


    //region ISubject *************************************************************

    /**
     * Contains info on the current turn, including the previous move. This
     * is NOT sent out when the game begins.
     * @see TurnInfo
     * @author Daniel Edwards
     */
    static public class MoveInfo {
        private int x;
        private int y;
        private Player nextPlayer;
        private Player previousPlayer;
        private Board currentBoard;

        public MoveInfo() {
        }

        public MoveInfo(int x, int y, Player nextPlayer, Player previousPlayer, Board currentBoard) {
            this.x = x;
            this.y = y;
            this.nextPlayer = nextPlayer;
            this.previousPlayer = previousPlayer;
            this.currentBoard = currentBoard;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        /**
         * Gets the player who will take the next turn.
         * If null, there is no turn after this one.
         * @return The next player or null.
         * @author Daniel Edwards
         */
        public Player getNextPlayer() {
            return nextPlayer;
        }

        /**
         * Gets the player who took the previous turn.
         * Logically, this shouldn't be null, but there is no
         * strong garuntee that it is not.
         * @return The previous player.
         * @author Daniel Edwards
         */
        public Player getPreviousPlayer() {
            return previousPlayer;
        }

        public Board getCurrentBoard() {
            return currentBoard;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MoveInfo that = (MoveInfo) o;
            return getX() == that.getX() &&
                    getY() == that.getY() &&
                    getPreviousPlayer() == that.getPreviousPlayer() &&
                    Objects.equals(getNextPlayer(), that.getNextPlayer()) &&
                    Objects.equals(getCurrentBoard(), that.getCurrentBoard());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY(), getNextPlayer(), getPreviousPlayer(), getCurrentBoard());
        }

        @Override
        public String toString() {
            return "Game.MoveInfo{" +
                    "x=" + x +
                    ", y=" + y +
                    ", nextPlayer=" + nextPlayer +
                    ", previousPlayer=" + previousPlayer +
                    ", currentBoard=\n" + currentBoard +
                    "\n}";
        }
    }

    /**
     * Tracks just info on who's turn it is and was. Doesn't keep track of
     * moves which have been made. This is sent out when the game
     * begins.
     * @see MoveInfo
     * @author Daniel Edwards
     */
    static public class TurnInfo {
        private Player nextPlayer;
        private Player previousPlayer;
        private Board currentBoard;

        public TurnInfo() {};

        public TurnInfo(Player nextPlayer, Player previousPlayer, Board currentBoard) {
            this.nextPlayer = nextPlayer;
            this.previousPlayer = previousPlayer;
            this.currentBoard = currentBoard;
        }

        /**
         * Returns the player who'll take the next turn. If this is the
         * last turn, null is returned instead.
         * @return The player who'll take the next turn or null.
         * @author Daniel Edwards
         */
        public Player getNextPlayer() {
            return nextPlayer;
        }

        /**
         * Returns the player who took the previous turn. If this is
         * the first turn, this will be null.
         * @return The player who took the previous turn or null.
         * @author Daniel Edwards
         */
        public Player getPreviousPlayer() {
            return previousPlayer;
        }

        public Board getCurrentBoard() {
            return currentBoard;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TurnInfo turnInfo = (TurnInfo) o;
            return Objects.equals(getNextPlayer(), turnInfo.getNextPlayer()) &&
                    Objects.equals(getPreviousPlayer(), turnInfo.getPreviousPlayer()) &&
                    Objects.equals(getCurrentBoard(), turnInfo.getCurrentBoard());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getNextPlayer(), getPreviousPlayer(), getCurrentBoard());
        }

        @Override
        public String toString() {
            return "Game.TurnInfo{" +
                    "nextPlayer=" + nextPlayer +
                    ", previousPlayer=" + previousPlayer +
                    ", currentBoard=\n" + currentBoard +
                    "\n}";
        }
    }

    /**
     * Contains info on the end result of a game. Only sent once no more moves
     * may be made.
     * @author Daniel Edwards
     */
    static public class ResultInfo {
        private Player winner;

        public ResultInfo() {
            winner = null;
        }

        public ResultInfo(Player winner) {
            this.winner = winner;
        }

        /**
         * Gets the player who won. If no players won
         * (i.e. the game ended in a draw), then null is returned.
         * @return The winner or null.
         * @author Daniel Edwards
         */
        public Player getWinner() {
            return winner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ResultInfo that = (ResultInfo) o;
            return Objects.equals(winner, that.winner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(winner);
        }

        @Override
        public String toString() {
            return "Gane.ResultInfo{" +
                    "winner=" + winner +
                    '}';
        }
    }


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
        // TODO Maybe needed for serializable?
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
        // TODO Maybe needed for serializable?
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.removeSubscriber(observer);
    }

    //endregion ISubject ***********************************************************


    private Player player1;
    private Player player2;
    private Board board;
    private Player nextPlayer;       // Used to track who's turn it is.
    private SubjectAssistant subjAssist;

    public Game(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;

        nextPlayer = player1;

        this.board = new Board();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     * Call this once everything is set up. This may get deprecated/deleted
     * if we force everything to be properly configured in the constructor.
     * @author Daniel Edwards
     */
    public void startGame() {

        // This is so that we can hear when the players decide their moves.
        player1.addSubscriber(this);
        player2.addSubscriber(this);

        // TODO
        subjAssist.triggerUpdate(new TurnInfo(nextPlayer, null, board));
        nextPlayer.makeMove(board);
    }

    void writeGameState() throws IOException{
        System.out.println("top of method");
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("gameState"));
        output.writeObject(player1);
        output.writeObject(player2);
        output.writeObject(nextPlayer);
        output.writeObject(board);
        output.close();
        System.out.println("Game State Saved!");
    }

    /**
     * Reads in the game state from a binary file.  The game state includes the board state, the player
     * information, as well as whose turn it is
     * @author Keane Kaiser
     */

    void loadGameState() {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("gameState"));
            boolean eof = false;
            while(!eof) {
                try {
                    this.player1 = (Player)input.readObject();
                    this.player2 = (Player)input.readObject();
                    this.nextPlayer = (Player)input.readObject();
                    this.board = (Board)input.readObject();
                    // The following code outputs tests
                    System.out.println("Player 1 info:");
                    System.out.println(player1);
                    System.out.println("Player 2 info:");
                    System.out.println(player2);
                    System.out.println("Current Player:");
                    if(nextPlayer.equals(player1)) {
                        System.out.println("player 1");
                    } else {
                        System.out.println("player 2");
                    }
                    System.out.println("Board state:");
                    System.out.println(board);
                }
                catch(EOFException ex) {
                    eof = true;
                }
            }

        }
        catch(Exception ex) {
            System.out.println("Shit Balls #2");
            ex.printStackTrace();
        }
    }
    
    /**
     * Puts down the given player's symbol.
     *
     * @param movingPlayer
     * @param x
     * @param y
     */
    void makePlay(Player movingPlayer, int x, int y) {

        boolean gameIsOver = gameOver();

        if(gameIsOver) {
            System.out.println("Game is over; no moves can be made.");
        }
        else if(movingPlayer == null) {
            throw new NullPointerException("movingPlayer must not be null!");
        }
        else if(movingPlayer != nextPlayer) {
            System.out.println("For some reason, " + movingPlayer.getName() + " tried to move, " +
                    "even though it's " + nextPlayer.getName() + "'s turn.");
        }
        else if(board.getPos(x, y) != board.DEFAULT_VALUE) {
            System.out.println(movingPlayer.getName() + " just tried to put something in (" + x + ", " + y + "), " +
                    "but this space is already taken!");
        }
        else {
            board.setPos(x, y, movingPlayer.getSymbol());

            gameIsOver = gameOver();

            // Save the result of the gameOver method; we need to use it a few times
            // and it would be wasteful to call it several times.

            if(gameIsOver) {
                nextPlayer = null;
            }
            else {
                nextPlayer = (movingPlayer != player1 ? player1 : player2);
            }

            subjAssist.triggerUpdate(
                    new Game.TurnInfo(nextPlayer, movingPlayer, board)
            );
            subjAssist.triggerUpdate(
                    new Game.MoveInfo(x, y, nextPlayer, movingPlayer, board)
            );

            if(!gameIsOver) {
                System.out.println("right spot");
                try {
                    this.writeGameState();
                }
                catch(Exception ex) {
                    // shit balls
                    System.out.println("shit balls: ");
                    ex.printStackTrace();
                }
                nextPlayer.makeMove(board);
            }
            else {
                File file = new File("gameState");
                if(file.exists()) {
                    file.delete();
                }
                subjAssist.triggerUpdate(
                        new Game.ResultInfo(getWinner())
                );
            }
        }
    }

    /**
     * Let the observer know that something happened with one of its subjects.
     *
     * @param eventInfo Whatever the subject decides to share about the event.
     * @author Daniel Edwards
     */
    @Override
    public void update(Object eventInfo) {
        if(eventInfo instanceof Player.MoveInfo) {
            Player.MoveInfo info = (Player.MoveInfo) eventInfo;

            //System.out.println(info.getSource().getName());

            makePlay(info.getSource(), info.getX(), info.getY());
        }
    }

    public boolean gameOver() {

        // We'll assume that the game has ended until proven otherwise.
        boolean ended = true;

        // If someone has won, the game has certainly ended.
        if(getWinner() == null) {

            // Someone hasn't won yet; we need to check if the board is full.
            // If a single space is empty, then the game hasn't ended yet.
            // Note that we abort early if we find that the game hasn't ended,
            // i.e. there's an empty space.
            for(int x = 0; x < board.BOARD_SIZE_X && ended; x++) {
                for (int y = 0; y < board.BOARD_SIZE_Y && ended; y++) {

                    if (board.getPos(x, y) == board.DEFAULT_VALUE) {
                        ended = false;
                    }
                }
            }
        }

        return ended;
    }

    /**
     * Figures out who's the current winner. Returns null if there is no winner
     * yet.
     * @return The winner or null if there is none.
     */
    public Player getWinner() {
        // TODO This needs to figure out who the current winner is.
        //      See the javadoc.

        int winVal = evalBoard(board, player1.getSymbol(),player2.getSymbol());
        if(winVal==10){
            return player1;
        }
        else if(winVal ==-10) {
            return player2;
        }
        return null;
    }


    //***************************************************************************
    Integer evalBoard(Board b, char player1Char, char player2Char) {
        Integer rowWim = checkRowWin(b,player1Char,player2Char);
        if (rowWim != null) return rowWim;
        Integer colWin = checkColWin(b,player1Char,player2Char);
        if (colWin != null) return colWin;
        Integer digWin = checkDig(b,player1Char,player2Char);
        if (digWin != null) return digWin;
        return 0;
    }
    //***************************************************************************



    //***************************************************************************
    Integer checkDig(Board b, char player1Char, char player2Char) {
        // Check dig's
        char[][] array = b.getBoardArray();
        if(array[0][0] == array[1][1] && array[1][1] == array[2][2]) {
            if(array[0][0] == player1Char) {
                return 10;
            }
            else if(array[0][0] == player2Char) {
                return -10;
            }
        }
        if(array[0][2]==array[1][1] && array[1][1] == array[2][0]) {
            if(array[0][2] == player1Char) {
                return 10;
            }
            else if(array[0][2] == player2Char) {
                return -10;
            }
        }
        return null;
    }
    //***************************************************************************


    //***************************************************************************
    private Integer checkColWin(Board b, char player1Char, char player2Char) {
        // CheckWinCol
        char[][] array = b.getBoardArray();
        for(int j = 0; j < b.BOARD_SIZE_Y ;j++) {
            if(array[0][j] == array[1][j] && array[1][j] == array[2][j]) {
                if(array[0][j] == player1Char) {
                    return 10;
                }
                else if(array[0][j] == player2Char) {
                    return -10;
                }
            }
        }
        return null;
    }
    //***************************************************************************
    //***************************************************************************
    private Integer checkRowWin(Board b, char player1Char, char player2Char) {
        // CheckWin Rows
        char[][] array = b.getBoardArray();
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            if(array[i][0] == array[i][1] && array[i][1] ==array [i][2]) {
                if(array[i][0] == player1Char) {
                    return 10;
                }
                else if(array[i][0] == player2Char){
                    return -10;
                }
            }
        }
        return null;
    }
    //***************************************************************************
    
    
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

    @Override
    public String toString() {
        return "Game{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", board=" + board +
                ", nextPlayer=" + nextPlayer +
                ", subjAssist=" + subjAssist +
                '}';
    }
}
