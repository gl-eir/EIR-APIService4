package com.ceir.CeirCode.response.tags;

public enum CurrencyTags {

	Curr_Save_Sucess ("Curr_Save_Sucess", "Currency has been sucessfully saved"),
	Curr_Already_Exist ("Curr_Already_Exist", "Currency data for this date is already exist"),
	Curr_Year_Null ("Curr_Year_Null", "Year value must not be null"),
	Curr_Month_Null ("Curr_Month_Null", "Month value must not be null"),
	Curr_Wrong_Id ("Curr_Wrong_Id", "Currency Id is wrong"),
	Curr_Save_Fail("Curr_Save_Fail", "Currency failed to save"),
	Curr_Update_Sucess ("Curr_Update_Sucess", "Currency has been sucessfully update"),
	Curr_Update_Fail("Curr_Update_Fail", "Currency failed to update"),
	Curr_Data_By_Id_Fail("Curr_Data_By_Id_Fail","Failed to find Currency by Id");
	private String tag;
	private String message;
	
	private CurrencyTags() {

	}

	CurrencyTags(String tag, String message) {
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
