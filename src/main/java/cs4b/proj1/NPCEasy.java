package cs4b.proj1;

import javafx.util.Pair;
import java.util.Random;
import java.util.ArrayList;

public class NPCEasy implements PlayerBehavior {

    NPCEasy() {
    }

    @Override
    public Pair<Integer, Integer> getMove(Board b) {

        Pair<Integer, Integer> result = null;
        char[][] boardArray = b.getBoardArray();

        ArrayList<Pair<Integer, Integer>> emptySpaces = new ArrayList<>();

        for(int row = 0; row < boardArray.length; row++) {
            for(int col = 0; col < boardArray[row].length; col++) {
                if(boardArray[row][col] == ' ') {
                    emptySpaces.add(new Pair<>(row, col));
                }
            }
        }

        if(emptySpaces.size() > 0) {
            Random r = new Random();

            //System.out.println(emptySpaces.size());
            //System.out.println(Math.abs(r.nextInt()) % emptySpaces.size());

            result = emptySpaces.get(Math.abs(r.nextInt()) % emptySpaces.size());
        }

        return result;
    }

    //    // idk if i like this. I might make this makeMove(Board b)
//    @Override
//    public Pair<Integer,Integer> getMove(Board b) {
//
//
//        return new Pair<>(0,0);
//    }
}
