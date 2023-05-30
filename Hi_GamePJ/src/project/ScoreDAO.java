package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDAO {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 데이터베이스 URL
    private static final String user = "oracle"; // 데이터베이스 사용자 이름
    private static final String password = "oracle"; // 데이터베이스 비밀번호

    public static void saveScore(int score) {
        String sql = "INSERT INTO scores (score) VALUES (?)"; // 쿼리문

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, score);
            stmt.executeUpdate();

            System.out.println("Score saved to the database.");

        } catch (SQLException e) {
            System.out.println("Failed to save score to the database.");
            e.printStackTrace();
        }
    }
}