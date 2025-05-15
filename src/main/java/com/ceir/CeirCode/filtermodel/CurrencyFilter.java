package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class CurrencyFilter extends AllRequest{

	public String  startDate;
	public String   endDate;
	private Integer currency;
	private Integer month; 
	private Double riel;
	private Double dollar;
	private String searchString;
	private String year;
	public String order,orderColumnName;
	
	
	
	
	public String getStartDate() {
		return startDate;
	}




	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}




	public String getEndDate() {
		return endDate;
	}




	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}




	public Integer getCurrency() {
		return currency;
	}




	public void setCurrency(Integer currency) {
		this.currency = currency;
	}




	public Integer getMonth() {
		return month;
	}




	public void setMonth(Integer month) {
		this.month = month;
	}




	public Double getRiel() {
		return riel;
	}




	public void setRiel(Double riel) {
		this.riel = riel;
	}




	public Double getDollar() {
		return dollar;
	}




	public void setDollar(Double dollar) {
		this.dollar = dollar;
	}




	public String getSearchString() {
		return searchString;
	}




	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public String getOrder() {
		return order;
	}




	public void setOrder(String order) {
		this.order = order;
	}




	public String getOrderColumnName() {
		return orderColumnName;
	}




	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CurrencyFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", month=");
		builder.append(month);
		builder.append(", riel=");
		builder.append(riel);
		builder.append(", dollar=");
		builder.append(dollar);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", year=");
		builder.append(year);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append("]");
		return builder.toString();
	}

}

