package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.vo.UserDetailsVO;

public class ReportDAO {
	
	private static ReportDAO dao = null;
	
	public static ReportDAO getInstance() {
		if(dao == null)
			dao = new ReportDAO();
		return dao;
	}
	
	public List<GuestLogDTO> getReportList(String type, UserDetailsVO vo) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<GuestLogDTO> reportList = new ArrayList<GuestLogDTO>();
		GuestLogDTO dto = null;
		String query = null;
		
		try {
			if(type.equalsIgnoreCase("ENTRY")) {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN"))
					query = GET_REPORT_LIST1 + GET_REPORT_LIST4;
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR"))
					query = GET_REPORT_LIST1 + GET_REPORT_LIST3 + GET_REPORT_LIST4;
				else
					query = GET_REPORT_LIST1 + GET_REPORT_LIST2 + GET_REPORT_LIST4;
			} else if(type.equalsIgnoreCase("EXIT")) {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN"))
					query = GET_REPORT_LIST1 + GET_REPORT_LIST4;
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR"))
					query = GET_REPORT_LIST1 + GET_REPORT_LIST3 + GET_REPORT_LIST4;
				else
					query = GET_REPORT_LIST1 + GET_REPORT_LIST2 + GET_REPORT_LIST4;
			} else {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN"))
					query = GET_ALERT_REPORT_LIST1 + GET_REPORT_LIST5 + GET_REPORT_LIST4;
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR"))
					query = GET_ALERT_REPORT_LIST1 + GET_REPORT_LIST5 + GET_REPORT_LIST3 + GET_REPORT_LIST4;
				else
					query = GET_ALERT_REPORT_LIST1 + GET_REPORT_LIST5 + GET_REPORT_LIST2 + GET_REPORT_LIST4;
			}
			
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(query);
			
			if(type.equalsIgnoreCase("ENTRY") || type.equalsIgnoreCase("EXIT")) {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN")){
					pst.setString(1, type);
				}
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")){
					pst.setString(1, type);
					pst.setString(2, vo.getDzongkhagId());
				}
				else {
					pst.setString(1, type);
					pst.setString(2, vo.getGateId());
				}
			} else {
				if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")){
					pst.setString(1, vo.getDzongkhagId());
				}
				else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")){
					pst.setString(1, vo.getGateId());
				}
			}
			
			rs = pst.executeQuery();
			while(rs.next()) {
				dto = new GuestLogDTO();
				dto.setIdentificationNo(rs.getString("identification_no"));
				dto.setIdentificationType(rs.getString("identification_type"));
				dto.setGuestName(rs.getString("guest_name"));
				dto.setGender(rs.getString("gender"));
				dto.setDob(rs.getString("dob"));
				dto.setContactNo(rs.getString("contact_no"));
				dto.setEntryOrExit(rs.getString("entry_or_exit"));
				dto.setTransactionTime(rs.getString("transactionTime"));
				dto.setGateName(rs.getString("gate_name"));
				dto.setDzongkhagName(rs.getString("dzongkhag"));
				dto.setAlertRemarks(rs.getString("remarks"));
				
				reportList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return reportList;
	}
	
	public List<GuestLogDTO> getReportList(String startDate, String endDate, String type, UserDetailsVO vo) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<GuestLogDTO> reportList = new ArrayList<GuestLogDTO>();
		GuestLogDTO dto = null;
		String query = null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if(type.equalsIgnoreCase("ENTRY")) {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN"))
					query = GET_REPORT_HISTORY_LIST1 + GET_REPORT_HISTORY_LIST4;
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR"))
					query = GET_REPORT_HISTORY_LIST1 + GET_REPORT_HISTORY_LIST3 + GET_REPORT_HISTORY_LIST4;
				else
					query = GET_REPORT_HISTORY_LIST1 + GET_REPORT_HISTORY_LIST2 + GET_REPORT_HISTORY_LIST4;
			} else if(type.equalsIgnoreCase("EXIT")) {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN"))
					query = GET_REPORT_HISTORY_LIST1 + GET_REPORT_HISTORY_LIST4;
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR"))
					query = GET_REPORT_HISTORY_LIST1 + GET_REPORT_HISTORY_LIST3 + GET_REPORT_HISTORY_LIST4;
				else
					query = GET_REPORT_HISTORY_LIST1 + GET_REPORT_HISTORY_LIST2 + GET_REPORT_HISTORY_LIST4;
			} else {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN"))
					query = GET_ALERT_HISTORY_REPORT_LIST1 + GET_REPORT_HISTORY_LIST5 + GET_REPORT_HISTORY_LIST4;
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR"))
					query = GET_ALERT_HISTORY_REPORT_LIST1 + GET_REPORT_HISTORY_LIST5 + GET_REPORT_HISTORY_LIST3 + GET_REPORT_HISTORY_LIST4;
				else
					query = GET_ALERT_HISTORY_REPORT_LIST1 + GET_REPORT_HISTORY_LIST5 + GET_REPORT_HISTORY_LIST2 + GET_REPORT_HISTORY_LIST4;
			}
			
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(query);
			
			java.util.Date dateStart = df.parse(startDate);
			String dateStartStr = sdf.format(dateStart);
			java.util.Date dateEnd = df.parse(endDate);
			String dateEndStr = sdf.format(dateEnd);
			
			if(type.equalsIgnoreCase("ENTRY") || type.equalsIgnoreCase("EXIT")) {
				if(vo.getRoleCode().equalsIgnoreCase("SUPER_ADMIN")){
					pst.setString(1, type);
					pst.setString(2, dateStartStr);
					pst.setString(3, dateEndStr);
				}
				else if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")){
					pst.setString(1, type);
					pst.setString(2, dateStartStr);
					pst.setString(3, dateEndStr);
					pst.setString(4, vo.getDzongkhagId());
				}
				else {
					pst.setString(1, type);
					pst.setString(2, dateStartStr);
					pst.setString(3, dateEndStr);
					pst.setString(4, vo.getGateId());
				}
			} else {
				if(vo.getRoleCode().equalsIgnoreCase("ADMINISTRATOR")){
					pst.setString(1, dateStartStr);
					pst.setString(2, dateEndStr);
					pst.setString(3, vo.getDzongkhagId());
				}
				else if(vo.getRoleCode().equalsIgnoreCase("DATA_MANAGER")){
					pst.setString(1, dateStartStr);
					pst.setString(2, dateEndStr);
					pst.setString(3, vo.getGateId());
				}
			}
			
			rs = pst.executeQuery();
			while(rs.next()) {
				dto = new GuestLogDTO();
				dto.setIdentificationNo(rs.getString("identification_no"));
				dto.setIdentificationType(rs.getString("identification_type"));
				dto.setGuestName(rs.getString("guest_name"));
				dto.setGender(rs.getString("gender"));
				dto.setDob(rs.getString("dob"));
				dto.setContactNo(rs.getString("contact_no"));
				dto.setEntryOrExit(rs.getString("entry_or_exit"));
				dto.setTransactionTime(rs.getString("transactionTime"));
				dto.setGateName(rs.getString("gate_name"));
				dto.setDzongkhagName(rs.getString("dzongkhag"));
				dto.setAlertRemarks(rs.getString("remarks"));
				reportList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return reportList;
	}
	
	private static final String GET_ALERT_HISTORY_REPORT_LIST1 = "SELECT "
			+ "  b.`identification_no`, "
			+ "  c.`identification_type`, "
			+ "  b.`guest_name`, "
			+ "  IF(b.`gender` = 'M', 'Male', 'Female') gender, "
			+ "  DATE_FORMAT(b.`dob`, '%d-%m-%Y') dob, "
			+ "  b.`contact_no`, "
			+ "  a.`entry_or_exit`, "
			+ "  DATE_FORMAT( "
			+ "    a.`transaction_date_time`, "
			+ "    '%d-%m-%Y %H:%i %p' "
			+ "  ) transactionTime, "
			+ "  d.`gate_name`, "
			+ "  g.`dzongkhag`, a.alert_remarks AS remarks "
			+ "FROM "
			+ "  guestlog a "
			+ "  LEFT JOIN guests b "
			+ "    ON a.`guest_id` = b.`guest_id` "
			+ "  LEFT JOIN `identificationtypes` c "
			+ "    ON b.`identification_type_id` = c.`identification_type_id` "
			+ "  LEFT JOIN gates d "
			+ "    ON a.`gate_id` = d.`gate_id` "
			+ "  LEFT JOIN gewog_gate_mapping e "
			+ "    ON d.`gate_id` = e.`gate_id` "
			+ "  LEFT JOIN gewogs f "
			+ "    ON e.`gewog_id` = f.`gewog_id` "
			+ "  LEFT JOIN dzongkhags g "
			+ "    ON f.`dzongkhag_id` = g.`dzongkhag_id` "
			+ "WHERE DATE_FORMAT(a.`created_on`, '%Y-%m-%d') BETWEEN ? AND ? ";
	
	private static final String GET_REPORT_HISTORY_LIST1 = "SELECT "
			+ "  b.`identification_no`, "
			+ "  c.`identification_type`, "
			+ "  b.`guest_name`, "
			+ "  IF(b.`gender` = 'M', 'Male', 'Female') gender, "
			+ "  DATE_FORMAT(b.`dob`, '%d-%m-%Y') dob, "
			+ "  b.`contact_no`, "
			+ "  a.`entry_or_exit`, "
			+ "  DATE_FORMAT( "
			+ "    a.`transaction_date_time`, "
			+ "    '%d-%m-%Y %H:%i %p' "
			+ "  ) transactionTime, "
			+ "  d.`gate_name`, "
			+ "  g.`dzongkhag`, a.reason AS remarks "
			+ "FROM "
			+ "  guestlog a "
			+ "  LEFT JOIN guests b "
			+ "    ON a.`guest_id` = b.`guest_id` "
			+ "  LEFT JOIN `identificationtypes` c "
			+ "    ON b.`identification_type_id` = c.`identification_type_id` "
			+ "  LEFT JOIN gates d "
			+ "    ON a.`gate_id` = d.`gate_id` "
			+ "  LEFT JOIN gewog_gate_mapping e "
			+ "    ON d.`gate_id` = e.`gate_id` "
			+ "  LEFT JOIN gewogs f "
			+ "    ON e.`gewog_id` = f.`gewog_id` "
			+ "  LEFT JOIN dzongkhags g "
			+ "    ON f.`dzongkhag_id` = g.`dzongkhag_id` "
			+ "WHERE a.`entry_or_exit` = ? "
			+ "  AND DATE_FORMAT(a.`created_on`, '%Y-%m-%d') BETWEEN ? AND ? ";
	
	private static final String GET_REPORT_HISTORY_LIST5 = " AND a.`alert_flag`='Y'";
	
	private static final String GET_REPORT_HISTORY_LIST2 = " AND a.`gate_id` = ?  ";
	
	private static final String GET_REPORT_HISTORY_LIST3 =  " AND g.`dzongkhag_id` = ? ";
	
	private static final String GET_REPORT_HISTORY_LIST4 =  "  GROUP BY a.`guest_id`";
	
	private static final String GET_ALERT_REPORT_LIST1 = "SELECT "
			+ "  b.`identification_no`, "
			+ "  c.`identification_type`, "
			+ "  b.`guest_name`, "
			+ "  IF(b.`gender` = 'M', 'Male', 'Female') gender, "
			+ "  DATE_FORMAT(b.`dob`, '%d-%m-%Y') dob, "
			+ "  b.`contact_no`, "
			+ "  a.`entry_or_exit`, "
			+ "  DATE_FORMAT( "
			+ "    a.`transaction_date_time`, "
			+ "    '%d-%m-%Y %H:%i %p' "
			+ "  ) transactionTime, "
			+ "  d.`gate_name`, "
			+ "  g.`dzongkhag`, a.alert_remarks AS remarks "
			+ "FROM "
			+ "  guestlog a "
			+ "  LEFT JOIN guests b "
			+ "    ON a.`guest_id` = b.`guest_id` "
			+ "  LEFT JOIN `identificationtypes` c "
			+ "    ON b.`identification_type_id` = c.`identification_type_id` "
			+ "  LEFT JOIN gates d "
			+ "    ON a.`gate_id` = d.`gate_id` "
			+ "  LEFT JOIN gewog_gate_mapping e "
			+ "    ON d.`gate_id` = e.`gate_id` "
			+ "  LEFT JOIN gewogs f "
			+ "    ON e.`gewog_id` = f.`gewog_id` "
			+ "  LEFT JOIN dzongkhags g "
			+ "    ON f.`dzongkhag_id` = g.`dzongkhag_id` "
			+ "WHERE DATE_FORMAT(a.`created_on`, '%Y-%m-%d') = CURRENT_DATE ";
	
	private static final String GET_REPORT_LIST1 = "SELECT "
			+ "  b.`identification_no`, "
			+ "  c.`identification_type`, "
			+ "  b.`guest_name`, "
			+ "  IF(b.`gender` = 'M', 'Male', 'Female') gender, "
			+ "  DATE_FORMAT(b.`dob`, '%d-%m-%Y') dob, "
			+ "  b.`contact_no`, "
			+ "  a.`entry_or_exit`, "
			+ "  DATE_FORMAT( "
			+ "    a.`transaction_date_time`, "
			+ "    '%d-%m-%Y %H:%i %p' "
			+ "  ) transactionTime, "
			+ "  d.`gate_name`, "
			+ "  g.`dzongkhag`, a.reason AS remarks "
			+ "FROM "
			+ "  guestlog a "
			+ "  LEFT JOIN guests b "
			+ "    ON a.`guest_id` = b.`guest_id` "
			+ "  LEFT JOIN `identificationtypes` c "
			+ "    ON b.`identification_type_id` = c.`identification_type_id` "
			+ "  LEFT JOIN gates d "
			+ "    ON a.`gate_id` = d.`gate_id` "
			+ "  LEFT JOIN gewog_gate_mapping e "
			+ "    ON d.`gate_id` = e.`gate_id` "
			+ "  LEFT JOIN gewogs f "
			+ "    ON e.`gewog_id` = f.`gewog_id` "
			+ "  LEFT JOIN dzongkhags g "
			+ "    ON f.`dzongkhag_id` = g.`dzongkhag_id` "
			+ "WHERE a.`entry_or_exit` = ? "
			+ "  AND DATE_FORMAT(a.`created_on`, '%Y-%m-%d') = CURRENT_DATE ";
	
	private static final String GET_REPORT_LIST5 = " AND a.`alert_flag`='Y'";
	
	private static final String GET_REPORT_LIST2 = " AND a.`gate_id` = ?  ";
	
	private static final String GET_REPORT_LIST3 =  " AND g.`dzongkhag_id` = ? ";
	
	private static final String GET_REPORT_LIST4 =  "  GROUP BY a.`guest_id`";

}
