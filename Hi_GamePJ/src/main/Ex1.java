package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ex1 extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.out.print(getClass());
		System.out.print(getClass().getResource("loginForm.fxml"));//경로
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
		Parent form = loader.load();
		
		primaryStage.setScene(new Scene(form));// 생성자 화면에 넣어줌
		primaryStage.setTitle("loginForm");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}