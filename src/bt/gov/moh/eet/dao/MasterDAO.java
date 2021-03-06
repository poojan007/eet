package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bt.gov.moh.eet.dto.MasterDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.framework.common.Log;

public class MasterDAO {
	
	private static MasterDAO masterdao;
	
	public static MasterDAO getInstance() {
		if(masterdao == null)
			masterdao = new MasterDAO();
		return masterdao;
	}
	
	public List<MasterDTO> getMasterList(String masterType) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MasterDTO> masterList = new ArrayList<MasterDTO>();
		MasterDTO dto = null;
		
		try {
			conn = ConnectionManager.getConnection();
			if(masterType.equalsIgnoreCase("MASTER_MANAGEMENT_GATES")) {
				pst = conn.prepareStatement(GET_GATE_LIST);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setGateId(rs.getString("gate_id"));
					dto.setGateName(rs.getString("gate_name"));
					masterList.add(dto);
				}
			} else if(masterType.equalsIgnoreCase("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")) {
				pst = conn.prepareStatement(GET_IDENTIFICATION_TYPES);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setIdentificationTypeId(rs.getString("identification_type_id"));
					dto.setIdentificationType(rs.getString("identification_type"));
					masterList.add(dto);
				}
			} else if(masterType.equalsIgnoreCase("MASTER_MANAGMENT_NATIONALITY")) {
				pst = conn.prepareStatement(GET_NATIONALITIES);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setNationalityId(rs.getString("nationality_id"));
					dto.setNationality(rs.getString("nationality"));
					masterList.add(dto);
				}
			} else if(masterType.equalsIgnoreCase("MASTER_MANAGMENT_USERTYPES")) {
				pst = conn.prepareStatement(GET_USER_TYPES);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setUserTypeId(rs.getString("user_type_id"));
					dto.setUserType(rs.getString("user_type"));
					masterList.add(dto);
				}
			} else if(masterType.equalsIgnoreCase("MASTER_MANAGMENT_EXITREASONS")) {
				pst = conn.prepareStatement(GET_EXIT_REASONS);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setReasonId(rs.getString("reason_id"));
					dto.setReason(rs.getString("reason"));
					masterList.add(dto);
				}
			} else if(masterType.equalsIgnoreCase("MASTER_MANAGMENT_AVERAGE_TIME")) {
				pst = conn.prepareStatement(GET_AVERAGE_TIME);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setId(rs.getString("id"));
					dto.setPointOneId(rs.getString("point_one"));
					dto.setPointTwoId(rs.getString("point_two"));
					dto.setPointOne(rs.getString("pointOne"));
					dto.setPointTwo(rs.getString("pointTwo"));
					dto.setAverageTime(rs.getString("average_time"));
					masterList.add(dto);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			Log.error("###Error at MasterDAO[getMasterList]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return masterList;
	}
	
	public List<MasterDTO> getGateList() throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MasterDTO> masterList = new ArrayList<MasterDTO>();
		MasterDTO dto = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
				pst = conn.prepareStatement(GET_GATE_LIST);
				rs = pst.executeQuery();
				while(rs.next()) {
					dto = new MasterDTO();
					dto.setGateId(rs.getString("gate_id"));
					dto.setGateName(rs.getString("gate_name"));
					masterList.add(dto);
				}
			
			
		} catch (Exception e) {
			System.out.println(e);
			Log.error("###Error at MasterDAO[getMasterList]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return masterList;
	}
	
	public String addMaster(MasterDTO dto) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = "SAVE_FAILURE";
		int count = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			if(dto.getMasterType().equalsIgnoreCase("MANAGE_GATES")) {
				pst = conn.prepareStatement(INSERT_INTO_GATES);
				pst.setString(1, dto.getGateName());
				count = pst.executeUpdate();
				if(count > 0)
					result = "SAVE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")) {
				pst = conn.prepareStatement(INSERT_INTO_IDENTIFICATION_TYPES);
				pst.setString(1, dto.getName());
				count = pst.executeUpdate();
				if(count > 0)
					result = "SAVE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_NATIONALITY")) {
				pst = conn.prepareStatement(INSERT_INTO_NATIONALITY);
				pst.setString(1, dto.getName());
				count = pst.executeUpdate();
				if(count > 0)
					result = "SAVE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_USERTYPES")) {
				pst = conn.prepareStatement(INSERT_INTO_USERTYPES);
				pst.setString(1, dto.getName());
				count = pst.executeUpdate();
				if(count > 0)
					result = "SAVE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_EXIT_REASONS")) {
				pst = conn.prepareStatement(INSERT_INTO_EXIT_REASONS);
				pst.setString(1, dto.getName());
				count = pst.executeUpdate();
				if(count > 0)
					result = "SAVE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_AVERAGE_TIME")) {
				pst = conn.prepareStatement(INSERT_INTO_AVERAGE_TRAVEL_TIME);
				pst.setString(1,dto.getPointTwo() );
				pst.setString(2,dto.getPointOne());
				pst.setString(3,dto.getName());
				count = pst.executeUpdate();
				if(count > 0)
					result = "SAVE_SUCCESS";
			}
		} catch (Exception e) {
			result = "SAVE_FAILURE";
			System.out.println(e);
			Log.error("###Error at MasterDAO[addMaster]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return result;
	}
	
	public String updateMaster(MasterDTO dto) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = "UPDATE_FAILURE";
		int count = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			if(dto.getMasterType().equalsIgnoreCase("MANAGE_GATES")) {
				pst = conn.prepareStatement(UPDATE_GATES);
				pst.setString(1, dto.getGateName());
				pst.setString(2, dto.getGateId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "UPDATE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")) {
				pst = conn.prepareStatement(UPDATE_IDENTIFICATION_TYPES);
				pst.setString(1, dto.getName());
				pst.setString(2, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "UPDATE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_NATIONALITY")) {
				pst = conn.prepareStatement(UPDATE_NATIONALITY);
				pst.setString(1, dto.getName());
				pst.setString(2, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "UPDATE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_USERTYPES")) {
				pst = conn.prepareStatement(UPDATE_USERTYPES);
				pst.setString(1, dto.getName());
				pst.setString(2, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "UPDATE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_EXIT_REASONS")) {
				pst = conn.prepareStatement(UPDATE_EXIT_REASONS);
				pst.setString(1, dto.getName());
				pst.setString(2, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "UPDATE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_AVERAGE_TIME")) {
				pst = conn.prepareStatement(UPDATE_AVERAGE_TRAVEL_TIME);
				pst.setString(1,dto.getPointOne());
				pst.setString(2,dto.getPointTwo());
				pst.setString(3,dto.getName());
				pst.setString(4,dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "UPDATE_SUCCESS";
			}
		} catch (Exception e) {
			result = "UPDATE_FAILURE";
			System.out.println(e);
			Log.error("###Error at MasterDAO[addMaster]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return result;
	}
	
	public String deleteMaster(MasterDTO dto) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = "DELETE_FAILURE";
		int count = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			if(dto.getMasterType().equalsIgnoreCase("MANAGE_GATES")) {
				pst = conn.prepareStatement(DELETE_GATES);
				pst.setString(1, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "DELETE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")) {
				pst = conn.prepareStatement(DELETE_IDENTIFICATION_TYPES);
				pst.setString(1, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "DELETE_SUCCESS";
			} else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_NATIONALITY")) {
				pst = conn.prepareStatement(DELETE_NATIONALITY);
				pst.setString(1, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "DELETE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_USERTYPES")) {
				pst = conn.prepareStatement(DELETE_USERTYPES);
				pst.setString(1, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "DELETE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_EXIT_REASONS")) {
				pst = conn.prepareStatement(DELETE_EXIT_REASONS);
				pst.setString(1, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "DELETE_SUCCESS";
			}else if(dto.getMasterType().equalsIgnoreCase("MASTER_MANAGMENT_AVERAGE_TIME")) {
				pst = conn.prepareStatement(DELETE_TRAVEL_TIME);
				pst.setString(1, dto.getId());
				count = pst.executeUpdate();
				if(count > 0)
					result = "DELETE_SUCCESS";
			}
		} catch (Exception e) {
			result = "DELETE_FAILURE";
			System.out.println(e);
			Log.error("###Error at MasterDAO[deleteMaster]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return result;
	}
	
	private static final String INSERT_INTO_NATIONALITY = "insert into `entry_exit_tracker`.`nationality` (`nationality`) values (?)";
	
	private static final String UPDATE_NATIONALITY = "update `entry_exit_tracker`.`nationality` set `nationality` = ? where `nationality_id` = ?";
	
	private static final String DELETE_NATIONALITY = "DELETE FROM `entry_exit_tracker`.`nationality` WHERE `nationality_id` = ?";
	
	private static final String DELETE_IDENTIFICATION_TYPES = "DELETE FROM `entry_exit_tracker`.`identificationtypes` WHERE `identification_type_id` = ?";
	
	private static final String UPDATE_IDENTIFICATION_TYPES = "update "
			+ "  `entry_exit_tracker`.`identificationtypes` "
			+ "set "
			+ "  `identification_type` = ? "
			+ "where `identification_type_id` = ?";
	
	private static final String INSERT_INTO_IDENTIFICATION_TYPES = "insert into `entry_exit_tracker`.`identificationtypes`(`identification_type`) values (?);";
	
	private static final String DELETE_GATES = "delete from `gates` where `gate_id` = ?";
	
	private static final String UPDATE_GATES = "update `entry_exit_tracker`.`gates` set `gate_name` = ? where `gate_id` = ?";
	
	private static final String INSERT_INTO_GATES = "insert into `entry_exit_tracker`.`gates` (`gate_name`) values (?)";
	
	private static final String GET_GATE_LIST = "select `gate_id`, `gate_name` from `gates`";
	
	private static final String GET_IDENTIFICATION_TYPES = "select `identification_type_id`,`identification_type` from `identificationtypes`";
	
	private static final String GET_NATIONALITIES = "select a.`nationality_id`, a.`nationality` from `nationality` a";
	
	private static final String GET_USER_TYPES = "SELECT a.`user_type_id`, a.`user_type` FROM `usertypes` a";
	
	private static final String GET_EXIT_REASONS = "select `reason_id`,`reason`,`threshold_hour` from `exitreasons`";
	
	private static final String GET_AVERAGE_TIME = "select "
			+ "  `id`, "
			+ "  a.`point_one`, "
			+ "  a.`point_two`, "
			+ "  b.`gate_name` pointOne, "
			+ "  c.`gate_name` pointTwo, "
			+ "  `average_time` "
			+ "from "
			+ "  `averagetime` a "
			+ "  left join gates b on a.`point_one`=b.`gate_id` "
			+ "  left join gates c on a.`point_two`=c.`gate_id` "
			+ "  order by a.`point_one`";
	
	private static final String INSERT_INTO_USERTYPES = "insert into `entry_exit_tracker`.`usertypes` (`user_type`) values (?)";
	
	private static final String INSERT_INTO_EXIT_REASONS = "insert into `entry_exit_tracker`.`exitreasons` (`reason`) values (?)";
	
	private static final String INSERT_INTO_AVERAGE_TRAVEL_TIME = "insert into `entry_exit_tracker`.`averagetime` (`point_one`,`point_two`,`average_time`) values (?,?,?)";
	
	private static final String UPDATE_USERTYPES = "update `entry_exit_tracker`.`usertypes` set `user_type` = ? where `user_type_id` = ?";
	
	private static final String UPDATE_EXIT_REASONS = "update `entry_exit_tracker`.`exitreasons` set `reason` = ? where `reason_id` = ?";
	
	private static final String UPDATE_AVERAGE_TRAVEL_TIME = "update `entry_exit_tracker`.`averagetime` set `point_one` = ?,`point_two` = ?,`average_time` = ? where `id` = ?";
	
	private static final String DELETE_USERTYPES = "delete from `entry_exit_tracker`.`usertypes` where `user_type_id` = ?";
	
	private static final String DELETE_EXIT_REASONS = "delete from `entry_exit_tracker`.`exitreasons` where `user_type_id` = ?";
	
	private static final String DELETE_TRAVEL_TIME = "delete from `entry_exit_tracker`.`averagetime` where `id` = ?";
}
