package com.ceir.CeirCode.model.app;

public class UploadStockAssigneModal {

	public UploadStockAssigneModal(String assigneId, String assigneName, String contactNumber, String emailId) {
		super();
		this.assigneId = assigneId;
		this.assigneName = assigneName;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
	}


	private String assigneId,assigneName,contactNumber,emailId;

	public String getAssigneId() {
		return assigneId;
	}

	public void setAssigneId(String assigneId) {
		this.assigneId = assigneId;
	}

	public String getAssigneName() {
		return assigneName;
	}

	public void setAssigneName(String assigneName) {
		this.assigneName = assigneName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadStockAssigneModal [assigneId=");
		builder.append(assigneId);
		builder.append(", assigneName=");
		builder.append(assigneName);
		builder.append(", contactNumber=");
		builder.append(contactNumber);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append("]");
		return builder.toString();
	}
}
