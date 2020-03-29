package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bt.gov.moh.eet.dto.StatisticDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.PasswordEncryptionUtil;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.framework.common.Log;

public class LoginDAO {
	
	private static LoginDAO logindao;
	
	public static LoginDAO getInstance() {
		if(logindao == null)
			logindao = new LoginDAO();
		return logindao;
	}
	
	public UserDetailsVO populateUserDetails(String uid, String password) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		UserDetailsVO vo = new UserDetailsVO();
		String salt = "";
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_PASSWORD_SALT);
			pst.setString(1, uid);
			rs = pst.executeQuery();
			if(rs.first()) {
				salt = rs.getString("password_salt");
			}
				
			String enteredHashPassword = PasswordEncryptionUtil.hashPassword(password, salt).get();
			
			pst.close();
			rs.close();
			pst = conn.prepareStatement(VALIDATE_USER_CREDENTIALS);
			pst.setString(1, uid);
			pst.setString(2, enteredHashPassword);
			rs = pst.executeQuery();
			rs.first();
			
			if(rs.getInt("rowCount") > 0) {
				pst.close();
				rs.close();
				
				pst = conn.prepareStatement(GET_USER_DETAILS);
				pst.setString(1, uid);
				rs = pst.executeQuery();
				
				while(rs.next()) {
					vo.setCid(rs.getString("cid"));
					vo.setFull_name(rs.getString("full_name"));
					vo.setMobile_number(rs.getString("mobile_number"));
					vo.setDesignation(rs.getString("designation"));
					vo.setWorking_address(rs.getString("working_address"));
					vo.setUser_type_id(rs.getString("user_type_id"));
					vo.setRole_id(rs.getString("role_id"));
					vo.setUser_type(rs.getString("user_type"));
					vo.setRole_name(rs.getString("role_name"));
					vo.setGateId(rs.getString("gate_id"));
					vo.setGateCode(rs.getString("gate_code"));
					vo.setGateName(rs.getString("gate_name"));
					vo.setRoleCode(rs.getString("role_desc"));
					vo.setDzongkhagId(rs.getString("dzongkhag_id"));
					vo.setDzongkhagName(rs.getString("dzongkhag"));
					vo.setUserCheck("ok");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			Log.error("###Error at LoginDAO[populateUserDetails]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return vo;
	}
	
	public StatisticDTO getDashboardStatistics(UserDetailsVO vo) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StatisticDTO dto = new StatisticDTO();
		
		try {
			String query = GET_DASHBOARD_STATS;
			
			if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				query += " AND a.`entry_or_exit`=? AND e.`dzongkhag_id`=?"; 
			} else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")) {
				query += " AND a.`entry_or_exit`=? AND a.`gate_id`=?"; 
			} else {
				query += " AND a.`entry_or_exit`=?";
			}
			
			conn = ConnectionManager.getConnection();
			
			// Getting Entry Count for all user types
			pst = conn.prepareStatement(query);
			if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				pst.setString(1, "ENTRY");
				pst.setString(2, vo.getDzongkhagId());
			} else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")) {
				pst.setString(1, "ENTRY");
				pst.setString(2, vo.getGateId());
			} else {
				pst.setString(1, "ENTRY");
			}
			rs = pst.executeQuery();
			rs.first();
			dto.setTotalEntry(rs.getString("rowCount"));
			
			// Getting Exit Count for all user types
			pst.close();
			rs.close();
			pst = conn.prepareStatement(query);
			if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				pst.setString(1, "EXIT");
				pst.setString(2, vo.getDzongkhagId());
			} else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")) {
				pst.setString(1, "EXIT");
				pst.setString(2, vo.getGateId());
			} else {
				pst.setString(1, "EXIT");
			}
			rs = pst.executeQuery();
			rs.first();
			dto.setTotalExit(rs.getString("rowCount"));
			
			// Getting Total Flags Raised
			query = GET_DASHBOARD_STATS;
			if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				query += " AND a.`alert_flag`='Y' AND e.`dzongkhag_id`=?"; 
			} else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")) {
				query += " AND a.`alert_flag`='Y' AND a.`gate_id`=?"; 
			} else {
				query += " AND a.`alert_flag`='Y'";
			}
			
			pst = conn.prepareStatement(query);
			if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")) {
				pst.setString(1, vo.getDzongkhagId());
			} else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")) {
				pst.setString(1, vo.getGateId());
			}
			
			rs = pst.executeQuery();
			rs.first();
			dto.setTotalAlertFlag(rs.getString("rowCount"));
		} catch (Exception e) {
			Log.error("###Error at LoginDAO[getDashboardStatistics]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dto;
	}
	
	private static final String GET_DASHBOARD_STATS = "select "
			+ "  count(log_id) rowCount "
			+ "from "
			+ "  guestlog a "
			+ "  left join gates b on a.`gate_id`=b.`gate_id` "
			+ "  left join gewog_gate_mapping c on b.`gate_id`=c.`gate_id` "
			+ "  left join gewogs d on c.`gewog_id`=d.`gewog_id` "
			+ "  left join dzongkhags e on d.`dzongkhag_id`=e.`dzongkhag_id` "
			+ "where date_format(a.`created_on`, '%Y-%m-%d') = current_date";
	
	private static final String GET_PASSWORD_SALT = "SELECT a.`password_salt` FROM users a WHERE a.`cid`=?";
	
	private static final String VALIDATE_USER_CREDENTIALS = "select count(*) rowCount from users a where a.`cid`=? and a.`password`=?";
	
	private static final String GET_USER_DETAILS = "SELECT "
			+ "  a.`cid`, "
			+ "  a.`full_name`, "
			+ "  a.`mobile_number`, "
			+ "  a.`designation`, "
			+ "  a.`working_address`, "
			+ "  a.`user_type_id`, "
			+ "  a.`role_id`, "
			+ "  b.`user_type`, "
			+ "  c.`role_name`, "
			+ "  e.`gate_id`, "
			+ "  e.`gate_code`, "
			+ "  e.`gate_name`, "
			+ "  c.role_desc, "
			+ "  a.`dzongkhag_id`, "
			+ "  f.`dzongkhag` "
			+ "FROM "
			+ "  users a "
			+ "  LEFT JOIN usertypes b "
			+ "    ON a.`user_type_id` = b.`user_type_id` "
			+ "  LEFT JOIN roles c "
			+ "    ON a.`role_id` = c.`role_id` "
			+ "  LEFT JOIN `usergatemapping` d "
			+ "    ON a.`cid` = d.`cid` "
			+ "  LEFT JOIN gates e "
			+ "    ON d.`gate_id` = e.`gate_id` "
			+ "  LEFT JOIN dzongkhags f ON a.`dzongkhag_id`=f.`dzongkhag_id` "
			+ "WHERE a.`cid` = ?";

}
