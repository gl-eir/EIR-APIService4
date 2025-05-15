package com.ceir.CeirCode.othermodel;

public class PaymentResponse {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PaymentResponse(String url) {
		super();
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentResponse []");
		return builder.toString();
	}
	
}
