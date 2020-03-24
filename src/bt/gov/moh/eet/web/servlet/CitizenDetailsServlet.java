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

import com.squareup.okhttp.OkHttpClient;

import bt.gov.moh.eet.dto.CitizenDTO;

/**
 * Servlet implementation class CitizenDetailsServlet
 */
@WebServlet("/getCitizenDetails")
public class CitizenDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CitizenDetailsServlet() {
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
			String cidNo = request.getParameter("cidNo");
			
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(bundle.getString("getCitizenDetailsAPI.endPointURL"));
			
			/*
			 * HttpSession session = request.getSession(); TokenDTO tokendto = (TokenDTO)
			 * session.getAttribute("TOKEN");
			 */
			apiClient.setAccessToken("1c57772f-1829-3576-b9f0-d722083fb65c");
			DefaultApi api = new DefaultApi(apiClient);
			CitizenDetailsResponse citizenDetailsResponse = api.citizendetailsCidGet(cidNo);
			CitizendetailsObj citizendetailsObj = null;
			
			if(citizenDetailsResponse.getCitizenDetailsResponse().getCitizenDetail()!=null && !citizenDetailsResponse.getCitizenDetailsResponse().getCitizenDetail().isEmpty() ){
				citizendetailsObj = citizenDetailsResponse.getCitizenDetailsResponse().getCitizenDetail().get(0);
				
				String firstName = citizendetailsObj.getFirstName();
				String middleName = citizendetailsObj.getMiddleName();
				String lastName = citizendetailsObj.getLastName();
				String dob = citizendetailsObj.getDob();
				String gender = citizendetailsObj.getGender();
				
				CitizenDTO dto = new CitizenDTO();
				
				if(middleName.equals("null")){
					middleName = "";
				}
				
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
					buffer.append("<name>"+dto.getCitizenName()+"</name>");
					buffer.append("<gender>"+dto.getGender()+"</gender>");
					buffer.append("<age>"+dto.getAge()+"</age>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			System.out.println("Error at GetCitizenDetails[doAction]"+e);
		}
		
		out.println(buffer);
		out.flush();
	}
}
