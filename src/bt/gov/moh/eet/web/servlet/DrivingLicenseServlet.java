package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.client.model.RSTA_LicenseAndVehicleInformationAPI.LicensedetailsbylicensenumberResponse;

import com.squareup.okhttp.OkHttpClient;

import bt.gov.moh.eet.dto.CitizenDTO;

/**
 * Servlet implementation class DrivingLicenseServlet
 */
@WebServlet("/DrivingLicenseServlet")
public class DrivingLicenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrivingLicenseServlet() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		
		try {
			String licenseNo = request.getParameter("licenseNo");
			
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(bundle.getString("getRSTADetailsAPI.endPointURL"));
			
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
					buffer.append("<name>"+dto.getCitizenName()+"</name>");
					buffer.append("<gender>"+dto.getGender()+"</gender>");
					buffer.append("<age>"+dto.getAge()+"</age>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		out.println(buffer);
		out.flush();
	}
}
