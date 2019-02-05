package cs4b.proj1;

public class Player {


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

}
