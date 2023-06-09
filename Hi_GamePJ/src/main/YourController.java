package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YourController {
    @FXML
    private ListView<String> postListView;
    @FXML
    private TextField postTextField;

    private Connection con;

    public YourController() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "oracle";
        String password = "oracle";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        // 애플리케이션 시작 시 DB에서 글을 가져와서 postListView에 표시
        loadPostsFromDB();
    }

    public void savePost() {
        String postText = postTextField.getText();
        if (!postText.isEmpty()) {
            // DB에 글 저장
            savePostToDB(postText);

            // 글 저장 후 postListView 갱신
            loadPostsFromDB();

            // 글 작성 후 텍스트 필드 초기화
            postTextField.clear();
        }
    }

    private void loadPostsFromDB() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "oracle";
            String password = "oracle";
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM posts";
            ResultSet resultSet = statement.executeQuery(query);

            List<String> posts = new ArrayList<>();
            while (resultSet.next()) {
                String post = resultSet.getString("post");
                posts.add(post);
            }

            postListView.getItems().setAll(posts);

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePostToDB(String postText) {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "oracle";
            String password = "oracle";
            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO posts (post) VALUES (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, postText);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}