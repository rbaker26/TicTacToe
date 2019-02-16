package cs4b.proj1;

import cs4b.proj1.observer.ISubject;

import java.util.Objects;

/**
 *
 */
public interface PlayerBehavior extends ISubject {

    /**
     * When this player behavior comes up with a move, this gets sent
     * to observers.
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

    public void getMove(Board b, char token);
}
