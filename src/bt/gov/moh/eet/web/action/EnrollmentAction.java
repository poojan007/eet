package bt.gov.moh.eet.web.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.dao.EnrollmentDAO;
import bt.gov.moh.eet.web.actionform.EnrollmentForm;

public class EnrollmentAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		EnrollmentForm enrollmentForm = (EnrollmentForm) form;
		String result = "";
		try {
			result = EnrollmentDAO.insertEnrollmentData(enrollmentForm, request);
			request.setAttribute("message", "SAVE_SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward("success");
		
	}

}
