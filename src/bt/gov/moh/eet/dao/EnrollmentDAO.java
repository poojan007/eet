package bt.gov.moh.eet.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.web.actionform.EnrollmentForm;
import bt.gov.moh.framework.common.Log;

public class EnrollmentDAO {
	
	public static String insertEnrollmentData(EnrollmentForm enrollmentForm, HttpServletRequest request) throws Exception {
		Connection conn = null;
		PreparedStatement pst = null;
		String result = "SAVE_FAILURE";
		ResultSet rst = null;
		int guestId = 0;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			String filePath = uploadImage(enrollmentForm, request);
			
			java.util.Date dob = df.parse(enrollmentForm.getDob());
			String dobStr = sdf.format(dob);
			
			pst = conn.prepareStatement(INSERT_ENROLLMENT_DATA, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, enrollmentForm.getIdentificationNo());
			pst.setString(2, enrollmentForm.getNationality());
			pst.setString(3, enrollmentForm.getName());
			pst.setString(4, enrollmentForm.getGender());
			pst.setString(5, dobStr);
			pst.setString(6, enrollmentForm.getPresentAddress());
			pst.setString(7, enrollmentForm.getMobileNo());
			pst.setString(8, enrollmentForm.getResidenceFlag());
			int count = pst.executeUpdate();
			
			if(count > 0) {
				rst = pst.getGeneratedKeys();
				while(rst.next()) {
					guestId = rst.getInt(1);
				}
				
				pst.close();
				pst = conn.prepareStatement(INSERT_IMAGE_PATH);
				pst.setInt(1, guestId);
				pst.setString(2, filePath);
				count  = pst.executeUpdate();
				
				if(count > 0) {
					result = "SAVE_SUCCESS";
					conn.commit();
				}
			}
		} catch (Exception e) {
			conn.rollback();
			result = "SAVE_FAILURE";
			System.out.println(e);
			Log.error("###Error at EnrollmentDAO[insertEnrollmentData]", e);
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
	
	private static String uploadImage(EnrollmentForm enrollmentForm, HttpServletRequest request) throws Exception 
	{
		String filePath = null;
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			
			//convert image
			HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(request);
            InputStream is = wrappedRequest.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            String imageString = enrollmentForm.getImageData();
            imageString = imageString.substring("data:image/jpeg;base64,".length());
            byte[] contentData = imageString.getBytes();
            byte[] decodedData = Base64.decodeBase64(contentData);
            String imgName = enrollmentForm.getName() + '-' + enrollmentForm.getIdentificationNo() + ".jpeg";
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
			String urlAppender = calendar.get(Calendar.YEAR) + "/" + dateFormat.format(calendar.getTime()) + "/";
			filePath = bundle.getString("IMAGE_STORE") + "/" + urlAppender + imgName;
			
			//check if the directory structure exists or not, if not then create the directory structure
			File file = new File(urlAppender);
			if(!file.exists())
			{
			  new File(bundle.getString("IMAGE_STORE") + "/" + urlAppender).mkdirs();
			}
			
			File newFile = new File(filePath);
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(newFile);
			fileOutputStream.write(decodedData);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			filePath = null;
			Log.error("######## Error in EnrollmentDAO[uploadImage] :"+e);
			throw new Exception(e);
		}
		
		return filePath;
	}
	
	private static final String INSERT_ENROLLMENT_DATA = ""
			+ "INSERT INTO `guests` "
			+ "            (`identification_no`, "
			+ "             `identification_type_id`, "
			+ "             `nationality_id`, "
			+ "             `guest_name`, "
			+ "             `gender`, "
			+ "             `dob`, "
			+ "             `present_address`, "
			+ "             `contact_no`, "
			+ "             `residing_across_border` "
			+ "             ) "
			+ "VALUES (?,(SELECT `identification_type_id` FROM `identificationtypes` WHERE `identification_type_desc`='BARCODE_NUMBER'),?,?,?,?,?,?,?);";
	
	private static final String INSERT_IMAGE_PATH = "INSERT INTO `guest_biometric` (`guest_id`,`image_path`) VALUES (?,?)";
	
}
