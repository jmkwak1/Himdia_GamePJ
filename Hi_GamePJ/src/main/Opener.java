package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Opener {
	private Stage primaryStage;
	private Stage regStage;
	private Parent regForm;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	// 메인 화면을 실행하는 기능
	public void mainOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectAgame.fxml"));
		
		Parent menuForm;
		try {
			menuForm = loader.load();
			primaryStage.setScene(new Scene(menuForm));
			primaryStage.setTitle("메인 화면");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 회원가입 화면을 실행하는 기능
	public void regOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("regForm.fxml"));
		
		regStage = new Stage();
		try {
			regForm = loader.load();
			RegController regCon = loader.getController();
			regCon.setRegStage(regStage);
			regCon.setRegForm(regForm);
			
			regStage.setScene(new Scene(regForm));
			regStage.setTitle("회원가입 화면");
			regStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
