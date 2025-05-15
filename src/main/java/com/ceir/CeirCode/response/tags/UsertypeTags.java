package com.ceir.CeirCode.response.tags;
public enum UsertypeTags {

	UTStatus_Update_Success("Usertype_Update_Success", "Usertype status sucessfully update"),
	UTStatus_Update_Fail("Usertype_Update_Fail","Usertype status failed to update"),
	Wrong_usertypeId("Wrong_usertypeId","This UserType Id is wrong"),
	UTStatus_Enable("UTStatus_Enable","This UserType is enabled"),
	UTStatus_Disable("UTStatus_Disable","This UserType is disabled");
	private String tag;
	private String message;

	private UsertypeTags() {
	}

	UsertypeTags(String tag, String message) {
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
