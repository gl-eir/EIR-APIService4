package com.ceir.CeirCode.filemodel;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ExchangeRateFile {
	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "Month")
	@CsvBindByPosition(position = 2)
	private String monthInterp;
	
	@CsvBindByName(column = "Year")
	@CsvBindByPosition(position = 3)
	private Integer year;
	
	@CsvBindByName(column = "Currency")
	@CsvBindByPosition(position = 4)
	private String currencyInterp;
	
	@CsvBindByName(column = "Cambodian Riel")
	@CsvBindByPosition(position = 5)
	private double riel;
	
	@CsvBindByName(column = "US Dollar")
	@CsvBindByPosition(position = 6)
	private double dollar;

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

	public String getMonthInterp() {
		return monthInterp;
	}

	public void setMonthInterp(String monthInterp) {
		this.monthInterp = monthInterp;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getCurrencyInterp() {
		return currencyInterp;
	}

	public void setCurrencyInterp(String currencyInterp) {
		this.currencyInterp = currencyInterp;
	}

	public double getRiel() {
		return riel;
	}

	public void setRiel(double riel) {
		this.riel = riel;
	}

	public double getDollar() {
		return dollar;
	}

	public void setDollar(double dollar) {
		this.dollar = dollar;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeRateFile [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", monthInterp=");
		builder.append(monthInterp);
		builder.append(", year=");
		builder.append(year);
		builder.append(", currencyInterp=");
		builder.append(currencyInterp);
		builder.append(", riel=");
		builder.append(riel);
		builder.append(", dollar=");
		builder.append(dollar);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
