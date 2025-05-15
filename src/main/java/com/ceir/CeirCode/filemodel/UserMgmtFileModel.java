package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class UserMgmtFileModel {
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "User ID")
	@CsvBindByPosition(position = 2)
	private String username;
	@CsvBindByName(column = "Email")
	@CsvBindByPosition(position = 3)
	private String email;
	@CsvBindByName(column = "Phone No.")
	@CsvBindByPosition(position = 4)
	private String phoneNo;
	@CsvBindByName(column = "User Type")
	@CsvBindByPosition(position = 5)
	private String usertypeName;
	public String getCreatedOn() {
		return createdOn;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMgmtFileModel [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", username=");
		builder.append(username);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", usertypeName=");
		builder.append(usertypeName);
		builder.append("]");
		return builder.toString();
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUsertypeName() {
		return usertypeName;
	}
	public void setUsertypeName(String usertypeName) {
		this.usertypeName = usertypeName;
	} 
	
}
