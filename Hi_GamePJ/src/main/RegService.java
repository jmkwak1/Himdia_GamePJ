package main;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import sceneBuilders.MemberDAO;

public class RegService {
	private MemberDAO memberDao;
	
	public RegService() {
		memberDao = new MemberDAO();
	}
	
	public void insert(Parent regForm) {
	
		ComboBox<String> ageCombo = (ComboBox<String>) regForm.lookup("#ageCombo");
		
		Button regButton = (Button) regForm.lookup("#regButton");
		PasswordField pwFld = (PasswordField) regForm.lookup("#pw");
		PasswordField confirmFld = (PasswordField) regForm.lookup("#confirm");
		if (pwFld.getText().equals(confirmFld.getText())) {
			TextField idFld = (TextField) regForm.lookup("#id");
			if (idFld.getText().isEmpty() == false) {
				TextField nameFld = (TextField) regForm.lookup("#name");
				RadioButton womanRadio = (RadioButton) regForm.lookup("#womanRadio");
				RadioButton manRadio = (RadioButton) regForm.lookup("#manRadio");

				String gender = "";
				if (womanRadio.isSelected())
					gender = womanRadio.getText();
				else if (manRadio.isSelected())
					gender = manRadio.getText();

				CheckBox musicCheck = (CheckBox) regForm.lookup("#musicCheck");
				CheckBox sportCheck = (CheckBox) regForm.lookup("#sportCheck");
				CheckBox movieCheck = (CheckBox) regForm.lookup("#movieCheck");
				String hobbys = "";
				if (musicCheck.isSelected())
					hobbys += musicCheck.getText() + " ";

				if (sportCheck.isSelected())
					hobbys += sportCheck.getText() + " ";

				if (movieCheck.isSelected())
					hobbys += movieCheck.getText();

				memberDao.insert(idFld.getText(), pwFld.getText(), nameFld.getText(), email);
				CommonService.windowClose(regForm);
			} else {
				CommonService.msg("아이디를 입력하고 다시 시도하세요.");
			}
		} else {
			CommonService.msg("비밀번호를 확인 후 다시 입력하세요.");
		}
	}
}
