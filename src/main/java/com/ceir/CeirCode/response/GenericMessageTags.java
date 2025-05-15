package com.ceir.CeirCode.response;

public enum GenericMessageTags {

	NULL_VISA ("NULL_VISA", "Request visa update should not be null."),
	EMPTY_VISA ("EMPTY_VISA", "Request visa update should not be empty."),
	NULL_NID ("NULL_NID", "Null NID is not allowed for the request."),
	VISA_UPDATE_NOT_ALLOWED ("VISA_UPDATE_NOT_ALLOWED", "You have not provided visa information at the time of registeration."),
	VISA_UPDATE_SUCCESS ("VISA_UPDATE_SUCCESS", "Visa of user have been updated succesfully."),
	VISA_UPDATE_FAIL ("VISA_UPDATE_FAIL", "Updation of Visa of user have been failed."),
	INVALID_USER ("INVALID_USER", "You are not a valid user. Please register first before updating"),
	NULL_REQ ("NULL_REQ", "Request can't be null."),
	USER_UPDATE_SUCCESS("USER_UPDATE_SUCCESS", "User have been updated successfully."),
	VISA_EMPTY("VISA_EMPTY", "Visa information in request not found"),	
	DISCREPENCY_IN_CONFIG("DISCREPENCY_IN_CONFIG", "Discrepancy found in configuration."),
	USER_REGISTER_SUCCESS("USER_REGISTER_SUCCESS", "User have been registered successfully."),
	FEATURE_NOT_ALLOWED("FEATURE_NOT_ALLOWED", "This functionality is not supported now."),
	//----------
	NULL_USER_DEPARTMENT ("NULL_USER_DEPARTMENT", "User Department is null for VIP."),
	INVALID_STATE_TRANSTION("INVALID_STATE_TRANSTION", "Operation not allowed.")
	;
	
	private String tag;
	private String message;
	
	private GenericMessageTags() {

	}

	GenericMessageTags(String tag, String message) {
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
