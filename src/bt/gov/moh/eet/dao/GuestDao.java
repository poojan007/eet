package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bt.gov.moh.eet.dto.GuestDTO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;

public class GuestDao {
	public GuestDTO fetchGuestDetail(Integer guestID) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		GuestDTO vo = new  GuestDTO();
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_GUEST_DETAILS);
			pst.setInt(1, guestID);
			rs = pst.executeQuery();
			
			if(rs.getInt("rowCount") > 0) {
				pst.close();
				rs.close();
				
				while(rs.next()) {
					vo.setGuest_id(rs.getInt("guest_id"));
					vo.setIdentification_no(rs.getInt("identification_no"));
					vo.setIdentification_type_id(rs.getInt("identification_type_id"));
					vo.setNationality_id(rs.getInt("nationality_id"));
					vo.setGuest_name(rs.getString("working_address"));
					vo.setGender(rs.getString("gender").charAt(0));
					vo.setAge(rs.getInt("age"));
					vo.setPresent_address(rs.getString("present_address"));
					vo.setContact_no(rs.getInt("contact_no"));
					vo.setResiding_across_border(rs.getString("residing_across_border").charAt(0));
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return vo;
	}

	public GuestLogDTO fetchLastGuestLogDetails(Integer guest_id) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		GuestLogDTO vo = new  GuestLogDTO();
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_LAST_GUEST_DETAILS);
			pst.setInt(1, guest_id);
			rs = pst.executeQuery();
			
			int i = 0;
			if(rs.getInt("rowCount") > i) {
				pst.close();
				rs.close();
				
				while(rs.next()) {
					vo.setAlert_remarks(rs.getString("alert_remarks"));
					vo.setAlert_update_time(rs.getDate("alert_update_time"));
					vo.setAlert_flag(rs.getString("alert_flag").charAt(i));
					vo.setCreated_on(rs.getDate("created_on"));
					vo.setCreated_by(rs.getString("created_by"));
					vo.setGate_id(rs.getInt("gate_id"));
					vo.setReason(rs.getString("reason"));
					vo.setRequested_gate_id(rs.getInt("requested_gate_id"));
					vo.setReason_id(rs.getInt("reason_id"));
					vo.setTransaction_date_time(rs.getDate("transaction_date_time"));
					vo.setEntry_or_exit(rs.getString("entry_or_exit"));
					vo.setTemperature(rs.getInt("temperature"));
					vo.setGuest_id(rs.getInt("guest_id"));
					vo.setLog_id(rs.getInt("log_id"));
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return vo;
	}
	
	private static final String GET_GUEST_DETAILS = "SELECT * FROM guests WHERE cid=?";
	private static final String GET_LAST_GUEST_DETAILS = "SELECT * FROM guestlog\n" +
            "WHERE guest_id = ? order by entry_or_exit desc limit 1";
}
