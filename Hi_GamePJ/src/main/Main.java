package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
		
		Parent form = loader.load();
		
		// fxml에 지정된 Controller의 참조값을 반환
		LoginController loginCon = loader.getController();
		
		Opener opener = new Opener();
		loginCon.setOpener(opener);
		opener.setPrimaryStage(primaryStage);
		
		primaryStage.setScene(new Scene(form));
		primaryStage.setTitle("로그인 화면");
		primaryStage.show();
	
	}
}



