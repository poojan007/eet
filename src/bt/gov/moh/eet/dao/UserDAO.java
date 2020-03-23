package bt.gov.moh.eet.dao;

import java.util.ArrayList;
import java.util.List;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import bt.gov.moh.eet.dto.UserDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.PasswordEncryptionUtil;


public class UserDAO {
	
	private static UserDAO userdao;
	
	public static UserDAO getInstance() {
		if(userdao == null)
			userdao = new UserDAO();
		return userdao;
	}
	public List<UserDTO> getUserDetails()
	{
		List<UserDTO> userDTO = new ArrayList<UserDTO>();	 
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		//String query = null;
		UserDTO dto = new UserDTO();
		try {
	    	connection = ConnectionManager.getConnection();
	    	//query = "GET_LEAVEAPPLICATION_LIST";
	    	 prepareStatement = connection.prepareStatement(GET_USER_LIST);
	    	 resultSet = prepareStatement.executeQuery();
	    	
	    	while(resultSet.next()){
	    		dto.setCid(resultSet.getString("cid"));
	    		dto.setFull_name(resultSet.getString("full_name"));
	    		dto.setMobile_number(resultSet.getString("mobile_number"));
	    		dto.setDesignation(resultSet.getString("designation"));
	    		dto.setWorking_address(resultSet.getString("working_address"));
	    		dto.setUser_type(resultSet.getString("user_type"));
	    		dto.setRole_name(resultSet.getString("role_name"));
	    		
	    		userDTO.add(dto);
	    		}
	    	}
		
		
		catch (Exception e)
		{
			/*connection.rollback();
			
			throw new Exception("###Error at LeaveApplicationDAO[submitLeaveApplication]: exception:: "+e);*/
		}
		finally
		{
			
			ConnectionManager.close(connection, resultSet, prepareStatement);
		}
		
		return userDTO;
		
	}
	
	public String add_user(UserDTO dto, Connection conn) 
	
	{
		PreparedStatement pst = null;
		String result = "USER_ADD_FAILURE";
		
		try 
		{
			String salt = PasswordEncryptionUtil.generateSalt(512).get();
			String hashPassword = PasswordEncryptionUtil.hashPassword(dto.getCid(), salt).get();
			
			pst = conn.prepareStatement(INSERT_INTO_USER_MASTER);
			pst.setString(1, dto.getCid());
			pst.setString(2, hashPassword);
			pst.setString(3, salt);
			pst.setString(4, dto.getFull_name());
			pst.setString(5, dto.getMobile_number());
			pst.setString(6, dto.getDesignation());
			pst.setString(7, dto.getWorking_address());
			pst.setString(8, dto.getUser_type_id());
			pst.setString(9, dto.getRole_id());
			
			int count = pst.executeUpdate();
					
			if(count > 0) {
				result = "USER_ADD_SUCCESS";
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "USER_ADD_FAILURE";
			//ConnectionManager.rollbackConnection(conn);
			
		}
		finally
		{
			ConnectionManager.close(null, null, pst);
		}
		
		return result;
	}
public String edit_user(UserDTO dto, Connection conn) 
	
	{
		PreparedStatement pst = null;
		String result = "USER_EDIT_FAILURE";
		
		try 
		{
			pst = conn.prepareStatement(EDIT_USER_MASTER);
			pst.setString(1, dto.getCid());
			pst.setString(2, dto.getPassword());
			pst.setString(3, dto.getPasswordsalt());
			pst.setString(4, dto.getFull_name());
			pst.setString(5, dto.getMobile_number());
			pst.setString(6, dto.getDesignation());
			pst.setString(7, dto.getWorking_address());
			pst.setString(8, dto.getUser_type_id());
			pst.setString(9, dto.getRole_id());
			pst.setString(10, dto.getCid());
			int count = pst.executeUpdate();
					
			if(count > 0) {
				result = "USER_EDIT_SUCCESS";
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "USER_EDIT_FAILURE";
			//ConnectionManager.rollbackConnection(conn);
			
		}
		finally
		{
			ConnectionManager.close(null, null, pst);
		}
		
		return result;
	}

	private static final String GET_USER_LIST="SELECT "
			+ "  a.`cid`, "
			+ "  a.`full_name`, "
			+ "  a.`mobile_number`, "
			+ "  a.`designation`, "
			+ "  a.`working_address`, "
			+ "  b.`user_type`, "
			+ "  c.`role_name` "
			+ "FROM "
			+ "  users a "
			+ "  LEFT JOIN `usertypes` b "
			+ "    ON b.`user_type_id` = a.`user_type_id` "
			+ "  LEFT JOIN `roles` c "
			+ "    ON c.`role_id` = a.`role_id` "
			+ "  LEFT JOIN `usergatemapping` d "
			+ "    ON d.`cid` = a.`cid`";
	
	private static final String INSERT_INTO_USER_MASTER="INSERT INTO "
			+ "`users` "
			+ "( "
			+ "`cid`, "
			+ "`password`, "
			+ "`password_salt`, "
			+ "`full_name`, "
			+ "`mobile_number`, "
			+ "`designation`, "
			+ "`working_address`, "
			+ "`user_type_id`, "
			+ "`role_id` "
			+ ") "
			+ "VALUES "
			+ "(?, "
			+ " ?, "
			+ " ?, "
			+ " ?, "
			+ " ?, "
			+ " ?, "
			+ " ?, "
			+ " ?, "
			+ " ?)";
	
	private static final String EDIT_USER_MASTER="UPDATE "
			+ "`users` "
			+ "SET "
			+ " cid =?, "
			+ "`password`=?, "
			+ "`password_salt`=?, "
			+ "`full_name`=?, "
			+ "`mobile_number`=?, "
			+ "`designation`=?, "
			+ "`working_address`=?, "
			+ "`user_type_id`=?, "
			+ "`role_id`=? "
			+ "WHERE cid=?";
}
