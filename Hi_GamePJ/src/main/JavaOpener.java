package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.Game;
import Blackjack.Blackjack;
import tetris.Tetris;

import java.io.IOException;

public class JavaOpener {

    private Stage primaryStage;

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

    public void blackjackOpen(ActionEvent event) {
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
            Parent boardForm = FXMLLoader.load(getClass().getResource("board.fxml"));

            Stage boardStage = new Stage();
            boardStage.setScene(new Scene(boardForm));
            boardStage.setTitle("게시판");
            boardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {
        try {
            Parent loginForm = FXMLLoader.load(getClass().getResource("loginForm.fxml"));

            primaryStage.setScene(new Scene(loginForm));
            primaryStage.setTitle("로그인 화면");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
