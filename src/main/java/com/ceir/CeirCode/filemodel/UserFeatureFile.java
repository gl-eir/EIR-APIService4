package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class UserFeatureFile {
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "User Type")
	@CsvBindByPosition(position = 2)
	private String usertypeInterp;
	
	@CsvBindByName(column = "Feature")
	@CsvBindByPosition(position = 3)
	private String featureInterp;

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

	public String getUsertypeInterp() {
		return usertypeInterp;
	}

	public void setUsertypeInterp(String usertypeInterp) {
		this.usertypeInterp = usertypeInterp;
	}

	public String getFeatureInterp() {
		return featureInterp;
	}

	public void setFeatureInterp(String featureInterp) {
		this.featureInterp = featureInterp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserFeatureFile [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", usertypeInterp=");
		builder.append(usertypeInterp);
		builder.append(", featureInterp=");
		builder.append(featureInterp);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	//@CsvBindByName(column = "Period")
	//@CsvBindByPosition(position = 4)
	//private String periodInterp;

	
	
	
}
