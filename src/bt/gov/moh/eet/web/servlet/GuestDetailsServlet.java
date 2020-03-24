package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.client.api.DCRC_CitizenDetailsAPI.DefaultApi;
import org.wso2.client.model.COVID_ImmigrantDetailsAPI.ImmigrantOBJ;
import org.wso2.client.model.COVID_ImmigrantDetailsAPI.ImmigrantResponse;
import org.wso2.client.model.DCRC_CitizenDetailsAPI.CitizenDetailsResponse;
import org.wso2.client.model.DCRC_CitizenDetailsAPI.CitizendetailsObj;
import org.wso2.client.model.MFA_PassportDetailsAPI.PersonalDetailsResponse;
import org.wso2.client.model.MFA_PassportDetailsAPI.PersonalOBJ;
import org.wso2.client.model.RSTA_LicenseAndVehicleInformationAPI.LicensedetailsbylicensenumberResponse;

import com.squareup.okhttp.OkHttpClient;

import bt.gov.moh.eet.dao.GuestLogDAO;
import bt.gov.moh.eet.dao.PopulateDropDownDAO;
import bt.gov.moh.eet.dto.CitizenDTO;
import bt.gov.moh.eet.dto.DropDownDTO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.framework.common.Log;

/**
 * Servlet implementation class GuestDetailsServlet
 */
@WebServlet("/getGuestDetails")
public class GuestDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doAction(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		
		try {
			String idNo = request.getParameter("idNo");
			String identificationType = request.getParameter("identificationType");
			String entryOrExit = request.getParameter("entryOrExit");
			
			GuestLogDTO dto = GuestLogDAO.getInstance().getGuestDetails(idNo, identificationType, entryOrExit);
			
			if(null != dto.getGuestId()) {
				buffer.append("<xml-response>");
					buffer.append("<guestId>"+dto.getGuestId()+"</guestId>");
					buffer.append("<name>"+dto.getGuestName()+"</name>");
					buffer.append("<gender>"+dto.getGender()+"</gender>");
					buffer.append("<age>"+dto.getAge()+"</age>");
					buffer.append("<contactno>"+dto.getContactNo()+"</contactno>");
					buffer.append("<nationality>"+dto.getNationality()+"</nationality>");
					buffer.append("<presentAddress>"+dto.getPresentAddress()+"</presentAddress>");
				buffer.append("</xml-response>");
			} else {
				getDetailsFromAPI(request, response, idNo, identificationType);
			}
		} catch (Exception e) {
			Log.error("Error at GuestDetailsServlet[doAction]"+e);
		}
		
		out.println(buffer);
		out.flush();
	}
	
	/**
	 * @see HttpServlet#getDetailsFromAPI(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void getDetailsFromAPI(HttpServletRequest request, HttpServletResponse response, String idNo, String identificationType) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		
		try {
			List<DropDownDTO> identificationTypeList = PopulateDropDownDAO.getInstance().getDropDownList("IDENTIFICATION_TYPE_ENDPOINT_LIST", identificationType);
			
			if(identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("CID")) {
				buffer = getCitizenDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName());
			} else if(identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("DRIVING_LICENSE")) {
				buffer = getDrivingLicenseDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName());
			} else if(identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("BARCODE_NUMBER")) {
				buffer = getBarcodeGuestDetails(request, response, idNo);
			} else if(identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("WORK_PERMIT")) {
				buffer = getWorkPermitDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName());
			} else if(identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("PASSPORT")) {
				buffer = getPassportDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName());
			}
		} catch (Exception e) {
			Log.error("Error at GuestDetailsServlet[getDetailsFromAPI]"+e);
		}
		
		out.println(buffer);
		out.flush();
	}
	
	protected StringBuffer getCitizenDetails(HttpServletRequest request, HttpServletResponse response, String cidNo, String endPointUrl) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();
		
		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);
			
			/*
			 * HttpSession session = request.getSession(); TokenDTO tokendto = (TokenDTO)
			 * session.getAttribute("TOKEN");
			 */
			apiClient.setAccessToken("1c57772f-1829-3576-b9f0-d722083fb65c");
			DefaultApi api = new DefaultApi(apiClient);
			CitizenDetailsResponse citizenDetailsResponse = api.citizendetailsCidGet(cidNo);
			System.out.println(citizenDetailsResponse);
			CitizendetailsObj citizendetailsObj = null;
			
			if(citizenDetailsResponse.getCitizenDetailsResponse().getCitizenDetail()!=null && !citizenDetailsResponse.getCitizenDetailsResponse().getCitizenDetail().isEmpty() ){
				citizendetailsObj = citizenDetailsResponse.getCitizenDetailsResponse().getCitizenDetail().get(0);
				
				String firstName = citizendetailsObj.getFirstName();
				String middleName = citizendetailsObj.getMiddleName();
				String lastName = citizendetailsObj.getLastName();
				String dob = citizendetailsObj.getDob();
				String gender = citizendetailsObj.getGender();
				
				CitizenDTO dto = new CitizenDTO();
				
				/*
				 * if(middleName.equals("null")){ middleName = ""; }
				 */
				
				dto.setCitizenName(firstName+" "+middleName+" "+lastName);
				dto.setGender(gender);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Date d = sdf.parse(dob);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int date = c.get(Calendar.DATE);
				LocalDate l1 = LocalDate.of(year, month, date);
				LocalDate now1 = LocalDate.now();
				Period diff1 = Period.between(l1, now1);
				dto.setAge(Integer.toString(diff1.getYears()));
				
				buffer.append("<xml-response>");
					buffer.append("<cid>"+citizendetailsObj.getCid()+"</cid>");
					buffer.append("<name>"+dto.getCitizenName()+"</name>");
					buffer.append("<gender>"+dto.getGender()+"</gender>");
					buffer.append("<age>"+dto.getAge()+"</age>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println("Error at GetCitizenDetails[doAction]"+e);
		}
		
		return buffer;
	}
	
	protected StringBuffer getDrivingLicenseDetails(HttpServletRequest request, HttpServletResponse response, String licenseNo, String endPointUrl) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();
		
		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);
			
			apiClient.setAccessToken("1c57772f-1829-3576-b9f0-d722083fb65c");
			org.wso2.client.api.RSTA_LicenseAndVehicleInformationAPI.DefaultApi rstaapi = new org.wso2.client.api.RSTA_LicenseAndVehicleInformationAPI.DefaultApi(apiClient);
			LicensedetailsbylicensenumberResponse licenseDetails = rstaapi.licensedetailsbylicensenumberLicenseNoGet(licenseNo);
			org.wso2.client.model.RSTA_LicenseAndVehicleInformationAPI.LicensedetailbylicensenumberObj licenseNoObj = null;
			
			if(licenseDetails.getLicenseDetails() != null && !licenseDetails.getLicenseDetails().getLicenseDetail().isEmpty()){
				licenseNoObj = licenseDetails.getLicenseDetails().getLicenseDetail().get(0);
				
				CitizenDTO dto = new CitizenDTO();
				dto.setCitizenName(licenseNoObj.getLicenseHolderName());
				dto.setGender("");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Date d = sdf.parse(licenseNoObj.getDateOfBirth());
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int date = c.get(Calendar.DATE);
				LocalDate l1 = LocalDate.of(year, month, date);
				LocalDate now1 = LocalDate.now();
				Period diff1 = Period.between(l1, now1);
				dto.setAge(Integer.toString(diff1.getYears()));
				
				buffer.append("<xml-response>");
					buffer.append("<cid></cid>");
					buffer.append("<name>"+dto.getCitizenName()+"</name>");
					buffer.append("<gender>"+dto.getGender()+"</gender>");
					buffer.append("<age>"+dto.getAge()+"</age>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println("Error at GetDrivingLicenseDetails[doAction]"+e);
		}
		
		return buffer;
	}
	
	protected StringBuffer getWorkPermitDetails(HttpServletRequest request, HttpServletResponse response, String workPermitNo, String endPointUrl) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();
		
		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);
			
			/*
			 * HttpSession session = request.getSession(); TokenDTO tokendto = (TokenDTO)
			 * session.getAttribute("TOKEN");
			 */
			apiClient.setAccessToken("1c57772f-1829-3576-b9f0-d722083fb65c");
			org.wso2.client.api.COVID_ImmigrantDetailsAPI.DefaultApi api = new org.wso2.client.api.COVID_ImmigrantDetailsAPI.DefaultApi(apiClient);
			
			ImmigrantResponse immigrantResponse = api.immigrantdetailWorkeridGet(workPermitNo);
			ImmigrantOBJ immigrantObj = null;
			
			if(immigrantResponse.getImmigrantdetails().getImmigrantdetail()!=null && !immigrantResponse.getImmigrantdetails().getImmigrantdetail().isEmpty() ){
				immigrantObj = immigrantResponse.getImmigrantdetails().getImmigrantdetail().get(0);
				
				String name = immigrantObj.getName();
				String gender	= immigrantObj.getGender();
				String workLocation = immigrantObj.getWorkLocation();
				String contactNo = immigrantObj.getContactNo();
				
				buffer.append("<xml-response>");
					buffer.append("<name>"+name+"</name>");
					buffer.append("<gender>"+gender+"</gender>");
					buffer.append("<worklocation>"+workLocation+"</worklocation>");
					buffer.append("<contactno>"+contactNo+"</contactno>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println("Error at ImmigrationDetailsServlet[doAction]"+e);
		}
		
		return buffer;
	}
	
	protected StringBuffer getPassportDetails(HttpServletRequest request, HttpServletResponse response, String passportNo, String endPointUrl) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();
		
		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);
			
			/*
			 * HttpSession session = request.getSession(); TokenDTO tokendto = (TokenDTO)
			 * session.getAttribute("TOKEN");
			 */
			apiClient.setAccessToken("1c57772f-1829-3576-b9f0-d722083fb65c");
			org.wso2.client.api.MFA_PassportDetailsAPI.DefaultApi api = new org.wso2.client.api.MFA_PassportDetailsAPI.DefaultApi(apiClient);
			PersonalDetailsResponse passportResponse = api.personaldetailsPassportNoGet(passportNo);
			PersonalOBJ personalObj = null;
			
			if(passportResponse.getPersonalDetails().getPersonalDetail()!=null && !passportResponse.getPersonalDetails().getPersonalDetail().isEmpty() ){
				personalObj = passportResponse.getPersonalDetails().getPersonalDetail().get(0);
				
				String cidNo = personalObj.getCidNo();
				String dob = personalObj.getDob();
				String fullName = personalObj.getFirstName()+" "+personalObj.getMiddleName()+" "+personalObj.getLastName();
				String gender = personalObj.getGender();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Date d = sdf.parse(dob);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int date = c.get(Calendar.DATE);
				LocalDate l1 = LocalDate.of(year, month, date);
				LocalDate now1 = LocalDate.now();
				Period diff1 = Period.between(l1, now1);
				String age = Integer.toString(diff1.getYears());
				
				buffer.append("<xml-response>");
					buffer.append("<cid>"+cidNo+"</cid>");
					buffer.append("<name>"+fullName+"</name>");
					buffer.append("<gender>"+gender+"</gender>");
					buffer.append("<age>"+age+"</age>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println("Error at GetPassportDetails[doAction]"+e);
		}
		
		return buffer;
	}
	
	protected StringBuffer getBarcodeGuestDetails(HttpServletRequest request, HttpServletResponse response, String barcodeNo) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();
		String GET_BARCODE_DETAILS = "select "
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
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
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
			Log.error("###Error at GetBarcodeGuestDetails[doAction]", e);
		}
		
		return buffer;
	}
}