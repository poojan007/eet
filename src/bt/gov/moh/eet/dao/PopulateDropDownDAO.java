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
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return dropDownList;
	}
}
