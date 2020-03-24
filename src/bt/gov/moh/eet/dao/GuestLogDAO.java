package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
				dto.setAge(rs.getString("age"));
				dto.setContactNo(rs.getString("contact_no"));
				dto.setNationality(rs.getString("nationality_id"));
				dto.setPresentAddress(rs.getString("present_address"));
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
		GuestLogDTO logdto = new GuestLogDTO();
		int count = 0, guestId = 0;
		String result = "GUESTLOG_ADD_FAILURE";
		
		try {
			if(null == dto.getGuestId() || "".equals(dto.getGuestId()) || dto.getGuestId().length() == 0) {
				pst = conn.prepareStatement(INSERT_INTO_GUESTS, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, dto.getIdentificationNo());
				pst.setString(2, dto.getIdentificationType());
				pst.setString(3, dto.getNationality());
				pst.setString(4, dto.getGuestName());
				pst.setString(5, dto.getGender());
				pst.setString(6, dto.getAge());
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
			
			pst = conn.prepareStatement(INSERT_INTO_GUEST_LOG);
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
			
			if(count > 0) {
				 result = "GUESTLOG_ADD_SUCCESS";
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
	
	private static final String GET_GUEST_DETAILS = "select "
			+ "  a.`guest_id`, "
			+ "  a.`identification_no`, "
			+ "  a.`identification_type_id`, "
			+ "  a.`nationality_id`, "
			+ "  a.`guest_name`, "
			+ "  a.`gender`, "
			+ "  a.`age`, "
			+ "  a.`present_address`, "
			+ "  a.`contact_no`, "
			+ "  a.`residing_across_border`, "
			+ "  a.`flag_status` "
			+ "from "
			+ "  `guests` a "
			+ "  left join guestlog b on a.`guest_id`=b.`guest_id` "
			+ "where `identification_no` = ? "
			+ "  and identification_type_id = ? "
			+ "  limit 1";
	
	private static final String INSERT_INTO_GUESTS = "INSERT INTO `guests` ( "
			+ "  `identification_no`, "
			+ "  `identification_type_id`, "
			+ "  `nationality_id`, "
			+ "  `guest_name`, "
			+ "  `gender`, "
			+ "  `age`, "
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
