package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        	Login.setGold(null);
            Stage mainStage = new Stage();
            Main main = new Main();
            main.start(mainStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
