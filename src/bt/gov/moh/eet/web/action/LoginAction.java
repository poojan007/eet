package bt.gov.moh.eet.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.dao.LoginDAO;
import bt.gov.moh.eet.dao.UserDAO;
import bt.gov.moh.eet.dto.StatisticDTO;
import bt.gov.moh.eet.dto.UserDTO;
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
			String param = request.getParameter("q");
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {
				StatisticDTO dto = LoginDAO.getInstance().getDashboardStatistics(vo);
				request.setAttribute("stats", dto);
				forwarder = "success";
			} else if(param.equals("dashboard") && vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")){
				StatisticDTO dto = LoginDAO.getInstance().getDashboardStatistics(vo);
				request.setAttribute("stats", dto);
				forwarder = "success";
			} else {
				forwarder = "failure";
				request.setAttribute("FAILURE", forwarder);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("###Login error ----> ", e);
			request.setAttribute("FAILURE", "failure");
			forwarder = "GLOBAL_REDIRECT_LOGIN";
		}
		
		return mapping.findForward(forwarder);
	}
}