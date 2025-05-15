package com.ceir.CeirCode.filemodel;


import org.springframework.format.annotation.DateTimeFormat;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;


public class ReqHeaderFile {
	@CsvBindByName(column = "Created On")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "User ID")
	@CsvBindByPosition(position = 1)
	private String username;
	
	@CsvBindByName(column = "Public IP")
	@CsvBindByPosition(position = 2)
	private String publicIp;
	
	
	@CsvBindByName(column = "Browser")
	@CsvBindByPosition(position = 3)
	private String browser;


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReqHeaderFile [createdOn=");
		builder.append(createdOn);
		builder.append(", username=");
		builder.append(username);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append(", browser=");
		builder.append(browser);
		builder.append("]");
		return builder.toString();
	}


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


	public String getPublicIp() {
		return publicIp;
	}


	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}


	public String getBrowser() {
		return browser;
	}


	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	/*
	 * @CsvBindByName(column = "User Agent")
	 * 
	 * @CsvBindByPosition(position = 4) private String userAgent;
	 */

	

	
}
