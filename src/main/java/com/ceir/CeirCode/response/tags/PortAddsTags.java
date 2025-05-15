package com.ceir.CeirCode.response.tags;

public enum PortAddsTags {

	PAdd_Save_Sucess("PAdd_Save_Sucess", "Port Address has been sucessfully saved"),
	PAdd_Wrong_PORT_ID("PAdd_Wrong_Port_Id", "Port Address is wrong"),
	PAdd_Save_Fail("PAdd_Save_Fail", "Port Address failed to save"),
	PAdd_Update_Sucess ("PAdd_Update_Sucess", "Port Address has been sucessfully update"),
	PAdd_Update_Fail("PAdd_Update_Fail", "Port Address failed to update"),
	PAdd_Data_By_Id_Fail("PAdd_Data_By_Id_Fail","Failed to find find address by Id"),
	PAdd_Del_Sucess("PAdd_Del_Sucess","Port Address has been sucessfully delete"),
	PAdd_Del_Fail("PAdd_Del_Fail","Port Address data failed to delete");
	private String tag;
	private String message;
	
	private PortAddsTags() {

	}

	PortAddsTags(String tag, String message) {
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
