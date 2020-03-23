package bt.gov.moh.eet.web.action;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class EntryExitForm extends ActionForm {

	private static final long serialVersionUID = 843948394823222L;

	// Guest form
	private String identification_no;
	private Integer identification_type_id;
	private Integer nationality_id;
	private String guest_name;
	private Character gender;
	private Integer age;
	private String present_address;
	private Integer contact_no;
	private Character residing_across_border;

	// Guest log form
	private Integer log_id;
	private Integer guest_id;
	private Integer temperature;
	private String entry_or_exit;
	private Date transaction_date_time;
	private Integer reason_id;
	private Integer requested_gate_id;
	private String reason;
	private Integer gate_id;
	private String created_by;
	private Date created_on;
	private Character alert_flag;
	private Date alert_update_time;
	private String alert_remarks;

	public String getIdentification_no() {
		return identification_no;
	}

	public void setIdentification_no(String identification_no) {
		this.identification_no = identification_no;
	}

	public Integer getIdentification_type_id() {
		return identification_type_id;
	}

	public void setIdentification_type_id(Integer identification_type_id) {
		this.identification_type_id = identification_type_id;
	}

	public Integer getNationality_id() {
		return nationality_id;
	}

	public void setNationality_id(Integer nationality_id) {
		this.nationality_id = nationality_id;
	}

	public String getGuest_name() {
		return guest_name;
	}

	public void setGuest_name(String guest_name) {
		this.guest_name = guest_name;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPresent_address() {
		return present_address;
	}

	public void setPresent_address(String present_address) {
		this.present_address = present_address;
	}

	public Integer getContact_no() {
		return contact_no;
	}

	public void setContact_no(Integer contact_no) {
		this.contact_no = contact_no;
	}

	public Character getResiding_across_border() {
		return residing_across_border;
	}

	public void setResiding_across_border(Character residing_across_border) {
		this.residing_across_border = residing_across_border;
	}

	public Integer getLog_id() {
		return log_id;
	}

	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}

	public Integer getGuest_id() {
		return guest_id;
	}

	public void setGuest_id(Integer guest_id) {
		this.guest_id = guest_id;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public String getEntry_or_exit() {
		return entry_or_exit;
	}

	public void setEntry_or_exit(String entry_or_exit) {
		this.entry_or_exit = entry_or_exit;
	}

	public Date getTransaction_date_time() {
		return transaction_date_time;
	}

	public void setTransaction_date_time(Date transaction_date_time) {
		this.transaction_date_time = transaction_date_time;
	}

	public Integer getReason_id() {
		return reason_id;
	}

	public void setReason_id(Integer reason_id) {
		this.reason_id = reason_id;
	}

	public Integer getRequested_gate_id() {
		return requested_gate_id;
	}

	public void setRequested_gate_id(Integer requested_gate_id) {
		this.requested_gate_id = requested_gate_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getGate_id() {
		return gate_id;
	}

	public void setGate_id(Integer gate_id) {
		this.gate_id = gate_id;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Character getAlert_flag() {
		return alert_flag;
	}

	public void setAlert_flag(Character alert_flag) {
		this.alert_flag = alert_flag;
	}

	public Date getAlert_update_time() {
		return alert_update_time;
	}

	public void setAlert_update_time(Date alert_update_time) {
		this.alert_update_time = alert_update_time;
	}

	public String getAlert_remarks() {
		return alert_remarks;
	}

	public void setAlert_remarks(String alert_remarks) {
		this.alert_remarks = alert_remarks;
	}

}
