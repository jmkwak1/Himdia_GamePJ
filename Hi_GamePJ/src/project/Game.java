package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Login;
import main.MemberDAO;
import project.ScoreDAO;

import java.util.Random;
import java.util.UUID;

public class Game extends Application {

    private static final int ROW = 10;
    private static final int COL = 10;
    private static final int MINE_CNT = 10;
    private static final String MINE = "*";
    private static final String NONE = " ";
    private String mineArr[][] = null;
    private boolean gameOver = false;
    private int score;
    private Label goldLabel;
    private int clickedRow = -1;
    private int clickedCol = -1;
    private GridPane gridPane;
    private Button restartButton;
    private ScoreDAO scoreDAO; // ScoreDAO 인스턴스 추가
    private MemberDAO memberDAO; // MemberDAO 인스턴스 추가

    public void start(Stage primaryStage) {
        scoreDAO = new ScoreDAO(); // ScoreDAO 인스턴스 생성
        memberDAO = new MemberDAO(); // MemberDAO 인스턴스 생성

        // 현재 사용자의 아이디 가져오기

        gridPane = createGridPane();

        setInit();
        setMine(MINE_CNT);
        setCellValues();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                Button cellButton = createCellButton(row, col);
                gridPane.add(cellButton, col, row);
            }
        }

        goldLabel = new Label("Gold: 0");
        goldLabel.setStyle("-fx-font-size: 16px;");

        restartButton = new Button("저장 및 재시작");
        restartButton.setOnAction(event -> restartGame());

        HBox goldBox = new HBox(goldLabel);
        goldBox.setAlignment(Pos.CENTER_LEFT);

        HBox restartBox = new HBox(restartButton);
        restartBox.setAlignment(Pos.CENTER_RIGHT);

        HBox hbox = new HBox(goldBox, restartBox);
        hbox.setSpacing(20);

        HBox.setHgrow(restartBox, Priority.ALWAYS);

        VBox vbox = new VBox(hbox, gridPane);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("지뢰찾기");
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(400, 400);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        return gridPane;
    }

    private Button createCellButton(int row, int col) {
        Button button = new Button();
        button.setPrefSize(40, 40);
        button.setStyle("-fx-focus-color: #b5b5b5; -fx-faint-focus-color: transparent;");

        button.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                if (button.isDisabled()) {
                    button.setDisable(false);
                    button.setText("");
                } else {
                    button.setDisable(true);
                    button.setText("★");
                }
            }
        });

        button.setOnAction(event -> {
            if (!gameOver && !button.isDisabled()) {
                String value = mineArr[row][col];
                if (value.equals(MINE)) {
                    showAlert("게임 오버", "다시 도전해 주세요!!");
                    gameOver = true;
                } else {
                    if (clickedRow == -1 && clickedCol == -1) {
                        clickedRow = row;
                        clickedCol = col;
                    } else {
                        int rowDiff = Math.abs(row - clickedRow);
                        int colDiff = Math.abs(col - clickedCol);
                        if (rowDiff <= 1 && colDiff <= 1) {
                            disableSurroundingButtons(clickedRow, clickedCol);
                        }
                    }

                    button.setText(value);
                    button.setDisable(true);
                    score += 10;
                    goldLabel.setText("Gold: " + score);
                }
            } else if (button.isDisabled()) {
                button.setDisable(false);
                button.setText("");
            }
        });

        return button;
    }

    private void disableSurroundingButtons(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < ROW && j >= 0 && j < COL) {
                    Button button = (Button) getNodeByRowColumnIndex(i, j);
                    button.setDisable(true);
                }
            }
        }
    }

    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    private void setInit() {
        mineArr = new String[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                mineArr[i][j] = NONE;
            }
        }
    }

    private void setMine(int mineCnt) {
        Random ran = new Random();
        while (mineCnt-- > 0) {
            int row = ran.nextInt(ROW);
            int col = ran.nextInt(COL);
            if (mineArr[row][col].equals(MINE)) {
                mineCnt++;
            }
            if (mineArr[row][col].equals(NONE)) {
                mineArr[row][col] = MINE;
            }
        }
    }

    private void setCellValues() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (!mineArr[row][col].equals(MINE)) {
                    int count = getMineCount(row, col);
                    mineArr[row][col] = Integer.toString(count);
                }
            }
        }
    }

    private int getMineCount(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < ROW && j >= 0 && j < COL && mineArr[i][j].equals(MINE)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void restartGame() {
        gameOver = false;
        goldLabel.setText("Gold: " + score);
        clickedRow = -1;
        clickedCol = -1;

        setInit();
        setMine(MINE_CNT);
        setCellValues();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                Button cellButton = createCellButton(row, col);
                gridPane.add(cellButton, col, row);
            }
        }

        // 게임 종료 후 gold 값을 저장
        scoreDAO.saveScore(Login.getId(), score); // ScoreDAO를 사용하여 gold 값을 저장

        // gold 값을 저장한 후에 게임 종료를 알리는 메시지를 표시
        showAlert("게임 종료", "게임이 종료되었습니다. 저장되었습니다.");

        score = Login.getGold(); // gold 값을 초기화하여 다음 게임에 영향을 주지 않도록 함
        goldLabel.setText("Gold: " + score);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
