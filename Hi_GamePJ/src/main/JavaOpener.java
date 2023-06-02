package main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.Game;
import Blackjack.Blackjack;
import tetris.Tetris;

import java.io.IOException;

public class JavaOpener {
		
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
}
