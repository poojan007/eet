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

public class RedirectionAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		
		try {
			HttpSession session = request.getSession();
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			String param = request.getParameter("q");
			
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {
				if(param.equalsIgnoreCase("MANAGE_USERS")) {
					//pull list of user here
					actionForward = param;
				}
			}
			else {
				actionForward = "GLOBAL_REDIRECT_LOGIN";
				request.setAttribute("FAILURE", "UNAUTHORIZED");
			}
		} catch (Exception e) {
			Log.error("###Redirection Error at RequestForwarderAction[execute] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
}
