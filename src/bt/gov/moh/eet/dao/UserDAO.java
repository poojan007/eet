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
	
	
	public UserDTO getEditUserDetails(String cid)
	{
		//List<UserDTO> edituserDTO = new ArrayList<UserDTO>();	 
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		//String query = null;
		UserDTO dto = new UserDTO();
		try {
	    	connection = ConnectionManager.getConnection();
	    	//query = "GET_LEAVEAPPLICATION_LIST";
	    	 prepareStatement = connection.prepareStatement(GET_EDIT_USER_LIST);
	    	 prepareStatement.setString(1, cid);
	    	 resultSet = prepareStatement.executeQuery();
	    	
	    	while(resultSet.next()){
	    		dto.setCid(resultSet.getString("cid"));
	    		dto.setFull_name(resultSet.getString("full_name"));
	    		dto.setMobile_number(resultSet.getString("mobile_number"));
	    		dto.setDesignation(resultSet.getString("designation"));
	    		dto.setWorking_address(resultSet.getString("working_address"));
	    		dto.setUser_type(resultSet.getString("user_type_id"));
	    		dto.setRole_name(resultSet.getString("role_id"));
	    		
	    		
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
		
		return dto;
		
	}
	
	public String add_user(UserDTO dto, Connection conn) 
	
	{
		PreparedStatement pst = null;
		String result = "SAVE_FAILURE";
		ResultSet rs = null;
		Connection connection = null;
		try 
		{
			connection = ConnectionManager.getConnection();
			String salt = PasswordEncryptionUtil.generateSalt(512).get();
			String hashPassword =  "VfVdrgVTVN5O2EUkOCeWNvXIQ7yR64JhQFqv7LvJlTrBKL2lx7gvcL1C1G+EILFa7PDKW6B2R2g2vVuy7R1fQQ==";
			
			pst = connection.prepareStatement(INSERT_INTO_USER_MASTER);
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
				result = "SAVE_SUCCESS";
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "SAVE_FAILURE";
			//ConnectionManager.rollbackConnection(conn);
			
		}
		finally
		{
			ConnectionManager.close(connection, rs, pst);
		}
		
		return result;
	}
	
public String edit_user(UserDTO dto, Connection conn) 
	
	{
		PreparedStatement pst = null;
		String result = "UPDATE_FAILURE";
		ResultSet rs = null;
		Connection connection = null;
		try 
		{
			connection = ConnectionManager.getConnection();
			pst = connection.prepareStatement(EDIT_USER_MASTER);
			pst.setString(1, dto.getCidedit());
			pst.setString(2, dto.getEditfull_name());
			pst.setString(3, dto.getEditmobile_number());
			pst.setString(4, dto.getEditdesignation());
			pst.setString(5, dto.getEditworking_address());
			pst.setString(6, dto.getEdituser_type_id());
			pst.setString(7, dto.getEditrole_id());
			pst.setString(8, dto.getCidedit());
			int count = pst.executeUpdate();
					
			if(count > 0) {
				result = "UPDATE_SUCCESS";
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "UPDATE_FAILURE";
			//ConnectionManager.rollbackConnection(conn);
			
		}
		finally
		{
			ConnectionManager.close(connection, rs, pst);
		}
		
		return result;
	}

public List<UserDTO> getTotalList()
{
	List<UserDTO> getTotalList = new ArrayList<UserDTO>();	 
	Connection connection = null;
	PreparedStatement prepareStatement = null;
	ResultSet resultSet , resultSet1, resultSet2 = null;
	//String query = null;
	UserDTO dto = new UserDTO();
	try {
    	connection = ConnectionManager.getConnection();
    	//query = "GET_LEAVEAPPLICATION_LIST";
    	 prepareStatement = connection.prepareStatement(GET_ENTRY_LIST);
    	 resultSet = prepareStatement.executeQuery();
    	
    	while(resultSet.next()){
    		dto.setEntrycount(resultSet.getString("totalEntry"));
    		
    		prepareStatement = connection.prepareStatement(GET_EXIT_LIST);
       	 	resultSet1 = prepareStatement.executeQuery();
       	 	
    		while(resultSet1.next()){
    			dto.setExitcount(resultSet1.getString("totalExit"));
    			
    			prepareStatement = connection.prepareStatement(GET_FLAG_LIST);
    	    	resultSet2 = prepareStatement.executeQuery();
    	    	 
    			while(resultSet2.next()){
    	    	dto.setFlagcount(resultSet2.getString("totalFlag"));	
    	    		
    	    		getTotalList.add(dto);
        		}
        		
    		}
    		}
    	}
	
	
	catch (Exception e)
	{
		/*connection.rollback();
		
		throw new Exception("###Error at LeaveApplicationDAO[submitLeaveApplication]: exception:: "+e);*/
	}
	finally
	{
		
		ConnectionManager.close(connection, null, prepareStatement);
	}
	
	return getTotalList;
	
}


	private static final String GET_USER_LIST = "SELECT "
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
	
	private static final String GET_EDIT_USER_LIST = "SELECT "
													+ "  a.`cid`, "
													+ "  a.`full_name`, "
													+ "  a.`mobile_number`, "
													+ "  a.`designation`, "
													+ "  a.`working_address`, "
													+ "  a.`user_type_id`, "
													+ "  a.`role_id` "
													+ "FROM "
													+ "  users a "
													+ "WHERE  a.`cid` =?";
	
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
			+ "`full_name`=?, "
			+ "`mobile_number`=?, "
			+ "`designation`=?, "
			+ "`working_address`=?, "
			+ "`user_type_id`=?, "
			+ "`role_id`=? "
			+ "WHERE cid=?";
	
	private static final String GET_ENTRY_LIST = "SELECT COUNT(*)totalEntry FROM `guestlog` WHERE entry_or_exit = 'ENTRY'";
	
	private static final String GET_EXIT_LIST = "SELECT COUNT(*)totalExit FROM `guestlog` WHERE entry_or_exit = 'EXIT'";
	
	private static final String GET_FLAG_LIST = "SELECT COUNT(*)totalFlag FROM `guestlog` WHERE alert_flag = 'Y'";
}
