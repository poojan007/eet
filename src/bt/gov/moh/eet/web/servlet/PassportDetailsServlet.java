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

import org.wso2.client.api.DCRC_CitizenDetailsAPI.DefaultApi;
import org.wso2.client.model.DCRC_CitizenDetailsAPI.CitizenDetailsResponse;
import org.wso2.client.model.DCRC_CitizenDetailsAPI.CitizendetailsObj;
import org.wso2.client.model.MFA_PassportDetailsAPI.PassportDetailsResponse;
import org.wso2.client.model.MFA_PassportDetailsAPI.PersonalDetailsResponse;
import org.wso2.client.model.MFA_PassportDetailsAPI.PersonalOBJ;

import com.squareup.okhttp.OkHttpClient;

import bt.gov.moh.eet.dto.CitizenDTO;

/**
 * Servlet implementation class PassportDetailsServlet
 */
@WebServlet("/getPassportDetails")
public class PassportDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassportDetailsServlet() {
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
			String passportNo = request.getParameter("passportNo");
			
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(bundle.getString("getPassportDetailsAPI.endPointURL"));
			
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
			System.out.println("Error at GetCitizenDetails[doAction]"+e);
		}
		
		out.println(buffer);
		out.flush();
	}
}
