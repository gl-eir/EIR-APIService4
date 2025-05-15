package com.ceir.CeirCode.model.constants;

public enum UserTypeStatusFlag {

	on(1, "on"), off(0, "off");
	private Integer code;
	private String description;

	UserTypeStatusFlag(Integer code, String description) {
		this.code = code;
		this.description = description; 
	}       

	public Integer getCode() {
		return code;
	}
            
	public String getDescription() {
		return description;
	}
	

	public static UserTypeStatusFlag getByCode(Integer code) {
		for (UserTypeStatusFlag approveStatus : UserTypeStatusFlag.values()) {
			if (approveStatus.getCode() == code)
				return approveStatus;
		}

		return null;
	}
	
	public static AssigneeType getByDesc(String desc) {
		for (AssigneeType approveStatus : AssigneeType.values()) {
			if (approveStatus.getDescription().equals(desc))
				return approveStatus;
		}

		return null;
	}
}
