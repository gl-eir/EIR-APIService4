package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class SlaFile {
	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "User ID")
	@CsvBindByPosition(position = 1)
	private String username;
	
	@CsvBindByName(column = "Transaction ID")
	@CsvBindByPosition(position = 2)
	private String transactionId;
	
	@CsvBindByName(column = "User Type")
	@CsvBindByPosition(position = 3)
	private String usertype;
	
	@CsvBindByName(column = "Feature Name")
	@CsvBindByPosition(position = 4)
	private String feature;
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 5)
	private String state;
	
	private String modifiedOn;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SlaFile [createdOn=");
		builder.append(createdOn);
		builder.append(", username=");
		builder.append(username);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", usertype=");
		builder.append(usertype);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", state=");
		builder.append(state);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append("]");
		return builder.toString();
	}
	
}
