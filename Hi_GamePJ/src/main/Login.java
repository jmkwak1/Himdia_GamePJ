package main;

public class Login {
	private static String id;
	private static String pw;
	private static String name;
	private static String email;
	private static String gold;
	
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		Login.id = id;
	}
	public static String getPw() {
		return pw;
	}
	public static void setPw(String pw) {
		Login.pw = pw;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Login.name = name;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		Login.email = email;
	}
	public static String getGold() {
		return gold;
	}
	public static void setGold(String gold) {
		Login.gold = gold;
	} 
	
}
