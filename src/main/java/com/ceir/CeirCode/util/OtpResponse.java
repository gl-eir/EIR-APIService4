package com.ceir.CeirCode.util;
public class OtpResponse {
	private String response;
	private Integer statusCode;
    private String tag;
    
	
	public OtpResponse() {
		super();
	}

	public OtpResponse(String response, Integer statusCode, long userId,String tag) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.userId = userId;
		this.tag=tag;
	}

	private long userId;

	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "OtpResponse [response=" + response + ", statusCode=" + statusCode + ", tag=" + tag + ", userId="
				+ userId + "]";
	}
}
