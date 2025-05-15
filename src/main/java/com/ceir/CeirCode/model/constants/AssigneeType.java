package com.ceir.CeirCode.model.constants;

public enum AssigneeType {

	NAME(1, "name"), CONTACT(2, "contact"),EMAIL(3, "email");
	private Integer code;
	private String description;

	AssigneeType(Integer code, String description) {
		this.code = code;
		this.description = description; 
	}       

	public Integer getCode() {
		return code;
	}
            
	public String getDescription() {
		return description;
	}
	

	public static AssigneeType getByCode(Integer code) {
		for (AssigneeType approveStatus : AssigneeType.values()) {
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
