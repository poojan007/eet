package bt.gov.moh.eet.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import bt.gov.moh.eet.dao.SMSDao;
import bt.gov.moh.eet.dto.SMSDTO;
import bt.gov.moh.eet.util.SMSUtil;
import bt.gov.moh.eet.vo.SMSModelVO;
import bt.gov.moh.framework.common.Log;

public class SMSAction extends DispatchAction{

	public ActionForward guestExit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		
		try {
			String mobileNo = request.getParameter("mobileNo");
			String gateCode = request.getParameter("gateCode");
			
			SMSDTO dto = SMSDao.getInstance().guestExit(mobileNo, gateCode);
			
			if(dto.getStatus().equals("SUCCESS")) {
				SMSModelVO vo = new SMSModelVO();
				vo.setSmsType("EXIT_SUCCESS_NOTIFICATION");
				sendSMSToUser(vo, dto);
				request.setAttribute("message", "SENT_SUCCESS");
			} else {
				SMSModelVO vo = new SMSModelVO();
				vo.setSmsType("EXIT_FAILURE_NOTIFICATION");
				sendSMSToUser(vo, dto);
				request.setAttribute("message", "SENT_FAILURE");
			}
			
			actionForward = "GLOBAL_REDIRECT_MESSAGE";
			
		} catch (Exception e) {
			Log.error("###SMSAction ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
	
	public ActionForward guestEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		
		try {
			String mobileNo = request.getParameter("mobileNo");
			String gateCode = request.getParameter("gateCode");
			
			SMSDTO dto = SMSDao.getInstance().guestEntry(mobileNo, gateCode);
			
			if(dto.getStatus().equals("SUCCESS")) {
				SMSModelVO vo = new SMSModelVO();
				vo.setSmsType("ENTRY_SUCCESS_NOTIFICATION");
				sendSMSToUser(vo, dto);
				request.setAttribute("message", "SENT_SUCCESS");
			} else if(dto.getStatus().equalsIgnoreCase("NO_EXIT_RECORD_FOUND")){
				SMSModelVO vo = new SMSModelVO();
				vo.setSmsType("NO_EXIT_RECORD_NOTIFICATION");
				sendSMSToUser(vo, dto);
				request.setAttribute("message", "SENT_SUCCESS");
			}
			
			actionForward = "GLOBAL_REDIRECT_MESSAGE";
			
		} catch (Exception e) {
			Log.error("###SMSAction ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
	
	/**
	 * @param smsObject the sms object
	 * @param NotificationDTO
	 * @throws DrukBusSystemException the system exception
	 */  
	private void sendSMSToUser(SMSModelVO smsObject, SMSDTO dto)
			throws Exception {
		String mobileNo = dto.getMobileNo();

		if (mobileNo != null && !mobileNo.trim().isEmpty()) {
			List<String> recipientList = new ArrayList<String>();
			recipientList.add(mobileNo);
			smsObject.setRecipentList(recipientList);
			smsObject.setGateName(dto.getGateName());
			smsObject.setTransactionTime(dto.getTransactionTime());
			
			new SMSUtil().sendSMS(smsObject);
		}
	}
}
