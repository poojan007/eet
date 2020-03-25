package bt.gov.moh.eet.dao;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.PasswordEncryptionUtil;
import bt.gov.moh.eet.web.actionform.EnrollmentForm;
import bt.gov.moh.framework.common.Log;

public class EnrollmentDAO {
	
	private static final String INSERT_ENROLLMENT_DATA = ""
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
	private static final String INSERT_IMAGE_PATH = "INSERT INTO `guest_biometric` (`guest_id`,`image_path`) VALUES (?,?)";
	
	public static String insertEnrollmentData(EnrollmentForm enrollmentForm, HttpServletRequest request) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String result = "failure";
		ResultSet rst = null;
		int guestId = 0;
		
		InputStream in = null;
        FileOutputStream fos = null;
		try {
			//convert image
			HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
            InputStream is = wrappedRequest.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            String imageString = enrollmentForm.getImageData();
            imageString = imageString.substring("data:image/jpeg;base64,".length());
            byte[] contentData = imageString.getBytes();
            byte[] decodedData = Base64.decodeBase64(contentData);
            String imgName = "D:\\" + enrollmentForm.getName() + '-' + enrollmentForm.getIdentificationNo() + '-' + String.valueOf(System.currentTimeMillis()) + ".jpeg";
            fos = new FileOutputStream(imgName);
            fos.write(decodedData);
			
			
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(INSERT_ENROLLMENT_DATA, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, enrollmentForm.getIdentificationNo());
			pst.setString(2, "5");
			pst.setString(3, enrollmentForm.getNationality());
			pst.setString(4, enrollmentForm.getName());
			pst.setString(5, enrollmentForm.getGender());
			pst.setString(6, enrollmentForm.getAge());
			pst.setString(7, enrollmentForm.getPresentAddress());
			pst.setString(8, enrollmentForm.getMobileNo());
			pst.setString(9, enrollmentForm.getResidenceFlag());
			int rs = pst.executeUpdate();
			if(rs > 0) {
				rst = pst.getGeneratedKeys();
				while(rst.next()) {
					guestId = rst.getInt(1);
				}
				pst.close();
				//save image path
				pst = conn.prepareStatement(INSERT_IMAGE_PATH);
				pst.setInt(1, guestId);
				pst.setString(2, imgName);
				rs = pst.executeUpdate();
				result = "success";
			}
				
		} catch (Exception e) {
			System.out.println(e);
			Log.error("###Error at LoginDAO[populateUserDetails]", e);
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn, null, pst);
		}
		return result;
	}

	public static String checkDuplicate(String identificationNo) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result =null;
		try {
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(GET_DUPLICATE_ENTRY);
			pst.setString(1, identificationNo);
			rs = pst.executeQuery();
			while(rs.next()){
				result = rs.getString("duplicateIdNo");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			ConnectionManager.close(conn, rs, pst);
		}

		return result;
	}
	
	private static final String GET_DUPLICATE_ENTRY = "SELECT COUNT(identification_no) AS duplicateIdNo FROM guests  WHERE identification_no = ? ";
	
}
