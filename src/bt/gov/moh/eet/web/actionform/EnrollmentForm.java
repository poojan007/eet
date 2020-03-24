package bt.gov.moh.eet.web.actionform;

import org.apache.struts.action.ActionForm;

public class EnrollmentForm extends ActionForm {
	
	private static final long serialVersionUID = 843948394823222L;

	//private variables region
	private String residenceFlag;
	private String identificationType;
	private String identificationNo;
	private String name;
	private String gender;
	private String age;
	private String nationality;
	private String presentAddress;
	private String mobileNo;
	private String imageData;
	
	public String getImageData() {
		return imageData;
	}
	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
	public String getResidenceFlag() {
		return residenceFlag;
	}
	public void setResidenceFlag(String residenceFlag) {
		this.residenceFlag = residenceFlag;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getIdentificationType() {
		return identificationType;
	}
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}
	public String getIdentificationNo() {
		return identificationNo;
	}
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getPresentAddress() {
		return presentAddress;
	}
	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

}
