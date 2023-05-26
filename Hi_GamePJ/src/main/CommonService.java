package main;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CommonService {

	public static void msg(String contextText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("알림");
		alert.setContentText(contextText);
		alert.show();
	}
	public static void windowClose(Stage stage) {
		stage.close();
	}
	
	public static void windowClose(Parent form) {
		Stage stage = (Stage) form.getScene().getWindow();
		stage.close();
	}
}










