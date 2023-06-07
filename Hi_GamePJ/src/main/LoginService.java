package main;

import java.lang.reflect.Member;

public class LoginService {
	private MemberDAO memberDao;
	
	public LoginService() {
		memberDao = new MemberDAO();
	}
	
	public void login(String id, String pw) {
		if (id == null || id.isEmpty() || pw == null || pw.isEmpty()) {
	        return;
	    }
		// 아이디는 5 ~ 20 
		if(id.length() <= 2 || id.length() > 20)
			return;
		String dbPw = memberDao.login(id);
		if(dbPw != null && dbPw.equals(pw)){
			// 로그인 성공
			Login.setId(id);
			CommonService.msg("로그인 성공");
		}else {
			// 아이디 또는 비밀번호가 틀렸습니다.
			CommonService.msg("아이디 또는 비밀번호가 틀렸습니다.");
		}

	}

}

