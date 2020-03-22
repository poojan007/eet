package bt.gov.moh.eet.dao;

import bt.gov.moh.eet.vo.UserDetailsVO;

public class LoginDAO {
	
	private static LoginDAO logindao;
	
	public static LoginDAO getInstance() {
		if(logindao == null)
			logindao = new LoginDAO();
		return logindao;
	}
	
	public UserDetailsVO populateUserDetails(String uid, String password) throws Exception {
		return null;
	}

}
