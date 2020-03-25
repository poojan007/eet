package bt.gov.moh.eet.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import bt.gov.moh.framework.common.Log;

public class ImageHelper {

	private static ImageHelper helper = null;
	
	public static ImageHelper getInstance() {
		if(helper == null)
			helper = new ImageHelper();
		return helper;
	}
	
	public String uploadImage(byte[] imageByte, String idNo) throws Exception 
	{
		String filePath = null;
		
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
		} catch (Exception e) {
			filePath = null;
			Log.error("######## Error in ImageHelper[uploadImage] :"+e);
			throw new Exception(e);
		}
		
		return filePath;
	}
}
