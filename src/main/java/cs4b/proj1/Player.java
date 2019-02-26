package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.ISubject;
import cs4b.proj1.observer.SubjectController;

import java.io.Serializable;
import java.util.Objects;

/**
 * This object represents a Tic-Tac-Toe player. It tracks things like the
 * player's name, symbol, and behavior pattern.
 *
 * @author Daniel Edwards
 * @author Bob Baker
 */
public class Player implements IObserver, ISubject, Serializable {

    //region ISubject *************************************************************

    /**
     * This contains info on the movement info for the player. This
     * specifically wraps PlayerBehavioor.MoveInfo; hence, it must be
     * constructed using an instance of PlayerBehavior.MoveInfo.
     */
    public static class MoveInfo {

        private Player source;
        private int x;
        private int y;

        public MoveInfo(Player source, int x, int y) {
            this.source = source;
            this.x = x;
            this.y = y;
        }

        public Player getSource() {
            return source;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MoveInfo moveInfo = (MoveInfo) o;
            return getX() == moveInfo.getX() &&
                    getY() == moveInfo.getY() &&
                    Objects.equals(getSource(), moveInfo.getSource());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getSource(), getX(), getY());
        }

        @Override
        public String toString() {
            return "MoveInfo{" +
                    "source=" + source +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    //endregion ISubject ***********************************************************


    private String name;
    private char symbol;
    PlayerBehavior pb;


    public Player(PlayerBehavior pb){
        this('\0',"",pb);
    }
    public Player(char symbol, PlayerBehavior pb) {
        this(symbol,"", pb);
    }
    public Player(char symbol, String name, PlayerBehavior pb) {
        this.symbol = symbol;
        this.name = name;
        //this.pb = pb;
        setPb(pb);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public PlayerBehavior getPb() {
        return pb;
    }

    public void makeMove(Board b) {
        pb.getMove(b, getSymbol());
    }

    public void setPb(PlayerBehavior pb) {
        if(this.pb != null) {
            //this.pb.removeSubscriber(this);
            SubjectController.removeObserver(this.pb, this);
        }

        this.pb = pb;

        if(this.pb != null) {
            //this.pb.addSubscriber(this);
            SubjectController.addObserver(this.pb, this);
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

        if(!SubjectController.hasObservers(this)) {
            System.out.println(getName() + " has no subscribers");
        }
        else if(eventInfo instanceof PlayerBehavior.MoveInfo) {

            PlayerBehavior.MoveInfo origMoveInfo = (PlayerBehavior.MoveInfo) eventInfo;
            SubjectController.triggerUpdate(
                    this, new Player.MoveInfo(this, origMoveInfo.getX(), origMoveInfo.getY())
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getSymbol() == player.getSymbol() &&
                Objects.equals(getName(), player.getName()) &&
                Objects.equals(getPb(), player.getPb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSymbol(), getPb());
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", symbol=" + symbol +
                ", pb=" + pb +
                '}';
    }
}
