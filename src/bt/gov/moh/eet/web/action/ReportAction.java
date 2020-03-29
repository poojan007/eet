package bt.gov.moh.eet.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.dao.ReportDAO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.framework.common.Log;

public class ReportAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		HttpSession session = request.getSession(true);
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String type = request.getParameter("type");
				
				List<GuestLogDTO> reportList = ReportDAO.getInstance().getReportList(startDate, endDate, type, vo);
				request.setAttribute("reportList", reportList);
				request.setAttribute("type", type);
				
				actionForward = "report";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("###ReportAction error ----> ", e);
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
}
