package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.client.api.DCRC_RestCitizenImageAPI.DefaultApi;
import org.wso2.client.model.DCRC_RestCitizenImageAPI.CitizenImageObj;
import org.wso2.client.model.DCRC_RestCitizenImageAPI.CitizenImageResponse;

import com.squareup.okhttp.OkHttpClient;

/**
 * Servlet implementation class CitizenImageServlet
 */
@WebServlet("/getCitizenImage")
public class CitizenImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CitizenImageServlet() {
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
		try {
			response.setContentType("image/png");
			
			String cidNo = request.getParameter("cid");
			ResourceBundle bundle = ResourceBundle.getBundle("eet");
			OkHttpClient httpClient = new OkHttpClient();
			httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
			httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
			
			org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
			apiClient.setHttpClient(httpClient);
			apiClient.setBasePath(bundle.getString("getCitizenImageAPI.endPointURL"));
			
			/*
			 * HttpSession session = request.getSession(); TokenDTO tokendto = (TokenDTO)
			 * session.getAttribute("TOKEN");
			 * apiClient.setAccessToken(tokendto.getAccessToken());
			 */
			apiClient.setAccessToken("1c57772f-1829-3576-b9f0-d722083fb65c");
			DefaultApi api = new DefaultApi(apiClient);
			CitizenImageResponse citizenImageResponse = api.citizenImageCidNoGet(cidNo);
			CitizenImageObj citizenImageObj = null;
			
			if(citizenImageResponse.getCitizenimages().getCitizenimage() != null && !citizenImageResponse.getCitizenimages().getCitizenimage().isEmpty()) {
				citizenImageObj = citizenImageResponse.getCitizenimages().getCitizenimage().get(0);
				String image = citizenImageObj.getImage();
				
				byte[] imageytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);
				ServletOutputStream out = response.getOutputStream();
				out.write(imageytes);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
