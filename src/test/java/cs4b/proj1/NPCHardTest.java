package cs4b.proj1;

import javafx.util.Pair;
import org.junit.Test;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional.*;
import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;
public class NPCHardTest {

    final int NUM_END_GAMES = 16;


    //***************************************************************************
    @Test public void getMoveTest() {
        Player p1 = new Player('X', new NPCEasy());
        Player p2 = new Player('O', new NPCEasy());


        NPCHard npcHard = new NPCHard(p1.getSymbol(), p2.getSymbol());

        char[][] tempBoard = {
                {' ', ' ', ' '},
                {' ', 'O', ' '},
                {' ', ' ', ' '}
        };

        Board b = new Board(tempBoard);
        System.out.println(b.toString());
        npcHard.getMove(b,p1.getSymbol());
        System.out.println(b.toString());

        System.out.println("End Test");
    }
    //***************************************************************************

    //***************************************************************************
    @Test public void evalBoardTest() {
        NPCHard npcHard = null;
        char[][] tempBoard = {
                {'X', 'O', 'X'},
                {'O', 'O', 'X'},
                {' ', ' ', ' '}
        };
        // 1 Block array hack for pass-by-ref
        char p1[] = new char[1];
        char p2[] = new char[1];
        char def[] = new char[1];
        int numRounds[] = new int[1];
        getFinishedBoardInfo(p1,p2,def,numRounds);
        // Test Code
        //System.out.println("" + p1[0]  + " " + p2[0]  + " " + def[0]  + " " + numRounds[0] );
        ArrayList<Board> boards = getFinishedBoards();
        ArrayList<Integer> scores = getFinishedBoardsScores();

        System.out.println("--------------------------------------------");
        System.out.println("Board Winner Evaluation Test");
        System.out.println("--------------------------------------------");
        System.out.println("Actual\tExpected");
        npcHard = new NPCHard(p1[0],p2[0]);
        for(int i = 0; i < numRounds[0]; i++) {
           assertEquals( npcHard.evalBoard(boards.get(i)),scores.get(i));
           System.out.println("" + npcHard.evalBoard(boards.get(i)) + "\t" +  scores.get(i));
        }
        System.out.println("--------------------------------------------\n\n");
    }
    //***************************************************************************


    //***************************************************************************
    private void getFinishedBoardInfo(char ch1[], char ch2[], char defChar[], int numRounds[]) {
        Scanner sc = null;
        try {
            File file = new File("src/test/resources/EndGameTestInfo.txt");
            //System.out.println(file.getAbsolutePath());
            sc = new Scanner(file);
            sc.useDelimiter("[\\,\\n,\\r]+");
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File Not Found!");
        }


        ch1[0]        = sc.next().charAt(0);
        ch2[0]        = sc.next().charAt(0);
        defChar[0]    = sc.next().charAt(0);
        numRounds[0]  = sc.nextInt();

        sc.close();
    }
    //***************************************************************************



    //***************************************************************************
    private ArrayList<Integer> getFinishedBoardsScores() {
        ArrayList<Integer> scores = new ArrayList<>();
        Scanner sc = null;
        try {
            File file = new File("src/test/resources/EndGameTestResults.txt");
            //System.out.println(file.getAbsolutePath());
            sc = new Scanner(file);
            sc.useDelimiter("[\\,\\n,\\r]+");
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File Not Found!");
        }

        for(int i = 0; i < NUM_END_GAMES; i++) {
            int tempInt = sc.nextInt();
            scores.add(tempInt);
        }
        // Test Code ******************************************
        //        for(int i = 0 ; i < NUM_END_GAMES; i++)
        //        {
        //            System.out.println(scores.get(i));
        //        }
        // ****************************************************
        sc.close();
        return scores;
    }
    //***************************************************************************


    //***************************************************************************
    private ArrayList<Board> getFinishedBoards() {
        ArrayList<Board> boards = new ArrayList<>();
        Scanner sc = null;



        try {
            File file = new File("src/test/resources/EndGameTestData.txt");
            // System.out.println(file.getAbsolutePath());
            sc = new Scanner(file);
            sc.useDelimiter("\n");
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("File Not Found!");
        }

        String temp = "";

        char tempArray[][] = new char[3][3];


        for(int i = 0; i < NUM_END_GAMES; i++) {
            for(int j = 0; j < 3; j++) {
                temp = sc.next(); // get entire row
                for(int k = 0; k < 3; k++) {
                    tempArray[j][k] = temp.charAt(k);
                }
            }
            boards.add(new Board(tempArray));
            boards.get(i).toString();
            sc.next();// Skip \n inbetween boards
        }

        // Test Code **************************************************
        //        for(int i = 0; i < NUM_END_GAMES; i++) {
        //            System.out.println(boards.get(i).toString());
        //
        //        }
        // ************************************************************

        sc.close();
        return boards;
    }
    //***************************************************************************

    //***************************************************************************
    // Redundant
    // Covered by evalBoardTest()
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
    //***************************************************************************



    //***************************************************************************
    @Test public void isBoardFullTest() {
        ArrayList<Board> boards = new ArrayList<>();
        NPCHard npcHard = new NPCHard('X', 'Y');


        char tempBoard[][] = null;
        tempBoard = new char[][] {
                {'X', 'O', 'O'},
                {'O', 'O', 'X'},
                {'O', ' ', 'X'}
        };
        boards.add(new Board(tempBoard));
        tempBoard = new char[][] {
                {'X', 'O', 'O'},
                {'O', 'O', 'X'},
                {'O', 'X', 'X'}
        };
        boards.add(new Board(tempBoard));
        tempBoard = new char[][] {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        boards.add(new Board(tempBoard));

        assertFalse(npcHard.isBoardFull(boards.get(0)));
        assertTrue (npcHard.isBoardFull(boards.get(1)));
        assertFalse(npcHard.isBoardFull(boards.get(0)));

        System.out.println("--------------------------------------------");
        System.out.println("isBoardFull Test");
        System.out.println("--------------------------------------------");

        for(int i = 0; i < 3; i++) {
            System.out.println("Board " + i + ":\t Is Full?\t" +
                    npcHard.isBoardFull(boards.get(i)));
            System.out.println(boards.get(i));
        }
        System.out.println("--------------------------------------------\n\n");
    }
    //***************************************************************************
}

