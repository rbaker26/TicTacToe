package cs4b.proj1;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.FileInputStream;

/*
 * This board is a simple 3 x 3 grid set up to play tic-tac-toe using a javafx gridpane
 * The constructor iterates through each cell of the gridpane placing a pane with an ImageView.
 * Each pane is set up with an on click event to handle the toggling the tokens in the space
 */
public class BoardGUI extends GridPane {
    private final int MAX_SIZE = 3;
    private Image xImg;
    private Image oImg;
    private Image emptyImg;

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
                        this.toggleToken((Node)event.getSource());
                        //TODO Daniel, here is where your signal goes.
//                    GridPane.getRowIndex((Node)event.getSource());
//                    GridPane.getColumnIndex((Node)event.getSource());
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
            image = (ImageView)((Pane)node).getChildren().get(0);
            image.setImage(emptyImg);
        }
    }
}
