package main;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MyController {
    @FXML
    private ListView<String> listView;

    @FXML
    private TextField textField;

    private MyDAO dao;

    public MyController() {
        // DAO 클래스의 인스턴스 생성
        dao = new MyDAO();
    }

    @FXML
    private void saveButtonClicked() {
        String boardT = textField.getText();

        // 게시글을 DB에 저장
        dao.saveBoard(boardT, Login.getId());

        // 리스트에 게시글 추가
        listView.getItems().add(boardT);
        textField.clear();
    }
}