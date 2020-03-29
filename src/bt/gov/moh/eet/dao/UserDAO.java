package bt.gov.moh.eet.dao;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import bt.gov.moh.eet.dto.UserDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.PasswordEncryptionUtil;
import bt.gov.moh.eet.vo.UserDetailsVO;


public class UserDAO {
	
	private static UserDAO userdao;
	
	public static UserDAO getInstance() {
		if(userdao == null)
			userdao = new UserDAO();
		return userdao;
	}
	
	public List<UserDTO> getUserDetails(String roleCode, String dzongkhagId) throws Exception
	{
		List<UserDTO> userDTO = new ArrayList<UserDTO>();	 
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		UserDTO dto = null;
		String query = null;
		
		try {
	    	connection = ConnectionManager.getConnection();
	    	
	    	if(roleCode.equalsIgnoreCase("SUPER_ADMIN"))
	    		query = GET_USER_LIST + " WHERE c.role_desc='ADMINISTRATOR'";
	    	else
	    		query = GET_USER_LIST + " WHERE f.dzongkhag_id=? AND c.role_desc='DATA_MANAGER'";
	    	
	    	 prepareStatement = connection.prepareStatement(query);
	    	 
	    	 if(roleCode.equalsIgnoreCase("ADMINISTRATOR"))
	    		 prepareStatement.setString(1, dzongkhagId);
	    	 
	    	 resultSet = prepareStatement.executeQuery();
	    	
	    	while(resultSet.next()){
	    		dto = new UserDTO();
	    		dto.setCid(resultSet.getString("cid"));
	    		dto.setFull_name(resultSet.getString("full_name"));
	    		dto.setMobile_number(resultSet.getString("mobile_number"));
	    		dto.setDesignation(resultSet.getString("designation"));
	    		dto.setWorking_address(resultSet.getString("working_address"));
	    		dto.setUser_type(resultSet.getString("user_type"));
	    		dto.setRole_name(resultSet.getString("role_name"));
	    		dto.setGateName(resultSet.getString("gate_name"));
	    		dto.setDzongkhagName(resultSet.getString("dzongkhag"));
	    		userDTO.add(dto);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
			throw new Exception();
		}
		finally {
			ConnectionManager.close(connection, resultSet, prepareStatement);
		}
		return userDTO;
	}
	
	public UserDTO getEditUserDetails(String cid) throws Exception
	{
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		UserDTO dto = new UserDTO();
		try {
	    	connection = ConnectionManager.getConnection();
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
	    		dto.setGate_id(resultSet.getString("gate_id"));
	    		dto.setDzongkhag_id(resultSet.getString("dzongkhag_id"));
    		}
    	}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			ConnectionManager.close(connection, resultSet, prepareStatement);
		}
		return dto;
	}
	
	public String add_user(UserDTO dto, UserDetailsVO vo, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		String result = "SAVE_FAILURE";
		ResultSet rs = null;
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
			
			if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN")) {
				pst.setString(9, "ADMINISTRATOR");
				pst.setString(10, dto.getDzongkhag_id());
			}
			else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				pst.setString(9, "DATA_MANAGER");
				pst.setString(10, vo.getDzongkhagId());
			}

			int count = pst.executeUpdate();
					
			if(count > 0) {
				
				if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
					pst.close();
					pst = conn.prepareStatement(INSERT_INTO_USER_GATE_MAPPING);
					pst.setString(1, dto.getCid());
					pst.setString(2, dto.getGate_id());
					count = pst.executeUpdate();
				}
				
				result = "SAVE_SUCCESS";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			result = "SAVE_FAILURE";
			throw new Exception();
		}
		finally {
			ConnectionManager.close(null, rs, pst);
		}
		
		return result;
	}
	
	public String edit_user(UserDTO dto, UserDetailsVO vo, Connection conn) throws Exception 
	{
		PreparedStatement pst = null, pst1 = null;
		String result = "UPDATE_FAILURE";
		try 
		{
			pst = conn.prepareStatement(EDIT_USER_MASTER);
			pst.setString(1, dto.getCidedit());
			pst.setString(2, dto.getEditfull_name());
			pst.setString(3, dto.getEditmobile_number());
			pst.setString(4, dto.getEditdesignation());
			pst.setString(5, dto.getEditworking_address());
			pst.setString(6, dto.getEdituser_type_id());
			
			if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN")) {
				pst.setString(7, "ADMINISTRATOR");
				pst.setString(8, dto.getEdit_dzongkhag_id());
			}
			else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				pst.setString(7, "DATA_MANAGER");
				pst.setString(8, vo.getDzongkhagId());
			}
			
			pst.setString(9, dto.getCidedit());
			int count = pst.executeUpdate();
					
			if(count > 0) {
				if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
					pst1 = conn.prepareStatement(UPDATE_USER_GATE);
					pst1.setString(1, dto.getGate_id());
					pst1.setString(2, dto.getCid());
					count = pst1.executeUpdate();
				}
				
				result = "UPDATE_SUCCESS";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			result = "UPDATE_FAILURE";
			throw new Exception();
		}
		finally {
			ConnectionManager.close(null, null, pst);
			ConnectionManager.close(null, null, pst1);
		}
		
		return result;
	}
	
	public boolean checkCurrentPasswordMatch(String userId, String cpassword) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean isMatch = false;
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_CURRENT_PASSWORD);
			pst.setString(1, userId);
			rs = pst.executeQuery();
			if(rs.first()) {
				String currentPassword = rs.getString("password");
				String salt = rs.getString("password_salt");
				
				String enteredHashPassword = PasswordEncryptionUtil.hashPassword(cpassword, salt).get();
				
				if(currentPassword.equals(enteredHashPassword))
					isMatch = true;
			}
		} catch (Exception e) {
			isMatch = false;
		    e.printStackTrace();
			throw new Exception("###Error at UserDAO[checkCurrentPasswordMatch]:: "+e);
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return isMatch;
	}
	
	public String updateUserPassword(String userId, String password) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String result = "FAILURE";
		
		try {
			conn = ConnectionManager.getConnection();
			
			String salt = PasswordEncryptionUtil.generateSalt(512).get();
			String hashPassword = PasswordEncryptionUtil.hashPassword(password, salt).get();
			
			pst = conn.prepareStatement(UPDATE_T_USER_MASTER_WITH_PASSWORD);
			pst.setString(1, hashPassword);
			pst.setString(2, salt);
			pst.setString(3, userId);
			int count = pst.executeUpdate();
			
			if(count > 0) {
				result = "UPDATED";
			}
		} catch (Exception e) {
			result = "FAILURE";
		    e.printStackTrace();
			result = "FAILURE";
			throw new Exception("###Error at UserDAO[updateUserPassword]:: "+e);
		} finally {
			ConnectionManager.close(null, null, pst);
		}
		
		return result;
	}
	
	public String updateProfile(UserDTO dto) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String result = "UPDATE_FAILURE";
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(UPDATE_PROFILE);
			pst.setString(1, dto.getMobile_number());
			pst.setString(2, dto.getDesignation());
			pst.setString(3, dto.getWorking_address());
			pst.setString(4, dto.getCid());
			int count = pst.executeUpdate();
			
			if(count > 0)
				result = "UPDATE_SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("###Error at UserDAO[updateProfile]:: "+e);
		} finally {
			ConnectionManager.close(conn, null, pst);
		}
		
		return result;
	}
	
	public String updateQRCodeNumber(String guestId, String identificationNo) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String result = "UPDATE_FAILURE";
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(UPDATE_GUEST_IDENTIFICATION_NO);
			pst.setString(1, identificationNo);
			pst.setString(2, guestId);
			int count = pst.executeUpdate();
			
			if(count > 0)
				result = "UPDATE_SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("###Error at UserDAO[updateQRCodeNumber]:: "+e);
		} finally {
			ConnectionManager.close(conn, null, pst);
		}
		
		return result;
	}
	
	public UserDTO getLoggedInUserDetails(String uid) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		UserDTO dto = new UserDTO();
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_LOGGED_IN_USER_DETAILS);
			pst.setString(1, uid);
			rs = pst.executeQuery();
			rs.first();
			dto.setMobile_number(rs.getString("mobile_number"));
			dto.setDesignation(rs.getString("designation"));
			dto.setWorking_address(rs.getString("working_address"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("###Error at UserDAO[getLoggedInUserDetails]:: "+e);
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dto;
	}
	
	public String deleteUser(String userId) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = "DELETE_FAILURE";
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(CHECK_IF_USER_HAS_TRANSACTIONS);
			pst.setString(1, userId);
			rs = pst.executeQuery();
			rs.first();
			if(rs.getInt("rowCount") > 0) {
				result = "USER_HAS_TRANSACTIONS";
			} else {
				pst.close();
				pst = conn.prepareStatement(DELETE_FROM_USER_GATE_MAPPING);
				pst.setString(1, userId);
				int count = pst.executeUpdate();
				
				if(count > 0) {
					pst = conn.prepareStatement(DELETE_FROM_USERS);
					pst.setString(1, userId);
					count = pst.executeUpdate();
					
					if(count > 0)
						result = "DELETE_SUCCESS";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("###Error at UserDAO[deleteUser]:: "+e);
		} finally {
			ConnectionManager.close(conn, null, pst);
		}
		
		return result;
	}
	
	public String resetPassword(String userId) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String result = "UPDATE_FAILURE";
		
		try {
			conn = ConnectionManager.getConnection();
			
			String salt = PasswordEncryptionUtil.generateSalt(512).get();
			String hashPassword = PasswordEncryptionUtil.hashPassword(userId, salt).get();
			
			pst = conn.prepareStatement(UPDATE_T_USER_MASTER_WITH_PASSWORD);
			pst.setString(1, hashPassword);
			pst.setString(2, salt);
			pst.setString(3, userId);
			int count = pst.executeUpdate();
			
			if(count > 0) {
				result = "UPDATE_SUCCESS";
			}
		} catch (Exception e) {
			result = "UPDATE_FAILURE";
		    e.printStackTrace();
			result = "UPDATE_FAILURE";
			throw new Exception("###Error at UserDAO[resetPassword]:: "+e);
		} finally {
			ConnectionManager.close(null, null, pst);
		}
		
		return result;
	}
	
	private static final String DELETE_FROM_USER_GATE_MAPPING = "delete from usergatemapping where cid=?";
	
	private static final String DELETE_FROM_USERS = "delete from users where cid=?";
	
	private static final String CHECK_IF_USER_HAS_TRANSACTIONS = "select count(log_id) rowCount from guestlog a where a.`created_by`=?";
	
	private static final String UPDATE_GUEST_IDENTIFICATION_NO = "UPDATE guests a SET a.`identification_no`=? WHERE a.`guest_id`=?";
	
	private static final String GET_LOGGED_IN_USER_DETAILS = "select a.`mobile_number`,a.`designation`,a.`working_address` from users a where a.`cid`=?";
	
	private static final String UPDATE_PROFILE = "update users a set a.`mobile_number`=?, a.`designation`=?, a.`working_address`=? where a.`cid`=?";
	
	private static final String GET_CURRENT_PASSWORD = "SELECT password_salt, password FROM users WHERE cid=?";
	
	private static final String UPDATE_T_USER_MASTER_WITH_PASSWORD = "UPDATE users a SET a.`password`=?, a.`password_salt`=? WHERE a.`cid`=?";

	private static final String INSERT_INTO_USER_GATE_MAPPING = "INSERT INTO `usergatemapping` (`cid`, `gate_id`) VALUES (?, ?);";

	private static final String GET_USER_LIST = "SELECT "
			+ "  a.`cid`, "
			+ "  a.`full_name`, "
			+ "  a.`mobile_number`, "
			+ "  a.`designation`, "
			+ "  a.`working_address`, "
			+ "  b.`user_type`, "
			+ "  c.`role_name`, "
			+ "  e.`gate_name`, "
			+ "  f.`dzongkhag` "
			+ "FROM "
			+ "  users a "
			+ "  LEFT JOIN `usertypes` b "
			+ "    ON b.`user_type_id` = a.`user_type_id` "
			+ "  LEFT JOIN `roles` c "
			+ "    ON c.`role_id` = a.`role_id` "
			+ "  LEFT JOIN `usergatemapping` d "
			+ "    ON d.`cid` = a.`cid` "
			+ "  left join gates e "
			+ "    on d.`gate_id` = e.`gate_id` "
			+ "  left join dzongkhags f "
			+ "    on a.`dzongkhag_id` = f.`dzongkhag_id`";
	
	private static final String GET_EDIT_USER_LIST = "SELECT "
													+ "  a.`cid`, "
													+ "  a.`full_name`, "
													+ "  a.`mobile_number`, "
													+ "  a.`designation`, "
													+ "  a.`working_address`, "
													+ "  a.`user_type_id`, "
													+ "  a.`role_id`, b.`gate_id`, a.dzongkhag_id "
													+ "FROM "
													+ "  users a LEFT JOIN usergatemapping b ON a.`cid`=b.`cid` "
													+ "WHERE  a.`cid` =?";
	
	private static final String INSERT_INTO_USER_MASTER = "INSERT INTO "
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
															+ "`role_id`, `dzongkhag_id` "
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
															+ " (SELECT role_id FROM roles WHERE role_desc=?), ?)";
	
	private static final String EDIT_USER_MASTER="UPDATE "
												+ "`users` "
												+ "SET "
												+ " cid =?, "
												+ "`full_name`=?, "
												+ "`mobile_number`=?, "
												+ "`designation`=?, "
												+ "`working_address`=?, "
												+ "`user_type_id`=?, "
												+ "`role_id`=(SELECT role_id from roles WHERE role_desc=?), `dzongkhag_id`=? "
												+ "WHERE cid=?";
	
	private static final String UPDATE_USER_GATE = "UPDATE usergatemapping a SET a.`gate_id`=? WHERE a.`cid`=?";
}
