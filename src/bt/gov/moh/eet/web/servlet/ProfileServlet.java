package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bt.gov.moh.eet.dao.UserDAO;
import bt.gov.moh.framework.common.Log;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profileservlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
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
		String q = request.getParameter("q");
		
		if(q == null)
			return;
		else if(q.equals("validateCurrentPassword"))
			validateCurrentPassword(request, response);
		else if(q.equals("updateUserPassword"))
			updateUserPassword(request, response);
	}
	
	protected void validateCurrentPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isPasswordValid = true;
		String cpassword = request.getParameter("cpassword");
		String uid = request.getParameter("userId");
		
		try {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			
			boolean flag = UserDAO.getInstance().checkCurrentPasswordMatch(uid, cpassword);
			
			if(flag)
				isPasswordValid = false;
			
			Gson gson = new Gson();
			String json = null;
			
			json = gson.toJson(!isPasswordValid);
			
			out.print(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("Error occurred while checking current password");
		}
	}
	
	protected void updateUserPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String passwordUpdateStatus = "FAILURE";
		String uid = request.getParameter("userId");
		String npassword = request.getParameter("npassword");
		
		try {
			PrintWriter out = response.getWriter();
			
			String flag = UserDAO.getInstance().updateUserPassword(uid, npassword);
			if(flag.equals("UPDATED")){
				passwordUpdateStatus = "SUCCESS";
			}
			
			out.print(passwordUpdateStatus);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("Error occurred while updating user password");
		}
	}
}
