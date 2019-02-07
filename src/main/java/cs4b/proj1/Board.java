package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.ISubject;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Consumer;

/**
 * A Board class the holds the data necessary to display a board.
 * This class contains no "win/lose" logic. It is simply a Data-Storage Object with
 * access rule-methods.
 * @class Board
 * @author Bob Baker
 */
public class Board implements ISubject<Board.SubjectMode> {

    //***************************************************************************
    // Signals
    //***************************************************************************
    static public class ChangedInfo {
        public ChangedInfo(int x, int y, char token) {
            this.x = x;
            this.y = y;
            this.token = token;
        }

        public ChangedInfo() {
            this(0, 0, '\0');
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public char getToken() {
            return token;
        }

        /*
        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setToken(char token) {
            this.token = token;
        }
        */

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChangedInfo that = (ChangedInfo) o;
            return getX() == that.getX() &&
                    getY() == that.getY() &&
                    getToken() == that.getToken();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY(), getToken());
        }

        private int x;
        private int y;
        private char token;
    }

    public enum SubjectMode {
        changed;
    }

    private HashMap<SubjectMode, ArrayList<IObserver>> observers;
    //***************************************************************************

    //***************************************************************************
    // Data
    //***************************************************************************
    final int BOARD_SIZE_X = 3;
    final int BOARD_SIZE_Y = 3;
    final char DEFAULT_VALUE = ' ';
    private char[][] boardArray = new char[BOARD_SIZE_X][BOARD_SIZE_Y];
    //***************************************************************************


    //***************************************************************************
    // Constructors
    //***************************************************************************
    /**
     * Default Board Constructor
     * Makes a Board Object with all positions set to ' '.
     * @author Bob Baker
     */
    public Board() {
        // Set Default-Board to all spaces
        for(int i = 0; i < BOARD_SIZE_X; ++i) {
            for(int j = 0; j < BOARD_SIZE_Y; ++j) {
                boardArray[i][j] = DEFAULT_VALUE;
            }
        }

        //boardChangedSignal = new Signal<>();

        // Create the HashMap and a TreeSet for each of our subject modes.
        observers = new HashMap<>();
        EnumSet.allOf(SubjectMode.class).forEach(mode -> observers.put(mode, new ArrayList<>()));
    }
    //***************************************************************************
    /**
     * Makes a Board Object from a 2D Array of a Board.
     * @param boardArray
     * @author Bob Baker
     */
    public Board(char[][] boardArray) {
        this.boardArray = boardArray;
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Returns a 2D array of the board data.
     * @return boardArray
     * @author Bob Baker
     */
    public char[][] getBoardArray() {
        return boardArray;
    }
    //***************************************************************************


    //** ISubject ***************************************************************
    @Override
    public void subscribe(IObserver newObserver, SubjectMode mode) {
        ArrayList list = observers.get(mode);

        if(!list.contains(newObserver)) {
            list.add(newObserver);
        }
    }

    @Override
    public void unsubscribe(IObserver oldObserver, SubjectMode mode) {
        observers.get(mode).remove(oldObserver);
    }

    @Override
    public void unsubscribeAll(IObserver oldObserver) {
        observers.forEach(
                (SubjectMode mode, ArrayList<IObserver> set) -> set.remove(oldObserver)
        );
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Sets the value of one position on the Board.
     * @param x
     * @param y
     * @param c
     * @throws IllegalArgumentException
     * @author Bob Baker
     */
    public void setPos(int x, int y, char c) {
        // Check Bounds - Throw RT-Expt if not in bounds
        if((x > BOARD_SIZE_X || y > BOARD_SIZE_Y) || (x < 0 || y < 0))  {
            String error = String.format("INVALID XY CHORD (%d,%d): Must be bounded by " +
                    "(BOARD_SIZE_X,BOARD_SIZE_Y)(%d,%d)",x,y,BOARD_SIZE_X,BOARD_SIZE_Y);
            throw new IllegalArgumentException(error);
        }
        else {
            boardArray[x][y] = c;

            observers.get( SubjectMode.changed).forEach(
                    (IObserver o) -> o.update(new ChangedInfo(x, y, c) )
            );
        }
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Returns the char value at a given position on the Board.
     * @param x
     * @param y
     * @return (char) value
     * @throws IllegalArgumentException
     * @author Bob Baker
     */
    public char getPos(int x, int y) {
        // Check Bounds - Throw RT-Expt if not in bounds
        if ((x > BOARD_SIZE_X || y > BOARD_SIZE_Y) || (x < 0 || y < 0)) {
            String error = String.format("INVALID XY CHORD (%d,%d): Must be bounded by " +
                    "(BOARD_SIZE_X,BOARD_SIZE_Y)(%d,%d)", x, y, BOARD_SIZE_X, BOARD_SIZE_Y);
            throw new IllegalArgumentException(error);
        } else {
            return boardArray[x][y];
        }
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Returns the hashCode reference of a Board Object
     * @return hashCode
     * @author Bob Baker
     */
    @Override
    public int hashCode() {
        return Objects.hash(boardArray,DEFAULT_VALUE,BOARD_SIZE_X,BOARD_SIZE_Y);
    }
    //***************************************************************************


    //***************************************************************************
    /**
     * Compares two Board objects
     * @param obj
     * @return (bool) isEqual
     * @author Bob Baker
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Board) {
            Board board= (Board) obj;
            return (Arrays.deepEquals(this.boardArray, board.boardArray)     &&
                     Objects.equals(this.DEFAULT_VALUE, board.DEFAULT_VALUE) &&
                     Objects.equals(this.BOARD_SIZE_X, board.BOARD_SIZE_X)   &&
                     Objects.equals(this.BOARD_SIZE_Y, board.BOARD_SIZE_Y)     );
        }
        else {
            return false;
        }
    }
    //***************************************************************************


    //***************************************************************************
    @Override
    /**
     * Returns a formatted string representing the Board View.
     * @return (String) toString
     * @author Bob Baker
     */
    // not for dev, only for testing.
    // prints with formatting
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < BOARD_SIZE_X; ++i) {
            for(int j = 0; j < BOARD_SIZE_Y; ++j) {
                sb.append(boardArray[i][j]);
                sb.append(' ');
            }
            sb.append('\n');
        }
        sb.append('\n');
        return sb.toString();
    }
    //***************************************************************************

}
