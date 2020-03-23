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
import org.wso2.client.model.COVID_ImmigrantDetailsAPI.ImmigrantOBJ;
import org.wso2.client.model.COVID_ImmigrantDetailsAPI.ImmigrantResponse;
import com.squareup.okhttp.OkHttpClient;

/**
 * Servlet implementation class ImmigrationDetailsServlet
 */
@WebServlet("/getImmigrationDetails")
public class ImmigrationDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImmigrationDetailsServlet() {
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
			String workPermitNo = request.getParameter("workPermitNo");
			
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(bundle.getString("getImmigrationDetailsAPI.endPointURL"));
			
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
		
		out.println(buffer);
		out.flush();
	}
}
