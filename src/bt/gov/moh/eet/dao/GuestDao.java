package bt.gov.moh.eet.dao;

import bt.gov.moh.eet.dto.GuestDTO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.web.actionform.EntryExitForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuestDao {
	private static GuestDao guestDao;

	public static GuestDao getInstance() {
		if (guestDao == null)
			guestDao = new GuestDao();
		return guestDao;
	}

	public GuestDTO fetchGuestDetail(Integer guestID) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		GuestDTO vo = new GuestDTO();

		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_GUEST_DETAILS);
			pst.setInt(1, guestID);
			rs = pst.executeQuery();

			if (rs.getInt("rowCount") > 0) {
				pst.close();
				rs.close();

				while (rs.next()) {
					vo.setGuest_id(rs.getInt("guest_id"));
					vo.setIdentification_no(rs.getString("identification_no"));
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
		GuestLogDTO vo = new GuestLogDTO();

		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_LAST_GUEST_DETAILS);
			pst.setInt(1, guest_id);
			rs = pst.executeQuery();

			int i = 0;
			if (rs.getInt("rowCount") > i) {
				pst.close();
				rs.close();

				while (rs.next()) {
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

	public String addGuest(GuestDTO dto, Connection conn) {
		PreparedStatement pst = null;
		ResultSet rst = null;
		String result = "GUEST_ADD_FAILURE";
		int guestId = 0;

		try {
			pst = conn.prepareStatement(INSERT_INTO_GUEST, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, dto.getIdentification_no());
			pst.setInt(2, dto.getIdentification_type_id());
			pst.setInt(3, dto.getNationality_id());
			pst.setString(4, dto.getGuest_name());
			pst.setString(5, dto.getGender().toString());
			pst.setInt(6, dto.getAge());
			pst.setString(7, dto.getPresent_address());
			pst.setInt(8, dto.getContact_no());
			pst.setString(9, "Y");

			int count = pst.executeUpdate();

			if (count > 0) {
				// add guest log table
				rst = pst.getGeneratedKeys();
				while(rst.next()) {
					guestId = rst.getInt(1);
				}
				dto.setGuest_id(guestId);
				result = "GUEST_ADD_SUCCESS";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "GUEST_ADD_FAILURE";
		} finally {
			ConnectionManager.close(null, null, pst);
		}
		return result;
	}

	public String addGuestLog(EntryExitForm dto, Connection conn) {
		PreparedStatement pst = null;
		String result = "GUESTLOG_ADD_FAILURE";

		try {
			pst = conn.prepareStatement(INSERT_INTO_GUESTLOG);
			pst.setInt(1, dto.getGuest_id());
			pst.setInt(2, dto.getTemperature());
			pst.setString(3, dto.getEntry_or_exit());
			pst.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			pst.setInt(5, dto.getReason_id());
			if(dto.getReason_id() == 2)
				pst.setInt(6, dto.getRequested_gate_id());
			else
				pst.setInt(6, 0);
			pst.setString(7, dto.getReason());
			pst.setInt(8, dto.getGate_id());
			pst.setString(9, dto.getCreated_by());
			pst.setDate(10, new java.sql.Date(System.currentTimeMillis()));
			pst.setString(11, "N"); //remember to change here tomorrow

			int count = pst.executeUpdate();

			if (count > 0) {
				result = "GUESTLOG_ADD_SUCCESS";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "GUESTLOG_ADD_FAILURE";
		} finally {
			ConnectionManager.close(null, null, pst);
		}
		return result;
	}

	public String editGuestLog(GuestLogDTO dto, Connection conn) {
		PreparedStatement pst = null;
		String result = "GUESTLOG_EDIT_FAILURE";

		try {
			pst = conn.prepareStatement(EDIT_INTO_GUESTLOG);
			pst.setInt(1, dto.getGuest_id());
			pst.setInt(2, dto.getTemperature());
			pst.setString(3, dto.getEntry_or_exit());
			pst.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			pst.setInt(5, dto.getReason_id());
			pst.setInt(6, dto.getRequested_gate_id());
			pst.setString(7, dto.getReason());
			pst.setInt(8, dto.getGate_id());
			pst.setString(9, dto.getCreated_by());
			pst.setDate(10, new java.sql.Date(System.currentTimeMillis()));
			pst.setInt(11, dto.getAlert_flag());
			pst.setString(12, dto.getAlert_remarks());
			pst.setDate(13, new java.sql.Date(System.currentTimeMillis()));
			pst.setInt(14, dto.getLog_id());

			int count = pst.executeUpdate();

			if (count > 0) {
				result = "GUESTLOG_EDIT_SUCCESS";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "GUESTLOG_EDIT_FAILURE";
		} finally {
			ConnectionManager.close(null, null, pst);
		}
		return result;
	}

	private static final String GET_GUEST_DETAILS = "SELECT * FROM guests WHERE cid=?";
	private static final String GET_LAST_GUEST_DETAILS = "SELECT * FROM guestlog\n"
			+ "WHERE guest_id = ? order by entry_or_exit desc limit 1";
	private static final String INSERT_INTO_GUEST = ""
			+ "INSERT INTO `guests` "
			+ "            (`identification_no`, "
			+ "             `identification_type_id`, "
			+ "             `nationality_id`, "
			+ "             `guest_name`, "
			+ "             `gender`, "
			+ "             `age`, "
			+ "             `present_address`, "
			+ "             `contact_no`, "
			+ "             `residing_across_border` "
			+ "             ) "
			+ "VALUES (?,?,?,?,?,?,?,?,?);";
	private static final String INSERT_INTO_GUESTLOG = "insert into guestlog(guest_id,\n" + "temperature,\n"
			+ "entry_or_exit,\n" + "transaction_date_time,\n" + "reason_id,\n" + "requested_gate_id,\n" + "reason,\n"
			+ "gate_id,\n" + "created_by,\n" + "created_on,\n" + "alert_flag)\n"
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String EDIT_INTO_GUESTLOG = "insert into guestlog(guest_id,\n" + "temperature,\n"
			+ "entry_or_exit,\n" + "transaction_date_time,\n" + "reason_id,\n" + "requested_gate_id,\n" + "reason,\n"
			+ "gate_id,\n" + "created_by,\n" + "created_on,\n" + "alert_flag, alert_remarks, alert_update_time)\n"
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) where log_id=?\n";
}
