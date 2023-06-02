package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	@FXML
	private TextField idFld;
	@FXML
	private PasswordField pwFld;
	private LoginService service;
	
	private Opener opener;
	public void setOpener(Opener opener) {
		this.opener = opener;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new LoginService();
	}
	
	// 로그인 버튼을 클릭하면 동작하는 메서드
	public void loginProc() {
		service.login(idFld.getText(), pwFld.getText());
		// 로그인성공이라면 메인화면을 로그인 창이 있는 곳에 메인화면을 실행
		// 로그인실패라면 아무것도 하지 않는다.
		System.out.println("로그인 성공여부 : " + Login.getId());
		if(Login.getId() != null) {
			opener.mainOpen();
			
 			// opener or mainOpen(); null 어디에 들어가있는 확인
		}
	}
	
	public void cancelProc() {
		idFld.clear();
		pwFld.clear();
		idFld.requestFocus();
	}
	
	public void regProc() {
		// 회원가입 화면을 새창으로 실행
		opener.regOpen();
	}
	

}





