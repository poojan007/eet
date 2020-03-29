package bt.gov.moh.eet.web.actionform;

import org.apache.struts.action.ActionForm;

public class GuestLogForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String entryOrExit;
	private String identificationType;
	private String identificationNo;
	private String guestName;
	private String gender;
	private String age;
	private String nationality;
	private String contactNo;
	private String reason;
	private String thermometerReading;
	private String presentAddress;
	private String remarks;
	private String nextEntryGate;
	private String guestId;
	private String dob;
	private String residenceFlag;
	private String imageId;
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getResidenceFlag() {
		return residenceFlag;
	}
	public void setResidenceFlag(String residenceFlag) {
		this.residenceFlag = residenceFlag;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGuestId() {
		return guestId;
	}
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	public String getNextEntryGate() {
		return nextEntryGate;
	}
	public void setNextEntryGate(String nextEntryGate) {
		this.nextEntryGate = nextEntryGate;
	}
	public String getThermometerReading() {
		return thermometerReading;
	}
	public void setThermometerReading(String thermometerReading) {
		this.thermometerReading = thermometerReading;
	}
	public String getPresentAddress() {
		return presentAddress;
	}
	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdentificationNo() {
		return identificationNo;
	}
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}
	public String getIdentificationType() {
		return identificationType;
	}
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}
	public String getEntryOrExit() {
		return entryOrExit;
	}
	public void setEntryOrExit(String entryOrExit) {
		this.entryOrExit = entryOrExit;
	}
}
