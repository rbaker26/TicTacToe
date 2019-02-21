package cs4b.proj1;

import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.ISubject;
import cs4b.proj1.observer.SubjectAssistant;
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
public class Board {

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
    }

    //***************************************************************************
    /**
     * Makes a Board Object from a 2D Array of a Board.
     * @param boardArray
     * @author Bob Baker
     */
    public Board(char[][] boardArray) {
        this.boardArray = new char[this.BOARD_SIZE_X][this.BOARD_SIZE_Y];
        for(int i = 0; i < this.BOARD_SIZE_X; i++) {
            for( int j = 0; j < this.BOARD_SIZE_Y; j++) {
                this.boardArray[i][j] = boardArray[i][j];
            }
        }
        //this.boardArray = boardArray;
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
            boardArray[y][x] = c;
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
            return boardArray[y][x];
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
        char delim = ' ';
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < BOARD_SIZE_X; ++i) {
            for(int j = 0; j < BOARD_SIZE_Y; ++j) {
                sb.append(boardArray[i][j]);
                //sb.append(delim);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    //***************************************************************************



    //***************************************************************************
    /**
     * Returns the amount of empty spaces on the board
     * @return (int) emptySpaces
     * @author Bob Baker
     */
    public int numEmptySpaces(){
        int count = 0;

        for(int i = 0; i < BOARD_SIZE_X; i++) {
            for(int j= 0; j < BOARD_SIZE_Y; j++){
                if(boardArray[i][j] == DEFAULT_VALUE) {
                    count++;
                }
            }
        }
        return count;
    }
    //***************************************************************************
}
