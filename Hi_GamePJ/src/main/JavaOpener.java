package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.Game;
import Blackjack.Blackjack;
<<<<<<< HEAD
import tetris.Tetris;
=======
import tetris.TetrisGame;
import javafx.scene.Node;
>>>>>>> branch 'main' of https://github.com/jmkwak1/Himedia_GamePJ.git

import java.io.IOException;

public class JavaOpener {

    private Stage primaryStage;
	private Stage primaryStage; // primaryStage 필드 추가

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
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
=======
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
>>>>>>> branch 'main' of https://github.com/jmkwak1/Himedia_GamePJ.git

<<<<<<< HEAD
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
=======
	public void tetrisOpen(ActionEvent event) {
		System.out.println("테트리스 시작");
		try {
			Stage tetrisStage = new Stage();
			TetrisGame main = new TetrisGame();
			main.start(tetrisStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
>>>>>>> branch 'main' of https://github.com/jmkwak1/Himedia_GamePJ.git

<<<<<<< HEAD
    public void boardOpen(ActionEvent event) {
        try {
            Parent boardForm = FXMLLoader.load(getClass().getResource("board.fxml"));
=======
	public void boardOpen(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("board.fxml"));
			Parent boardForm = loader.load();
>>>>>>> branch 'main' of https://github.com/jmkwak1/Himedia_GamePJ.git

<<<<<<< HEAD
            Stage boardStage = new Stage();
            boardStage.setScene(new Scene(boardForm));
            boardStage.setTitle("게시판");
            boardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
=======
			Stage boardStage = new Stage();
			boardStage.setScene(new Scene(boardForm));
			boardStage.setTitle("게시판");
			boardStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
>>>>>>> branch 'main' of https://github.com/jmkwak1/Himedia_GamePJ.git

<<<<<<< HEAD
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
=======
	public void logout(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("loginForm.fxml"));
			Parent boardForm = loader.load();

			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(new Scene(boardForm));
			primaryStage.setTitle("로그인 화면");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mainOpen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainForm.fxml"));
			Parent mainForm = loader.load();

			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(mainForm));
			primaryStage.setTitle("메인 화면");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void regOpen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("registerForm.fxml"));
			Parent regForm = loader.load();

			Stage regStage = new Stage();
			regStage.setScene(new Scene(regForm));
			regStage.setTitle("회원가입");
			regStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
>>>>>>> branch 'main' of https://github.com/jmkwak1/Himedia_GamePJ.git
}
