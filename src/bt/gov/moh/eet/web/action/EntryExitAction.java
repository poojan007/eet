package bt.gov.moh.eet.web.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.dao.GuestDao;
import bt.gov.moh.eet.dto.*;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.util.FlagGuestHelper;

public class EntryExitAction extends Action {
	EntryExitForm entryExitForm = new EntryExitForm();

	Connection conn = null;

	public ActionForward addGuest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actionForward = null;

		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			EntryExitForm formBean = (EntryExitForm) form;

			if (conn != null) {
				GuestDTO dto = new GuestDTO();
				BeanUtils.copyProperties(dto, formBean);

				GuestDao guestDao = new GuestDao();
				String result = guestDao.getInstance().addGuest(dto, conn);
				request.setAttribute("MESSAGE", result);

				actionForward = "message";
			}
		} catch (Exception e) {

		} finally {
			ConnectionManager.close(conn);
		}
		return mapping.findForward(actionForward);
	}

	public ActionForward addGuestLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actionForward = null;

		EntryExitForm formBean = (EntryExitForm) form;
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);

			if (conn != null) {
				GuestLogDTO dto = new GuestLogDTO();
				FlagGuestHelper flagGuestHelper = new FlagGuestHelper();
				GuestDao guestDao = new GuestDao();

				BeanUtils.copyProperties(dto, formBean);
				dto = flagGuestHelper.run(dto);

				String result = guestDao.getInstance().addGuestLog(dto, conn);
				request.setAttribute("MESSAGE", result);

				actionForward = "message";
			}
		} catch (Exception e) {

		} finally {
			ConnectionManager.close(conn);
		}

		return mapping.findForward(actionForward);
	}

	public ActionForward editGuestLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actionForward = null;

		EntryExitForm formBean = (EntryExitForm) form;
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);

			if (conn != null) {
				GuestLogDTO dto = new GuestLogDTO();
				BeanUtils.copyProperties(dto, formBean);

				GuestDao guestDao = new GuestDao();
				String result = guestDao.getInstance().editGuestLog(dto, conn);
				request.setAttribute("MESSAGE", result);

				actionForward = "message";
			}
		} catch (Exception e) {

		} finally {
			ConnectionManager.close(conn);
		}

		return mapping.findForward(actionForward);
	}
}
