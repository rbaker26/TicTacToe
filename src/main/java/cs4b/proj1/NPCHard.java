package cs4b.proj1;


import cs4b.proj1.observer.IObserver;
import cs4b.proj1.observer.SubjectAssistant;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class NPCHard implements PlayerBehavior {
    // Player 1 = +10
    // Player 2 = -10

    private char player1Char = '\0';
    private char player2Char = '\0';
    private Integer MAX_SCORE;
    private Integer MIN_SCORE;

    //***************************************************************************
    NPCHard(char p1, char p2){
      
        player1Char = p1;
        player2Char = p2;


        Board b = new Board();
        MAX_SCORE =  ((b.BOARD_SIZE_X*b.BOARD_SIZE_Y) + 1);
        MIN_SCORE = -((b.BOARD_SIZE_X*b.BOARD_SIZE_Y) + 1);

    }
    //region ISubject *************************************************************

    private SubjectAssistant subjAssist;

    /**
     * Subscribes the given observer, causing its update function to be called
     * for the given event. As there can be a variety of modes, subjects are
     * expected to implement some kind of object (e.g. an enum) to allow
     * subscribers to select what kind of events they are interested in.
     * <p>
     * If an observer attempts to addSubscriber itself more than once, the first
     * subscription should be replaced. (Unless they are with differenct
     * modes, of course.)
     *
     * @param observer The observer which will be subscribed.
     * @author Daniel Edwards
     */
    @Override
    public void addSubscriber(IObserver observer) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.addSubscriber(observer);
    }

    /**
     * Unsubscribes the given observer so that they will no longer receive
     * updates for the given event. Nothing should happen if the observer
     * isn't subscribed.
     *
     * @param observer Observer to be unsubscribed.
     * @author Daniel Edwards
     */
    @Override
    public void removeSubscriber(IObserver observer) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.removeSubscriber(observer);
    }

    private void triggerUpdate(Object event) {
        if(subjAssist == null) {
            subjAssist = new SubjectAssistant();
        }

        subjAssist.triggerUpdate(event);
    }

    //endregion ISubject ***********************************************************



    //***************************************************************************


    //***************************************************************************
    @Override
    public void getMove(Board b, char token) {

        boolean isMax = false;
        int bestVal = -1000;
        int xBest = -1;
        int yBest = -1;
        if(token == player2Char) {
            isMax = true;
        }
        System.out.println("Suggested Move:\t" + "Col " + "\t" + "Row");

        Map<Integer, Pair<Integer,Integer>> moveMap = new TreeMap<>();
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                    // Make Move
                    b.setPos(i,j,token);

                    // get mov val
                    int moveVal = minimax(b,0,isMax);
                    moveMap.put(moveVal,new Pair(i,j));
                    System.out.println("Move Value:\t"+moveVal + "\t" +i +"\t\t"+ j);

                    // undo move
                    b.setPos(i,j,b.DEFAULT_VALUE);

                    if(moveVal > bestVal) {
                        //TODO check xy for correctness
                        xBest = i;
                        yBest = j;
                    }
                }
            }
        }
        int i =  ((TreeMap<Integer, Pair<Integer, Integer>>) moveMap).firstEntry().getValue().getKey();
        int j =  ((TreeMap<Integer, Pair<Integer, Integer>>) moveMap).firstEntry().getValue().getValue();
        System.out.println("*****************************************");
        System.out.println("Suggested Move:\t" + "Col " + "\t" + "Row");
        System.out.println("Suggested Move:\t" + xBest + "\t\t" + yBest);
        System.out.println("Suggested Move:\t" + i + "\t\t" + j);
        System.out.println("*****************************************");
        triggerUpdate(new PlayerBehavior.MoveInfo(i, j));
        System.out.println(b);

    }
    //***************************************************************************



    //***************************************************************************
    public int minimax(Board b, int depth, boolean isMax) {

        int score = evalBoard(b);

        if(score == MAX_SCORE) {
            return score-depth;
        }
        if(score == MIN_SCORE) {
            return score+depth;
        }

        if(isBoardFull(b)) {
            return 0;
        }

        if(isMax) {
            int best = -1000;
            for(int i =0; i < b.BOARD_SIZE_X; i++) {
                for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                    if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                        b.setPos(i,j,player1Char);

                        best = Integer.max(best, minimax(b,depth+1, !isMax));

                        b.setPos(i,j,b.DEFAULT_VALUE);
                    }
                }
            }
            return best;
        }
        else {
            int best = 1000;
            for(int i =0; i < b.BOARD_SIZE_X; i++) {
                for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                    if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                        b.setPos(i,j,player2Char);
                        best = Integer.min(best,minimax(b,depth+1, !isMax));
                        b.setPos(i,j,b.DEFAULT_VALUE);
                    }
                }
            }
            return best;

        }

    }
    //***************************************************************************


    //***************************************************************************
    private int minimize(Board b, int depth, boolean isMax) {
        // minimize
        int worst = Integer.MAX_VALUE;
//        Board temp = new Board(b.getBoardArray()); //TODO make sure this is a deep copy
        
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                if(b.getPos(i,j) == b.DEFAULT_VALUE){
                    b.setPos(i,j,player2Char);
//                    System.out.println("***************");
//                    System.out.print(temp.toString());
//                    System.out.println("***************\n");
                    worst = Integer.min(worst, minimax(b,depth+1,!isMax));
                    b.setPos(i,j,b.DEFAULT_VALUE);
                }
            }
        }
        return worst;
    }
    //***************************************************************************


    //***************************************************************************
    private int maximize(Board b, int depth, boolean isMax) {
        // maximize
        int best = Integer.MIN_VALUE;
        //Board temp = new Board(b.getBoardArray()); //TODO make sure this is a deep copy
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                if(b.getPos(i,j) == b.DEFAULT_VALUE){
                    b.setPos(i,j,player1Char);
//                    System.out.println("***************");
//                    System.out.print(temp.toString());
//                    System.out.println("***************\n");
                    best = Integer.max(best, minimax(b,depth+1,!isMax));
                    b.setPos(i,j,b.DEFAULT_VALUE);
                }
            }
        }
        return best;
    }
    //***************************************************************************


    //***************************************************************************
    boolean isBoardFull(Board b) {
        int defaultSpaceCount = 0;
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            for(int j = 0; j < b.BOARD_SIZE_Y; j++) {
                if(b.getPos(i,j) == b.DEFAULT_VALUE) {
                    defaultSpaceCount++;
                }
            }
        }
        if(defaultSpaceCount > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    //***************************************************************************

    //***************************************************************************
    Integer evalBoard(Board b) {
        Integer rowWim = checkRowWin(b);
        if (rowWim != null) return rowWim;
        Integer colWin = checkColWin(b);
        if (colWin != null) return colWin;
        Integer digWin = checkDig(b);
        if (digWin != null) return digWin;
        return 0;
    }
    //***************************************************************************



    //***************************************************************************
    Integer checkDig(Board b) {
        // Check dig's
        char[][] array = b.getBoardArray();
        if(array[0][0] == array[1][1] && array[1][1] == array[2][2]) {
            if(array[0][0] == player1Char) {
                return MAX_SCORE;
            }
            else if(array[0][0] == player2Char) {
                return MIN_SCORE;
            }
        }
        if(array[0][2]==array[1][1] && array[1][1] == array[2][0]) {
            if(array[0][2] == player1Char) {
                return MAX_SCORE;
            }
            else if(array[0][2] == player2Char) {
                return MIN_SCORE;
            }
        }
        return null;
    }
    //***************************************************************************


    //***************************************************************************
    private Integer checkColWin(Board b) {
        // CheckWinCol
        char[][] array = b.getBoardArray();
        for(int j = 0; j < b.BOARD_SIZE_Y ;j++) {
            if(array[0][j] == array[1][j] && array[1][j] == array[2][j]) {
                if(array[0][j] == player1Char) {
                    return MAX_SCORE;
                }
                else if(array[0][j] == player2Char) {
                    return MIN_SCORE;
                }
            }
        }
        return null;
    }
    //***************************************************************************


    //***************************************************************************
    private Integer checkRowWin(Board b) {
        // CheckWin Rows
        char[][] array = b.getBoardArray();
        for(int i = 0; i < b.BOARD_SIZE_X; i++) {
            if(array[i][0] == array[i][1] && array[i][1] ==array [i][2]) {
                if(array[i][0] == player1Char) {
                    return MAX_SCORE;
                }
                else if(array[i][0] == player2Char){
                    return MIN_SCORE;
                }
            }
        }
        return null;
    }
    //***************************************************************************



    //***************************************************************************
    //***************************************************************************





}
