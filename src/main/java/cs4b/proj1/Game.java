package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.ISubject;
import javafx.util.Pair;

import java.util.*;

/**
 * The Game Engine
 *
 */
public class Game implements ISubject {
    private Player player1;
    private Player player2;
    Board board;


    //** ISubject ***************************************************************
    static public class TurnChangeInfo {
        private int x;
        private int y;
        private Player nextPlayer;
        private Player previousPlayer;

        public TurnChangeInfo() {
        }

        public TurnChangeInfo(int x, int y, Player nextPlayer, Player previousPlayer) {
            this.x = x;
            this.y = y;
            this.nextPlayer = nextPlayer;
            this.previousPlayer = previousPlayer;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Player getNextPlayer() {
            return nextPlayer;
        }

        public Player getPreviousPlayer() {
            return previousPlayer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TurnChangeInfo that = (TurnChangeInfo) o;
            return getX() == that.getX() &&
                    getY() == that.getY() &&
                    getPreviousPlayer() == that.getPreviousPlayer() &&
                    Objects.equals(getNextPlayer(), that.getNextPlayer());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY(), getNextPlayer(), getPreviousPlayer());
        }
    }

    public enum SubjectMode {
        TurnChange, GameEnd,
    }
    private HashMap<SubjectMode, ArrayList<IObserver>> observers;
    //***************************************************************************


    public Game(PlayerBehavior player1Behavior, PlayerBehavior player2Behavior) {
        // Init Player Behaviors
        this.player1 = new Player(player1Behavior);
        this.player2 = new Player(player2Behavior);

        this.board = new Board();

        observers = new HashMap<>();
        EnumSet.allOf(SubjectMode.class).forEach(mode -> observers.put(mode, new ArrayList<>()));
    }

    @Deprecated
    void makePlay(Player player) {
        // For AI Plays.  Calls the same makePlay(Player player, int x, int y)
        makePlay(player,0, 0);
    }

    void makePlay(Player currentPlayer, int x, int y) {

        board.setPos(x, y, currentPlayer.getSymbol());

        Player nextPlayer = (currentPlayer != player1 ? player1 : player2);

        TurnChangeInfo info = new TurnChangeInfo(x, y, nextPlayer, currentPlayer);
        observers.get(SubjectMode.TurnChange).forEach((IObserver obs) -> obs.update(info));

        /*
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
        */

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


    //** ISubject ***************************************************************
    /**
     * subscribe
     * Subscribes the given observer, causing its update function to be called
     * for the given event. As there can be a variety of modes, subjects are
     * expected to implement some kind of object (e.g. an enum) to allow
     * subscribers to select what kind of events they are interested in.
     * <p>
     * If an observer attempts to subscribe itself more than once, the first
     * subscription should be replaced. (Unless they are with differenct
     * modes, of course.)
     *
     * @param observer The observer which will be subscribed.
     * @param mode     The subject-specific mode.
     * @author Daniel Edwards
     */
    @Override
    public void subscribe(IObserver observer, Object mode) {

        ArrayList list = observers.get(mode);

        if(!list.contains(observer)) {
            list.add(observer);
        }
    }

    /**
     * unsubscribe
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @param mode     The subject-specific mode.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribe(IObserver observer, Object mode) {
        observers.get(mode).remove(observer);
    }

    /**
     * unsubscribeAll
     * Unsubcribes the given observer entirely, causing them to no longer
     * recieve any updates from the subject.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    @Override
    public void unsubscribeAll(IObserver observer) {

        observers.forEach(
                (SubjectMode mode, ArrayList<IObserver> set) -> set.remove(observer)
        );
    }
    //***************************************************************************
}
