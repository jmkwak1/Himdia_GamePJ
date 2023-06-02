package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    @FXML
    private TextField idFld;
    @FXML
    private PasswordField pwFld;
    private LoginService service;

    private Opener opener;
    private Stage primaryStage;

    public void setOpener(Opener opener) {
        this.opener = opener;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = new LoginService();
    }

    // 로그인 버튼을 클릭하면 동작하는 메서드
    public void loginProc() {
        service.login(idFld.getText(), pwFld.getText());
        // 로그인 성공이라면 메인 화면을 실행하고, 로그인 실패라면 아무 작업도 수행하지 않음
        System.out.println("로그인 성공여부: " + Login.getId());
        System.out.println("@LoginSerVice_idFld: " + idFld);
        System.out.println("@LoginSerVice_pwFld: " + pwFld);
        System.out.println("@LoginSerVice_Login.getId(): " + Login.getId());

        if (Login.getId() != null || opener == null) {
            if (opener != null) {
                // opener 객체에 값을 설정하여 메인 화면을 열도록 변경
                opener.setPrimaryStage(primaryStage);
                opener.mainOpen();
                System.out.println("#LoginSerVice_opener: " + opener);
                System.out.println("#LoginSerVice_idFld: " + idFld);
                System.out.println("#LoginSerVice_pwFld: " + pwFld);
                System.out.println("#LoginSerVice_Login.getId(): " + Login.getId());
            } else {
                System.out.println("Opener 객체가 초기화되지 않았습니다.");
                System.out.println("LoginSerVice_opener: " + opener);
                System.out.println("LoginSerVice_idFld: " + idFld);
                System.out.println("LoginSerVice_pwFld: " + pwFld);
                System.out.println("LoginSerVice_Login.getId(): " + Login.getId());
            }
        }
    }

    public void cancelProc() {
        idFld.clear();
        pwFld.clear();
        idFld.requestFocus();
    }

    public void regProc() {
        if (opener != null) {
            opener.regOpen();
        } else {
            System.out.println("Opener 객체가 초기화되지 않았습니다.");
        }
    }
}
