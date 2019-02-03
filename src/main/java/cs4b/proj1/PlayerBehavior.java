package cs4b.proj1;

import javafx.util.Pair;

public abstract interface PlayerBehavior {
    //PlayerBehavior(){}
    public Pair<Integer,Integer> getMove(Board b);
}
