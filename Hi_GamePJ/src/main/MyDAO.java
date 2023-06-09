package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDAO {
    private Connection connection;

    public MyDAO() {
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

    public void saveBoard(String boardT, String id) {
        try {
            // 중복 여부 확인
            String checkQuery = "SELECT boardT FROM higame WHERE boardT = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, boardT);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // 이미 중복된 값이 존재하면 예외 처리
                System.out.println("중복된 값이 이미 존재합니다.");
                return;
            }

            // 중복이 없으면 삽입
            String insertQuery = "INSERT INTO higame (boardT, id) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, boardT);
            insertStmt.setString(2, id);
            insertStmt.executeUpdate();
            System.out.println("게시글 저장 완료");
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }
    }
}
