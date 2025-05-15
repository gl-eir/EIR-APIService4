package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CustomPortFile {
	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "Address")
	@CsvBindByPosition(position = 2)
	private String address;
	
	@CsvBindByName(column = "Port Type")
	@CsvBindByPosition(position = 3)
	private String portInterp;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPortInterp() {
		return portInterp;
	}

	public void setPortInterp(String portInterp) {
		this.portInterp = portInterp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomPortFile [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", address=");
		builder.append(address);
		builder.append(", portInterp=");
		builder.append(portInterp);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
