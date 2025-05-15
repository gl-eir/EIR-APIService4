package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class UserTypeFile {
	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "User Type")
	@CsvBindByPosition(position = 2)
	private String usertypeName;
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 3)
	private String statusInterp;

	public String getCreatedOn() {
		return createdOn;
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

	public String getUsertypeName() {
		return usertypeName;
	}

	public void setUsertypeName(String usertypeName) {
		this.usertypeName = usertypeName;
	}

	public String getStatusInterp() {
		return statusInterp;
	}

	public void setStatusInterp(String statusInterp) {
		this.statusInterp = statusInterp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserTypeFile [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", usertypeName=");
		builder.append(usertypeName);
		builder.append(", statusInterp=");
		builder.append(statusInterp);
		builder.append("]");
		return builder.toString();
	}
	
	

}
