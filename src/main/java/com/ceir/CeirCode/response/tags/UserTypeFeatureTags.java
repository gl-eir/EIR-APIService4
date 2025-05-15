package com.ceir.CeirCode.response.tags;

public enum UserTypeFeatureTags {

	UTFPeriod_Update_Success("UTFPeriod_Update_Success", "period sucessfully update"),
	UTFPeriod_Update_Fail("UTFPeriod_Update_Fail","period failed to update"),
	Wrong_userTypeFeatureId("Wrong_userTypeFeatureId","UserType Feaure id is incorrect");
	private String tag;
	private String message;

	private UserTypeFeatureTags() {
	}

	UserTypeFeatureTags(String tag, String message) {
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
