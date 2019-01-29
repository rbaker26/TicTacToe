package cs4b.proj1;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;
public class BoardTest {


    //***************************************************************************
    @Test public void testBoardInitialValues() {
        Board b = new Board();
        for(int i = 0; i < b.BOARD_SIZE_X; ++i) {
            for(int j = 0; j < b.BOARD_SIZE_Y; ++j) {
                assertEquals(b.getPos(i,j), b.DEFAULT_VALUE);
            }
        }
    }
    //***************************************************************************


    //***************************************************************************
    @Test public void testBoardSetValues() {
        Board b = new Board();
        final char TEST_VAL = 'X';

        for(int i = 0; i < b.BOARD_SIZE_X; ++i) {
            for(int j = 0; j < b.BOARD_SIZE_Y; ++j) {
                b.setPos(i,j,TEST_VAL);
            }
        }
        for(int i = 0; i < b.BOARD_SIZE_X; ++i) {
            for(int j = 0; j < b.BOARD_SIZE_Y; ++j) {
                assertEquals(b.getPos(i,j), TEST_VAL);
            }
        }
    }
    //***************************************************************************


    //***************************************************************************
    @Test public void testGetBoardArray() {
        Board b = new Board();

        char testAr[][] = b.getBoardArray();
        for(int i = 0; i < b.BOARD_SIZE_X; ++i) {
            for(int j = 0; j < b.BOARD_SIZE_Y; ++j) {
                assertEquals(testAr[i][j], b.DEFAULT_VALUE);
            }
        }
    }
    //***************************************************************************


    //***************************************************************************
    @Test public void testEquals() {
        Board b1 = new Board();
        Board b2 = new Board();

        assertTrue(b1.equals(b2));

        b2.setPos(0,0,'X');
        assertFalse(b1.equals(b2));
    }
    //***************************************************************************
}
