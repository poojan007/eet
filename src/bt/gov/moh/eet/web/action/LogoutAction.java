package bt.gov.moh.eet.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.framework.common.Log;

public class LogoutAction extends Action
{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String actionForward = null;
		try {
			
			HttpSession userSession = request.getSession(false);
			if(userSession != null)
			{
				synchronized(userSession)
				{
					userSession.removeAttribute("");
					userSession.invalidate();
					request.setAttribute("message", "LOGOUT_SUCCESS");
					actionForward = "success";
				}
			}
		} catch (Exception e) {
			Log.error("###Error at LogoutAction[execute]", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
	
}
