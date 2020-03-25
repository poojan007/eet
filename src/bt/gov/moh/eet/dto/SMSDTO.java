package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class SMSDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;
	private String mobileNo;
	private String gateName;
	private String transactionTime;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
