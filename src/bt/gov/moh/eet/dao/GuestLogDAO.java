package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.vo.UserDetailsVO;

public class GuestLogDAO {

	private static GuestLogDAO dao = null;
	
	public static GuestLogDAO getInstance() {
		if(dao == null)
			dao = new GuestLogDAO();
		return dao;
	}
	
	public GuestLogDTO getGuestDetails(String idNo, String identificationType, String entryOrExit) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		GuestLogDTO dto = new GuestLogDTO();
		
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_GUEST_DETAILS);
			pst.setString(1, idNo);
			pst.setString(2, identificationType);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				dto.setGuestId(rs.getString("guest_id"));
				dto.setGuestName(rs.getString("guest_name"));
				dto.setGender(rs.getString("gender"));
				dto.setDob(rs.getString("dob"));
				dto.setContactNo(rs.getString("contact_no"));
				dto.setNationality(rs.getString("nationality_id"));
				dto.setPresentAddress(rs.getString("present_address"));
				dto.setImagePath(rs.getString("image_path"));
				dto.setResidenceFlag(rs.getString("residing_across_border"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dto;
	}
	
	public String guestLog(GuestLogDTO dto, UserDetailsVO vo, Connection conn) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0, guestId = 0, logId = 0, previousLogId = 0;
		boolean alertFlag = false;
		String result = "GUESTLOG_ADD_FAILURE";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if(null == dto.getGuestId() || "".equals(dto.getGuestId()) || dto.getGuestId().length() == 0) {
				java.util.Date dob = df.parse(dto.getDob());
				String dobStr = sdf.format(dob);
				
				pst = conn.prepareStatement(INSERT_INTO_GUESTS, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, dto.getIdentificationNo());
				pst.setString(2, dto.getIdentificationType());
				pst.setString(3, dto.getNationality());
				pst.setString(4, dto.getGuestName());
				pst.setString(5, dto.getGender());
				pst.setString(6, dobStr);
				pst.setString(7, dto.getPresentAddress());
				pst.setString(8, dto.getContactNo());
				count = pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				while(rs.next()) {
					guestId = rs.getInt(1);
				}
				
				pst.close();
				rs.close();
			} else {
				guestId = Integer.parseInt(dto.getGuestId());
			}
			
			pst = conn.prepareStatement(GET_GUEST_ID);
			pst.setString(1, dto.getIdentificationNo());
			pst.setString(2, dto.getIdentificationType());
			rs = pst.executeQuery();
			rs.first();
			guestId = rs.getInt("guest_id");
			String residenceFlag = rs.getString("residing_across_border");
			String nationality = rs.getString("nationality");
			
			if(guestId > 0 && (residenceFlag.equalsIgnoreCase("N") && nationality.equalsIgnoreCase("BHUTAN")) || 
					(residenceFlag.equalsIgnoreCase("Y") && !nationality.equalsIgnoreCase("BHUTAN"))) {
				if(dto.getEntryOrExit().equalsIgnoreCase("ENTRY")) {
				//	pst.close();
				//	rs.close();
					pst = conn.prepareStatement(GET_RECORD_STATUS);
					pst.setInt(1, guestId);
					pst.setString(2, "EXIT");
					rs = pst.executeQuery();
					rs.first();
					previousLogId = rs.getInt("log_id");
					
					if(rs.getInt("rowCount") == 0) {
						result = "NO_EXIT_RECORD_FOUND";
						alertFlag = true;
					}
				}
			} else if(guestId > 0 && (residenceFlag.equalsIgnoreCase("Y") && nationality.equalsIgnoreCase("BHUTAN")) || 
					(residenceFlag.equalsIgnoreCase("N") && !nationality.equalsIgnoreCase("BHUTAN"))) {
				if(dto.getEntryOrExit().equalsIgnoreCase("EXIT")) {
				//	pst.close();
				//	rs.close();
					pst = conn.prepareStatement(GET_RECORD_STATUS);
					pst.setInt(1, guestId);
					pst.setString(2, "ENTRY");
					rs = pst.executeQuery();
					rs.first();
					
					if(rs.getInt("rowCount") == 0) {
						result = "NO_ENTRY_RECORD_FOUND";
						alertFlag = true;
					}
				}
			}
			
			pst = conn.prepareStatement(INSERT_INTO_GUEST_LOG, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setInt(1, guestId);
			if(dto.getEntryOrExit().equalsIgnoreCase("ENTRY"))
				pst.setString(2, dto.getThermometerReading());
			else
				pst.setString(2, "0");
			pst.setString(3, dto.getEntryOrExit());
			pst.setString(4, dto.getReason());
			if(null != dto.getNextEntryGate())
				pst.setString(5, dto.getNextEntryGate());
			else
				pst.setInt(5, 0);
			pst.setString(6, dto.getRemarks());
			pst.setString(7, vo.getGateId());
			pst.setString(8, vo.getCid());
			count = pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			while(rs.next()) {
				logId = rs.getInt(1);
			}
			
			if(count > 0) {
				if(dto.getTransactionType().equalsIgnoreCase("MARK")) {
					pst.close();
					pst = conn.prepareStatement(MARK_GUEST);
					pst.setInt(1, logId);
					count = pst.executeUpdate();
					
					if(count > 0) {
						result = "GUESTLOG_MARK_SUCCESS";
					} else {
						result = "GUESTLOG_MARK_FAILURE";
					}
				} else {
					if(alertFlag) {
						pst = conn.prepareStatement(MARK_GUEST);
						pst.setInt(1, logId);
						count = pst.executeUpdate();
						
						if(count > 0) {
							pst = conn.prepareStatement(UPDATE_RECORD_STATUS);
							pst.setInt(1, logId);
							count = pst.executeUpdate();
						}
					} else {
						result = "GUESTLOG_ADD_SUCCESS";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 result = "GUESTLOG_ADD_FAILURE";
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(null, rs, pst);
		}
		
		return result;
	}
	
	private static final String UPDATE_RECORD_STATUS = "UPDATE `guestlog` a SET a.`record_status`='H' WHERE a.`log_id`=?";
	
	private static final String GET_RECORD_STATUS = "SELECT COUNT(log_id) rowCount, a.log_id FROM guestlog a WHERE a.`guest_id`=? AND a.`entry_or_exit`=? AND a.`record_status`='A'";
	
	private static final String GET_GUEST_ID = "SELECT "
			+ "  a.`guest_id`, "
			+ "  a.`residing_across_border`, "
			+ "  b.`nationality` "
			+ "FROM "
			+ "  guests a "
			+ "  left join nationality b "
			+ "    on a.`nationality_id` = b.`nationality_id` "
			+ "WHERE a.`identification_no` = ? "
			+ "  AND a.`identification_type_id` = ? "
			+ "LIMIT 1";
	
	private static final String MARK_GUEST = "UPDATE `guestlog` a SET a.`alert_flag` = 'Y', a.`alert_update_time` = CURRENT_TIMESTAMP WHERE a.`log_id`=?";
	
	private static final String GET_GUEST_DETAILS = "select "
			+ "  a.`guest_id`, "
			+ "  a.`identification_no`, "
			+ "  a.`identification_type_id`, "
			+ "  a.`nationality_id`, "
			+ "  a.`guest_name`, "
			+ "  a.`gender`, "
			+ "  DATE_FORMAT(a.`dob`,'%d-%m-%Y') dob, "
			+ "  a.`present_address`, "
			+ "  a.`contact_no`, "
			+ "  a.`residing_across_border`, "
			+ "  a.`flag_status`, b.`image_path`, a.`residing_across_border` "
			+ "from "
			+ "  `guests` a "
			+ "  left join guest_biometric b on a.`guest_id`=b.`guest_id` "
			+ "where `identification_no` = ? "
			+ "  and identification_type_id = ? "
			+ "  limit 1";
	
	private static final String INSERT_INTO_GUESTS = "INSERT INTO `guests` ( "
			+ "  `identification_no`, "
			+ "  `identification_type_id`, "
			+ "  `nationality_id`, "
			+ "  `guest_name`, "
			+ "  `gender`, "
			+ "  `dob`, "
			+ "  `present_address`, "
			+ "  `contact_no` "
			+ ") "
			+ "VALUES "
			+ "  (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String INSERT_INTO_GUEST_LOG = "insert into `guestlog` ( "
			+ "  `guest_id`, "
			+ "  `temperature`, "
			+ "  `entry_or_exit`, "
			+ "  `transaction_date_time`, "
			+ "  `reason_id`, "
			+ "  `requested_gate_id`, "
			+ "  `reason`, "
			+ "  `gate_id`, "
			+ "  `created_by`, "
			+ "  `created_on` "
			+ ") "
			+ "values "
			+ "  (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
}
