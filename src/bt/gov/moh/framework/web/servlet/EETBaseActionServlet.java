package bt.gov.moh.framework.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionServlet;

import bt.gov.moh.eet.dao.LoginDAO;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.framework.common.Log;

public class EETBaseActionServlet extends ActionServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
	{
		HttpSession session = request.getSession();
		String uid = null, password=null;
		
		if(request.getUserPrincipal()!=null && request.getUserPrincipal().getName()!=null) {
		    uid = request.getUserPrincipal().getName();
		}
		else if(request.getAttribute("UID") != null) {
			uid =(String) request.getAttribute("UID");
		}
		
		password = (String)request.getAttribute("PASS");
		
		try
		{
			if(uid != null)
			{
				UserDetailsVO vo = new UserDetailsVO();
				vo = LoginDAO.getInstance().populateUserDetails(uid,password);
				
				if(vo != null) {
					session.setAttribute("userdetails", vo);
					session.setAttribute("SESSIONID", session.getId());
				}
				else {
					session.setAttribute("userdetails", null);
					session.setAttribute("SESSIONID", null);
				}
			}
		}
		catch(Exception e){
			Log.error("Error at EETBaseActionServlet[process]", e);
			e.printStackTrace();
		}
		
		super.process(request, response);
	}
}
