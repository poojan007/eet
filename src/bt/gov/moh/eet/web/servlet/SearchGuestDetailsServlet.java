package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bt.gov.moh.eet.dao.GuestLogDAO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.framework.common.Log;

/**
 * Servlet implementation class GetGuestDetails
 */
@WebServlet("/searchGuestDetails")
public class SearchGuestDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchGuestDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();

		try {
			String mobileNo = request.getParameter("mobileNo");
			String type = request.getParameter("type");

			GuestLogDTO dto = GuestLogDAO.getInstance().getGuestDetails(mobileNo, "", type);

			if (null != dto.getGuestId()) {
				buffer.append("<xml-response>");
					buffer.append("<guestId>" + dto.getGuestId() + "</guestId>");
					buffer.append("<cidNo>" +dto.getIdentificationNo()+ "</cidNo>");
					buffer.append("<name>" + dto.getGuestName() + "</name>");
					buffer.append("<gender>" + dto.getGender() + "</gender>");
					buffer.append("<dob>" + dto.getDob() + "</dob>");
					buffer.append("<age>" + dto.getAge() + "</age>");
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
				buffer.append("<xml-response>");
					buffer.append("<guestId></guestId>");
					buffer.append("<cidNo></cidNo>");
					buffer.append("<name></name>");
					buffer.append("<gender></gender>");
					buffer.append("<dob></dob>");
					buffer.append("<contactno></contactno>");
					buffer.append("<nationality></nationality>");
					buffer.append("<presentAddress></presentAddress>");
					buffer.append("<residentflag></residentflag>");
					buffer.append("<imagepath></imagepath>");
					buffer.append("<data-type>SYSTEM</data-type>");
				buffer.append("</xml-response>");
			}
		} catch (Exception e) {
			Log.error("Error at GuestDetailsServlet[doAction]" + e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
