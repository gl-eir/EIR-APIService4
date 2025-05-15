package com.ceir.CeirCode.model.constants;

public enum ApproveStatus {
	APPROVED(0, "Approved"), REJECTED(1, "Rejected");
	private Integer code;
	private String description;

	ApproveStatus(Integer code, String description) {
		this.code = code;
		this.description = description; 
	}       

	public Integer getCode() {
		return code;
	}
            
	public String getDescription() {
		return description;
	}
	

	public static ApproveStatus getUserStatusByCode(Integer code) {
		for (ApproveStatus approveStatus : ApproveStatus.values()) {
			if (approveStatus.getCode() == code)
				return approveStatus;
		}

		return null;
	}
	
	public static ApproveStatus getUserStatusByDesc(String desc) {
		for (ApproveStatus approveStatus : ApproveStatus.values()) {
			if (approveStatus.getDescription().equals(desc))
				return approveStatus;
		}

		return null;
	}
}
