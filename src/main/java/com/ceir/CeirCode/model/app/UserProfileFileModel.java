package com.ceir.CeirCode.model.app;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class UserProfileFileModel {
    
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String requestedOn;
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "User ID")
	@CsvBindByPosition(position = 2)
	private String userID;
	
	
	@CsvBindByName(column = "Email")
	@CsvBindByPosition(position = 3)
	private String email;
	
	@CsvBindByName(column = "Phone No.")
	@CsvBindByPosition(position = 4)
	private String phone;
	
	@CsvBindByName(column = "Type")
	@CsvBindByPosition(position = 5)
	private String type;
	
	@CsvBindByName(column = "User Type")
	@CsvBindByPosition(position = 6)
	private String userType;
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 7)
	private String status;
	
	
	@CsvBindByName(column = "Approved By")
	@CsvBindByPosition(position = 8)
	private String approvedBy;
	
	
	public String getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfileFileModel [requestedOn=");
		builder.append(requestedOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", userID=");
		builder.append(userID);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", type=");
		builder.append(type);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", status=");
		builder.append(status);
		builder.append(", approvedBy=");
		builder.append(approvedBy);
		builder.append("]");
		return builder.toString();
	}


	
	
}
