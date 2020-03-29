package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.wso2.client.model.COVID_ImmigrantDetailsAPI.ImmigrantOBJ;
import org.wso2.client.model.COVID_ImmigrantDetailsAPI.ImmigrantResponse;
import org.wso2.client.model.DCRC_IndividualCitizenDetailAPI.CitizenOBJ;
import org.wso2.client.model.DCRC_IndividualCitizenDetailAPI.CitizenResponse;
import org.wso2.client.model.DCRC_RestCitizenImageAPI.CitizenImageObj;
import org.wso2.client.model.DCRC_RestCitizenImageAPI.CitizenImageResponse;
import org.wso2.client.model.MFA_PassportDetailsAPI.PersonalDetailsResponse;
import org.wso2.client.model.MFA_PassportDetailsAPI.PersonalOBJ;
import org.wso2.client.model.RSTA_LicenseAndVehicleInformationAPI.LicensedetailsbylicensenumberResponse;
import com.squareup.okhttp.OkHttpClient;
import bt.gov.moh.eet.dao.GuestLogDAO;
import bt.gov.moh.eet.dao.PopulateDropDownDAO;
import bt.gov.moh.eet.dto.CitizenDTO;
import bt.gov.moh.eet.dto.DropDownDTO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.dto.TokenDTO;
import bt.gov.moh.eet.util.ImageHelper;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doAction(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();

		try {
			String idNo = request.getParameter("idNo");
			String identificationType = request.getParameter("identificationType");
			String entryOrExit = request.getParameter("entryOrExit");

			GuestLogDTO dto = GuestLogDAO.getInstance().getGuestDetails(idNo, identificationType, entryOrExit);

			if (null != dto.getGuestId()) {
				buffer.append("<xml-response>");
				buffer.append("<guestId>" + dto.getGuestId() + "</guestId>");
				buffer.append("<cidNo></cidNo>");
				buffer.append("<name>" + dto.getGuestName() + "</name>");
				buffer.append("<gender>" + dto.getGender() + "</gender>");
				buffer.append("<dob>" + dto.getDob() + "</dob>");
				buffer.append("<contactno>" + dto.getContactNo() + "</contactno>");
				buffer.append("<nationality>" + dto.getNationality() + "</nationality>");
				buffer.append("<presentAddress>" + dto.getPresentAddress() + "</presentAddress>");
				buffer.append("<residentflag>" + dto.getResidenceFlag() + "</residentflag>");
				buffer.append("<imagepath>" + dto.getImagePath() + "</imagepath>");
				buffer.append("<data-type>SYSTEM</data-type>");
				buffer.append("</xml-response>");

				out.println(buffer);
				out.flush();
			} else {
				out.println(getDetailsFromAPI(request, response, idNo, identificationType, dto.getGuestId()));
				out.flush();
			}
		} catch (Exception e) {
			Log.error("Error at GuestDetailsServlet[doAction]" + e);
		}
	}

	/**
	 * @see HttpServlet#getDetailsFromAPI(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected StringBuffer getDetailsFromAPI(HttpServletRequest request, HttpServletResponse response, String idNo,
			String identificationType, String guestId) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();

		try {
			List<DropDownDTO> identificationTypeList = PopulateDropDownDAO.getInstance().getDropDownList("IDENTIFICATION_TYPE_ENDPOINT_LIST", identificationType);
			
			HttpSession session = request.getSession();
			TokenDTO dto = (TokenDTO)session.getAttribute("TOKEN");
			String accessToken = "7dd8dc5b-a021-36f8-a8bb-99753c9c5cdb";//dto.getAccessToken();
			
			if (identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("CID")) {
				request.setAttribute("cidNo", idNo);
				buffer = getCitizenDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName(), accessToken, guestId);
			} else if (identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("DRIVING_LICENSE")) {
				request.setAttribute("licenseNo", idNo);
				buffer = getDrivingLicenseDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName(), accessToken, guestId);
			} else if (identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("WORK_PERMIT")) {
				request.setAttribute("workPermitNo", idNo);
				buffer = getWorkPermitDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName(), accessToken);
			} else if (identificationTypeList.get(0).getHeaderId().equalsIgnoreCase("PASSPORT")) {
				request.setAttribute("passportNo", idNo);
				buffer = getPassportDetails(request, response, idNo, identificationTypeList.get(0).getHeaderName(), accessToken, guestId);
			}
		} catch (Exception e) {
			Log.error("Error at GuestDetailsServlet[getDetailsFromAPI]" + e);
		}

		return buffer;
	}

	protected StringBuffer getCitizenDetails(HttpServletRequest request, HttpServletResponse response, String cidNo, String endPointUrl, String accessToken, String guestId) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();

		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);

			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);

			apiClient.setAccessToken(accessToken);
			org.wso2.client.api.DCRC_IndividualCitizenDetailAPI.DefaultApi api = new org.wso2.client.api.DCRC_IndividualCitizenDetailAPI.DefaultApi(apiClient);
			CitizenResponse citizenDetailsResponse = api.citizendetailCidGet(cidNo);
			CitizenOBJ citizendetailsObj = null;

			if (citizenDetailsResponse.getCitizendetails().getCitizendetail() != null
					&& !citizenDetailsResponse.getCitizendetails().getCitizendetail().isEmpty()) {
				citizendetailsObj = citizenDetailsResponse.getCitizendetails().getCitizendetail().get(0);

				String firstName = citizendetailsObj.getFirstName();
				String middleName = citizendetailsObj.getMiddlename();
				String lastName = citizendetailsObj.getLastName();
				String dob = citizendetailsObj.getDob();
				String gender = citizendetailsObj.getGender();

				CitizenDTO dto = new CitizenDTO();
				dto.setCitizenName(firstName + " " + middleName + " " + lastName);
				dto.setGender(gender);
				dto.setDob(dob);

				buffer.append("<xml-response>");
				buffer.append("<guestId></guestId>");
				buffer.append("<cidNo>" + citizendetailsObj.getCidNumber() + "</cidNo>");
				buffer.append("<name>" + dto.getCitizenName() + "</name>");
				buffer.append("<gender>" + dto.getGender() + "</gender>");
				buffer.append("<dob>" + dto.getDob() + "</dob>");
				buffer.append("<contactno></contactno>");
				buffer.append("<nationality></nationality>");
				buffer.append("<presentAddress></presentAddress>");
				buffer.append("<residentflag></residentflag>");
				buffer.append("<imagepath>" + getCitizenImage(request, response, cidNo, accessToken, guestId) + "</imagepath>");
				buffer.append("<data-type>CITIZEN_API</data-type>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Error at GetCitizenDetails[doAction]" + e);
		}

		return buffer;
	}

	protected StringBuffer getDrivingLicenseDetails(HttpServletRequest request, HttpServletResponse response, String licenseNo, String endPointUrl, String accessToken, String guestId) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();

		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);

			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);

			apiClient.setAccessToken(accessToken);
			org.wso2.client.api.RSTA_LicenseAndVehicleInformationAPI.DefaultApi rstaapi = new org.wso2.client.api.RSTA_LicenseAndVehicleInformationAPI.DefaultApi(apiClient);
			LicensedetailsbylicensenumberResponse licenseDetails = rstaapi
					.licensedetailsbylicensenumberLicenseNoGet(licenseNo);
			org.wso2.client.model.RSTA_LicenseAndVehicleInformationAPI.LicensedetailbylicensenumberObj licenseNoObj = null;

			if (licenseDetails.getLicenseDetails() != null
					&& !licenseDetails.getLicenseDetails().getLicenseDetail().isEmpty()) {
				licenseNoObj = licenseDetails.getLicenseDetails().getLicenseDetail().get(0);

				buffer.append("<xml-response>");
				buffer.append("<guestId></guestId>");
				buffer.append("<cidNo>" + licenseNoObj.getCidNumber() + "</cidNo>");
				buffer.append("<name>" + licenseNoObj.getLicenseHolderName() + "</name>");
				buffer.append("<gender></gender>");
				buffer.append("<dob>" + licenseNoObj.getDateOfBirth() + "</dob>");
				buffer.append("<contactno></contactno>");
				buffer.append("<nationality></nationality>");
				buffer.append("<presentAddress></presentAddress>");
				buffer.append("<residentflag></residentflag>");
				buffer.append("<imagepath>" + getCitizenImage(request, response, licenseNoObj.getCidNumber(), accessToken, guestId)
						+ "</imagepath>");
				buffer.append("<data-type>RSTA_API</data-type>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Error at GetDrivingLicenseDetails[doAction]" + e);
		}

		return buffer;
	}

	protected StringBuffer getWorkPermitDetails(HttpServletRequest request, HttpServletResponse response, String workPermitNo, String endPointUrl, String accessToken) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();

		try {
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);

			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(endPointUrl);

			apiClient.setAccessToken(accessToken);
			org.wso2.client.api.COVID_ImmigrantDetailsAPI.DefaultApi api = new org.wso2.client.api.COVID_ImmigrantDetailsAPI.DefaultApi(apiClient);

			ImmigrantResponse immigrantResponse = api.immigrantdetailWorkeridGet(workPermitNo);
			ImmigrantOBJ immigrantObj = null;

			if (immigrantResponse.getImmigrantdetails().getImmigrantdetail() != null
					&& !immigrantResponse.getImmigrantdetails().getImmigrantdetail().isEmpty()) {
				immigrantObj = immigrantResponse.getImmigrantdetails().getImmigrantdetail().get(0);

				String gender = "";
				if (immigrantObj.getGender().equalsIgnoreCase("MALE"))
					gender = "M";
				else
					gender = "F";

				buffer.append("<xml-response>");
				buffer.append("<guestId></guestId>");
				buffer.append("<cidNo></cidNo>");
				buffer.append("<name>" + immigrantObj.getName() + "</name>");
				buffer.append("<gender>" + gender + "</gender>");
				buffer.append("<dob></dob>");
				buffer.append("<contactno></contactno>");
				buffer.append("<nationality>" +immigrantObj.getNationality()+ "</nationality>");
				buffer.append("<presentAddress>" + immigrantObj.getWorkLocation() + "</presentAddress>");
				buffer.append("<residentflag></residentflag>");
				buffer.append("<imagepath></imagepath>");
				buffer.append("<data-type>IMMIGRATION_API</data-type>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Error at ImmigrationDetailsServlet[doAction]" + e);
		}

		return buffer;
	}

	protected StringBuffer getPassportDetails(HttpServletRequest request, HttpServletResponse response, String passportNo, String endPointUrl, String accessToken, String guestId) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();
		
		try {
			  OkHttpClient httpClient = new OkHttpClient();
			  httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			  httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			  
			  org.wso2.client.api.ApiClient apiClient = new
			  org.wso2.client.api.ApiClient(); apiClient.setHttpClient(httpClient);
			  apiClient.setBasePath(endPointUrl);
			  
			  apiClient.setAccessToken(accessToken);
			  org.wso2.client.api.MFA_PassportDetailsAPI.DefaultApi api = new
			  org.wso2.client.api.MFA_PassportDetailsAPI.DefaultApi(apiClient);
			  PersonalDetailsResponse passportResponse = api.personaldetailsPassportNoGet(passportNo);
			  PersonalOBJ personalObj = null;
			  
			  if(passportResponse.getPersonalDetails().getPersonalDetail()!=null && !passportResponse.getPersonalDetails().getPersonalDetail().isEmpty() ){
				  
				  System.out.println(passportResponse.getPersonalDetails());
				  System.out.println(passportResponse.getPersonalDetails().getPersonalDetail());
				  System.out.println(passportResponse.getPersonalDetails().getPersonalDetail().get(0));
				  
				  personalObj = passportResponse.getPersonalDetails().getPersonalDetail().get(0);
			  
				  String cidNo = personalObj.getCidNo(); 
				  String dob = personalObj.getDob();
				  String fullName = personalObj.getFirstName()+" "+personalObj.getMiddleName()+" "+personalObj.getLastName(); 
				  String gender = personalObj.getGender();
				  
				  buffer.append("<xml-response>"); 
				  	  buffer.append("<guestId></guestId>");
					  buffer.append("<cidNo>"+cidNo+"</cidNo>");
					  buffer.append("<name>"+fullName+"</name>");
					  buffer.append("<gender>"+gender+"</gender>");
					  buffer.append("<dob>"+dob+"</dob>");
					  buffer.append("<contactno></contactno>");
					  buffer.append("<nationality></nationality>");
					  buffer.append("<presentAddress></presentAddress>");
					  buffer.append("<residentflag></residentflag>");
					  buffer.append("<imagepath>"+getCitizenImage(request, response, cidNo, accessToken, guestId)+"</imagepath>"); 
					  buffer.append("<data-type>PASSPORT_API</data-type>");
				  buffer.append("</xml-response>"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Error at GetPassportDetails[doAction]" + e);
		}

		return buffer;
	}

	protected String getCitizenImage(HttpServletRequest request, HttpServletResponse response, String cidNo, String accessTokens, String guestId) throws ServletException, IOException {
		String imagePath = null;

		try {
			HttpSession session = request.getSession();
			TokenDTO dto = (TokenDTO)session.getAttribute("TOKEN");
			String accessToken = dto.getAccessToken();
			
			ResourceBundle bundle = ResourceBundle.getBundle("eet");

			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);

			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(bundle.getString("getCitizenImageAPI.endPointURL"));

			apiClient.setAccessToken(accessToken);
			org.wso2.client.api.DCRC_RestCitizenImageAPI.DefaultApi api = new org.wso2.client.api.DCRC_RestCitizenImageAPI.DefaultApi(apiClient);
			CitizenImageResponse citizenImageResponse = api.citizenImageCidNoGet(cidNo);
			CitizenImageObj citizenImageObj = null;

			if (citizenImageResponse.getCitizenimages().getCitizenimage() != null
					&& !citizenImageResponse.getCitizenimages().getCitizenimage().isEmpty()) {
				citizenImageObj = citizenImageResponse.getCitizenimages().getCitizenimage().get(0);
				String image = citizenImageObj.getImage();
				byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);
				
				imagePath = ImageHelper.getInstance().uploadImage(imageByte, cidNo, guestId, "API_CALL");
			}
		} catch (Exception e) {
			Log.error("###Error at getCitizenImage[doAction]", e);
		}

		return imagePath;
	}
}
