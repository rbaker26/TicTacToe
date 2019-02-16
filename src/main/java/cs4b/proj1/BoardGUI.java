package cs4b.proj1;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;
import java.util.Objects;

import cs4b.proj1.observer.*;


/*
 * This board is a simple 3 x 3 grid set up to play tic-tac-toe using a javafx gridpane
 * The constructor iterates through each cell of the gridpane placing a pane with an ImageView.
 * Each pane is set up with an on click event to handle the toggling the tokens in the space
 */
public class BoardGUI extends GridPane implements ISubject, IObserver {

    //region Event info containers **********************************************
    public static class SelectedSpaceInfo {
        private int x;
        private int y;

        public SelectedSpaceInfo(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public SelectedSpaceInfo() {
            this(0, 0);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SelectedSpaceInfo that = (SelectedSpaceInfo) o;
            return getX() == that.getX() &&
                    getY() == that.getY();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getX(), getY());
        }

        @Override
        public String toString() {
            return "SelectedSpaceInfo{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    //endregion Event info containers *******************************************



    private final int MAX_SIZE = 3;
    private Image xImg;
    private Image oImg;
    private Image emptyImg;
    private SubjectAssistant subjAssist;

    private boolean xTurn = true;

    BoardGUI() {
        try {
            xImg = new Image(new FileInputStream("src/main/resources/cs4b/proj1/img/X.png"));
            oImg = new Image(new FileInputStream("src/main/resources/cs4b/proj1/img/O.png"));
            emptyImg = new Image(new FileInputStream("src/main/resources/cs4b/proj1/img/Empty.png"));
        }
        catch(Exception ex) {
            System.out.println("File not found");
        }

        subjAssist = new SubjectAssistant();

        for (int row = 0; row < MAX_SIZE; row++) {
            for (int col = 0; col < MAX_SIZE; col++) {
                Pane space = new Pane();
                ImageView token = new ImageView();
                token.setImage(emptyImg);
                token.setPreserveRatio(true);
                token.setFitHeight(100);
                space.getChildren().add(token);
                space.setOnMouseClicked(event -> {
                    System.out.println(event.getButton());
                    if(event.getButton() == MouseButton.SECONDARY) {
                        this.resetBoard();
                    } else {
                        //this.toggleToken((Node)event.getSource());
                        subjAssist.triggerUpdate(
                            new SelectedSpaceInfo(
                                GridPane.getColumnIndex((Node)event.getSource()),
                                GridPane.getRowIndex((Node)event.getSource())
                            )
                        );
                    }
                });
                this.add(space, col, row);
            }
        }
        this.setGridLinesVisible(true);
    }

    public void toggleToken(Node node) {
        ImageView token = (ImageView)((Pane)node).getChildren().get(0);
        if(token.getImage().equals(emptyImg)) {
            if(xTurn) {
                token.setImage(xImg);
                xTurn = false;
            } else {
                token.setImage(oImg);
                xTurn = true;
            }
        }
    }

    public void resetBoard() {
        ObservableList<Node> nodes = this.getChildren();
        ImageView image;
        for(Node node : nodes) {
            if(node instanceof Pane) {
                image = (ImageView) ((Pane) node).getChildren().get(0);
                image.setImage(emptyImg);
            }
        }
    }

    public void drawBoard(Board currentBoard) {
        System.out.println("Drawing board");

        ObservableList<Node> nodes = this.getChildren();
        ImageView image;
        for(Node node : nodes) {

            if(node instanceof Pane) {
                image = (ImageView) ((Pane) node).getChildren().get(0);
                int x = GridPane.getColumnIndex(node);
                int y = GridPane.getRowIndex(node);

                // TODO This is terrible. Characters are garbage.
                switch(currentBoard.getPos(x, y)) {
                    case 'X':
                        image.setImage(xImg);
                        break;
                    case 'O':
                        image.setImage(oImg);
                        break;
                    case ' ':
                        image.setImage(emptyImg);
                        break;
                    default:
                        throw new RuntimeException("Invalid board");
                }
            }
        }
    }

    /**
     * Let the observer know that something happened with one of its subjects.
     *
     * @param eventInfo Whatever the subject decides to share about the event.
     * @author Daniel Edwards
     */
    @Override
    public void update(Object eventInfo) {
        Board currentBoard = null;

        if(eventInfo instanceof Game.TurnInfo) {
            System.out.println("BoardGUI recieved an update: " + eventInfo);
            currentBoard = ((Game.TurnInfo) eventInfo).getCurrentBoard();
        }
        else {
            System.out.println("BoardGUI recieved an update (unhandled): " + eventInfo.getClass().getName());
        }

        if(currentBoard != null) {
            drawBoard(currentBoard);
        }
    }

    //region ISubject *************************************************************

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
        subjAssist.removeSubscriber(observer);
    }

    //endregion ISubject ***********************************************************
}
