package bt.gov.moh.eet.web.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RedirectionAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String actionForward = null;
		
		try {
			HttpSession session = request.getSession();
			UserDetailsVO vo = (UserDetailsVO)session.getAttribute(CRSTConstants.USERDETAILS.getQuery());
			String param = request.getParameter(CRSTConstants.PARAM_P.getQuery());
			
			if(param.equalsIgnoreCase("forgotpassword")){
				return mapping.findForward(param);
			} else if(param.equals("register")){
				return mapping.findForward(param);
			} else if(param.equals("publicsearch")){
				return mapping.findForward(param);
			} else if(param.equals("comingsoon")) {
				return mapping.findForward(param);
			}
			List<DropDownDTO> nationality = PopulateDropDownDAO.getInstance().getDropDownList("NATIONALITYLIST", null);
			request.setAttribute("NATIONALITY", nationality);
			
			if(vo != null && vo.getRole() != null && vo.getUserCheck().equalsIgnoreCase("ok")) {
				
				if(param.equalsIgnoreCase("MANAGE_USERS")){
					List<DropDownDTO> agencyList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("AGENCY_LIST", agencyList);
					
					List<DropDownDTO> branchList = PopulateDropDownDAO.getInstance().getDropDownList("BRANCH_LIST", Integer.toString(vo.getAgencySerialNo()));
					request.setAttribute("BRANCH_LIST", branchList);
					
					List<DropDownDTO> privilegeList = PopulateDropDownDAO.getInstance().getDropDownList("PRIVILEGE_LIST", null);
					request.setAttribute("PRIVILEGE_LIST", privilegeList);
					
					if(vo.getUserType().equals("SUPERADMIN")){
						List<UserDTO> userList = UserBusiness.getInstance().getUserList(vo.getRole(), Integer.toString(vo.getAgencySerialNo()), null);
						request.setAttribute("USER_LIST", userList);
					} else {
						request.setAttribute("USER_LIST", null);
					}
					
					actionForward = param;
				} else if(param.equalsIgnoreCase("BANK_MERGE")){
					List<DropDownDTO> agencyList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("AGENCY_LIST", agencyList);
					actionForward = param;
				} else if(param.equalsIgnoreCase("AMENDMENT")){
					actionForward = param;
				} else if(param.equalsIgnoreCase("CONTINUATION")){
					actionForward = param;
				} else if(param.equalsIgnoreCase("DISCHARGE")){
					actionForward = param;
				} else if(param.equalsIgnoreCase("MANAGE_AGENCY") || param.equals("MANAGE_BRANCH") || param.equals("MANAGE_NATIONALITY") || param.equals("MANAGE_LOAN_PURPOSE") || param.equals("MANAGE_SEARCH_PURPOSE")) {
					List<MasterDTO> masterList = Masterbusiness.getInstance().getMasterDataList(param, vo.getRole(), null); 
					
					request.setAttribute("masterList", masterList);
					request.setAttribute("MASTER_TYPE", param);
					request.setAttribute("HEADER", param.toLowerCase().replaceAll("_", " "));
					
					List<DropDownDTO> agencyList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("AGENCY_LIST", agencyList);
					
					List<DropDownDTO> branchList = PopulateDropDownDAO.getInstance().getDropDownList("BRANCH_LIST", Integer.toString(vo.getAgencySerialNo()));
					request.setAttribute("BRANCH_LIST", branchList);
					
					actionForward = "master-management";
				} else if(param.equalsIgnoreCase("REGISTRATION")) {
					List<DropDownDTO> collateralList = PopulateDropDownDAO.getInstance().getDropDownListwithCondition("COLLATERALS_LIST", null);
					request.setAttribute("collaterals", collateralList);
					List<DropDownDTO> Dzongkhag = PopulateDropDownDAO.getInstance().getDropDownList("DZONGKHAGLIST", null);
					request.setAttribute("DZONGKHAG", Dzongkhag);
					List<DropDownDTO> InstitutionList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("INSTITUTION", InstitutionList);
					List<DropDownDTO> loanPurposeList = PopulateDropDownDAO.getInstance().getDropDownList("LOAN_PURPOSE_LIST", null);
					request.setAttribute("LOAN_PURPOSE", loanPurposeList);
					actionForward = param;
				} else if(param.equalsIgnoreCase("SEARCH_INDIVIDUAL") || param.equalsIgnoreCase("SEARCH_INSTITUTION") || param.equalsIgnoreCase("SEARCH_COLLATERAL")){
					List<DropDownDTO> collateralList = PopulateDropDownDAO.getInstance().getDropDownListwithCondition("COLLATERALS_LIST", null);
					request.setAttribute("collaterals", collateralList);
					
					List<DropDownDTO> searchPurposeList = PopulateDropDownDAO.getInstance().getDropDownList("SEARCH_PURPOSE_LIST", null);
					request.setAttribute("searchPurposeList", searchPurposeList);
					
					actionForward = param;
				} else if(param.equals("GENERATE_MONTHLY_INVOICE")){
					List<DropDownDTO> agencyList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("AGENCY_LIST", agencyList);
					actionForward = param;
				} else if(param.equalsIgnoreCase("continuation_due")){
					List<ContinuationDTO> continuationDueList = AdministrationBusiness.getInstance().getContinuationDueList(vo.getRole(), vo.getAgencySerialNo(), vo.getUserBranchSerialNo());
					request.setAttribute("CONTINUATION_DUE_LIST", continuationDueList);
					actionForward = param;
				} else if(param.equalsIgnoreCase("SEARCH_REGIS_NO")){
					actionForward = param;
				} else if(param.equalsIgnoreCase("VIEW_AGENT_ADMIN_INVOICE")){
					String agencyId = request.getParameter("agencyId");
					String month = request.getParameter("month");
					String year = request.getParameter("year");
					String invoiceId = request.getParameter("invoiceId");
					request.setAttribute("agencyId", agencyId);	
					request.setAttribute("month", month);	
					request.setAttribute("year", year);	
					request.setAttribute("invoiceId", invoiceId);	
					actionForward = param;
				} else if (param.equalsIgnoreCase("AGENT_ADMIN_INVOICE")) {
					 List<InvoiceDTO> dto = InvoiceBusiness.getInstance().viewInvoiceDtls(vo.getAgencySerialNo(),"","AGENT_ADMIN");
					 request.setAttribute("INVOICE_LIST", dto);
					 actionForward = param;
				} else if (param.equalsIgnoreCase("SUPER_ADMIN_INVOICE")) {
					List<DropDownDTO> agencyList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("AGENCY_LIST", agencyList);
					 actionForward = param;
				} else if(param.equals("REPORT_SEARCH") || param.equals("REPORT_OVER_ALL_STATS") 
								|| param.equals("REPORT_EXPIRY") || param.equals("REPORT_REVENUE") 
								|| param.equals("REPORT_GENDER_WISE_ENTREPRENUER") || param.equals("REPORT_DISCHARGE")){
					
					List<DropDownDTO> agencyList = PopulateDropDownDAO.getInstance().getDropDownList("AGENCY_LIST", null);
					request.setAttribute("AGENCY_LIST", agencyList);
					
					List<DropDownDTO> branchList = PopulateDropDownDAO.getInstance().getDropDownList("BRANCH_LIST", Integer.toString(vo.getAgencySerialNo()));
					request.setAttribute("BRANCH_LIST", branchList);
					
					request.setAttribute("REPORT_TYPE", param);
					actionForward = "REPORT-PAGE";
				} else if(param.equals("PROFILE")){
					UserDTO dto = UserBusiness.getInstance().getLoggedInUserDetails(vo.getUserId());
					request.setAttribute("user", dto);
					actionForward = param;
				} else if (param.equalsIgnoreCase("END_USER_INVOICE")) {
					 List<InvoiceDTO> dto = InvoiceBusiness.getInstance().viewInvoiceDtls(vo.getAgencySerialNo(),vo.getUserId(),"END_USER");
					 request.setAttribute("INVOICE_LIST", dto);
					 actionForward = param;
				} else if(param.equals("SEARCH_REGISTRATION_NUMBER")){
					actionForward = param;
				} else if(param.equals("MANAGE_ONLINE_USERS")) {
					List<UserDTO> onlineUserList = UserBusiness.getInstance().getListOfOnlineUsers(vo.getRole(), Integer.toString(vo.getAgencySerialNo()));
					request.setAttribute("ONLINE_USER_LIST", onlineUserList);
					actionForward = param;
				} else if(param.equals("MANAGE_COLLATERAL_TYPE")) {
					List<MasterDTO> masterList = Masterbusiness.getInstance().getMasterDataList(param, vo.getRole(), null); 
					
					request.setAttribute("masterList", masterList);
					request.setAttribute("MASTER_TYPE", param);
					request.setAttribute("HEADER", param.toLowerCase().replaceAll("_", " "));
					actionForward = "master-management";
				}
			}
			else {
				actionForward = "GLOBAL_REDIRECT_LOGIN";
				request.setAttribute("FAILURE", "UNAUTHORIZED");
			}
		} catch (Exception e) {
			Log.error("###Redirection Error at RequestForwarderAction[execute] ----> ", e);
			request.setAttribute("ERROR", e.getMessage());
			actionForward = "GLOBAL_REDIRECT_ERROR";
		}
		
		return mapping.findForward(actionForward);
	}
}
