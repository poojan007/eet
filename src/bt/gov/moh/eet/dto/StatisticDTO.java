package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class StatisticDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String totalEntry;
	private String totalExit;
	private String totalAlertFlag;
	
	public String getTotalEntry() {
		return totalEntry;
	}
	public void setTotalEntry(String totalEntry) {
		this.totalEntry = totalEntry;
	}
	public String getTotalExit() {
		return totalExit;
	}
	public void setTotalExit(String totalExit) {
		this.totalExit = totalExit;
	}
	public String getTotalAlertFlag() {
		return totalAlertFlag;
	}
	public void setTotalAlertFlag(String totalAlertFlag) {
		this.totalAlertFlag = totalAlertFlag;
	}
}
