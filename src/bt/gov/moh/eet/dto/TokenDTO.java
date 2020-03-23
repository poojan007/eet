package bt.gov.moh.eet.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accessToken;
	private String tokenExpiryTime;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenExpiryTime() {
		return tokenExpiryTime;
	}
	public void setTokenExpiryTime(String tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}
}
