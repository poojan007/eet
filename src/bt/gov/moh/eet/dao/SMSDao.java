package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bt.gov.moh.eet.dto.SMSDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.framework.common.Log;

public class SMSDao {

	private static SMSDao dao = null;
	
	public static SMSDao getInstance() {
		if(dao == null)
			dao = new SMSDao();
		return dao;
	}
	
	public SMSDTO guestExit(String mobileNo, String gateCode) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		SMSDTO dto = new SMSDTO();
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(CHECK_IF_GUEST_EXISTS);
			pst.setString(1, mobileNo);
			rs = pst.executeQuery();
			rs.first();
			
			if(rs.getInt("rowCount") > 0) {
				pst.close();
				rs.close();
				
				pst = conn.prepareStatement(GET_LATEST_LOG);
				pst.setString(1, mobileNo);
				pst.setString(2, "ENTRY");
				rs = pst.executeQuery();
				rs.first();
				String latestLogId = rs.getString("latestLogId");
				
				pst.close();
				rs.close();
				pst = conn.prepareStatement(INSERT_TRANSACTION_LOG, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, latestLogId);
				pst.setString(2, "EXIT");
				pst.setString(3, gateCode);
				int count = pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				
				int logId = 0;
				while(rs.next()) {
					logId = rs.getInt(1);
				}
				
				if(count > 0) {
					pst.close();
					rs.close();
					pst = conn.prepareStatement(GET_LATEST_TRANSACTION_DETAILS);
					pst.setInt(1, logId);
					rs = pst.executeQuery();
					rs.first();
					
					dto.setGateName(rs.getString("gate_name"));
					dto.setTransactionTime(rs.getString("transactionTime"));
					dto.setMobileNo(mobileNo);
					dto.setStatus("SUCCESS");
				}
			} else {
				dto.setStatus("NO_RECORD_FOUND");
			}
		} catch (Exception e) {
			Log.error("###SMSDao guestExit ----> ", e);
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dto;
	}
	
	public SMSDTO guestEntry(String mobileNo, String gateCode) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		SMSDTO dto = new SMSDTO();
		int previousLogId = 0, latestLogId = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			pst = conn.prepareStatement(GET_LATEST_LOG);
			pst.setString(1, mobileNo);
			pst.setString(2, "EXIT");
			rs = pst.executeQuery();
			if(rs.first()) {
				previousLogId = rs.getInt("latestLogId");
				
				pst.close();
				rs.close();
				pst = conn.prepareStatement(INSERT_TRANSACTION_LOG, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setInt(1, previousLogId);
				pst.setString(2, "ENTRY");
				pst.setString(3, gateCode);
				int count = pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				
				while(rs.next()) {
					latestLogId = rs.getInt(1);
				}
				
				if(count > 0) {
					
				}
			} else {
				dto.setStatus("NO_EXIT_RECORD_FOUND");
			}
		} catch (Exception e) {
			Log.error("###SMSDao guestEntry ----> ", e);
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dto;
	}
	
	private static final String GET_LATEST_TRANSACTION_DETAILS = "select "
			+ "  b.`gate_name`, "
			+ "  date_format( "
			+ "    a.`transaction_date_time`, "
			+ "    '%d-%m-%Y %h:%i %p' "
			+ "  ) transactionTime "
			+ "from "
			+ "  guestlog a "
			+ "  left join gates b "
			+ "    on a.`gate_id` = b.`gate_id` "
			+ "where a.`log_id` = ?";
	
	private static final String INSERT_TRANSACTION_LOG = "INSERT INTO `guestlog` ( "
			+ "  `guest_id`, "
			+ "  `entry_or_exit`, "
			+ "  `transaction_date_time`, "
			+ "  `gate_id`, "
			+ "  `created_by`, "
			+ "  `created_on` "
			+ ") "
			+ "VALUES "
			+ "  ((SELECT guest_id FROM guestlog WHERE log_id=?), ?, CURRENT_TIMESTAMP, (SELECT gate_id FROM gates WHERE gate_code=?), 'SYSTEM', CURRENT_TIMESTAMP)";
	
	private static final String GET_LATEST_LOG = "SELECT "
			+ "  MAX(a.`log_id`) latestLogId "
			+ "FROM "
			+ "  guestlog a "
			+ "WHERE a.`guest_id` = "
			+ "  (SELECT "
			+ "    guest_id "
			+ "  FROM "
			+ "    guests "
			+ "  WHERE contact_no = ? "
			+ "  LIMIT 1) "
			+ "  AND a.`entry_or_exit`=?";
	
	private static final String CHECK_IF_GUEST_EXISTS = "SELECT COUNT(guest_id) rowCount FROM guests a WHERE a.`contact_no`=?";
}
