package TipTapToe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Board extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 300, 300);

        Button button = new Button();
        button.setPrefSize(100, 100);

        for (int rowIndex =0; rowIndex <3; rowIndex++) {
            for (int colIndex =0; colIndex<3; colIndex++) {
                gridPane.add(button, rowIndex, colIndex);
                rowIndex++;
            }
        }

        primaryStage.setTitle("TipTapToe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
