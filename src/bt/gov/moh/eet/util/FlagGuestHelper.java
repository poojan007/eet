package bt.gov.moh.eet.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bt.gov.moh.eet.dto.EntryExitDTO;

public class FlagGuestHelper {
	
	private static FlagGuestHelper guestHelper = null;
	
	public static FlagGuestHelper getInstance() {
		if(guestHelper == null)
			guestHelper = new FlagGuestHelper();
		return guestHelper;
	}
	
	public String determineAlertFlag(String idNo, String identificationType, String transactionType) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer message = new StringBuffer();
		int guestId = 0;
		boolean alertFlag = false;
		EntryExitDTO entry = new EntryExitDTO();
		EntryExitDTO exit = new EntryExitDTO();
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(GET_GUEST_ID);
			pst.setString(1, idNo);
			pst.setString(2, identificationType);
			rs = pst.executeQuery();
			rs.first();
			guestId = rs.getInt("guest_id");
			String residenceFlag = rs.getString("residing_across_border");
			String nationality = rs.getString("nationality");
			
			if(guestId > 0 && (residenceFlag.equalsIgnoreCase("N") && nationality.equalsIgnoreCase("BHUTAN")) || 
					(residenceFlag.equalsIgnoreCase("Y") && !nationality.equalsIgnoreCase("BHUTAN"))) {
				
				pst.close();
				rs.close();
				pst = conn.prepareStatement(GET_EXIT_LOG);
				pst.setInt(1, guestId);
				rs = pst.executeQuery();
				rs.first();
				exit.setExitCount(rs.getString("exitCount"));
				exit.setEntryOfExit(rs.getString("entry_or_exit"));
				exit.setTransactionDateTime(rs.getString("transaction_date_time"));
				exit.setReasonId(rs.getString("reason_id"));
				exit.setReason(rs.getString("reason"));
				exit.setRequestedGateId(rs.getString("requested_gate_id"));
				exit.setGateId(rs.getString("gate_id"));
				exit.setLogId(rs.getInt("log_id"));
				
				pst.close();
				rs.close();
				pst = conn.prepareStatement(GET_ENTRY_LOG);
				pst.setInt(1, guestId);
				rs = pst.executeQuery();
				rs.first();
				entry.setEntryCount(rs.getString("entryCount"));
				entry.setEntryOfExit(rs.getString("entry_or_exit"));
				entry.setTransactionDateTime(rs.getString("transaction_date_time"));
				entry.setReasonId(rs.getString("reason_id"));
				entry.setReason(rs.getString("reason"));
				entry.setRequestedGateId(rs.getString("requested_gate_id"));
				entry.setGateId(rs.getString("gate_id"));
				entry.setTemperatureCheck(rs.getString("temperatureCheck"));
				entry.setTemperatureThreshold(rs.getString("temperatureThreshold"));
				entry.setLogId(rs.getInt("log_id"));
				
				if(transactionType.equalsIgnoreCase("ENTRY")) {
					int timeGap = 0;
					if(!exit.getReason().equalsIgnoreCase("TRAVEL")) {
						pst.close();
						rs.close();
						pst = conn.prepareStatement(GET_TIME_DIFFERENCE_IN_HOURS);
						pst.setString(1, exit.getTransactionDateTime());
						pst.setString(2, entry.getTransactionDateTime());
						rs = pst.executeQuery();
						rs.first();
						timeGap = rs.getInt("timeGap");
						
						pst = conn.prepareStatement(GET_THRESHOLD_HOURS_FOR_NON_TRAVEL_REASONS);
						pst.setString(1, exit.getReasonId());
						rs = pst.executeQuery();
						rs.first();
						int thresholdHour = rs.getInt("threshold_hour");
						
						if(timeGap > thresholdHour) {
							message.append("Average permitted time has exceeded the threshold .\n");
							alertFlag = true;
						} if(!entry.getGateId().equals(exit.getGateId())) {
							message.append("Exit and Entry gate doesnot match .\n");
							alertFlag = true;
						} if(entry.getTemperatureCheck().equals("TRUE")) {
							message.append("Temperature is above the permit threshold of " + entry.getTemperatureThreshold() + " degree.\n");
							alertFlag = true;
						} 
					} else if(exit.getReason().equalsIgnoreCase("TRAVEL")) {
						if(!exit.getRequestedGateId().equals(entry.getGateId())) {
							message.append("The requested gate and entry gate doesnot match.\n");
							alertFlag = true;
						} if(entry.getTemperatureCheck().equals("TRUE")) {
							message.append("Temperature is above the permit threshold of " + entry.getTemperatureThreshold() + " degree.\n");
							alertFlag = true;
						} if(Integer.parseInt(exit.getExitCount()) > 0 && (exit.getRequestedGateId().equals(entry.getGateId()))) {
							pst = conn.prepareStatement(GET_TIME_DIFFERENCE_IN_HOURS);
							pst.setString(1, exit.getTransactionDateTime());
							pst.setString(2, entry.getTransactionDateTime());
							rs = pst.executeQuery();
							rs.first();
							timeGap = rs.getInt("timeGap");
							
							pst = conn.prepareStatement(GET_AVERAGE_TIME);
							pst.setString(1, exit.getGateId());
							pst.setString(2, entry.getGateId());
							rs = pst.executeQuery();
							rs.first();
							double averageTime = rs.getDouble("average_time");
							
							if(timeGap > averageTime) {
								message.append("Average travel time has exceeded the permitted threshold.\n");
								alertFlag = true;
							}
						}
					}
				}
			} else if(guestId > 0 && (residenceFlag.equalsIgnoreCase("Y") && nationality.equalsIgnoreCase("BHUTAN")) || 
					(residenceFlag.equalsIgnoreCase("N") && !nationality.equalsIgnoreCase("BHUTAN"))) {
				pst.close();
				rs.close();
				pst = conn.prepareStatement(GET_EXIT_LOG);
				pst.setInt(1, guestId);
				rs = pst.executeQuery();
				rs.first();
				exit.setExitCount(rs.getString("exitCount"));
				exit.setEntryOfExit(rs.getString("entry_or_exit"));
				exit.setTransactionDateTime(rs.getString("transaction_date_time"));
				exit.setReasonId(rs.getString("reason_id"));
				exit.setReason(rs.getString("reason"));
				exit.setRequestedGateId(rs.getString("requested_gate_id"));
				exit.setGateId(rs.getString("gate_id"));
				exit.setLogId(rs.getInt("log_id"));
				
				pst.close();
				rs.close();
				pst = conn.prepareStatement(GET_ENTRY_LOG);
				pst.setInt(1, guestId);
				rs = pst.executeQuery();
				rs.first();
				entry.setEntryCount(rs.getString("entryCount"));
				entry.setEntryOfExit(rs.getString("entry_or_exit"));
				entry.setTransactionDateTime(rs.getString("transaction_date_time"));
				entry.setReasonId(rs.getString("reason_id"));
				entry.setReason(rs.getString("reason"));
				entry.setRequestedGateId(rs.getString("requested_gate_id"));
				entry.setGateId(rs.getString("gate_id"));
				entry.setTemperatureCheck(rs.getString("temperatureCheck"));
				entry.setTemperatureThreshold(rs.getString("temperatureThreshold"));
				entry.setLogId(rs.getInt("log_id"));
				
				if(entry.getTemperatureCheck().equals("TRUE")) {
					message.append("Temperature is above the permit threshold of " + entry.getTemperatureThreshold() + " degree.\n");
					alertFlag = true;
				}
			}
			
			if(alertFlag) {
				pst.close();
				int transactionLogId = 0;
				transactionLogId = entry.getLogId() > exit.getLogId() ? entry.getLogId() : exit.getLogId();
				
				pst = conn.prepareStatement(UPDATE_ALERT_FLAG);
				pst.setString(1, message.toString());
				pst.setInt(2, transactionLogId);
				int count = pst.executeUpdate();
				
				if(count > 0) {
					pst = conn.prepareStatement(UPDATE_RECORD_STATUS);
					pst.setInt(1, entry.getLogId());
					count = pst.executeUpdate();
					
					pst = conn.prepareStatement(UPDATE_RECORD_STATUS);
					pst.setInt(1, exit.getLogId());
					count = pst.executeUpdate();
					
					conn.commit();
				}
			} else {
				if(Integer.parseInt(entry.getEntryCount()) > 0 && Integer.parseInt(exit.getExitCount()) > 0) {
					pst = conn.prepareStatement(UPDATE_RECORD_STATUS);
					pst.setInt(1, entry.getLogId());
					pst.executeUpdate();
					
					pst = conn.prepareStatement(UPDATE_RECORD_STATUS);
					pst.setInt(1, exit.getLogId());
					pst.executeUpdate();
				}
				
				message.append("NO_ALERT_TRIGGERED");
				conn.commit();
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return message.toString();
	}
	
	private static final String UPDATE_RECORD_STATUS = "UPDATE `guestlog` a SET a.`record_status`='H' WHERE a.`log_id`=?";
	
	private static final String UPDATE_ALERT_FLAG = "UPDATE `guestlog` a SET a.`alert_flag` = 'Y', a.`alert_update_time` = CURRENT_TIMESTAMP, a.alert_remarks=? WHERE a.`log_id`=?";
	
	private static final String GET_THRESHOLD_HOURS_FOR_NON_TRAVEL_REASONS = "SELECT a.`threshold_hour` FROM `exitreasons` a WHERE a.`reason_id`=?";
	
	private static final String GET_AVERAGE_TIME = "SELECT a.`average_time` FROM `averagetime` a WHERE a.`point_one`=? AND a.`point_two`=?";
	
	private static final String GET_TIME_DIFFERENCE_IN_HOURS = "select TIMESTAMPDIFF(HOUR, ?, ?) timeGap";
	
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
	
	private static final String GET_ENTRY_LOG = "select "
			+ "  a.log_id, count(log_id) entryCount, "
			+ "  a.`entry_or_exit`, "
			+ "  a.`transaction_date_time`, "
			+ "  a.`reason_id`, "
			+ "  b.`reason`, "
			+ "  a.`requested_gate_id`, "
			+ "  a.`gate_id`, "
			+ "  if( "
			+ "    a.`temperature` > "
			+ "    (select "
			+ "      `threshold` "
			+ "    from "
			+ "      `appconstants` "
			+ "    where `app_constant` = 'TEMPERATURE'), "
			+ "    'TRUE', "
			+ "    'FALSE' "
			+ "  ) temperatureCheck, (SELECT `threshold` FROM `appconstants` WHERE `app_constant` = 'TEMPERATURE') temperatureThreshold "
			+ "from "
			+ "  guestlog a "
			+ "  LEFT JOIN exitreasons b "
			+ "    ON a.`reason_id` = b.`reason_id` "
			+ "where a.`guest_id` = ? "
			+ "  and a.`entry_or_exit` = 'ENTRY' AND record_status='A' "
			+ "order by log_id desc "
			+ "LIMIT 1";
	
	private static final String GET_EXIT_LOG = "SELECT "
			+ "  a.log_id, COUNT(log_id) exitCount, "
			+ "  a.`entry_or_exit`, "
			+ "  a.`transaction_date_time`, "
			+ "  a.`reason_id`, "
			+ "  b.`reason`, "
			+ "  a.`requested_gate_id`, "
			+ "  a.`gate_id` "
			+ "FROM "
			+ "  guestlog a "
			+ "  LEFT JOIN exitreasons b "
			+ "    ON a.`reason_id` = b.`reason_id` "
			+ "WHERE a.`guest_id` = ? "
			+ "  AND a.`entry_or_exit` = 'EXIT' AND record_status='A' "
			+ "ORDER BY log_id DESC "
			+ "LIMIT 1";
	
//	private GuestDao guestDao;
//	public GuestLogDTO run(GuestLogDTO guestLogDTO) throws Exception {
//		guestLogDTO = determineEntryOrExit(guestLogDTO);
//		guestLogDTO = determineAlertFlag(guestLogDTO);
//		return guestLogDTO;
//	}
//
//	private GuestLogDTO determineEntryOrExit(GuestLogDTO guestLogDTO) throws Exception {
//		guestDao = new GuestDao();
//		GuestDTO guestDTO = guestDao.getInstance().fetchGuestDetail(guestLogDTO.getGuest_id());
//		GuestLogDTO lastGuestLogDTO = guestDao.getInstance().fetchLastGuestLogDetails(guestLogDTO.getGuest_id());
//
//		if (guestDTO == null && lastGuestLogDTO == null && guestLogDTO.getEntry_or_exit() == null) {
//			throw new Exception("For the guest registering for the first time should set exit or entry manually");
//		} else {
//			if (guestDTO != null && guestDTO.getResiding_across_border().equals('Y')) {
//				guestLogDTO.setEntry_or_exit(lastGuestLogDTO.getEntry_or_exit().equals("ENTRY") ? "EXIT" : "ENTRY");
//			}
//		}
//		return guestLogDTO;
//	}
//
//	private GuestLogDTO determineAlertFlag(GuestLogDTO guestLogDTO) throws Exception {
//		if (guestLogDTO.getTemperature() == null) {
//			throw new Exception("For the guest registering for the first time should set exit or entry manually");
//		} else if (guestLogDTO.getTemperature() > 37) {
//			guestLogDTO.setAlert_flag('Y');
//		}
//		return guestLogDTO;
//	}
}
