package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bt.gov.moh.eet.dao.GuestLogDAO;
import bt.gov.moh.eet.dto.NotificationDTO;
import bt.gov.moh.framework.common.Log;

/**
 * Servlet implementation class SMSServlet
 */
@WebServlet("/sms")
public class SMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SMSServlet() {
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
		try {
			String keyword = request.getParameter("keyword");
			String mobileNo = request.getParameter("mobileNo");
			String gateCode = request.getParameter("gateCode");
			
			NotificationDTO dto = GuestLogDAO.getInstance().guestLog(keyword, mobileNo, gateCode);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Error at SMSServlet[doAction]"+e);
		}
	}
}
