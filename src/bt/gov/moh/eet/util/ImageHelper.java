package bt.gov.moh.eet.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import bt.gov.moh.framework.common.Log;

public class ImageHelper {

	private static ImageHelper helper = null;
	
	public static ImageHelper getInstance() {
		if(helper == null)
			helper = new ImageHelper();
		return helper;
	}
	
	public String uploadImage(byte[] imageByte, String idNo, String guestId, String type) throws Exception 
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String filePath = null;
		int imageId = 0;
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			
            String imgName = idNo + ".jpeg";
			
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
			fileOutputStream.write(imageByte);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			conn = ConnectionManager.getConnection();
			pst = conn.prepareStatement(INSERT_IMAGE_PATH, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, guestId);
			pst.setString(2, filePath);
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			while(rs.next()) {
				imageId = rs.getInt(1);
			}
		} catch (Exception e) {
			filePath = null;
			Log.error("######## Error in ImageHelper[uploadImage] :"+e);
			throw new Exception(e);
		} finally {
			ConnectionManager.close(conn, null, pst);
		}
		
		return imageId+"#"+filePath;
	}
	
	private static final String INSERT_IMAGE_PATH = "INSERT INTO `guest_biometric` (`guest_id`,`image_path`) VALUES (?,?)";
}
