package cs4b.proj1;

import java.io.Serializable;
import java.util.Formatter;
import java.util.Locale;
import java.util.Objects;

public class Player implements Serializable {


    private String name;
    private char symbol;
    PlayerBehavior pb;


    public Player(PlayerBehavior pb){
        this('\0',"",pb);
    }
    public Player(char symbol, PlayerBehavior pb) {
        this(symbol,"", pb);
    }
    public Player(char symbol, String name, PlayerBehavior pb) {
        this.symbol = symbol;
        this.name = name;
        this.pb = pb;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public PlayerBehavior getPb() {
        return pb;
    }

    public void setPb(PlayerBehavior pb) {
        this.pb = pb;
    }

    @Override
    public String toString() {
        StringBuffer strB = new StringBuffer();
        Formatter form = new Formatter(strB, Locale.US);

        form.format("player name: %s%n", this.name);
        form.format("player symbol: %c%n", this.symbol);
        form.format("player difficulty: ");
        if(pb instanceof NPCEasy) {
            form.format("easy%n");
        } else if(pb instanceof NPCHard) {
            form.format("hard%n");
        } else {
            form.format("human%n");
        }

        return form.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Player) {
            return this.hashCode() == ((Player)o).hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.symbol, this.pb);
    }

}
