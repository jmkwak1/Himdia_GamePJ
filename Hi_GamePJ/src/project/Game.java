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
      GridPane gridPane = new GridPane();// GridPane 객체를 생성합니다.
      gridPane.setPrefSize(400, 400);// setPrefSize 메서드를 사용하여 그리드 패널의 크기를 400x400으로 설정합니다.
      gridPane.setAlignment(Pos.CENTER);// setAlignment 메서드를 사용하여 그리드 패널의 정렬 방식을 가운데로 설정합니다.
      gridPane.setHgap(5);
      gridPane.setVgap(5);// setHgap과 setVgap 메서드를 사용하여 셀 사이의 수평 및 수직 간격을 설정합니다.
      return gridPane;// 생성한 GridPane 객체를 반환합니다.
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

   /*
    * disableSurroundingButtons 메서드는 주어진 row와 col 값을 기준으로 주변 버튼을 비활성화하는 역할을 합니다.
    */
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

   /*
    * disableSurroundingButtons 메서드는 row와 col 값을 기준으로 주변의 버튼을 비활성화(disable)하는 기능을
    * 수행합니다.
    */
   private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column) {
      for (javafx.scene.Node node : gridPane.getChildren()) {
         if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
            return node;
         }
      }
      return null;
   }

   /*
    * setInit 메서드는 mineArr 배열을 초기화하는 역할을 합니다. mineArr 배열을 크기 ROW x COL로 생성합니다.
    * 이중반복문을 사용하여 모든 배열 요소를 순회하면서 NONE 값을 할당합니다.
    */
   private void setInit() {
      mineArr = new String[ROW][COL];
      for (int i = 0; i < ROW; i++) {
         for (int j = 0; j < COL; j++) {
            mineArr[i][j] = NONE;
         }
      }
   }

   /*
    * setMine 메서드는 mineArr 배열에 지뢰를 배치하는 역할을 합니다. 
    * mineCnt 변수에 저장된 값만큼 반복하면서 지뢰를 배치합니다.
    * Random 객체를 사용하여 무작위로 row와 col 값을 생성합니다. 
    * 생성된 위치에 이미 지뢰가 있는 경우 mineCnt를 증가시킵니다.
    * 이는 중복된 위치에 지뢰를 배치하지 않기 위한 처리입니다. 
    * 생성된 위치에 지뢰가 없는 경우에만 해당 위치에 MINE 값을 할당합니다.
    */
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
   
   /*
    * setCellValues 메서드는 지뢰가 아닌 셀에 해당하는 mineArr 배열 요소에 주변 지뢰 개수를 설정하는 역할을 합니다.
    * ROW와 COL 범위를 반복하면서 배열의 각 요소를 확인합니다.
    * 현재 위치가 지뢰가 아닌 경우에만 처리를 진행합니다.
    * getMineCount 메서드를 호출하여 현재 위치의 주변 지뢰 개수를 계산합니다.
    * 계산된 주변 지뢰 개수를 문자열로 변환하여 현재 위치의 mineArr 배열 요소에 할당합니다.
    */
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
   
   /*
    * getMineCount 메서드는 주어진 위치(row, col) 주변에 있는 지뢰의 개수를 반환하는 역할을 합니다.
    * count 변수를 초기화합니다.
    * 현재 위치를 기준으로 주변 셀을 반복하면서 지뢰의 개수를 세어줍니다.
    * 주변 셀을 확인하기 위해 이중 반복문을 사용하며, 현재 위치의 상하좌우 및 대각선 방향을 확인합니다.
    * 주변 셀이 유효한 범위 내에 있고, 해당 셀이 지뢰인 경우 count를 증가시킵니다.
    * 최종적으로 계산된 count 값을 반환합니다.
    */
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
   
   /*
    * showAlert 메서드는 경고 창을 표시하는 역할을 합니다.
    * Alert 객체를 생성합니다. AlertType.INFORMATION을 사용하여 정보를 나타내는 경고 창을 생성합니다.
    * 경고 창의 제목을 설정합니다. setTitle 메서드를 사용하여 title 값을 설정합니다.
     * 경고 창의 헤더 텍스트를 설정합니다. setHeaderText 메서드를 사용하여 null 값을 설정합니다.
    * 경고 창의 본문 내용을 설정합니다. setContentText 메서드를 사용하여 message 값을 설정합니다.
    * showAndWait 메서드를 호출하여 경고 창을 표시합니다. 이 메서드는 사용자의 입력을 기다리고, 창이 닫힐 때까지 실행을 일시 중단합니다.
    */
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