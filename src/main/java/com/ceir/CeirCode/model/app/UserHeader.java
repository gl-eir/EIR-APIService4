package com.ceir.CeirCode.model.app;

public class UserHeader {
private String userAgent;
	private String publicIp;
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	private String browser;
	public String getBrowser() {
	return browser;
}
public void setBrowser(String browser) {
	this.browser = browser;
}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserHeader [browser=");
		builder.append(browser);
		builder.append(", userAgent=");
		builder.append(userAgent);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append("]");
		return builder.toString();
	}
	public UserHeader(String userAgent, String publicIp) {
		super();
		this.userAgent = userAgent;
		this.publicIp = publicIp;
	}
	public UserHeader() {
		super();
	}
	
}
