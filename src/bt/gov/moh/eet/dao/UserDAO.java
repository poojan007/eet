package bt.gov.moh.eet.dao;

public class UserDAO {
	
	private static UserDAO userdao;
	
	public static UserDAO getInstance() {
		if(userdao == null)
			userdao = new UserDAO();
		return userdao;
	}

}
