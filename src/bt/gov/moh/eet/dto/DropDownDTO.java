package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class DropDownDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String headerId;
	private String headerName;
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
}
