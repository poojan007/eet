package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class MasterDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String masterType;
	private String gateId;
	private String gateName;
	private String id;
	private String pointOne;
	private String pointTwo;
	private String pointOneId;
	private String pointTwoId;
	private String averageTime;
	private String reasonId;
	private String reason;
	private String thresholdHour;
	private String identificationTypeId;
	private String identificationType;
	private String nationalityId;
	private String nationality;
	private String userTypeId;
	private String userType;
	private String name;
	
	public String getPointOneId() {
		return pointOneId;
	}
	public void setPointOneId(String pointOneId) {
		this.pointOneId = pointOneId;
	}
	public String getPointTwoId() {
		return pointTwoId;
	}
	public void setPointTwoId(String pointTwoId) {
		this.pointTwoId = pointTwoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMasterType() {
		return masterType;
	}
	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getGateName() {
		return gateName;
	}
	public void setGateName(String gateName) {
		this.gateName = gateName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPointOne() {
		return pointOne;
	}
	public void setPointOne(String pointOne) {
		this.pointOne = pointOne;
	}
	public String getPointTwo() {
		return pointTwo;
	}
	public void setPointTwo(String pointTwo) {
		this.pointTwo = pointTwo;
	}
	public String getAverageTime() {
		return averageTime;
	}
	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getThresholdHour() {
		return thresholdHour;
	}
	public void setThresholdHour(String thresholdHour) {
		this.thresholdHour = thresholdHour;
	}
	public String getIdentificationTypeId() {
		return identificationTypeId;
	}
	public void setIdentificationTypeId(String identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}
	public String getIdentificationType() {
		return identificationType;
	}
	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}
	public String getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
