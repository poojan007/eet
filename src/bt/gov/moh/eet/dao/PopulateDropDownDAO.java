package bt.gov.moh.eet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.dto.*;


public class PopulateDropDownDAO {
	
	private static PopulateDropDownDAO dao = null;
	public static PopulateDropDownDAO getInstance() {
		if(dao == null)
			dao = new PopulateDropDownDAO();
		return dao;
	}

	public List<DropDownDTO> getDropDownList(String fieldConstructor, String parentId) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<DropDownDTO> dropDownList = new ArrayList<DropDownDTO>();
		
		if ("USER".equalsIgnoreCase(fieldConstructor)) 
			query = GET_USER_TYPE_LIST_QUERY;
		
		if ("USER".equalsIgnoreCase(fieldConstructor)) 
			query = GET_ROLE_LIST_QUERY;
		if ("IDENTIFICATIONTYPELIST".equalsIgnoreCase(fieldConstructor)) {
		      query = "SELECT identification_type_id AS HEADER_ID, identification_type AS HEADER_NAME FROM identificationtypes";
		    }
		    if ("NATIONALITYLIST".equalsIgnoreCase(fieldConstructor)) {
		      query = "SELECT nationality_id AS HEADER_ID, nationality AS HEADER_NAME FROM nationality";
		    }
		    if ("GATELIST".equalsIgnoreCase(fieldConstructor)) {
		      query = "SELECT gate_id AS HEADER_ID, gate_name AS HEADER_NAME FROM gates";
		    }
		
		try {
			conn = ConnectionManager.getConnection();
			if(conn != null) {
				
				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
				while(rs.next()) {
					DropDownDTO dto = new DropDownDTO();
					dto.setHeaderId(rs.getString("HEADER_ID"));
					dto.setHeaderName(rs.getString("HEADER_NAME"));
					dropDownList.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dropDownList;
	}
	
	private static final String GET_USER_TYPE_LIST_QUERY = "SELECT "
			+ "  a.`user_type_id` AS HEADER_ID, "
			+ "  a.`user_type` AS HEADER_NAME "
			+ "FROM "
			+ "  `usertypes` a";
	
	private static final String GET_ROLE_LIST_QUERY = "SELECT "
			+ "  a.`role_id` AS HEADER_ID, "
			+ "  a.`role_name` AS HEADER_NAME "
			+ "FROM "
			+ " `roles` a";
	
	
}
