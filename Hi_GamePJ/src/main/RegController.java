package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MemberDAO;

public class RegController implements Initializable {
	private Stage regStage;
	private Parent regForm;
	private RegService regService;
	
	public void setRegStage(Stage regStage) {
		this.regStage = regStage;
	}

	public void setRegForm(Parent regForm) {
		this.regForm = regForm;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		regService = new RegService();
	}

	// 회원가입 버튼 클릭 시 호출되는 메서드
	public void regProc() {
		regService.insert(regForm);
	}

	// 취소 버튼 클릭 시 호출되는 메서드
	public void cancelProc() {
		regStage.close();
	}
}








