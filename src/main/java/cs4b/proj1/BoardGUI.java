package cs4b.proj1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.FileInputStream;

public class BoardGUI extends TilePane {
    private final int MAX_SIZE = 3;
    private Image xImg;
    private Image oImg;
    private Image emptyImg;

    BoardGUI() {
        try {
            xImg = new Image(new FileInputStream("src/main/resources/cs4b/proj1/img/X.png"));
            oImg = new Image(new FileInputStream("src/main/resources/cs4b/proj1/img/O.png"));
            emptyImg = new Image(new FileInputStream("src/main/resources/cs4b/proj1/img/Empty.png"));
        }
        catch(Exception ex) {
            System.out.println("File not found");
        }


        try {
            for (int row = 0; row < MAX_SIZE; row++) {
                for (int col = 0; col < MAX_SIZE; col++) {

//                    this.getChildren().add();

                }
            }
        }
        catch(Exception ex) {
            System.out.println("Fuck");
        }

    }
}
