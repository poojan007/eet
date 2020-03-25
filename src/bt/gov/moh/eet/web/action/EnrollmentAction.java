package bt.gov.moh.eet.web.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.dao.EnrollmentDAO;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.eet.web.actionform.EnrollmentForm;
import bt.gov.moh.framework.common.Log;

public class EnrollmentAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		HttpSession session = request.getSession(true);
		EnrollmentForm enrollmentForm = (EnrollmentForm) form;
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {	
				String result = EnrollmentDAO.insertEnrollmentData(enrollmentForm, request);
				request.setAttribute("message", result);
				actionForward = "GLOBAL_REDIRECT_MESSAGE";
			} else {
				request.setAttribute("message", "UNAUTHORIZED");
				actionForward = "GLOBAL_REDIRECT_LOGIN";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("###EnrollmentAction[execute] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";	
		}
		
		return mapping.findForward(actionForward);
	}

}
