package bt.gov.moh.eet.util;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import bt.gov.moh.eet.vo.SMSModelVO;
import bt.gov.moh.framework.common.Log;

/**
 * The Class SMSUtil.
 */
public final class SMSUtil implements Runnable {

	/** The Constant SMS_PROPERTIES_FILE. */
	public static final String SMS_PROPERTIES_FILE = "sms";

	public static final String SMS_ENCONDING_TYPE = "UTF-8";
	public static final String URL_MIDDLE_PART = "&msg=";

	/** The SMS object. */
	private bt.gov.moh.eet.vo.SMSModelVO smsObject = null;

	/**
	 * This method retrieves the SMS template from DB for a given module name
	 * and populates the data from the business object.
	 * 
	 * @param SMSObj
	 *            the SMS obj
	 * @param conn
	 *            the conn
	 * @throws SystemException
	 *             the system exception
	 * @author prasanta
	 */

	public static void prepareSMSObject(SMSModelVO SMSObj, Connection conn)
			throws Exception {
		Log.info("Inside SMSUtil::prepareSMSObject");
		try 
		{
			getMailTemplate(SMSObj, conn);
			String SMSBody = SMSObj.getSmsContent();
			if (SMSBody != null && !SMSBody.trim().equals("")) 
			{
//				SMSBody = SMSBody.replaceAll("#GATE_NAME#", SMSObj.getGateName());
//				SMSBody = SMSBody.replaceAll("#TRANSACTION_TIME#", SMSObj.getTransactionTime());
				SMSBody = SMSBody.replaceAll("#GUEST_ID#", SMSObj.getGuestId());
				SMSObj.setSmsContent(SMSBody);
			}

		} 
		catch (Exception e) 
		{
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Gets the given property from the resource bundle.
	 * 
	 * @param propsFile
	 *            the props file
	 * @param propertyName
	 *            the property name
	 * @return String the property
	 */
	public static String getProperty(String propsFile, String propertyName) {
		String value = "";
		ResourceBundle res = null;
		try {
			res = ResourceBundle.getBundle(propsFile);
			value = res.getString(propertyName);
		} catch (Exception e) {
			Log.fatal("could not create local RB:" + e.toString());
		}
		return value;
	}

	/**
	 * This method loads the properties files.
	 * 
	 * @param propsFile
	 *            the props file
	 * @return the properties
	 * @throws SystemException
	 *             the system exception
	 * @author prasanta
	 */

	public static Properties loadPropertyFile(String propsFile)
			throws Exception {
		Properties props = null;
		try {
			if (propsFile != null) {
				props = new Properties();
				FileInputStream fis = new java.io.FileInputStream(
						new java.io.File(propsFile));
				props.load(fis);
				Log.info(propsFile + ":" + props);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return props;
	}

	/**
	 * This method returns the property value for a key.
	 * 
	 * @param props
	 *            the props
	 * @param key
	 *            the key
	 * @return the string
	 * @author prasanta
	 */

	public static String readProperty(Properties props, String key) {
		String value = null;
		value = props.getProperty(key, "");
		return value;
	}

	/**
	 * This method loads local properties file of the application in the
	 * context.
	 * 
	 * @return the properties
	 * @author prasanta
	 */

	public static Properties loadLocalPropsFile() {
		Properties localprops = null;

		try {
			localprops = loadPropertyFile(SMS_PROPERTIES_FILE);
			Log.info("loaded local props:" + SMS_PROPERTIES_FILE);
		} catch (Exception fnex2) {
			Log.fatal("could not find file:" + fnex2.toString());
		}
		return localprops;
	}

	/**
	 * This method loads the Resource Bundle of the app.
	 * 
	 * @return the resource bundle
	 * @author prasanta
	 */

	public static ResourceBundle loadLocalRBFile() {
		ResourceBundle localRB = null;

		try {
			localRB = ResourceBundle.getBundle(SMS_PROPERTIES_FILE);
			Log.info("loaded local loadLocalRBFile:" + SMS_PROPERTIES_FILE);
		} catch (MissingResourceException resex2) {
			Log.fatal("could not find file:" + resex2.toString());
		}
		return localRB;
	}

	/**
	 * This method loads Property from the resource bundle.
	 * 
	 * @param localRB
	 *            the local rb
	 * @param propertyName
	 *            the property name
	 * @param defaultValue
	 *            the default value
	 * @return the properties
	 * @author prasanta
	 */

	public static Properties loadProps(ResourceBundle localRB,
			String propertyName, String defaultValue) {
		Properties props = null;
		try {
			props = loadPropertyFile(localRB.getString(propertyName));
			Log.info("loaded props:" + localRB.getString(propertyName));
		} catch (Exception fnex) {
			Log.fatal("could not find file:" + fnex.toString());
		}
		return props;
	}

	/**
	 * This method gets properties from the local Resource Bundle.
	 * 
	 * @param localRB
	 *            the local rb
	 * @return the props from rb
	 * @author prasanta
	 */

	public static Properties getPropsFromRB(ResourceBundle localRB) {
		String key = null;
		Properties localProps = new Properties();
		if (localRB != null) {
			Enumeration<String> localenum = localRB.getKeys();
			while (localenum.hasMoreElements()) {
				key = localenum.nextElement();
				localProps.put(key, localRB.getString(key));
			}
		}
		return localProps;
	}

	/**
	 * Gets a list of strings and returns a string of all elements delimmited by
	 * comma.
	 * 
	 * @param list
	 *            the list
	 * @return String
	 */

	public static String prepareCommaSepStrFromList(List<String> list) {

		java.util.Iterator<String> itr = list.iterator();
		StringBuffer strBuff = new StringBuffer();
		while (itr.hasNext()) {
			strBuff.append(itr.next());
			strBuff.append(",");
		}
		return strBuff.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			sendSms();
		} catch (Exception e) {
			Log.error("At SMSUtil.run(): exception is -> ");
		}

	}

	/**
	 * Send e mail.
	 * 
	 * @throws SystemException
	 *             the system exception
	 */
	public void sendSMS(SMSModelVO smsObj) throws Exception {
		Log.info("Inside SMSUtil::sendSMS");
		Connection conn = null;

		try {
			conn = ConnectionManager.getConnection();
			prepareSMSObject(smsObj, conn);
			smsObject = new SMSModelVO();
			smsObject.setRecipentList(smsObj.getRecipentList());
			smsObject.setSmsContent(smsObj.getSmsContent());
			smsObject.setGateName(smsObj.getGateName());
			smsObject.setTransactionTime(smsObj.getTransactionTime());
			smsObject.setGuestId(smsObj.getGuestId());
			
			Thread thread = new Thread(new SMSUtil(smsObject));
			thread.start();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			ConnectionManager.close(conn);
		}
	}

	/**
	 * Send sms.
	 * 
	 * @throws SystemException
	 *             the system exception
	 */
	private void sendSms() throws Exception {
		boolean result = false;
		String encodedMobileNo = null;
		String encodedSMScontent = null;
		String fullURLStr = null;
		URL url = null;
		HttpURLConnection connection = null;
		String responseMSg = null;
		String smsContent = smsObject.getSmsContent();
		try {
			ResourceBundle localRB = loadLocalRBFile();
			Properties localProps = getPropsFromRB(localRB);

			String smsUrl = localProps.getProperty("sms.url.firstPart");

			for (String mobileNo : smsObject.getRecipentList()) {

				encodedMobileNo = URLEncoder.encode(mobileNo,
						SMS_ENCONDING_TYPE);
				Log.debug("######## SMSUtil[sendSMS] encoded Mobile Number = "
						+ encodedMobileNo);

				encodedSMScontent = URLEncoder.encode(smsContent,
						SMS_ENCONDING_TYPE);
				Log.debug("######## SMSUtil[sendSMS] encoded SMS content = "
						+ encodedSMScontent);

				fullURLStr = smsUrl + encodedMobileNo + URL_MIDDLE_PART
						+ encodedSMScontent;
				url = new URL(fullURLStr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(false);
				connection.setDoInput(true);
				responseMSg = connection.getResponseMessage();
				Log.info("responseMSg::" + responseMSg);

				int code = connection.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					connection.disconnect();
					result = true;
				}
			}
		} catch (Exception e) {
			Log.error("######## Error in SMSUtil[sendSMS] : " + e.toString());
		}
		if (result) {
			Log.info("SMS sent successfully");
		} else {
			Log.fatal("SMS could not be sent");
		}

	}

	/**
	 * Instantiates a new SMS util.
	 */
	public SMSUtil() {

	}

	/**
	 * Instantiates a new SMS util.
	 */
	public SMSUtil(SMSModelVO smsObj) {
		this.smsObject = smsObj;
	}
	
	private static void getMailTemplate(SMSModelVO smsObject, Connection conn)
			throws Exception
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(GET_SMS_TEMPLATES_BY_ID);
			pstmt.setString(1, smsObject.getSmsType());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				smsObject.setSmsContent(rs.getString("sms_body"));				
			}
		} catch (Exception ee) {
			Log.error("##### Error in SMSDAO[getMailTemplate]",ee);
			throw new Exception(ee.getMessage());
		} finally {
			ConnectionManager.close(null, rs, pstmt);
		}

	}

	/** The Constant GET_SMS_TEMPLATES_BY_ID. */
	private static final String GET_SMS_TEMPLATES_BY_ID = new StringBuffer(
			"SELECT `sms_body`,`sms_template_id` ")
			.append(" FROM `smstemplate` ")
			.append(" WHERE `sms_template` =?").toString();
}
