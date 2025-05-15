package com.ceir.CeirCode.model.constants;

public enum UsertypeData {

	
	Importer(4, "Importer"), Distributor(5, "Distributor"),Retailer(6, "Retailer"),
	Custom(7, "Custom"),CEIRAdmin(8, "CEIRAdmin"),Operator(9, "Operator"),
	TRC(10, "TRC"),Manufacturer(12, "Manufacturer"),SystemAdmin(13, "SystemAdmin"),
	Lawful_Agency(14, "Lawful Agency"),End_User(17, "End User"),Immigration(18, "Immigration"),
	Anonymous(19, "Anonymous"),Customer_Care(20, "Customer Care"),DRT(21, "DRT"),
	Operation(22,"Operation"),Reporting(23,"Reporting"),NOC(24,"NOC")
	;
	private Integer code;
	private String description;

	UsertypeData(Integer code, String description) {
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
