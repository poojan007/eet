package bt.gov.moh.eet.vo;

import java.io.Serializable;
import java.util.List;

public class SMSModelVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String moduleType;
	private String templateModule;
	private String smsType;
	private List<String> recipentList;
	private String smsContent;
	private String mobileNo;
	private String gateName;
	private String transactionTime;
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getGateName() {
		return gateName;
	}
	public void setGateName(String gateName) {
		this.gateName = gateName;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getTemplateModule() {
		return templateModule;
	}
	public void setTemplateModule(String templateModule) {
		this.templateModule = templateModule;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	public List<String> getRecipentList() {
		return recipentList;
	}
	public void setRecipentList(List<String> recipentList) {
		this.recipentList = recipentList;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
}
