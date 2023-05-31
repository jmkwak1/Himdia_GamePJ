 package project;

import javafx.application.Application;
import javafx.stage.Stage;

public class MineSweeper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        game.start(primaryStage);
    }
}