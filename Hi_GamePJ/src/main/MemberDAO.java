package main;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Blackjack.Blackjack;

public class MemberDAO {
	private Connection con;
	
	public MemberDAO() {
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
	
	public MemberDTO login(String id) {
	      String sql = "SELECT pw, gold FROM higame WHERE id=?";
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	            ps = con.prepareStatement(sql);
	            ps.setString(1, id);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                MemberDTO member = new MemberDTO();
	                member.setId(id);
	                member.setPw(rs.getString("pw"));
	                member.setGold(Integer.parseInt(rs.getString("gold"))); // String을 int로 변환하여 전달
	                return member;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	      }
	      return null;
	   }
	
	public void insert(String id, String pw, String name, String email, int gold) {
		String sql = "INSERT INTO higame (id, pw, name, email) VALUES(?,?,?,?,?)";
		PreparedStatement ps = null;
		Login.setGold(100);
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, email);
			ps.setInt(5, gold);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void BJGold(String id, double gold) {
        String sql = "UPDATE higame SET gold = ? WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, (int) gold);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}