package bt.gov.moh.eet.web.action;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import bt.gov.moh.eet.dao.UserDAO;
import bt.gov.moh.eet.dto.UserDTO;
import bt.gov.moh.eet.util.ConnectionManager;

public class UserAction extends DispatchAction {
	Connection conn = null;
	public ActionForward addUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
			String actionForward = null;

			//userFormBean formBean = (userFormBean) form;
			try
			{
				conn = ConnectionManager.getConnection();
				conn.setAutoCommit(false);
				
				if(conn != null) {
			String add_cid = request.getParameter("add_cid");
			String add_full_name = request.getParameter("add_full_name");
			String add_mobile_number = request.getParameter("add_mobile_number");
			String add_designation = request.getParameter("add_designation");
			String add_working_address = request.getParameter("add_working_address");
			String add_user_type = request.getParameter("add_user_type");
			String add_role =request.getParameter("add_role");
			
			UserDTO dto = new UserDTO();
			dto.setCid(add_cid);
			dto.setFull_name(add_full_name);
			dto.setMobile_number(add_mobile_number);
			dto.setDesignation(add_designation);
			dto.setWorking_address(add_working_address);
			dto.setUser_type_id(add_user_type);;
			dto.setRole_id(add_role);
			
			UserDAO userDao= new UserDAO();
			String result = userDao.getInstance().add_user(dto,conn);
			request.setAttribute("MESSAGE", result);
			
			actionForward = "USERS";
				}
			}
			catch(Exception e){
				
			}
			finally
			{
				ConnectionManager.close(conn);
			}
			
			return mapping.findForward(actionForward);
			}
	
	public ActionForward editUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
			String actionForward = null;

			//userFormBean formBean = (userFormBean) form;
			try
			{
				conn = ConnectionManager.getConnection();
				conn.setAutoCommit(false);
				
				if(conn != null) {
			String edit_cid = request.getParameter("edit_cid");
			String edit_full_name = request.getParameter("edit_full_name");
			String edit_mobile_number = request.getParameter("edit_mobile_number");
			String edit_designation = request.getParameter("edit_designation");
			String edit_working_address = request.getParameter("edit_working_address");
			String edit_user_type = request.getParameter("edit_user_type");
			String edit_role =request.getParameter("edit_role");
			
			UserDTO dto = new UserDTO();
			dto.setCid(edit_cid);
			dto.setFull_name(edit_full_name);
			dto.setMobile_number(edit_mobile_number);
			dto.setDesignation(edit_designation);
			dto.setWorking_address(edit_working_address);
			dto.setUser_type_id(edit_user_type);;
			dto.setRole_id(edit_role);
			
			UserDAO userDao= new UserDAO();
			String result = userDao.getInstance().edit_user(dto,conn);
			request.setAttribute("MESSAGE", result);
			
			actionForward = "message";
				}
			}
			catch(Exception e){
				
			}
			finally
			{
				ConnectionManager.close(conn);
			}
			
			return mapping.findForward(actionForward);
			}
}
