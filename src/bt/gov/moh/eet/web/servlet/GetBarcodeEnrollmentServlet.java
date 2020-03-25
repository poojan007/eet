package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.framework.common.Log;

/**
 * Servlet implementation class GetBarcodeEnrollmentServlet
 */
@WebServlet("/getBarcodeEnrollmentDetails")
public class GetBarcodeEnrollmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBarcodeEnrollmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			
			String barcodeNo = request.getParameter("barcodeNo");
			
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_BARCODE_DETAILS);
			pst.setString(1, barcodeNo);
			rs = pst.executeQuery();
			if(rs.first()) {
				buffer.append("<xml-response>");
					buffer.append("<identificationNo>"+rs.getString("identification_no")+"</identificationNo>");
					buffer.append("<identificationType>"+rs.getString("identification_type_id")+"</identificationType>");
					buffer.append("<nationality>"+rs.getString("nationality_id")+"</nationality>");
					buffer.append("<name>"+rs.getString("guest_name")+"</name>");
					buffer.append("<gender>"+rs.getString("gender")+"</gender>");
					buffer.append("<age>"+rs.getString("age")+"</age>");
					buffer.append("<presentAddress>"+rs.getString("present_address")+"</presentAddress>");
					buffer.append("<contactNo>"+rs.getString("contact_no")+"</contactNo>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println(e);
			Log.error("###Error at GetBarcodeEnrollmentServlet[doGet]", e);
		}
		
		out.println(buffer);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private static final String GET_BARCODE_DETAILS = "select "
			+ "  a.`identification_no`, "
			+ "  a.`identification_type_id`, "
			+ "  a.`nationality_id`, "
			+ "  a.`guest_name`, "
			+ "  a.`gender`, "
			+ "  a.`age`, "
			+ "  a.`present_address`, "
			+ "  a.`contact_no` "
			+ "from "
			+ "  `guests` a "
			+ "where a.`identification_no` = ?";

}
