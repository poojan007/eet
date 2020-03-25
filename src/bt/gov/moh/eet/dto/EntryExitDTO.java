package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class EntryExitDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int logId;
	private String entryCount;
	private String exitCount;
	private String entryOfExit;
	private String transactionDateTime;
	private String reasonId;
	private String reason;
	private String requestedGateId;
	private String gateId;
	private String temperatureCheck;
	private String temperatureThreshold;
	
	public String getEntryCount() {
		return entryCount;
	}
	public void setEntryCount(String entryCount) {
		this.entryCount = entryCount;
	}
	public String getExitCount() {
		return exitCount;
	}
	public void setExitCount(String exitCount) {
		this.exitCount = exitCount;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getTemperatureThreshold() {
		return temperatureThreshold;
	}
	public void setTemperatureThreshold(String temperatureThreshold) {
		this.temperatureThreshold = temperatureThreshold;
	}
	public String getEntryOfExit() {
		return entryOfExit;
	}
	public void setEntryOfExit(String entryOfExit) {
		this.entryOfExit = entryOfExit;
	}
	public String getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
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
	public String getRequestedGateId() {
		return requestedGateId;
	}
	public void setRequestedGateId(String requestedGateId) {
		this.requestedGateId = requestedGateId;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getTemperatureCheck() {
		return temperatureCheck;
	}
	public void setTemperatureCheck(String temperatureCheck) {
		this.temperatureCheck = temperatureCheck;
	}
}
