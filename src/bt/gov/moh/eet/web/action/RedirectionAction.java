package bt.gov.moh.eet.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bt.gov.moh.eet.dao.MasterDAO;
import bt.gov.moh.eet.dao.PopulateDropDownDAO;
import bt.gov.moh.eet.dao.UserDAO;
import bt.gov.moh.eet.dto.DropDownDTO;
import bt.gov.moh.eet.dto.MasterDTO;
import bt.gov.moh.eet.dto.UserDTO;
import bt.gov.moh.eet.vo.UserDetailsVO;
import bt.gov.moh.framework.common.Log;

public class RedirectionAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		
		try {
			HttpSession session = request.getSession();
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute("userdetails");
			String param = request.getParameter("q");
			String parentId=null;
			if(vo != null && vo.getRole_id() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {
				if(param.equalsIgnoreCase("MANAGE_USERS")) {
					//public static final String USER_DROP_DOWN_FIELD_CONSTRUCTOR;
					//pull list of user here
					List<UserDTO> userDetails = UserDAO.getInstance().getUserDetails();
					request.setAttribute("userDetails", userDetails);
					List<DropDownDTO> userTypeList = PopulateDropDownDAO.getInstance().getDropDownList("USER", parentId);
					List<DropDownDTO> roleList = PopulateDropDownDAO.getInstance().getDropDownList("ROLE", parentId);
					
					request.setAttribute("userTypeList", userTypeList);
					request.setAttribute("roleList", roleList);
					actionForward = param;
				} else if(param.equalsIgnoreCase("MASTER_MANAGEMENT_GATES") || param.equalsIgnoreCase("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")
						 || param.equalsIgnoreCase("MASTER_MANAGEMENT_IDENTIFICATION_TYPES") || param.equalsIgnoreCase("MASTER_MANAGMENT_NATIONALITY")
						 || param.equalsIgnoreCase("MASTER_MANAGMENT_USERTYPES") || param.equalsIgnoreCase("MASTER_MANAGMENT_EXITREASONS") 
						 || param.equalsIgnoreCase("MASTER_MANAGMENT_AVERAGE_TIME")) {
					List<MasterDTO> masterList = MasterDAO.getInstance().getMasterList(param);
					request.setAttribute("masterList", masterList);
					request.setAttribute("masterType", param);
					
					List<MasterDTO> gateList = MasterDAO.getInstance().getGateList();
					request.setAttribute("gateList", gateList);
					actionForward = "master-management";
				}
				if(param.equalsIgnoreCase("MANAGE_ENTRY_EXIT")) {
					//pull list of master here
					List<DropDownDTO> identificationTypeList = PopulateDropDownDAO.getInstance().getIdentificationDropDownList("IDENTIFICATIONTYPELIST", null);
					request.setAttribute("IDENTIFICATIONTYPELIST", identificationTypeList);
					List<DropDownDTO> nationalityList = PopulateDropDownDAO.getInstance().getNationalityDropDownList("NATIONALITYLIST", null);
					request.setAttribute("NATIONALITYLIST", nationalityList);
					List<DropDownDTO> reasonList = PopulateDropDownDAO.getInstance().getDropDownList("REASONLIST", null);
					request.setAttribute("REASONLIST", reasonList);
					List<DropDownDTO> gateList = PopulateDropDownDAO.getInstance().getGateDropDownList("GATELIST", null);
					request.setAttribute("GATELIST", gateList);
					actionForward = "entry_exit";
				}
				if(param.equalsIgnoreCase("MANAGE_ENROLLMENT")) {
			          //pull list of master here
			          List<DropDownDTO> identificationTypeList = PopulateDropDownDAO.getInstance().getDropDownList("IDENTIFICATIONTYPELIST", null);
			          request.setAttribute("IDENTIFICATIONTYPELIST", identificationTypeList);
			          List<DropDownDTO> nationalityList = PopulateDropDownDAO.getInstance().getDropDownList("NATIONALITYLIST", null);
			          request.setAttribute("NATIONALITYLIST", nationalityList);
			          
//			          List<DropDownDTO> gateList = PopulateDropDownDAO.getInstance().getDropDownList("GATELIST", null);
//			          request.setAttribute("GATELIST", gateList);
			          actionForward = param;
			        }
			}
			else {
				actionForward = "GLOBAL_REDIRECT_LOGIN";
				request.setAttribute("FAILURE", "UNAUTHORIZED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("###Redirection Error at RequestForwarderAction[execute] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
	
}
