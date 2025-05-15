package com.ceir.CeirCode.response.tags;

public enum UserRoleTags {
	Role_Add_Not_Found("Role_Add_Not_Found", "No Roles found to add"),
	Role_Del_Not_Found("Role_Del_Not_Found", "No Roles found to delete"),
    Add_Role_Not_Allowed("Add_Role_Not_Allowed","Add Role feature for this usertype is not available"),
    Del_Role_Not_Allowed("Del_Role_Not_Allowed","Delete Role feature for this usertype is not available"),
	Invalid_Action("Invalid_Action", "Action is invalid");
	private String tag;
	private String message;
	
	private UserRoleTags() {

	}

	UserRoleTags(String tag, String message) {
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
