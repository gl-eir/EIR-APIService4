package com.ceir.CeirCode.response.tags;

public enum ProfileTags {

	PRO_CPASS_LAST_3PASS_ERROR("PRO_CPASS_LAST_3PASS_ERROR", "Password should not be last 3 password"),
	PRO_CPASS_SUCESS("PRO_CPASS_SUCESS", "User password  has been sucessfully changed"),
	PRO_CPASS_FAIL("PRO_CPASS_FAIL", "User password failed to change"),
	PRO_OldPass_Error("PRO_OldPass_Error", "Kindly enter the correct password"),
	PRO_CORRECT_PASS("PRO_CORRECT_PASS","Please enter correct password"),
	PRO_SUCESS_MSG("PRO_SUCESS_MSG","User profile data succesfully update"),
	PRO_SUCESS_OTPMSG("PRO_SUCESS_OTPMSG","OTP has been sent to your  Phone Number and E-Mail ID to verify"),
	PRO_FAIL_MSG("PRO_FAIL_MSG","User profile data failed to update"),
	SEC_QUES_MATCH("secu_ques_match","security question and answer match"),
	SECU_QUEST_NOT_MATCH("secu_ques_not_match","Security question and answer did not match"),
	WRONG_USERID("wrong_userId","Please enter correct User Id"),
	NEW_PASS_SUC("new_pass_sucess","User new password has been added"),
	NEW_PASS_FAIL("new_pass_fail","User new password fail to update");
	
	private String tag;
	private String message;
	
	private ProfileTags() {

	}

	ProfileTags(String tag, String message) {
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
