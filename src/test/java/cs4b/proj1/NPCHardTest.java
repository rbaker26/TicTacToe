package cs4b.proj1;

import javafx.util.Pair;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Optional.*;
import static org.junit.Assert.*;
public class NPCHardTest {

    @Test public void getMoveTest() {
//        Player p1 = new Player('X', new NPCEasy());
////        Player p2 = new Player('O', new NPCEasy());
////
////
////        NPCHard npcHard = new NPCHard(p1.getSymbol(), p2.getSymbol());
////
////        char[][] tempBoard = {
////                {'X', 'O', 'X'},
////                {'O', 'O', 'X'},
////                {' ', ' ', ' '}
////        };
////        Board b = new Board(tempBoard);
////        npcHard.getMove(b,p2.getSymbol());
////
////        System.out.println("End Test");
    }



    @Test public void evalBoardTest() {
        char[][] tempBoard = {
                {'X', 'O', 'X'},
                {'O', 'O', 'X'},
                {' ', ' ', ' '}
        };
    }

    private ArrayList<Board> getFinishedBoards() {
        ArrayList<Board> boards = new ArrayList<>();




        return boards;
    }


    @Test public void checkDig() {
        NPCHard npcHard = new NPCHard('X','O');
        char[][] tempBoard = {
                {'O', 'O', 'X'},
                {'O', 'O', 'X'},
                {' ', ' ', 'O'}
        };
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(new Board(tempBoard));
        tempBoard = new char[][] {
                {'X', 'O', 'O'},
                {'O', 'O', 'X'},
                {'O', ' ', 'X'}
        };
        boards.add(new Board(tempBoard));

        for(int i = 0; i < boards.size(); i++) {

            java.util.Optional a1 = java.util.Optional.ofNullable(npcHard.checkDig(boards.get(i)));
            java.util.Optional a2 = java.util.Optional.ofNullable(-10);
            assertEquals(a1,a2);
        }


    }
}
