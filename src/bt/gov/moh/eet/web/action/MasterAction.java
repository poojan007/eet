package bt.gov.moh.eet.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import bt.gov.moh.eet.dao.MasterDAO;
import bt.gov.moh.eet.dto.MasterDTO;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.framework.common.Log;

public class MasterAction extends DispatchAction {
	
	public ActionForward addMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		MasterDTO dto = new MasterDTO();
		String actionForward = null;
		HttpSession session = request.getSession(true);
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {	
				String masterType = request.getParameter("masterType");
				String gateName = request.getParameter("gateName");
				String name = request.getParameter("name");
				String pointOne = request.getParameter("pointOne");
				String pointTwo = request.getParameter("pointTwo");
				
				dto.setMasterType(masterType);
				dto.setGateName(gateName);
				dto.setName(name);
				dto.setPointOne(pointOne);
				dto.setPointTwo(pointTwo);
				
				String result = MasterDAO.getInstance().addMaster(dto);
				request.setAttribute("message", result);
				
				actionForward = "GLOBAL_REDIRECT_MESSAGE";
			} else {
				request.setAttribute("message", "UNAUTHORIZED");
				actionForward = "GLOBAL_REDIRECT_LOGIN";
			}
		} catch (Exception e) {
			Log.error("###MasterAction[addMaster] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
	
	public ActionForward updateMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		MasterDTO dto = new MasterDTO();
		String actionForward = null;
		HttpSession session = request.getSession(true);
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {	
				String masterType = request.getParameter("masterType");
				String gateId = request.getParameter("gateId");
				String gateName = request.getParameter("gateName");
				String name = request.getParameter("name");
				String id = request.getParameter("id");
				String pointOne = request.getParameter("pointOne");
				String pointTwo = request.getParameter("pointTwo");
				
				dto.setGateId(gateId);
				dto.setMasterType(masterType);
				dto.setGateName(gateName);
				dto.setId(id);
				dto.setName(name);
				dto.setPointOne(pointOne);
				dto.setPointTwo(pointTwo);
				
				String result = MasterDAO.getInstance().updateMaster(dto);
				request.setAttribute("message", result);
				
				actionForward = "GLOBAL_REDIRECT_MESSAGE";
			} else {
				request.setAttribute("message", "UNAUTHORIZED");
				actionForward = "GLOBAL_REDIRECT_LOGIN";
			}
		} catch (Exception e) {
			Log.error("###MasterAction[updateMaster] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
	
	
	
	public ActionForward deleteMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		MasterDTO dto = new MasterDTO();
		String actionForward = null;
		HttpSession session = request.getSession(true);
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {	
				String masterType = request.getParameter("masterType");
				String id = request.getParameter("id");
				
				dto.setId(id);
				dto.setMasterType(masterType);
				
				String result = MasterDAO.getInstance().deleteMaster(dto);
				request.setAttribute("message", result);
				
				actionForward = "GLOBAL_REDIRECT_MESSAGE";
			} else {
				request.setAttribute("message", "UNAUTHORIZED");
				actionForward = "GLOBAL_REDIRECT_LOGIN";
			}
		} catch (Exception e) {
			Log.error("###MasterAction[deleteMaster] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
}
