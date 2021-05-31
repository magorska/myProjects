package TipTapToe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Board extends Application {
    private GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(gridPane, 300, 300);

        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setOnAction(event -> {
                    if (button.getText().equals("")) {
                        button.setText("X");
                        button.setStyle("-fx-font-size:42");
                        button.setTextFill(Color.rgb(8, 138, 152));
                        System.out.println("Checking if User wins");
                        Optional<String> result = whoWins("X");
                        if (result.isPresent()) {
                            popUpWhoWin("X has won!");
                            return;
                        }
                        computerMoves();
                        System.out.println("Checking if Computer wins");
                        Optional<String> resultForO = whoWins("O");
                        if (resultForO.isPresent()) {
                            popUpWhoWin("O has won!");
                            return;
                        }
                    }
                });
                gridPane.add(button, rowIndex, colIndex);
            }
        }
        primaryStage.setTitle("TipTapToe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void computerMoves() {
        System.out.println("Computer turn");
        List<Button> buttons = gridPane.getChildren().stream()
                .filter(element -> element instanceof Button)
                .map(element -> (Button) element)
                .filter(button -> button.getText().equals(""))
                .collect(Collectors.toList());

        Random random = new Random();
        int randomNumber = random.nextInt(buttons.size());
        Button button = buttons.get(randomNumber);
        button.setText("O");
        button.setStyle("-fx-font-size:42");
        button.setTextFill(Color.rgb(173, 16, 212));
    }

    public Optional<String> whoWins(String sign) {
        List<Button> filledButtons = gridPane.getChildren().stream()
                .filter(element -> element instanceof Button)
                .map(element -> (Button) element)
                .filter(button -> button.getText().equals(sign))
                .collect(Collectors.toList());
        if (filledButtons.size() < 3) {
            return Optional.empty();
        }
        if (checkInRow(filledButtons)
                || checkInColumn(filledButtons)
                || checkInCross(filledButtons)) {
            return Optional.of(sign);
        }
        if (filledButtons.size() == 9 && !checkInRow(filledButtons)
                && !checkInColumn(filledButtons) && !checkInCross(filledButtons)) {
            return Optional.of("It's a draw!");

        }
        return Optional.empty();
    }

    public boolean checkInRow(List<Button> filledButtons) {

        int sumInFirstRow = 0;
        int sumInSecondRow = 0;
        int sumInThirdRow = 0;

        for (Button elements : filledButtons) {
            int row = GridPane.getRowIndex(elements);
            if (row == 0) {
                sumInFirstRow++;
            } else if (row == 1) {
                sumInSecondRow++;
            } else {
                sumInThirdRow++;
            }
        }
        return (sumInFirstRow == 3 || sumInSecondRow == 3 || sumInThirdRow == 3);
    }

    public boolean checkInColumn(List<Button> filledButtons) {

        int sumInFirstCol = 0;
        int sumInSecondCol = 0;
        int sumInThirdCol = 0;

        for (Button elements : filledButtons) {
            int column = GridPane.getColumnIndex(elements);
            if (column == 0) {
                sumInFirstCol++;
            } else if (column == 1) {
                sumInSecondCol++;
            } else {
                sumInThirdCol++;
            }
        }
        return (sumInFirstCol == 3 || sumInSecondCol == 3 || sumInThirdCol == 3);
    }

    public boolean checkInCross(List<Button> filledButtons) {

        int sumInCross1 = 0;
        int sumInCross2 = 0;
        for (Button elements : filledButtons) {
            int getRow = GridPane.getRowIndex(elements);
            int getCol = GridPane.getColumnIndex(elements);
            if (getRow == 0 && getCol == 0) {
                sumInCross1++;
            }
            if (getRow == 0 && getCol == 2) {
                sumInCross2++;
            }
            if (getRow == 1 && getCol == 1) {
                sumInCross1++;
                sumInCross2++;
            }
            if (getRow == 2 && getCol == 2) {
                sumInCross1++;
            }
            if (getRow == 2 && getCol == 0) {
                sumInCross2++;
            }
        }
        return (sumInCross1 == 3 || sumInCross2 == 3);
    }

    public void popUpWhoWin(String sign) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(sign);
        dialogStage.initModality(Modality.WINDOW_MODAL);


        Button newGameButton = new Button();
        newGameButton.setText("Start a new game");
        newGameButton.setFont(new Font("Open Sans", 14));
        newGameButton.setOnAction(event -> {
            gridPane.getChildren().stream()
                    .filter(element -> element instanceof Button)
                    .map(element -> (Button) element)
                    .filter(button -> button.getText().equals("X") || button.getText().equals("O"))
                    .forEach(button -> button.setText(""));
            dialogStage.close();

        });

        Button endGameButton = new Button();
        endGameButton.setText("End of the game");
        endGameButton.setFont(new Font("Open Sans", 14));
        endGameButton.setOnAction(event -> {
            System.exit(0);
        });

        VBox vbox = new VBox(newGameButton, endGameButton);
        vbox.setSpacing(7);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));

        dialogStage.setScene(new Scene(vbox, 250, 150));
        dialogStage.show();
    }
}
