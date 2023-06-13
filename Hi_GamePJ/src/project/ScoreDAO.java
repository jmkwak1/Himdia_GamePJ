package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Login;
import main.MemberDAO;
import main.MemberDTO;

public class ScoreDAO {
    private Connection connection;

    public ScoreDAO() {
        // 데이터베이스 연결 설정
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 데이터베이스 URL
        String user = "oracle"; // 데이터베이스 사용자명
        String password = "oracle"; // 데이터베이스 비밀번호

        try {
            // 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 데이터베이스 연결
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("데이터베이스 연결 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("드라이버 로드 오류: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("데이터베이스 연결 오류: " + e.getMessage());
        }
    }

    public void saveScore(String id, int score) {
        MemberDAO memberDAO = new MemberDAO();
        MemberDTO member = memberDAO.login(id);

        try {
            int newGold = member.getGold(); // 기존 gold 값 가져오기

            String updateQuery = "UPDATE higame SET gold = ? WHERE id = ?";

            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

            newGold += score; // newGold 값에 score를 더함

            updateStatement.setInt(1, newGold);
            updateStatement.setString(2, id);
            updateStatement.executeUpdate();

            System.out.println("데이터베이스에 값이 저장되었습니다.");
            System.out.println(newGold);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("데이터베이스 오류: " + e.getMessage());
        }
    }

}
