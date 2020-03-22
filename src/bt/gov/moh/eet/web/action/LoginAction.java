package bt.gov.moh.eet.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.framework.common.Log;

public class LoginAction extends Action
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String forwarder = null;
		HttpSession session = request.getSession(true);
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("");
			
			if(vo != null && vo.getRole() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {	
				
				String q = request.getParameter("q");
				
				if(q != null){
					forwarder = "SUCCESS";
				} else {
					forwarder = "FAILURE";
					request.setAttribute("FAILURE", forwarder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			Log.error("###Login error ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			forwarder = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(forwarder);
	}
}