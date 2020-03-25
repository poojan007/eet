package bt.gov.moh.eet.web.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import bt.gov.moh.eet.dao.GuestLogDAO;
import bt.gov.moh.eet.dto.GuestLogDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.FlagGuestHelper;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.eet.web.actionform.GuestLogForm;
import bt.gov.moh.framework.common.Log;

public class GuestLogAction extends DispatchAction {

	public ActionForward guestLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		HttpSession session = request.getSession(true);
		
		try {
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {	
				GuestLogForm guestForm = (GuestLogForm) form;
				GuestLogDTO dto = new GuestLogDTO();
				BeanUtils.copyProperties(dto, guestForm);
				
				String transactionType = request.getParameter("transactionType");
				dto.setTransactionType(transactionType);
				
				Connection conn = ConnectionManager.getConnection();
				conn.setAutoCommit(false);
				
				String result = GuestLogDAO.getInstance().guestLog(dto, vo, conn);
				if(result.equalsIgnoreCase("GUESTLOG_ADD_SUCCESS") || 
						result.equalsIgnoreCase("GUESTLOG_MARK_SUCCESS") ||
							result.equalsIgnoreCase("NO_EXIT_RECORD_FOUND") || 
								result.equalsIgnoreCase("NO_ENTRY_RECORD_FOUND")) {
					conn.commit();
					
					if(result.equalsIgnoreCase("NO_EXIT_RECORD_FOUND")) {
						result = "ALERT_FLAG_TRIGGERED";
						request.setAttribute("FLAG_MESSAGES", "No EXIT record found\n");
					}
					else if(result.equalsIgnoreCase("NO_ENTRY_RECORD_FOUND")) {
						result = "ALERT_FLAG_TRIGGERED";
						request.setAttribute("FLAG_MESSAGES",  "No ENTRY record found\n");
					}
					
					if(result.equalsIgnoreCase("GUESTLOG_ADD_SUCCESS")) {
						String message = FlagGuestHelper.getInstance().determineAlertFlag(dto.getIdentificationNo(), dto.getIdentificationType(), dto.getEntryOrExit());
						if(!message.equalsIgnoreCase("NO_ALERT_TRIGGERED")) {
							result = "ALERT_FLAG_TRIGGERED";
							request.setAttribute("FLAG_MESSAGES", message);
						}
					}
				} else {
					conn.rollback();
				}
				
				request.setAttribute("message", result);
				request.setAttribute("status", dto.getEntryOrExit());
				
				actionForward = "GLOBAL_REDIRECT_MESSAGE";
			} else {
				request.setAttribute("message", "UNAUTHORIZED");
				actionForward = "GLOBAL_REDIRECT_LOGIN";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("###GuestLogAction[guestLog] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
}
