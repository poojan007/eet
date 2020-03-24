package bt.gov.moh.eet.web.action;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import bt.gov.moh.eet.dao.PopulateDropDownDAO;
import bt.gov.moh.eet.dao.UserDAO;
import bt.gov.moh.eet.dto.DropDownDTO;
import bt.gov.moh.eet.dto.UserDTO;
import bt.gov.moh.eet.util.ConnectionManager;
import bt.gov.moh.eet.web.actionform.UserForm;

public class UserAction extends DispatchAction {
	Connection conn = null;
	
	public ActionForward addUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String actionForward = null;
		UserDTO dto = new UserDTO();
		
		try
		{
			UserForm userform = (UserForm) form;
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			if(conn != null) {
				BeanUtils.copyProperties(dto, userform);
				String result = UserDAO.getInstance().add_user(dto, conn);
				request.setAttribute("MESSAGE", result);
				actionForward = "GLOBAL_REDIRECT_MESSAGE";
			}
		} catch(Exception e){
					
		} finally {
			ConnectionManager.close(conn);
		}
	
		return mapping.findForward(actionForward);
	}
	
	public ActionForward editUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
			{
			String actionForward = null;
			UserDTO dto = new UserDTO();
			//userFormBean formBean = (userFormBean) form;
			try
			{
				UserForm userform = (UserForm) form;
				
				conn = ConnectionManager.getConnection();
				conn.setAutoCommit(false);
				
				if(conn != null) {
					
					BeanUtils.copyProperties(dto, userform);
			
					String result = UserDAO.getInstance().edit_user(dto,conn);
					request.setAttribute("message", result);
					
					actionForward = "GLOBAL_REDIRECT_MESSAGE";
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
	
	public ActionForward getEditUserDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String actionForward = null;
		UserDTO dto = new UserDTO();
		
		try
		{
			
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			if(conn != null) {
				String cid= request.getParameter("cid");
				String parentId=null;
				
				dto =  UserDAO.getInstance().getEditUserDetails(cid);
				List<DropDownDTO> userTypeList = PopulateDropDownDAO.getInstance().getDropDownList("USER", parentId);
				List<DropDownDTO> roleList = PopulateDropDownDAO.getInstance().getDropDownList("ROLE", parentId);
				request.setAttribute("userTypeList", userTypeList);
				request.setAttribute("roleList", roleList);
				PrintWriter out = response.getWriter();
		 	    response.setContentType("text/xml");
		 	    StringBuffer buffer = new StringBuffer();
		 	    
		 	     buffer.append("<xml-response>");
			 	 buffer.append("<cid>"+dto.getCid()+"</cid>");
			 	 buffer.append("<name>"+dto.getFull_name()+"</name>");
			 	 buffer.append("<mobile>"+dto.getMobile_number()+"</mobile>");
		 		 buffer.append("<designation>"+dto.getDesignation()+"</designation>");
		 		 buffer.append("<address>"+dto.getWorking_address()+"</address>");
			 	 buffer.append("<usertype>"+dto.getUser_type()+"</usertype>");
		 		 buffer.append("<rolename>"+dto.getRole_name()+"</rolename>");
		 		 
	 		     buffer.append("</xml-response>");
		 	  out.println(buffer);
		 	  //Log.debug(buffer);
			  return null;
				
				
			}
		} catch(Exception e){
					
		} finally {
			ConnectionManager.close(conn);
		}
	
		return mapping.findForward(actionForward);
	}
}
