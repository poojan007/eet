package bt.gov.moh.eet.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONArray;

import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.ToJSON;

public class V1_EETRestAPIDao {

	private static V1_EETRestAPIDao dao = null;
	
	public static V1_EETRestAPIDao getInstance() {
		if(dao == null)
			dao = new V1_EETRestAPIDao();
		return dao;
	}
	
	public JSONArray guestEntry(String mobileNo, String gateCode) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "";
		ToJSON convertor = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = ConnectionManager.getConnection();
			if(conn != null){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return json;
	}
	
	public JSONArray guestExit(String mobileNo, String gateCode) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "";
		ToJSON convertor = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = ConnectionManager.getConnection();
			if(conn != null){
				pst = conn.prepareStatement(CHECK_IF_GUEST_EXISTS);
				pst.setString(1, mobileNo);
				rs = pst.executeQuery();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return json;
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}
		
		return json;
	}
	
	private static final String CHECK_IF_GUEST_EXISTS = "SELECT COUNT(guest_id) rowCount FROM guests a WHERE a.`contact_no`=?";
}
