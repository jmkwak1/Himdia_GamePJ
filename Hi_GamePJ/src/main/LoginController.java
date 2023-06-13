package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project.ScoreDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField idFld;
    @FXML
    private PasswordField pwFld;
    private LoginService service;
    private Opener opener;
    private ScoreDAO scoreDAO;

    public void setOpener(Opener opener) {
        this.opener = opener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = new LoginService();
        scoreDAO = new ScoreDAO();
    }

    // 로그인 버튼을 클릭하면 동작하는 메서드
    public void loginProc() {
        service.login(idFld.getText(), pwFld.getText());
        // 로그인 성공이라면 메인 화면을 열고, 로그인 실패라면 아무 작업도 수행하지 않음
        System.out.println("ID : " + Login.getId() + " gold : " + Login.getGold());
        if (Login.getId() != null) {
            opener.mainOpen();

            // gold 값을 저장
            String id = Login.getId(); // 로그인한 아이디 가져오기
            int goldValue = Login.getGold(); // 로그인한 사용자의 gold 값 가져오기
        }
    }

    public void cancelProc() {
        idFld.clear();
        pwFld.clear();
        idFld.requestFocus();
    }

    public void regProc() {
        // 회원가입 화면을 열기 위해 Opener 객체의 메서드를 호출
        opener.regOpen();
    }
}
