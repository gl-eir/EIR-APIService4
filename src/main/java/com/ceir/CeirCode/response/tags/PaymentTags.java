package com.ceir.CeirCode.response.tags;

public enum PaymentTags {
	url_Sucess ("url_Sucess", "success"),
	url_Fail ("url_Fail", "fail");
    private String tag;
	private String message;
	
	private PaymentTags() {

	}

	PaymentTags(String tag, String message) {
		this.tag = tag;
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public String getMessage() {
		return message;
	}
}
