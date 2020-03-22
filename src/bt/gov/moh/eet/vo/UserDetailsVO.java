package bt.gov.moh.eet.vo;

public class UserDetailsVO implements java.io.Serializable 
{
	private static final long serialVersionUID = 6647518571149081230L;
	private String role;
	private String userId;
	private String userName;
	private String userType;
	private int userBranchSerialNo;
	private int agencySerialNo;
	private String privilege;
	private int privId;
	private String userCheck;
	private String userFlag;
	private String agencyName;
	private String password;
	private String userCode;
	private String designation;
	private String branchName;
	private String agencyCode;
	private String phoneNo;
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}
	public String getUserCheck() {
		return userCheck;
	}
	public void setUserCheck(String userCheck) {
		this.userCheck = userCheck;
	}
	public int getPrivId() {
		return privId;
	}
	public void setPrivId(int privId) {
		this.privId = privId;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public int getAgencySerialNo() {
		return agencySerialNo;
	}
	public void setAgencySerialNo(int agencySerialNo) {
		this.agencySerialNo = agencySerialNo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getUserBranchSerialNo() {
		return userBranchSerialNo;
	}
	public void setUserBranchSerialNo(int userBranchSerialNo) {
		this.userBranchSerialNo = userBranchSerialNo;
	}
}
