package com.ceir.CeirCode.filemodel;



import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;


public class LinkModel {


	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;

	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;

	@CsvBindByName(column = "linkId")
	@CsvBindByPosition(position = 2)
	private String linkId;

	@CsvBindByName(column = "status")
	@CsvBindByPosition(position = 3)
	private String status;

	// Getters and Setters

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

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
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
		builder.append("UserMgmtFileModel [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", linkId=");
		builder.append(linkId);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
