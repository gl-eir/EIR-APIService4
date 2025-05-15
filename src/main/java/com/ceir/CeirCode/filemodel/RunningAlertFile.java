package com.ceir.CeirCode.filemodel;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;


public class RunningAlertFile {
	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Alert ID")
	@CsvBindByPosition(position = 1)
	private String alertId;
	
	@CsvBindByName(column = "Description")
	@CsvBindByPosition(position = 2)
	private String description;
	
	//@CsvBindByName(column = "Status")
	//@CsvBindByPosition(position = 3)
	private String status;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RunningAlertFile [createdOn=");
		builder.append(createdOn);
		builder.append(", alertId=");
		builder.append(alertId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}