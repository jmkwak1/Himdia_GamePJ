package main;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.MemberDAO;

public class RegService {
	private MemberDAO memberDao;
	
	public RegService() {
		memberDao = new MemberDAO();
	}
	
	public void insert(Parent regForm) {
	
		Button regButton = (Button) regForm.lookup("#regButton");		//회원가입
		PasswordField pwFld = (PasswordField) regForm.lookup("#pw");	//비번
		PasswordField confirmFld = (PasswordField) regForm.lookup("#confirm");	//비번확인
		TextField emailFld = (TextField) regForm.lookup("#email");		//이메일
		
		if (pwFld.getText().equals(confirmFld.getText())) {
			TextField idFld = (TextField) regForm.lookup("#id");		//아이디
			if (idFld.getText().isEmpty() == false) {
				TextField nameFld = (TextField) regForm.lookup("#name");//이름

				memberDao.insert(idFld.getText(), pwFld.getText(), nameFld.getText(), emailFld.getText());
				CommonService.windowClose(regForm);
			} else {
				CommonService.msg("아이디를 입력하고 다시 시도하세요.");
			}
		} else {
			CommonService.msg("비밀번호를 확인 후 다시 입력하세요.");
		}
	}
}
