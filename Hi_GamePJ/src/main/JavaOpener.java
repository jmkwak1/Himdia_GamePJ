package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.Game;
import Blackjack.Blackjack;
import tetris.Tetris;
import main.Main;
import javafx.scene.Node;

import java.io.IOException;

public class JavaOpener{
	
    private Stage primaryStage; // primaryStage 필드 추가
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public void boomOpen(ActionEvent event) {
        System.out.println("지뢰찾기 시작");
        try {
            Stage gameStage = new Stage();
            Game game = new Game();
            game.start(gameStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BlackjackOpen(ActionEvent event) {
        System.out.println("블랙잭 시작");
        try {
            Stage blackjackStage = new Stage();
            Blackjack main = new Blackjack();
            main.start(blackjackStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tetrisOpen(ActionEvent event) {
        System.out.println("테트리스 시작");
        try {
            Stage tetrisStage = new Stage();
            Tetris main = new Tetris();
            main.start(tetrisStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void boardOpen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
            Parent boardForm = loader.load();

            Stage boardStage = new Stage();
            boardStage.setScene(new Scene(boardForm));
            boardStage.setTitle("게시판");
            boardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void MyPageOpen() throws IOException {
	       FXMLLoader loader = new FXMLLoader(getClass().getResource("MyPage.fxml"));
	       Stage MyPageStage = new Stage();
	       Parent menuForm = loader.load();
	       
	       try {
	           Label MyID = (Label) menuForm.lookup("#MyID");    // label:id인 MyID값을 지정
	           MyID.setText(String.valueOf(Login.getId()));   // gold값에 해당하는 값을 문자열로 변환하여 입력
	           
	           Label MyName = (Label) menuForm.lookup("#MyName");    // label:id인 MyName값을 지정
	           MyName.setText(String.valueOf(Login.getName()));
	           
	           Label MyEmail = (Label) menuForm.lookup("#MyEmail");    // label:id인 MyEmail값을 지정
	           MyEmail.setText(String.valueOf(Login.getEmail()));
	           
	           Label gold = (Label) menuForm.lookup("#MyGold");    // label:id인 MyGold값을 지정
	           gold.setText(String.valueOf(Login.getGold()));
	           
	           MyPageStage.setScene(new Scene(menuForm));
	           MyPageStage.setTitle("내 정보");
	           MyPageStage.show();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }

    // 로그아웃 버튼 클릭 이벤트 핸들러
    @FXML
    private void logout(ActionEvent event) {
        // 현재 창을 닫음
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        // Main.java 파일 실행
        try {
        	Login.setId(null);
        	Login.setPw(null);
        	Login.setGold(0);
            Stage mainStage = new Stage();
            Main main = new Main();
            main.start(mainStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
