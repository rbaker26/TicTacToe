package cs4b.proj1;


import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Board {

    //***************************************************************************
    // Data
    //***************************************************************************
    final int BOARD_SIZE_X = 3;
    final int BOARD_SIZE_Y = 3;
    final char DEFAULT_VALUE = ' ';
    private char boardArray[][] = new char[BOARD_SIZE_X][BOARD_SIZE_Y];

    //***************************************************************************



    //***************************************************************************
    // Constructors
    //***************************************************************************
    public Board() {
        // Set Default-Board to all spaces
        for(int i = 0; i < BOARD_SIZE_X; ++i) {
            for(int j = 0; j < BOARD_SIZE_Y; ++j) {
                boardArray[i][j] = DEFAULT_VALUE;
            }
        }
    }

    public Board(char[][] boardArray) {
        this.boardArray = boardArray;
    }
    //***************************************************************************


    //***************************************************************************
    public char[][] getBoardArray() {
        return boardArray;
    }
    //***************************************************************************


    //***************************************************************************
    public void setPos(int x, int y, char c) {
        // Check Bounds - Throw RT-Expt if not in bounds
        if(x > BOARD_SIZE_X || y > BOARD_SIZE_Y) {
            String error = String.format("INVALID XY CHORD (%d,%d): Must be bounded by " +
                    "(BOARD_SIZE_X,BOARD_SIZE_Y)(%d,%d)",x,y,BOARD_SIZE_X,BOARD_SIZE_Y);
            throw new IllegalArgumentException(error);
        }
        else {
            boardArray[x][y] = c;
        }
    }
    //***************************************************************************

    //***************************************************************************
    public char getPos(int x, int y) {
        // Check Bounds - Throw RT-Expt if not in bounds
        if (x > BOARD_SIZE_X || y > BOARD_SIZE_Y) {
            String error = String.format("INVALID XY CHORD (%d,%d): Must be bounded by " +
                    "(BOARD_SIZE_X,BOARD_SIZE_Y)(%d,%d)", x, y, BOARD_SIZE_X, BOARD_SIZE_Y);
            throw new IllegalArgumentException(error);
        } else {
            return boardArray[x][y];
        }
    }
    //***************************************************************************


    //***************************************************************************
    @Override
    public int hashCode() {
        return Objects.hash(boardArray,DEFAULT_VALUE,BOARD_SIZE_X,BOARD_SIZE_Y);
    }
    //***************************************************************************


    //***************************************************************************
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
