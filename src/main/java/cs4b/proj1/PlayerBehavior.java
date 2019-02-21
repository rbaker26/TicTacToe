package cs4b.proj1;

import cs4b.proj1.observer.ISubject;

import java.io.Serializable;
import java.util.Objects;

/**
 * This represents the behavior for a player. To request a move, call
 * the getMove function. However, depending on the player's behavior, this
 * may not be able to return a move immediately. To recieve the final result,
 * addSubscriber to the PlayerBehavior and listen for its MoveInfo object.
 *
 * @author Daniel Edwards
 */
public interface PlayerBehavior extends ISubject, Serializable {

    /**
     * When this player behavior comes up with a move, this gets sent
     * to observers. This has no default constructor because this object
     * makes no sense without x and y values.
     */
    class MoveInfo {
        private int x;
        private int y;

        public MoveInfo(int x, int y) {
            this.x = x;
            this.y = y;
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
                    getY() == moveInfo.getY();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY());
        }

        @Override
        public String toString() {
            return "MoveInfo{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    /**
     * Puts in the request for a move. This will eventually result
     * in MoveInfo getting sent out to all observers.
     * @param b The current board state.
     * @param token Player's token or symbol.
     */
    void getMove(Board b, char token);
}
