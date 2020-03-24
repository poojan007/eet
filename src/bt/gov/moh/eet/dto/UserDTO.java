package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cid;
	private String password;
	private String passwordsalt;
	private String full_name;
	private String mobile_number;
	private String designation;
	private String working_address;
	private String user_type_id;
	private String role_id;
	private String gate_id;
	private String user_type;
	private String role_name;
	private String edituser_type_id;
	private String editrole_id;
	private String editworking_address;
	private String editdesignation;
	private String editmobile_number;
	private String editfull_name;
	private String cidedit;
	
	
	
	
	
	public String getEdituser_type_id() {
		return edituser_type_id;
	}
	public void setEdituser_type_id(String edituser_type_id) {
		this.edituser_type_id = edituser_type_id;
	}
	public String getEditrole_id() {
		return editrole_id;
	}
	public void setEditrole_id(String editrole_id) {
		this.editrole_id = editrole_id;
	}
	public String getEditworking_address() {
		return editworking_address;
	}
	public void setEditworking_address(String editworking_address) {
		this.editworking_address = editworking_address;
	}
	public String getEditdesignation() {
		return editdesignation;
	}
	public void setEditdesignation(String editdesignation) {
		this.editdesignation = editdesignation;
	}
	public String getEditmobile_number() {
		return editmobile_number;
	}
	public void setEditmobile_number(String editmobile_number) {
		this.editmobile_number = editmobile_number;
	}
	public String getEditfull_name() {
		return editfull_name;
	}
	public void setEditfull_name(String editfull_name) {
		this.editfull_name = editfull_name;
	}
	public String getCidedit() {
		return cidedit;
	}
	public void setCidedit(String cidedit) {
		this.cidedit = cidedit;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordsalt() {
		return passwordsalt;
	}
	public void setPasswordsalt(String passwordsalt) {
		this.passwordsalt = passwordsalt;
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
	public String getGate_id() {
		return gate_id;
	}
	public void setGate_id(String gate_id) {
		this.gate_id = gate_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
