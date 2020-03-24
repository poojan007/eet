package bt.gov.moh.eet.vo;

public class UserDetailsVO implements java.io.Serializable 
{
	private static final long serialVersionUID = 6647518571149081230L;
	private String cid;
	private String full_name;
	private String mobile_number;
	private String designation;
	private String working_address;
	private String user_type_id;
	private String role_id;
	private String user_type;
	private String role_name;
	private String userCheck;
	private String gateId;
	private String gateCode;
	private String gateName;
	
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getGateCode() {
		return gateCode;
	}
	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}
	public String getGateName() {
		return gateName;
	}
	public void setGateName(String gateName) {
		this.gateName = gateName;
	}
	public String getUserCheck() {
		return userCheck;
	}
	public void setUserCheck(String userCheck) {
		this.userCheck = userCheck;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getWorking_address() {
		return working_address;
	}
	public void setWorking_address(String working_address) {
		this.working_address = working_address;
	}
	public String getUser_type_id() {
		return user_type_id;
	}
	public void setUser_type_id(String user_type_id) {
		this.user_type_id = user_type_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	
}
