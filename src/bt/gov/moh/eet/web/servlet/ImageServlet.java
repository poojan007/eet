package bt.gov.moh.eet.web.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/getImage")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	/**
	 * @see HttpServlet#processRequest(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String imageURL;
		response.setContentType("image/png");
		imageURL = request.getParameter("url");
		
		if(imageURL == null || imageURL.equals(""))
			return;
		
		try 
		{
			byte[] image = getImage(imageURL);
			ServletOutputStream out = response.getOutputStream();
			out.write(image);
			out.flush();
			out.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	private byte[] getImage(String imageURL) throws IOException
	{
		FileInputStream fileInputStream = new FileInputStream(imageURL);
        return IOUtils.toByteArray(fileInputStream);
	}

}
