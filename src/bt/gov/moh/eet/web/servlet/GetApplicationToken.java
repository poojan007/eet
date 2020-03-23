package bt.gov.moh.eet.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bt.gov.moh.eet.dto.TokenDTO;

/**
 * Servlet implementation class GetApplicationToken
 */
@WebServlet("/getToken")
public class GetApplicationToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String tokenEndpoint = null;
	private static String consumerKey = null;
	private static String consumerSecret = null;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetApplicationToken() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		tokenEndpoint = getServletConfig().getInitParameter("TOKEN_ENDPOINT");
		consumerKey = getServletConfig().getInitParameter("CONSUMER_KEY");
		consumerSecret = getServletConfig().getInitParameter("CONSUMER_SECRET");
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
		try {
			HttpSession session = request.getSession();
			
			if(session.getAttribute("TOKEN") == null) {
				generateToken(request, response);
			} else {
				TokenDTO tokendto = (TokenDTO) session.getAttribute("TOKEN");
				if(System.currentTimeMillis() > Long.parseLong(tokendto.getTokenExpiryTime())) {
					generateToken(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String authStringEnc = Base64.getEncoder().encodeToString((consumerKey+":"+consumerSecret).getBytes());
			HttpPost httppost = new HttpPost(tokenEndpoint);
			httppost.setHeader("Authorization", "Basic " + authStringEnc);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("grant_type", "client_credentials"));
			httppost.setEntity(new UrlEncodedFormEntity(params));

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpresponse = client.execute(httppost);
			
			int statusCode = httpresponse.getStatusLine().getStatusCode();
			if(statusCode == 200){
				BufferedReader rd = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while((line = rd.readLine()) != null){
					result.append(line);
				}
				
				System.out.println("<------------- Data-hub Response : START ----------> ");
				System.out.println("JSON: "+result.toString());
				JsonParser parser = new JsonParser(); 
				JsonObject json = (JsonObject) parser.parse(result.toString());
				
				String accessToken = json.get("access_token").getAsString();
				String tokenExpiryTime = json.get("expires_in").getAsString();
				System.out.println("Parsed Token: "+accessToken);
				System.out.println("Parsed Expiry Time: "+tokenExpiryTime);
				
				HttpSession session = request.getSession();
				TokenDTO dto = new TokenDTO();
				dto.setAccessToken(accessToken);
				dto.setTokenExpiryTime(tokenExpiryTime);
				session.setAttribute("TOKEN", dto);
				System.out.println(" <------------- Data-hub Response : END -----------> ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
