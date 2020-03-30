package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class NotificationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;
	private String mobileNo;
	private String keyword;
	private String guestId;
	
	public String getGuestId() {
		return guestId;
	}
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
}
